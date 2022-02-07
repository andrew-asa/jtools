package com.asa.jtools.chrome.plugin.manage;

import com.asa.jtools.base.ui.BaseJToolsApplication;
import com.asa.jtools.base.ui.JToolsUIService;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * @author andrew_asa
 * @date 2021/12/15.
 */
public class ChromePluginManageApp extends BaseJToolsApplication {

    private ChromePluginManagePane mainPane;

    @Override
    protected Scene getScene(Stage stage) {

        mainPane = new ChromePluginManagePane();
        mainPane.init(stage);
        Scene scene = new Scene(mainPane.getNode(), 900, 700);
        return scene;
    }

    public void onClose() {

        mainPane.onClose();
    }

    public static void main(String[] args) {

        launch(args);
    }
}
