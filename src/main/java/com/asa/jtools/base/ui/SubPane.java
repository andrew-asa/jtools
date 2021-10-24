package com.asa.jtools.base.ui;

import javafx.scene.Parent;

/**
 * @author andrew_asa
 * @date 2021/10/24.
 */
public interface SubPane {

    Parent getNode();

    void init();

    void onClose();
}
