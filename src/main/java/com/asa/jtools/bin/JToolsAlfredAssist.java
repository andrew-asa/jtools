package com.asa.jtools.bin;

import com.asa.base.utils.StringUtils;
import org.apache.commons.cli.Options;

import java.util.List;

/**
 * @author andrew_asa
 * @date 2021/11/12.
 */
public class JToolsAlfredAssist extends DefaultArgumentJtoolsBin {


    public JToolsAlfredAssist(String[] args) {

        super(args);
    }

    public static void main(String[] args) {

        JToolsAlfredAssist ls = new JToolsAlfredAssist(args);
        ls.doCmd();
    }

    private void printAlfredInfo(List<String> cmds) {

        StringBuffer bf = new StringBuffer();
        bf.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>\n");
        bf.append("<items>\n");
        for (String cmd : cmds) {
            bf.append(createItem(cmd));
        }
        bf.append("</items>");
        System.out.println(bf.toString());
    }

    public String createItem(String cmd) {

        StringBuffer sb = new StringBuffer();
        sb.append(StringUtils.messageFormat("<item uid=\"{}\" arg=\"jtools_exec {}\" valid=\"yes\" autocomplete=\"yes\" >\n", cmd, cmd));
        sb.append(StringUtils.messageFormat("<title>exec:{}</title>\n", cmd));
        sb.append(StringUtils.messageFormat("<subtitle>Press Enter to exec</subtitle>\n"));
        sb.append(StringUtils.messageFormat("<icon>icon.png</icon>\n", cmd));
        sb.append(StringUtils.messageFormat("</item>\n", cmd));
        return sb.toString();
    }

    private void doCmd() {

        printHelpIfHasOption("h");
        Ls ls = new Ls();
        if (!hasArg() || hasOption(OPT_PRINT_ALL)) {
            printAlfredInfo(ls.getCmdName());
        }
        if (hasOption(OPT_PRINT_SEARCH)) {
            String s = getOptionValue(OPT_PRINT_SEARCH);
            printAlfredInfo(ls.getCmdName(s));
        }
    }


    public static final String OPT_PRINT_ALL = "a";

    public static final String LONG_OPT_PRINT_ALL = "list_all";

    public static final String OPT_PRINT_SEARCH = "s";

    public static final String LONG_OPT_PRINT_SEARCH = "search";


    protected void createOptions(Options options) {

        options.addOption("h", "help", false, "打印帮助信息");
        options.addOption(OPT_PRINT_ALL, LONG_OPT_PRINT_ALL, false, "列出所有命令");
        options.addOption(OPT_PRINT_SEARCH, LONG_OPT_PRINT_SEARCH, true, "搜索命令");
    }
}
