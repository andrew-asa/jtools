package com.asa.jtools.switchhost;

import com.asa.base.log.LoggerFactory;
import com.asa.base.ui.controls.button.JButton;
import com.asa.base.utils.ListUtils;
import com.asa.base.utils.StringUtils;
import com.asa.jtools.base.utils.FontIconUtils;
import com.asa.jtools.base.utils.RandomStringUtils;
import com.asa.jtools.switchhost.bean.HostItem;
import com.asa.jtools.switchhost.bean.HostItems;
import com.asa.jtools.switchhost.constant.SwitchHostConstant;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
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

    //private SwitchHostService dbService;

    public SwitchHostNavPane(StackPane rootStackPane, HostItems treeItems) {

        super();
        init(rootStackPane, treeItems);
    }

    private void init(StackPane rootStackPane, HostItems items) {

        this.rootStackPane = rootStackPane;
        this.treeItems = items;
        //this.dbService = dbService;
        this.treeView = getTreeView();
        HBox bottom = createOperation();
        setBottom(bottom);
        setCenter(treeView);
    }

    private TreeView getTreeView() {

        treeView = new TreeView<>();
        treeView.setCellFactory((TreeView<HostItem> p) -> new HostTreeCell(treeView));
        treeView.addEventHandler(SwitchHostEvent.SWITCH_HOST_REMOVE_EVENT, e -> {
            e.consume();
            removeTreeItem(e.getValue());
        });
        TreeItem<HostItem> root = getRootItem(treeItems);
        treeView.setRoot(root);
        treeView.setShowRoot(false);
        root.setExpanded(true);
        return treeView;
    }

    private TreeItem<HostItem> getRootItem(HostItems treeItems) {

        HostItem rootItem = new HostItem();
        rootItem.setName("Root");
        TreeItem<HostItem> root = new TreeItem<HostItem>(rootItem);
        TreeItem<HostItem> netRootTreeItem = getNetRootTreeItem(treeItems);
        TreeItem<HostItem> localRootTreeItem = geLocalRootTreeItem(treeItems);
        root.getChildren().addAll(localRootTreeItem, netRootTreeItem);
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

    private TreeItem<HostItem> getNetRootTreeItem(HostItems treeItems) {
        // 网络hosts
        HostItem netItem = new HostItem();
        netItem.setType(HostItem.HostType.NET);
        netItem.setParent(true);
        netItem.setName(SwitchHostConstant.REMOTE);
        TreeItem<HostItem> netHost = createTreeParentItem(netItem, FontAwesome.GLOBE, 18);
        List<HostItem> items = treeItems.getItems(HostItem.HostType.NET);
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
            if (hostTreeItem != null) {
                removeTreeItem(hostTreeItem);
            }
        });
        return remove;
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

    private Button createAddButton() {

        Button add = FontIconUtils.createIconButton(FontAwesome.PLUS, 14);
        add.setOnAction(event -> {
            JFXDialogLayout content = new JFXDialogLayout();
            content.setHeading(new Text("添加hosts规则"));
            SwitchHostAddPane switchHostAddPane = new SwitchHostAddPane();
            content.setBody(switchHostAddPane);
            content.setPrefSize(600, 400);
            JFXDialog dialog = new JFXDialog(rootStackPane, content, JFXDialog.DialogTransition.LEFT, true);
            JButton cancel = new JButton("取消");
            JButton sure = new JButton("确定");
            sure.setOnAction(e -> {
                HostItem item = switchHostAddPane.getItem();
                addTreeItem(item);
                dialog.close();
            });
            cancel.setOnAction(e -> {
                dialog.close();
            });
            cancel.setButtonLevel(JButton.ButtonLevel.IGNORE);
            sure.setButtonLevel(JButton.ButtonLevel.SUCCESS);
            content.setActions(cancel, sure);
            dialog.show();
        });
        return add;
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
