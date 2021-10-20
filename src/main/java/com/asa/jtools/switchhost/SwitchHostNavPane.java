package com.asa.jtools.switchhost;

import com.asa.base.log.LoggerFactory;
import com.asa.base.ui.controls.button.JButton;
import com.asa.base.utils.ListUtils;
import com.asa.jtools.base.utils.FontIconUtils;
import com.asa.jtools.switchhost.bean.HostItem;
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

    private SwitchHostService dbService;

    public SwitchHostNavPane(StackPane rootStackPane, SwitchHostService dbService) {

        super();
        init(rootStackPane, dbService);
    }

    private void init(StackPane rootStackPane, SwitchHostService dbService) {

        this.rootStackPane = rootStackPane;
        this.dbService = dbService;
        this.treeView = getTreeView();
        HBox bottom = createOperation();
        setBottom(bottom);
        setCenter(treeView);
    }

    private TreeView getTreeView() {

        treeView = new TreeView<>();
        treeView.setCellFactory((TreeView<HostItem> p) -> new HostTreeCell(dbService, treeView));
        treeView.addEventHandler(SwitchHostEvent.SWITCH_HOST_REMOVE_EVENT, e -> {
           removeHostItem(e.getValue());
        });
        TreeItem<HostItem> root = getRootItem(dbService);
        treeView.setRoot(root);
        treeView.setShowRoot(false);
        root.setExpanded(true);
        return treeView;
    }

    private TreeItem<HostItem> getRootItem(SwitchHostService dbService) {

        HostItem rootItem = new HostItem();
        rootItem.setName("Root");
        TreeItem<HostItem> root = new TreeItem<HostItem>(rootItem);
        TreeItem<HostItem> netRootTreeItem = getNetRootTreeItem(dbService);
        TreeItem<HostItem> localRootTreeItem = geLocalRootTreeItem(dbService);
        root.getChildren().addAll(localRootTreeItem, netRootTreeItem);
        return root;
    }

    private TreeItem<HostItem> geLocalRootTreeItem(SwitchHostService dbService) {
        // 系统hosts
        HostItem localItem = new HostItem();
        localItem.setName(SwitchHostConstant.LOCAL);
        localItem.setType(HostItem.HostType.LOCAL);
        localItem.setParent(true);
        TreeItem<HostItem> systemHost = createTreeParentItem(localItem, FontAwesome.DESKTOP, 16);
        List<HostItem> items = dbService.getItems(HostItem.HostType.LOCAL);
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

    private TreeItem<HostItem> getNetRootTreeItem(SwitchHostService dbService) {
        // 网络hosts
        HostItem netItem = new HostItem();
        netItem.setType(HostItem.HostType.NET);
        netItem.setParent(true);
        netItem.setName(SwitchHostConstant.REMOTE);
        TreeItem<HostItem> netHost = createTreeParentItem(netItem, FontAwesome.GLOBE, 18);
        List<HostItem> items = dbService.getItems(HostItem.HostType.NET);
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
                //TreeItem<HostItem> p = hostTreeItem.getParent();
                //p.getChildren().remove(hostTreeItem);
                HostItem hostItem = hostTreeItem.getValue();
                if (!hostItem.isParent()) {
                    removeHostItem(hostItem);
                }
            }
        });
        return remove;
    }

    private void removeHostItem(HostItem hostItem) {

        LoggerFactory.getLogger().debug("remove host item {}",hostItem);
        SwitchHostEvent addEvent = new SwitchHostEvent(SwitchHostNavPane.this, SwitchHostEvent.SWITCH_HOST_REMOVE_EVENT, hostItem);
        fireEvent(addEvent);
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
                HostItem.HostType hostType = item.getType();
                TreeItem<HostItem> parent = getTreeParentItemByType(hostType);
                if (parent != null) {
                    parent.getChildren().add(createTreeLeafItem(item));
                }
                SwitchHostEvent addEvent = new SwitchHostEvent(SwitchHostNavPane.this, SwitchHostEvent.SWITCH_HOST_ADD_EVENT, item);
                fireEvent(addEvent);
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
