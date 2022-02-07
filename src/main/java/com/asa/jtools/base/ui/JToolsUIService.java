package com.asa.jtools.base.ui;

import javafx.stage.Stage;

/**
 * @author andrew_asa
 * @date 2021/12/16.
 */
public class JToolsUIService {


    private Stage stage;

    private static JToolsUIService instance = new JToolsUIService();

    JToolsUIService() {

    }

    public static JToolsUIService getInstance() {

        return instance;
    }

    public void init(String appName,Stage stage) {

        this.stage = stage;
    }

    public Stage getStage() {

        return stage;
    }
}
