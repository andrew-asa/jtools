package com.asa.jtools.base.utils;

import com.asa.base.log.LoggerFactory;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * @author andrew_asa
 * @date 2021/11/25.
 */
public class ShellUtils {

    public static List<String> execForStringList(String cmd) {

        Process process = null;
        List<String> processList = new ArrayList<String>();
        try {
            process = Runtime.getRuntime().exec(cmd);
            BufferedReader input = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line = "";
            while ((line = input.readLine()) != null) {
                processList.add(line);
            }
            input.close();
        } catch (Exception e) {
            LoggerFactory.getLogger().error(e, "error exec [{}]", cmd);
        }
        return processList;
    }
}
