package com.asa.jtools.switchhost;

import com.asa.base.utils.StringUtils;
import com.asa.jtools.base.utils.FontIconUtils;
import com.asa.jtools.switchhost.bean.HostItem;
import com.jfoenix.controls.JFXButton;
import javafx.beans.InvalidationListener;
import javafx.beans.WeakInvalidationListener;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
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

    private HBox operateBox;

    //private SwitchHostService switchHostService;

    private InvalidationListener treeItemGraphicInvalidationListener = observable -> updateDisplay(getItem(),
                                                                                                   isEmpty());

    private WeakInvalidationListener weakTreeItemGraphicListener = new WeakInvalidationListener(
            treeItemGraphicInvalidationListener);

    private WeakReference<TreeItem<HostItem>> treeItemRef;

    public HostTreeCell(TreeView<HostItem> treeView) {

        //this.switchHostService = switchHostService;
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
            operateBox = null;
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


            customContextMenu(hostItem);
            // 叶子节点移动上去添加删除,编辑按钮
            if (!hostItem.isParent()) {
                operateBox = customOperationBox(hostItem);
                HBox editBox = customEditButton(hostItem);
                pane.setRight(operateBox);
                pane.setOnMouseEntered(e -> {
                    if (operateBox != null) {
                        operateBox.getChildren().add(0, editBox);
                    }
                });
                pane.setOnMouseExited(event -> {
                    if (operateBox != null) {
                        operateBox.getChildren().remove(editBox);
                    }
                });
            }
            setGraphic(pane);
        }
    }

    private void customContextMenu(HostItem hostItem) {

        HostItem.HostType type = hostItem.getType();
        if (!hostItem.isParent() && HostItem.HostType.NET.equals(type)) {
            ContextMenu contextMenu = new ContextMenu();
            MenuItem refresh = new MenuItem("刷新");
            refresh.setOnAction(event -> {
                System.out.println("刷新");
            });
            contextMenu.getItems().add(refresh);
            setContextMenu(contextMenu);
        } else if (hostItem.isParent()) {
            ContextMenu contextMenu = new ContextMenu();
            MenuItem add = new MenuItem("添加子节点");
            add.setOnAction(event -> {
                addChild(hostItem);
            });
            contextMenu.getItems().add(add);
            setContextMenu(contextMenu);
        }
    }

    private void addChild(HostItem hostItem) {

        SwitchHostEvent addEvent = new SwitchHostEvent(HostTreeCell.this, SwitchHostEvent.SWITCH_HOST_ADD_CHILD_EVENT, hostItem);
        fireEvent(addEvent);
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

    private HBox customOperationBox(HostItem hostItem) {

        HBox hBox = new HBox();
        if (!hostItem.isParent()) {
            boolean apply = hostItem.isApply();
            Button button;
            if (apply) {
                button = FontIconUtils.createIconButton(FontAwesome.TOGGLE_ON, Color.GREEN);
                button.setOnAction(e -> {

                });
            } else {
                button = FontIconUtils.createIconButton(FontAwesome.TOGGLE_OFF);
            }
            button.setPrefWidth(30);
            hBox.getChildren().addAll(button);
        }
        return hBox;
    }

    private HBox customEditButton(HostItem hostItem) {

        HBox hBox = new HBox();
        Button edit = FontIconUtils.createIconButton(FontAwesome.EDIT);
        Button delete = FontIconUtils.createIconButton(FontAwesome.TRASH);
        delete.setOnAction(e -> {
            SwitchHostEvent addEvent = new SwitchHostEvent(HostTreeCell.this, SwitchHostEvent.SWITCH_HOST_REMOVE_EVENT, hostItem);
            fireEvent(addEvent);
        });
        edit.setPrefWidth(30);
        hBox.getChildren().addAll(delete, edit);
        return hBox;
    }

    @Override
    protected void updateItem(HostItem item, boolean empty) {

        super.updateItem(item, empty);
        updateDisplay(item, empty);
    }
}
