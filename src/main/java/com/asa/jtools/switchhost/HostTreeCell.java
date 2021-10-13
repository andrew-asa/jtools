package com.asa.jtools.switchhost;

import com.asa.base.utils.StringUtils;
import com.asa.jtools.base.utils.FontIconUtils;
import com.asa.jtools.switchhost.bean.HostItem;
import com.jfoenix.controls.JFXButton;
import javafx.beans.InvalidationListener;
import javafx.beans.WeakInvalidationListener;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import org.kordamp.ikonli.fontawesome.FontAwesome;

import java.lang.ref.WeakReference;

/**
 * @author andrew_asa
 * @date 2021/10/12.
 */
public class HostTreeCell extends TreeCell<HostItem> {

    private TreeView<HostItem> treeView;

    private BorderPane pane;

    private HBox editBox;

    private SwitchHostService switchHostService;

    private InvalidationListener treeItemGraphicInvalidationListener = observable -> updateDisplay(getItem(),
                                                                                                   isEmpty());

    private WeakInvalidationListener weakTreeItemGraphicListener = new WeakInvalidationListener(
            treeItemGraphicInvalidationListener);

    private WeakReference<TreeItem<HostItem>> treeItemRef;

    public HostTreeCell(SwitchHostService switchHostService, TreeView<HostItem> treeView) {

        this.switchHostService = switchHostService;
        this.treeView = treeView;
        final InvalidationListener treeItemInvalidationListener = observable -> {
            TreeItem<HostItem> oldTreeItem = treeItemRef == null ? null : treeItemRef.get();
            if (oldTreeItem != null) {
                oldTreeItem.graphicProperty().removeListener(weakTreeItemGraphicListener);
            }

            TreeItem<HostItem> newTreeItem = getTreeItem();
            if (newTreeItem != null) {
                newTreeItem.graphicProperty().addListener(weakTreeItemGraphicListener);
                treeItemRef = new WeakReference<>(newTreeItem);
            }
        };
        final WeakInvalidationListener weakTreeItemListener = new WeakInvalidationListener(treeItemInvalidationListener);
        treeItemProperty().addListener(weakTreeItemListener);
        if (getTreeItem() != null) {
            getTreeItem().graphicProperty().addListener(weakTreeItemGraphicListener);
        }
    }

    private void updateDisplay(HostItem item, boolean empty) {

        if (item == null || empty) {
            pane = null;
            editBox = null;
            setText(null);
            setGraphic(null);
        } else {
            if (pane == null) {
                pane = new BorderPane();
            }
            TreeItem<HostItem> treeItem = getTreeItem();
            HostItem hostItem = treeItem.getValue();
            HBox display = customShowBox(treeItem);
            pane.setLeft(display);
            editBox = customEditButton(hostItem);
            if (editBox != null) {
                pane.setRight(editBox);
            }
            customContextMenu(hostItem);
            //pane.setBorder(new Border(new BorderStroke(Color.valueOf("#9E9E9E"),
            //                                           BorderStrokeStyle.SOLID,
            //                                           CornerRadii.EMPTY,
            //                                           BorderWidths.DEFAULT)));
            // 叶子节点移动上去添加删除按钮
            if (treeItem.isLeaf()) {
                pane.setOnMouseEntered(new EventHandler<MouseEvent>() {

                    @Override
                    public void handle(MouseEvent event) {

                        if (editBox != null && editBox.getChildren() != null && editBox.getChildren().size() == 1) {
                            Button edit = FontIconUtils.createIconButton(FontAwesome.EDIT, 16);
                            edit.setPrefWidth(30);
                            editBox.getChildren().add(0, edit);
                        }
                        // TODO 编辑事件
                        //System.out.println("进入");
                    }
                });
                pane.setOnMouseExited(new EventHandler<MouseEvent>() {

                    @Override
                    public void handle(MouseEvent event) {
                        //System.out.println("退出");
                        if (editBox != null && editBox.getChildren() != null && editBox.getChildren().size() == 2) {
                            editBox.getChildren().remove(0);
                        }
                    }
                });
                pane.setOnMouseClicked(new EventHandler<MouseEvent>() {

                    @Override
                    public void handle(MouseEvent event) {

                        //System.out.println("点击了");
                    }
                });

            }
            setGraphic(pane);
        }
    }

    private void customContextMenu(HostItem hostItem) {

        HostItem.HostType type = hostItem.getType();
        if (HostItem.HostType.NET.equals(type)) {
            ContextMenu contextMenu = new ContextMenu();
            MenuItem refresh = new MenuItem("刷新");
            refresh.setOnAction(event -> {
                System.out.println("刷新");
                // TODO
            });
            contextMenu.getItems().add(refresh);
            setContextMenu(contextMenu);
        }
    }

    private HBox customShowBox(TreeItem<HostItem> treeItem) {

        HBox display = new HBox();
        Node graphic = treeItem.getGraphic();
        HostItem hostItem = treeItem.getValue();
        if (graphic != null) {
            display.getChildren().add(graphic);

        }
        String name = hostItem.getName();
        if (StringUtils.isNotEmpty(name)) {
            Button button = new JFXButton(name);
            display.getChildren().add(button);
        }
        for (Node n : display.getChildren()) {
            if (n instanceof Button) {
                ((Button) n).setOnAction(e -> {
                    treeView.getSelectionModel().select(treeItem);
                });
            }
        }
        return display;
    }

    private HBox customEditButton(HostItem hostItem) {

        HBox hBox = new HBox();
        HostItem.HostType type = hostItem.getType();
        if (type == null) {
            return null;
        }
        String id = hostItem.getId();
        Button button;
        if (StringUtils.equals(id, switchHostService.getCurrentHostsId())) {
            button = FontIconUtils.createIconButton(FontAwesome.TOGGLE_ON, 16, Color.GREEN);
        } else {
            button = FontIconUtils.createIconButton(FontAwesome.TOGGLE_OFF, 16);
        }
        // TODO
        button.setPrefWidth(30);
        hBox.getChildren().add(button);
        return hBox;
    }

    @Override
    protected void updateItem(HostItem item, boolean empty) {

        super.updateItem(item, empty);
        updateDisplay(item, empty);
    }
}
