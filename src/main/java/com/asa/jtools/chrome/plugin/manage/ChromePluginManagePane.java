package com.asa.jtools.chrome.plugin.manage;

import com.asa.jtools.base.ui.SubPane;
import com.asa.jtools.chrome.plugin.base.ChromeConstant;
import com.jfoenix.controls.JFXListView;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 * @author andrew_asa
 * @date 2021/12/15.
 */
public class ChromePluginManagePane implements SubPane {

    private StackPane stackPane;

    private BorderPane borderPane;

    public ChromePluginManagePane() {

        stackPane = new StackPane();
        borderPane = new BorderPane();
    }

    @Override
    public Parent getNode() {

        return stackPane;
    }

    @Override
    public void init(Stage stage) {
        JFXListView<Label> list = new JFXListView<>();
        for (int i = 0; i < 100; i++) {
            list.getItems().add(new Label("Item " + i));
        }
        borderPane.setCenter(list);
        stackPane.getChildren().addAll(borderPane);

    }

    @Override
    public void onClose() {

    }

    @Override
    public String getName() {

        return ChromeConstant.APP_NAME;
    }
}
