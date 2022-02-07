package com.asa.jtools.base.ui;

import com.asa.jtools.chrome.plugin.base.ChromeConstant;
import com.asa.jtools.chrome.plugin.manage.ChromePluginManagePane;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * @author andrew_asa
 * @date 2021/12/16.
 */
public abstract class BaseJToolsApplication extends Application {

    @Override
    public void start(Stage stage) throws Exception {

        JToolsUIService.getInstance().init(ChromeConstant.APP_NAME,stage);
        Scene scene = getScene(stage);
        stage.setScene(scene);
        stage.show();
        stage.setOnCloseRequest(e -> {
            onClose();
            System.exit(0);
        });
    }

    protected abstract Scene getScene(Stage stage);

    public void onClose() {

    }


    public void init() throws Exception {

        super.init();
    }
}
