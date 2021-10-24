package com.asa.jtools.base.ui;

import javafx.scene.Parent;
import javafx.stage.Stage;

/**
 * @author andrew_asa
 * @date 2021/10/24.
 */
public interface SubPane {

    Parent getNode();

    void init(Stage stage);

    void onClose();

    String getName();
}
