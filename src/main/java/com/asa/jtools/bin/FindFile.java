package com.asa.jtools.bin;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import java.io.File;
import java.util.regex.Pattern;

/**
 * @author andrew_asa
 * @date 2021/8/2.
 */
public class FindFile {

    private String[] args;

    private CommandLine line;

    public FindFile(String[] args) {

        this.args = args;
    }

    public void find() {

        Options options = createOptions();
        line = parseArgs(options, args);
        if (line != null) {
            if (line.hasOption("h")) {
                HelpFormatter hf = new HelpFormatter();
                hf.setSyntaxPrefix("提示: ");
                hf.printHelp("FindFile 帮助文档", options);
                return;
            }
            find(line);
        }
    }

    private void find(CommandLine line) {

        String dir = line.getOptionValue("dir", new File("").getAbsolutePath());
        File fd = new File(dir);
        if (!fd.exists()) {
            System.out.println("文件夹不存在");
        } else if (!fd.isDirectory()) {
            System.out.println("非文件夹");
        } else {
            String keyword = line.getOptionValue("keyword", ".");
            boolean recurse = line.hasOption("recurse");
            boolean absolute = line.hasOption("absolute");
            boolean ignoreHide = line.hasOption("ignore_hide");
            find(fd, keyword, recurse, absolute,ignoreHide);
        }
    }

    public void find(File root, String keyword, boolean recurse, boolean absolute,boolean ignoreHide) {
        //Pattern p= Pattern.compile(keyword);
        for (File f : root.listFiles()) {
            if (ignoreHide && f.isHidden()) {
                continue;
            }
            String name = f.getName();
            if (matches(name,keyword)) {
                if (absolute) {
                    System.out.println(f.getAbsolutePath());
                } else {
                    System.out.println(f.getName());
                }
            }
            if (f.isDirectory() && recurse) {
                find(f,keyword,recurse,absolute,ignoreHide);
            }
        }
    }

    public static boolean matches(String fn, String keyword) {
        Pattern p= Pattern.compile(keyword);
        return fn != null && keyword != null && p.matcher(fn).find();
    }



    private CommandLine parseArgs(Options options, String[] args) {

        CommandLineParser parser = new DefaultParser();
        try {
            return parser.parse(options, args);
        } catch (ParseException e) {
            System.out.println("参数解析错误，请重试");
            System.out.println(e.getMessage());
            return null;
        }
    }

    private Options createOptions() {

        Options options = new Options();
        options.addOption("h", "help", false, "打印帮助信息");
        options.addOption("d", "dir", true, "目录");
        options.addOption("k", "keyword", true, "关键字");
        options.addOption("a", "absolute", false, "输出绝对路径");
        options.addOption("r", "recurse", false, "是否递归");
        options.addOption("ih", "ignore_hide", false, "忽略隐藏文件");
        return options;
    }

    public static void main(String[] args) {
        //LoggerFactory.getLogger(FindFile.class).setLevel(Level.INFO);
        //if (args.length <= 0) {
        //    System.out.println("清输入参数参数");
        //    System.exit(0);
        //}
        FindFile findFile = new FindFile(args);
        findFile.find();
    }
}
