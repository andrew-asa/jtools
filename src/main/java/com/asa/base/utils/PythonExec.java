package com.asa.base.utils;

import com.asa.base.log.LoggerFactory;
import org.slf4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @author andrew_asa
 * @date 2022/6/13.
 */
public class PythonExec {

    /**
     * 最大等待时间
     */
    public static final long MAX_WAIT_SECOND = 12 * 60 * 60;

    /**
     * 等待执行完成
     *
     * @param pf
     * @param parameters
     */
    public static void waitExec(String pf, List<String> parameters, long timeout, TimeUnit unit) {

        Process proc;
        try {
            String p = "";
            if (ListUtils.isNotEmpty(parameters)) {
                p = parameters.stream().collect(Collectors.joining(" "));
            }
            String execStr = StringUtils.messageFormat("python {} {}", pf, p);
            LoggerFactory.getLogger().debug("exec {}", execStr);
            proc = Runtime.getRuntime().exec(execStr);
            BufferedReader in = new BufferedReader(new InputStreamReader(proc.getInputStream()));
            String line = null;
            while ((line = in.readLine()) != null) {
                System.out.println(line);
            }
            in.close();
            proc.waitFor(timeout, unit);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void waitExec(String pf, List<String> parameters) {

        waitExec(pf, parameters, MAX_WAIT_SECOND, TimeUnit.SECONDS);
    }

    public static void waitExecForSeconds(String pf, List<String> parameters, long seconds) {

        waitExec(pf, parameters, seconds, TimeUnit.SECONDS);
    }
}
