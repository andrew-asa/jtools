package com.asa.jtools.switchhost;

import com.asa.base.ui.controls.button.JButton;
import com.asa.base.utils.ListUtils;
import com.asa.base.utils.StringUtils;
import com.asa.jtools.base.utils.FontIconUtils;
import com.asa.jtools.switchhost.bean.HostItem;
import com.asa.jtools.switchhost.constant.SwitchHostConstant;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import org.kordamp.ikonli.fontawesome.FontAwesome;
import org.kordamp.ikonli.javafx.FontIcon;

import java.util.List;

/**
 * @author andrew_asa
 * @date 2021/10/19.
 */
public class SwitchHostNavPane extends BorderPane {

    private TreeView treeView;

    private StackPane rootStackPane;

    private SwitchHostService dbService;

    public SwitchHostNavPane(StackPane rootStackPane,SwitchHostService dbService) {

        super();
        init(rootStackPane,dbService);
    }

    private void init(StackPane rootStackPane,SwitchHostService dbService) {
        this.rootStackPane = rootStackPane;
        this.dbService =dbService;
        this.treeView = getTreeView();
        HBox bottom = createOperation();
        setBottom(bottom);
        setCenter(treeView);
    }

    private TreeView getTreeView() {

        TreeView<HostItem> treeView = new TreeView<>();
        treeView.setCellFactory((TreeView<HostItem> p) -> new HostTreeCell(dbService, treeView));
        TreeItem<HostItem> root = getRootItem(dbService);
        treeView.setRoot(root);
        treeView.setShowRoot(false);
        root.setExpanded(true);
        return treeView;
    }

    private TreeItem<HostItem> getRootItem(SwitchHostService dbService) {

        HostItem rootItem = new HostItem();
        rootItem.setId("root");
        rootItem.setName("Root");
        TreeItem<HostItem> root = new TreeItem<HostItem>(rootItem);
        TreeItem<HostItem> net = getNetItem(dbService);
        TreeItem<HostItem> local = getSystemItem(dbService);
        root.getChildren().addAll(local, net);
        return root;
    }

    private TreeItem<HostItem> getSystemItem(SwitchHostService dbService) {
        // 系统hosts
        HostItem localItem = new HostItem();
        localItem.setId(SwitchHostConstant.LOCAL);
        localItem.setName(SwitchHostConstant.LOCAL);
        TreeItem<HostItem> systemHost = new TreeItem<HostItem>(localItem, FontIconUtils.createIconButton(FontAwesome.DESKTOP, 16));
        List<HostItem> items = dbService.getItems(HostItem.HostType.LOCAL);
        if (ListUtils.isNotEmpty(items)) {
            for (HostItem item : items) {
                TreeItem<HostItem> itemHost = new TreeItem<HostItem>(item, FontIconUtils.createIconButton(FontAwesome.FILE, 16));
                systemHost.getChildren().add(itemHost);
            }
        }
        systemHost.setExpanded(true);
        return systemHost;
    }

    private TreeItem<HostItem> getNetItem(SwitchHostService dbService) {
        // 网络hosts
        HostItem netItem = new HostItem();
        netItem.setId(SwitchHostConstant.NET);
        netItem.setName(SwitchHostConstant.NET);
        TreeItem<HostItem> netHost = new TreeItem<HostItem>(netItem, FontIconUtils.createIconButton(FontAwesome.GLOBE, 18));
        List<HostItem> items = dbService.getItems(HostItem.HostType.NET);
        if (ListUtils.isNotEmpty(items)) {
            for (HostItem item : items) {
                TreeItem<HostItem> itemHost = new TreeItem<HostItem>(item, FontIconUtils.createIconButton(FontAwesome.FILE, 16));
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
        remove.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {

                TreeItem<HostItem> hostTreeItem = (TreeItem<HostItem>) treeView.getSelectionModel().getSelectedItem();
                if (hostTreeItem != null) {
                    TreeItem<HostItem> p = hostTreeItem.getParent();
                    HostItem hostItem = hostTreeItem.getValue();
                    HostItem.HostType hostType = hostItem.getType();
                    //if (HostItem.HostType.NET.equals(hostType)) {
                    //    p.getChildren().remove(hostTreeItem);
                    //}
                }
            }
        });
        return remove;
    }

    private Button createAddButton() {

        Button add = FontIconUtils.createIconButton(FontAwesome.PLUS, 14);
        //
        add.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {

                //JFXTextArea textArea = new JFXTextArea("添加hosts规则");

                JFXDialogLayout content = new JFXDialogLayout();
                content.setHeading(new Text("添加hosts规则"));
                SwitchHostAddPane switchHostAddPane = new SwitchHostAddPane();
                content.setBody(switchHostAddPane);
                content.setPrefSize(600, 400);
                JFXDialog dialog = new JFXDialog(rootStackPane, content, JFXDialog.DialogTransition.LEFT, true);
                JButton cancel = new JButton("取消");
                JButton sure = new JButton("确定");
                sure.setOnAction(e -> {
                    switchHostAddPane.saveAdd();
                    dialog.close();
                });
                cancel.setOnAction(e -> {
                    dialog.close();
                });
                cancel.setButtonLevel(JButton.ButtonLevel.IGNORE);
                sure.setButtonLevel(JButton.ButtonLevel.SUCCESS);
                content.setActions(cancel, sure);
                dialog.show();
            }
        });
        return add;
    }

    private TreeItem<HostItem> getTreeRootItemById(String id) {

        ObservableList<TreeItem<HostItem>> childrens = treeView.getRoot().getChildren();
        if (ListUtils.isNotEmpty(childrens)) {
            for (TreeItem<HostItem> item : childrens) {
                if (StringUtils.equals(item.getValue().getId(), id)) {
                    return item;
                }
            }
        }
        return null;
    }
}
