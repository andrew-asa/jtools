package com.asa.jtools.switchhost;

import com.asa.base.log.LoggerFactory;
import com.asa.base.utils.ListUtils;
import com.asa.base.utils.StringUtils;
import com.asa.jtools.base.utils.FontIconUtils;
import com.asa.jtools.base.utils.RandomStringUtils;
import com.asa.jtools.switchhost.bean.HostItem;
import com.asa.jtools.switchhost.bean.HostItems;
import com.asa.jtools.switchhost.constant.SwitchHostConstant;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import org.kordamp.ikonli.Ikon;
import org.kordamp.ikonli.fontawesome.FontAwesome;

import java.util.List;

/**
 * @author andrew_asa
 * @date 2021/10/19.
 */
public class SwitchHostNavPane extends BorderPane {

    private TreeView<HostItem> treeView;

    private StackPane rootStackPane;

    private HostItems treeItems;

    public SwitchHostNavPane(StackPane rootStackPane, HostItems treeItems) {

        super();
        init(rootStackPane, treeItems);
    }

    private void init(StackPane rootStackPane, HostItems items) {

        this.rootStackPane = rootStackPane;
        this.treeItems = items;
        this.treeView = createTreeView(items);
        treeView.addEventHandler(SwitchHostEvent.SWITCH_HOST_ADD_CHILD_EVENT, e -> showAddDialog(e.getValue()));
        HBox bottom = createOperation();
        setBottom(bottom);
        setCenter(treeView);
    }

    private TreeView createTreeView(HostItems treeItems) {

        treeView = new TreeView<>();
        treeView.setCellFactory((TreeView<HostItem> p) -> new HostTreeCell(treeView));
        treeView.addEventHandler(SwitchHostEvent.SWITCH_HOST_REMOVE_EVENT, e -> {
            e.consume();
            removeTreeItem(e.getValue());
        });
        treeView.addEventHandler(SwitchHostEvent.SWITCH_HOST_EDIT_EVENT, e -> {
            e.consume();
            editTreeItem(e.getValue());
        });
        // 开关按钮
        treeView.addEventHandler(SwitchHostEvent.SWITCH_HOST_UPDATE_EVENT, e -> {
            e.consume();
            updateTreeItem(e.getValue(),e.getOldValue());
        });
        treeView.setShowRoot(false);
        setTreeItems(treeItems);
        return treeView;
    }

    public void setTreeItems(HostItems treeItems) {

        TreeItem<HostItem> root = createTreeRoot(treeItems);
        root.setExpanded(true);
        treeView.setRoot(root);
    }

    public HostItems getTreeItems() {

        HostItems items = new HostItems();
        TreeItem<HostItem> root = treeView.getRoot();
        for (TreeItem<HostItem> parent : root.getChildren()) {
            for (TreeItem<HostItem> item : parent.getChildren()) {
                items.addItem(item.getValue());
            }
        }
        return items;
    }


    private TreeItem<HostItem> createTreeRoot(HostItems treeItems) {

        HostItem rootItem = new HostItem();
        rootItem.setName(SwitchHostConstant.ROOT);
        TreeItem<HostItem> root = new TreeItem<HostItem>(rootItem);
        if (treeItems != null) {
            TreeItem<HostItem> netRootTreeItem = getRemoteRootTreeItem(treeItems);
            TreeItem<HostItem> localRootTreeItem = geLocalRootTreeItem(treeItems);
            root.getChildren().addAll(localRootTreeItem, netRootTreeItem);
        }
        return root;
    }

    private TreeItem<HostItem> geLocalRootTreeItem(HostItems treeItems) {
        // 系统hosts
        HostItem localItem = new HostItem();
        localItem.setName(SwitchHostConstant.LOCAL);
        localItem.setType(HostItem.HostType.LOCAL);
        localItem.setParent(true);
        TreeItem<HostItem> systemHost = createTreeParentItem(localItem, FontAwesome.DESKTOP, 16);
        List<HostItem> items = treeItems.getItems(HostItem.HostType.LOCAL);
        if (ListUtils.isNotEmpty(items)) {
            for (HostItem item : items) {
                TreeItem<HostItem> itemHost = createTreeLeafItem(item);
                systemHost.getChildren().add(itemHost);
            }
        }
        systemHost.setExpanded(true);
        return systemHost;
    }

    private TreeItem<HostItem> createTreeParentItem(HostItem item, Ikon ikon, int iconSize) {

        return new TreeItem<HostItem>(item, FontIconUtils.createIconButton(ikon, iconSize));
    }

    private TreeItem<HostItem> createTreeLeafItem(HostItem item) {

        return new TreeItem<HostItem>(item, FontIconUtils.createIconButton(FontAwesome.FILE, 16));
    }

    private TreeItem<HostItem> getRemoteRootTreeItem(HostItems treeItems) {
        // 网络hosts
        HostItem netItem = new HostItem();
        netItem.setType(HostItem.HostType.Remote);
        netItem.setParent(true);
        netItem.setName(SwitchHostConstant.REMOTE);
        TreeItem<HostItem> netHost = createTreeParentItem(netItem, FontAwesome.GLOBE, 18);
        List<HostItem> items = treeItems.getItems(HostItem.HostType.Remote);
        if (ListUtils.isNotEmpty(items)) {
            for (HostItem item : items) {
                TreeItem<HostItem> itemHost = createTreeLeafItem(item);
                netHost.getChildren().add(itemHost);
            }
        }
        netHost.setExpanded(true);
        return netHost;
    }

