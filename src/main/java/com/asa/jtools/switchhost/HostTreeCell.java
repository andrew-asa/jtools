package com.asa.jtools.switchhost;

import com.asa.base.utils.StringUtils;
import com.asa.jtools.base.utils.FontIconUtils;
import com.asa.jtools.switchhost.bean.HostItem;
import com.jfoenix.controls.JFXButton;
import javafx.beans.InvalidationListener;
import javafx.beans.WeakInvalidationListener;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.layout.Background;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import org.kordamp.ikonli.fontawesome.FontAwesome;

import java.lang.ref.WeakReference;

/**
 * @author andrew_asa
 * @date 2021/10/12.
 */
public class HostTreeCell extends TreeCell<HostItem> {


    private BorderPane pane;

    private SwitchHostService switchHostService;

    private InvalidationListener treeItemGraphicInvalidationListener = observable -> updateDisplay(getItem(),
                                                                                                   isEmpty());

    private WeakInvalidationListener weakTreeItemGraphicListener = new WeakInvalidationListener(
            treeItemGraphicInvalidationListener);

    private WeakReference<TreeItem<HostItem>> treeItemRef;

    public HostTreeCell(SwitchHostService switchHostService) {

        this.switchHostService = switchHostService;
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
            setText(null);
            setGraphic(null);
        } else {
            if (pane == null) {
                pane=new BorderPane();
            }
            HBox display = new HBox();
            TreeItem<HostItem> treeItem = getTreeItem();
            Node graphic = treeItem.getGraphic();
            if (graphic != null) {
                display.getChildren().add(graphic);
            }
            HostItem hostItem = treeItem.getValue();
            String name = hostItem.getName();
            if (StringUtils.isNotEmpty(name)) {
                display.getChildren().add(new JFXButton(name));
            }
            pane.setLeft(display);
            HostItem.HostType type = hostItem.getType();
            if (type != null) {
                String id = hostItem.getId();
                Button button;
                if (StringUtils.equals(id, switchHostService.getCurrentHostsId())) {
                    button = FontIconUtils.createIconButton(FontAwesome.TOGGLE_ON, 16, Color.GREEN);
                } else {
                    button = FontIconUtils.createIconButton(FontAwesome.TOGGLE_OFF, 16);
                }
                button.setPrefWidth(30);
                pane.setRight(button);

            }
            //pane.setBorder(new Border(new BorderStroke(Color.valueOf("#9E9E9E"),
            //                                           BorderStrokeStyle.SOLID,
            //                                           CornerRadii.EMPTY,
            //                                           BorderWidths.DEFAULT)));
            setGraphic(pane);
        }
    }

    @Override
    protected void updateItem(HostItem item, boolean empty) {

        super.updateItem(item, empty);
        updateDisplay(item, empty);
    }
}
