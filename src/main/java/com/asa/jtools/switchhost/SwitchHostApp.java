package com.asa.jtools.switchhost;

import com.asa.base.utils.ListUtils;
import com.asa.base.utils.StringUtils;
import com.asa.jtools.base.utils.RandomStringUtils;
import com.asa.jtools.switchhost.bean.HostItem;
import com.asa.jtools.switchhost.constant.SwitchHostConstant;
import com.asa.third.org.apache.commons.lang3.RandomUtils;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import org.jsoup.internal.StringUtil;
import org.kordamp.ikonli.fontawesome.FontAwesome;
import org.kordamp.ikonli.javafx.FontIcon;

import java.util.List;
import java.util.UUID;


/**
 * @author andrew_asa
 * @date 2021/10/5.
 */
public class SwitchHostApp extends Application {

    private TreeView treeView;

    private SwitchHostService dbService;

    @Override
    public void start(Stage stage) {

        treeView = getTreeView();
        HBox bottom = createOperation();
        HBox right = createShowPanel();
        BorderPane borderPane = new BorderPane();
        borderPane.setBottom(bottom);
        borderPane.setCenter(treeView);
        borderPane.setRight(right);
        Scene scene = new Scene(borderPane, 900, 700);
        stage.setScene(scene);
        stage.show();
        stage.setOnCloseRequest(e -> {
            System.exit(0);
        });
    }

    public void init() throws Exception {

        super.init();
        dbService = new SwitchHostService();
        dbService.init();
    }

    private HBox createShowPanel() {

        HBox right = new HBox();
        TextArea textArea = new TextArea();
        // TODO show current hosts
        right.getChildren().add(textArea);
        return right;
    }

    private HBox createOperation() {

        HBox bottom = new HBox();
        Button add = createAddButton();
        Button remove = createRemoveButton();
        bottom.getChildren().addAll(add, remove);
        return bottom;
    }

    private Button createAddButton() {

        Button add = getIconButton(FontAwesome.PLUS, 14);
        //
        add.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {

                JFXDialog dialog = new JFXDialog();
                //dialog.set
                //TreeItem<HostItem> net = getTreeRootItemById(SwitchHostConstant.NET);
                //HostItem r = new HostItem();
                //r.setType(HostItem.HostType.NET);
                //String id = RandomStringUtils.randomAlphabetic(10);
                //r.setId(id);
                //r.setName(id);
                //TreeItem<HostItem> a = new TreeItem<>(r, getIconButton(FontAwesome.FILE, 16));
                //net.getChildren().add(a);
            }
        });
        return add;
    }

    private Button createRemoveButton() {

        Button remove = getIconButton(FontAwesome.MINUS, 14);
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

    private TreeView getTreeView() {

        TreeView<HostItem> treeView = new TreeView<>();
        treeView.setCellFactory((TreeView<HostItem> p) -> new HostTreeCell(dbService,treeView));
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
        TreeItem<HostItem> systemHost = new TreeItem<HostItem>(localItem, getIconButton(FontAwesome.DESKTOP, 16));
        List<HostItem> items = dbService.getItems(HostItem.HostType.LOCAL);
        if (ListUtils.isNotEmpty(items)) {
            for (HostItem item : items) {
                TreeItem<HostItem> itemHost = new TreeItem<HostItem>(item, getIconButton(FontAwesome.FILE, 16));
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
        TreeItem<HostItem> netHost = new TreeItem<HostItem>(netItem, getIconButton(FontAwesome.GLOBE, 18));
        List<HostItem> items = dbService.getItems(HostItem.HostType.NET);
        if (ListUtils.isNotEmpty(items)) {
            for (HostItem item : items) {
                TreeItem<HostItem> itemHost = new TreeItem<HostItem>(item, getIconButton(FontAwesome.FILE, 16));
                netHost.getChildren().add(itemHost);
            }
        }
        netHost.setExpanded(true);
        return netHost;
    }

    private Button getIconButton(FontAwesome des, int iconSize) {

        JFXButton button = new JFXButton();
        button.setGraphic(FontIcon.of(des, iconSize));
        return button;
    }

    public static void main(String[] args) {

        launch(args);
    }

}
