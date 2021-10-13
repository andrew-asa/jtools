package com.asa.jtools.bin;

import com.asa.jtools.switchhost.SwitchHostApp;
import javafx.application.Application;

/**
 * @author andrew_asa
 * @date 2021/10/5.
 * 切换host文件
 */
public class SwitchHosts {

    public static void main(String[] args) {

        System.out.println("--start switch host--");
        Application.launch(SwitchHostApp.class, args);
    }
}
