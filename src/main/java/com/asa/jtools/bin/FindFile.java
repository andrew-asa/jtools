package com.asa.jtools.bin;

import com.asa.jtools.bin.intr.ConsoleSupport;
import org.apache.commons.cli.Options;

import java.io.File;
import java.util.regex.Pattern;

/**
 * @author andrew_asa
 * @date 2021/8/2.
 */
public class FindFile extends DefaultArgumentJtoolsBin implements ConsoleSupport {

    private String[] args;

    public FindFile(String[] args) {

        super(args);
    }

    public void find() {

        if (hasOption("h")) {
            printHelp();
        }
        doFind();
    }


    private void doFind() {

        String dir = getOptionValue("dir", new File("").getAbsolutePath());
        File fd = new File(dir);
        if (!fd.exists()) {
            System.out.println("文件夹不存在");
        } else if (!fd.isDirectory()) {
            System.out.println("非文件夹");
        } else {
            String keyword = getOptionValue("keyword", ".");
            boolean recurse = hasOption("recurse");
            boolean absolute = hasOption("absolute");
            boolean ignoreHide = hasOption("ignore_hide");
            doFind(fd, keyword, recurse, absolute, ignoreHide);
        }
    }

    public void doFind(File root, String keyword, boolean recurse, boolean absolute, boolean ignoreHide) {
        //Pattern p= Pattern.compile(keyword);
        for (File f : root.listFiles()) {
            if (ignoreHide && f.isHidden()) {
                continue;
            }
            String name = f.getName();
            if (matches(name, keyword)) {
                if (absolute) {
                    System.out.println(f.getAbsolutePath());
                } else {
                    System.out.println(f.getName());
                }
            }
            if (f.isDirectory() && recurse) {
                doFind(f, keyword, recurse, absolute, ignoreHide);
            }
        }
    }

    public static boolean matches(String fn, String keyword) {

        Pattern p = Pattern.compile(keyword);
        return fn != null && keyword != null && p.matcher(fn).find();
    }


    protected void createOptions(Options options) {

        options.addOption("h", "help", false, "打印帮助信息");
        options.addOption("d", "dir", true, "目录");
        options.addOption("k", "keyword", true, "关键字");
        options.addOption("a", "absolute", false, "输出绝对路径");
        options.addOption("r", "recurse", false, "是否递归");
        options.addOption("ih", "ignore_hide", false, "忽略隐藏文件");
    }

    public static void main(String[] args) {

        FindFile findFile = new FindFile(args);
        findFile.find();
    }
}
