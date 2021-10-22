package com.asa.jtools.switchhost;

import com.asa.base.ui.controls.button.JButton;
import com.asa.base.utils.StringUtils;
import com.asa.jtools.base.utils.FontIconUtils;
import com.asa.jtools.switchhost.bean.HostItem;
import com.asa.jtools.switchhost.constant.SwitchHostConstant;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXTabPane;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import org.controlsfx.control.PrefixSelectionChoiceBox;
import org.controlsfx.control.textfield.CustomTextField;
import org.kordamp.ikonli.fontawesome.FontAwesome;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author andrew_asa
 * @date 2021/10/19.
 */
public class SwitchHostAddPane extends BorderPane {

    private int labelHeight = 25;

    private int labelWidth = 100;

    private int inputWidth = 300;

    private TabPane tabPane;


    public SwitchHostAddPane() {

        super();
        init();
    }

    private void init() {

        tabPane = new JFXTabPane();
        Tab local = new Tab(SwitchHostConstant.LOCAL, FontIconUtils.createIconButton(FontAwesome.FILE, 16));
        local.setContent(createLocalAddPane());
        Tab remote = new Tab(SwitchHostConstant.REMOTE, FontIconUtils.createIconButton(FontAwesome.GLOBE, 18));
        remote.setContent(createNetAddPane());
        tabPane.getTabs().add(local);
        tabPane.getTabs().add(remote);
        setTop(tabPane);
    }

    private HostItem getNetItemValue(HostItem oldItem) {

        HostItem item = new HostItem();
        item.setType(HostItem.HostType.Remote);
        item.setName(netName.getText());
        item.setPath(netUrl.getText());
        Map configure = new HashMap<>();
        configure.put(SwitchHostConstant.URL, netUrl.getText());
        configure.put(SwitchHostConstant.FREQUENCY, netUpdateFrequency.getValue());
        item.setConfigure(configure);
        return item;
    }

    private HostItem getLocalItemValue(HostItem oldItem) {

        HostItem item = new HostItem();
        item.setType(HostItem.HostType.LOCAL);
        item.setName(localName.getText());
        return item;
    }

    private void setItem(HostItem item) {

        if (item != null) {
            HostItem.HostType type = item.getType();
            if (HostItem.HostType.Remote.equals(type)) {
                netName.setText(item.getName());
                netUrl.setText(item.getPath());
            } else if (HostItem.HostType.LOCAL.equals(type)) {
                localName.setText(item.getName());
            }
            removeOtherAndSelectItemByType(type);
        }
    }

    private void removeOtherAndSelectItemByType(HostItem.HostType type) {

        Tab select = null;
        List<Tab> remove = new ArrayList<>();
        for (Tab tab : tabPane.getTabs()) {
            if (StringUtils.equalsIgnoreCase(tab.getText(), type.name())) {
                select = tab;
            } else {
                remove.add(tab);
            }
        }
        tabPane.getTabs().removeAll(remove);
        tabPane.getSelectionModel().select(select);
    }

    private Tab getTabByType(HostItem.HostType type) {

        for (Tab tab : tabPane.getTabs()) {
            if (StringUtils.equalsIgnoreCase(tab.getText(), type.name())) {
                return tab;
            }
        }
        return null;
    }

    public HostItem getItem(HostItem oldItem) {

        if (StringUtils.equals(tabPane.getSelectionModel().getSelectedItem().getText(), SwitchHostConstant.LOCAL)) {
            return getLocalItemValue(oldItem);
        } else {
            return getNetItemValue(oldItem);
        }
    }


    private TextField netName;

    private TextField netUrl;

    private ChoiceBox<String> netUpdateFrequency;

    public Node createNetAddPane() {

        VBox root = new VBox();
        root.setPadding(new Insets(10, 0, 0, 0));
        root.setSpacing(15);
        Label nameLabel = new Label("Host方案名");
        netName = new CustomTextField();
        nameLabel.setPrefHeight(labelHeight);
        nameLabel.setAlignment(Pos.CENTER_LEFT);
        nameLabel.setPrefWidth(labelWidth);
        netName.setPrefWidth(inputWidth);
        HBox name = new HBox();
        name.getChildren().addAll(nameLabel, netName);

        HBox url = new HBox();
        Label urlLabel = new Label("url地址");
        netUrl = new CustomTextField();
        url.getChildren().addAll(urlLabel, netUrl);
        urlLabel.setAlignment(Pos.CENTER_LEFT);
        urlLabel.setPrefWidth(labelWidth);
        urlLabel.setPrefHeight(labelHeight);
        netUrl.setPrefWidth(inputWidth);

        HBox update = new HBox();
        Label updateLabel = new Label("更新频率");
        ObservableList<String> fre = FXCollections.<String>observableArrayList(SwitchHostConstant.UPDATE_FREQUENCY);
        netUpdateFrequency = new PrefixSelectionChoiceBox();
        netUpdateFrequency.setValue(SwitchHostConstant.UPDATE_FREQUENCY.get(0));
        netUpdateFrequency.setItems(fre);
        updateLabel.setAlignment(Pos.CENTER_LEFT);
        updateLabel.setPrefWidth(labelWidth);
        updateLabel.setPrefHeight(labelHeight);
        update.getChildren().addAll(updateLabel, netUpdateFrequency);

        root.getChildren().addAll(name, url, update);
        return root;
    }


    private TextField localName;

    public Node createLocalAddPane() {

        VBox root = new VBox();
        root.setPadding(new Insets(20, 0, 0, 0));
        root.setSpacing(15);
        Label nameLabel = new Label("Hosts方案名");
        localName = new CustomTextField();
        nameLabel.setPrefHeight(labelHeight);
        nameLabel.setAlignment(Pos.CENTER_LEFT);
        nameLabel.setPrefWidth(labelWidth);
        localName.setPrefWidth(inputWidth);
        HBox name = new HBox();
        name.getChildren().addAll(nameLabel, localName);
        root.getChildren().addAll(name);
        return root;
    }


    public void showDialog(StackPane rootStackPane, HostItem info) {

        setItem(info);
        JFXDialogLayout content = new JFXDialogLayout();
        content.setHeading(new Text("添加hosts规则"));
        //SwitchHostAddPane switchHostAddPane = new SwitchHostAddPane();
        content.setBody(this);
        content.setPrefSize(600, 400);
        JFXDialog dialog = new JFXDialog(rootStackPane, content, JFXDialog.DialogTransition.LEFT, true);
        JButton cancel = new JButton("取消");
        JButton sure = new JButton("确定");
        sure.setOnAction(e -> {
            HostItem item = getItem(info);
            onSureAction(info, item);
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

    /**
     * 通知调用者
     *
     * @param oldItem
     * @param newItem
     */
    protected void onSureAction(HostItem oldItem, HostItem newItem) {

        SwitchHostEvent addEvent = new SwitchHostEvent(SwitchHostAddPane.this, SwitchHostEvent.SWITCH_HOST_ADD_EVENT, newItem);
        fireEvent(addEvent);
    }
}
