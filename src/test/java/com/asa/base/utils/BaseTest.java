package com.asa.base.utils;

import com.asa.base.log.LoggerFactory;
import com.asa.jtools.base.utils.ShellUtils;
import junit.framework.TestCase;
import org.junit.Ignore;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @author andrew_asa
 * @date 2022/6/12.
 */
public class BaseTest extends TestCase {

    @Ignore
    public  void testShell() {

        LoggerFactory.getLogger().debug("{}",ShellUtils.execForStringList("echo $PATH"));;
        LoggerFactory.getLogger().debug("{}",ShellUtils.execForStringList("pwd"));;
        LoggerFactory.getLogger().debug("{}",ShellUtils.execForStringList("python table_to_ocr.py"));;
    }

    @Ignore
    public void testOcr() throws Exception{

        Process proc;
        try {
            proc = Runtime.getRuntime().exec("python /Users/andrew_asa/Documents/code/github/andrew-asa/exec/jtools/bin/table_to_ocr.py --img_path=/Users/andrew_asa/Desktop/5.png");
            BufferedReader in = new BufferedReader(new InputStreamReader(proc.getInputStream()));
            String line = null;
            while ((line = in.readLine()) != null) {
                System.out.println(line);
            }
            in.close();
            proc.waitFor();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
