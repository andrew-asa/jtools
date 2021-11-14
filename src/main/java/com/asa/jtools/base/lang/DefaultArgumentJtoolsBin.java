package com.asa.jtools.base.lang;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

/**
 * @author andrew_asa
 * @date 2021/11/3.
 */
public class DefaultArgumentJtoolsBin {

    private Options options;

    private CommandLine commandLine;

    private String[] args;

    public DefaultArgumentJtoolsBin(String[] args) {

        this.init(args);
    }

    private void init(String[] args) {

        this.args = args;
        options = new Options();
        createOptions(options);
        commandLine = parseArgs(args);
    }

    public boolean hasArg() {

        return args != null && args.length > 0;
    }

    protected CommandLine parseArgs(String[] args) {

        CommandLineParser parser = new DefaultParser();
        try {
            return parser.parse(options, args, false);
        } catch (ParseException e) {
            System.out.println("参数解析错误，请重试");
            System.out.println(e.getMessage());
            return null;
        }
    }

    public boolean hasOption(String opt) {

        return this.commandLine.hasOption(opt);
    }

    public String getOptionValue(String opt) {

        return this.commandLine.getOptionValue(opt);
    }

    public String getOptionValue(String opt, String defaultValue) {

        return commandLine.getOptionValue(opt, defaultValue);
    }


    public void printHelp() {

        HelpFormatter hf = new HelpFormatter();
        hf.setSyntaxPrefix("提示: ");
        hf.printHelp("FindFile 帮助文档", options);
    }

    public void printHelpIfHasOption(String option) {

        if (hasOption(option)) {
            printHelp();
            System.out.println();
        }
    }

    protected void createOptions(Options options) {


    }
}
