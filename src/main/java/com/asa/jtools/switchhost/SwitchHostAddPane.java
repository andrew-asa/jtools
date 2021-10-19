package com.asa.jtools.switchhost;

import com.asa.jtools.base.utils.FontIconUtils;
import com.jfoenix.controls.JFXTabPane;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.layout.BorderPane;
import org.kordamp.ikonli.fontawesome.FontAwesome;

/**
 * @author andrew_asa
 * @date 2021/10/19.
 */
public class SwitchHostAddPane extends BorderPane {

    public SwitchHostAddPane() {
        super();
        init();
    }

    private void init() {
        JFXTabPane tabPane = new JFXTabPane();
        tabPane.setPrefSize(400, 100);
        Tab tab1 = new Tab("Local", FontIconUtils.createIconButton(FontAwesome.FILE, 16));
        tab1.setContent(new Label("本地"));
        Tab tab2 = new Tab("Remote",FontIconUtils.createIconButton(FontAwesome.GLOBE, 18));
        tab2.setContent(new Label("网络"));
        tabPane.getTabs().add(tab1);
        tabPane.getTabs().add(tab2);
        setTop(tabPane);
    }

    public void saveAdd() {

    }
}
