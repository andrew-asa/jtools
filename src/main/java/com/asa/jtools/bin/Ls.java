package com.asa.jtools.bin;

import com.asa.base.utils.ClassScanUtils;
import com.asa.base.utils.ListUtils;
import com.asa.base.utils.StringUtils;
import org.apache.commons.cli.Options;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * @author andrew_asa
 * @date 2021/10/5.
 * 切换host文件
 */
public class Ls extends DefaultArgumentJtoolsBin {

    public List<Class<?>> ignoreBins = Arrays.asList(DefaultArgumentJtoolsBin.class);

    public Ls(String[] args) {

        super(args);
    }

    public List<String> getSupportCmd() {

        List<String> names = new ArrayList<String>();
        try {
            ClassScanUtils.traverseClass("com.asa.jtools.bin",
                                         aClass -> !aClass.getName().contains("$") &&
                                                 !isIgnoreClass(aClass),
                                         aClass -> names.add(aClass.getSimpleName()));

        } catch (Exception e) {
            e.printStackTrace();
        }
        return names;
    }

    public void printAllCmd() {

        System.out.println("[" + StringUtils.join(getSupportCmd(), ",") + "]");
    }

    public boolean isIgnoreClass(Class<?> aClass) {

        return ListUtils.contain(ignoreBins, aClass);
    }


    public static void main(String[] args) {

        Ls ls = new Ls(args);
        ls.doCmd();
    }

    private void printAlfredInfo() {

        List<String> cmds = getSupportCmd();
        String filter = getOptionValue(OPT_PRINT_SEARCH);
        if (StringUtils.isNotEmpty(filter)) {
            cmds = cmds.stream()
                    .filter(s -> StringUtils.contains(s.toLowerCase(), filter.toLowerCase()))
                    .collect(Collectors.toList());
        }
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
        if (!hasArg() || hasOption(OPT_PRINT_ALL)) {
            printAllCmd();
        }
        if (hasOption(OPT_PRINT_ALFRED_INFO)) {
            printAlfredInfo();
        }
    }

    public static final String OPT_PRINT_ALFRED_INFO = "pai";

    public static final String LONG_OPT_PRINT_ALFRED_INFO = "print_alfred_info";

    public static final String OPT_PRINT_ALL = "a";

    public static final String LONG_OPT_PRINT_ALL = "list_all";

    public static final String OPT_PRINT_SEARCH = "s";

    public static final String LONG_OPT_PRINT_SEARCH = "search";


    protected void createOptions(Options options) {

        options.addOption("h", "help", false, "打印帮助信息");
        options.addOption(OPT_PRINT_ALL, LONG_OPT_PRINT_ALL, false, "列出所有命令");
        options.addOption(OPT_PRINT_ALFRED_INFO, LONG_OPT_PRINT_ALFRED_INFO, false, "打印Alfred参数");
        options.addOption(OPT_PRINT_SEARCH, LONG_OPT_PRINT_SEARCH, true, "搜索命令");
    }
}
