package com.asa.jtools.base.service;

import javafx.stage.Stage;

/**
 * @author andrew_asa
 * @date 2021/10/24.
 */
public class JToolsService {

    private static Stage stage;

    public static Stage getStage() {

        return stage;
    }

    public static void setStage(Stage stage) {

        JToolsService.stage = stage;
    }
}
