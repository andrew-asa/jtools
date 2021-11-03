package com.asa.jtools.bin;

import com.asa.base.utils.ClassScanUtils;
import com.asa.base.utils.ListUtils;
import com.asa.base.utils.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author andrew_asa
 * @date 2021/10/5.
 * 切换host文件
 */
public class Ls {

    public List<Class<?>> ignoreBins = Arrays.asList(DefaultArgumentJtoolsBin.class);

    public Ls() {

    }

    public void listCmd() {

        try {
            List<String> names = new ArrayList<String>();
            ClassScanUtils.traverseClass("com.asa.jtools.bin",
                                         aClass -> !aClass.getName().contains("$") && !isIgnoreClass(aClass),
                                         aClass -> names.add(aClass.getSimpleName()));

            System.out.println("[" + StringUtils.join(names, ",") + "]");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean isIgnoreClass(Class<?> aClass) {

        return ListUtils.contain(ignoreBins, aClass);
    }


    public static void main(String[] args) {

        Ls ls = new Ls();
        if (args.length == 0) {
            ls.listCmd();
        }
    }
}
