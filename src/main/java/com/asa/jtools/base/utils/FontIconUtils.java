package com.asa.jtools.base.utils;

import com.jfoenix.controls.JFXButton;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import org.kordamp.ikonli.Ikon;
import org.kordamp.ikonli.fontawesome.FontAwesome;
import org.kordamp.ikonli.javafx.FontIcon;

/**
 * @author andrew_asa
 * @date 2021/10/8.
 */
public class FontIconUtils {

    public static Button createIconButton(Ikon des, int iconSize) {

        return createIconButton(des,iconSize,Color.BLACK);
    }

    public static Button createIconButton(Ikon des, int iconSize, Color color) {

        JFXButton button = new JFXButton();
        FontIcon fontIcon = FontIcon.of(des, iconSize,color);
        button.setGraphic(fontIcon);
        return button;
    }
}