    private HBox createOperation() {

        HBox bottom = new HBox();
        Button add = createAddButton();
        Button remove = createRemoveButton();
        bottom.getChildren().addAll(add, remove);
        return bottom;
    }

    private Button createRemoveButton() {

        Button remove = FontIconUtils.createIconButton(FontAwesome.MINUS, 14);
        remove.setOnAction(event -> {
            TreeItem<HostItem> hostTreeItem = treeView.getSelectionModel().getSelectedItem();
            if (isHostItemLeaf(hostTreeItem)) {
                removeTreeItem(hostTreeItem);
            }
        });
        return remove;
    }

    private boolean isHostItemLeaf(TreeItem<HostItem> hostTreeItem) {

        return hostTreeItem != null && !hostTreeItem.getValue().isParent();
    }

    /**
     * 删除叶子节点，并生成触发删除事件
     *
     * @param hostTreeItem
     */
    private void removeTreeItem(TreeItem<HostItem> hostTreeItem) {

        if (hostTreeItem == null) {
            return;
        }
        HostItem item = hostTreeItem.getValue();
        if (!item.isParent()) {
            HostItem.HostType hostType = item.getType();
            TreeItem<HostItem> parent = getTreeParentItemByType(hostType);
            if (parent != null) {
                parent.getChildren().remove(hostTreeItem);
            }
        }
        SwitchHostEvent addEvent = new SwitchHostEvent(SwitchHostNavPane.this, SwitchHostEvent.SWITCH_HOST_REMOVE_EVENT, item);
        fireEvent(addEvent);
    }

    private void removeTreeItem(HostItem hostItem) {

        removeTreeItem(getTreeItemById(hostItem.getId()));
    }

    /**
     * 添加树节点，刷新树，同时生成事件进行通知，让父类监听器进行响应的操作
     *
     * @param item
     */
    private void addTreeItem(HostItem item) {

        HostItem.HostType hostType = item.getType();
        TreeItem<HostItem> parent = getTreeParentItemByType(hostType);
        if (parent != null) {
            item.setId(RandomStringUtils.randomAlphabetic(SwitchHostConstant.DEFAULT_ID_LENGTH));
            parent.getChildren().add(createTreeLeafItem(item));
        }
        SwitchHostEvent addEvent = new SwitchHostEvent(SwitchHostNavPane.this, SwitchHostEvent.SWITCH_HOST_ADD_EVENT, item);
        fireEvent(addEvent);
    }

    /**
     * 修改样式
     *
     * @param hostItem
     */
    private void editTreeItem(HostItem hostItem) {

        SwitchHostUpdatePane panel = new SwitchHostUpdatePane();
        panel.addEventHandler(SwitchHostEvent.SWITCH_HOST_UPDATE_EVENT, e -> updateTreeItem(e.getValue(), e.getOldValue()));
        panel.showDialog(rootStackPane, hostItem);
    }

    private void updateTreeItem(HostItem newItem, HostItem oldItem) {
        // 修改树
        TreeItem<HostItem> treeItem = getTreeItemById(oldItem.getId());
        treeItem.setValue(newItem);
       // 继续往上传
        SwitchHostEvent addEvent = new SwitchHostEvent(SwitchHostNavPane.this, SwitchHostEvent.SWITCH_HOST_ADD_EVENT, newItem,oldItem);
        fireEvent(addEvent);
    }


    private Button createAddButton() {

        Button add = FontIconUtils.createIconButton(FontAwesome.PLUS, 14);
        add.setOnAction(event -> {
            showAddDialog();
        });
        return add;
    }

    private void showAddDialog() {

        showAddDialog(null);
    }

    private void showAddDialog(HostItem item) {

        SwitchHostAddPane panel = new SwitchHostAddPane();
        panel.addEventHandler(SwitchHostEvent.SWITCH_HOST_ADD_EVENT, e -> addTreeItem(e.getValue()));
        panel.showDialog(rootStackPane, item);
    }


    private TreeItem<HostItem> getTreeItemById(String id) {

        if (StringUtils.isNotEmpty(id)) {
            ObservableList<TreeItem<HostItem>> childs = treeView.getRoot().getChildren();
            for (TreeItem<HostItem> item : childs) {
                for (TreeItem<HostItem> childItem : item.getChildren()) {
                    HostItem hostItem = childItem.getValue();
                    if (StringUtils.equals(hostItem.getId(), id)) {
                        return childItem;
                    }
                }
            }
        }
        return null;
    }

    private TreeItem<HostItem> getTreeParentItemByType(HostItem.HostType type) {

        ObservableList<TreeItem<HostItem>> childs = treeView.getRoot().getChildren();
        for (TreeItem<HostItem> item : childs) {
            if (type.equals(item.getValue().getType())) {
                return item;
            }
        }
        return null;
    }
}
