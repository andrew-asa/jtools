package com.asa.jtools.bin;

import com.asa.base.utils.ClassScanUtils;
import com.asa.base.utils.ListUtils;
import com.asa.base.utils.StringUtils;
import com.asa.jtools.bin.intr.ConsoleSupport;
import org.apache.commons.cli.Options;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

/**
 * @author andrew_asa
 * @date 2021/10/5.
 * 切换host文件
 */
public class Ls extends DefaultArgumentJtoolsBin implements ConsoleSupport {

    public List<Class<?>> ignoreBins = Arrays.asList(DefaultArgumentJtoolsBin.class);

    public Ls() {

        super(new String[]{});
    }

    public Ls(String[] args) {

        super(args);
    }

    public List<String> getCmdName() {

        return getCmdName(StringUtils.EMPTY);
    }

    public List<String> getCmdName(String search) {

        List<String> names = new ArrayList<>();
        getSupportCmdClass().forEach(aClass -> {
            if (StringUtils.isEmpty(search) || StringUtils.contains(aClass.getSimpleName().toLowerCase(), search.toLowerCase())) {
                names.add(aClass.getSimpleName());
            }
        });
        return names;
    }


    public Set<Class<?>> getSupportCmdClass() {

        return getSupportCmdClass(ClassScanUtils.ALL_CLASS_ACCEPT_FILTER);
    }

    public Set<Class<?>> getSupportCmdClass(Predicate<Class<?>> filter) {

        try {
            return ClassScanUtils.getClasses("com.asa.jtools.bin",
                                             aClass -> isInternalSupport(aClass, filter));

        } catch (Exception e) {
            e.printStackTrace();
        }
        return new HashSet<>();
    }

    public boolean isInternalSupport(Class<?> aClass, Predicate<Class<?>> filter) {

        if (filter == null) {
            filter = ClassScanUtils.ALL_CLASS_ACCEPT_FILTER;
        }
        return !aClass.getName().contains("$") &&
                !isIgnoreClass(aClass) &&
                !aClass.isInterface() && filter.test(aClass);
    }

    public void printCmd(List<String> cmds) {

        System.out.println("[" + StringUtils.join(cmds, ",") + "]");
    }

    public boolean isIgnoreClass(Class<?> aClass) {

        return ListUtils.contain(ignoreBins, aClass);
    }


    public static void main(String[] args) {

        Ls ls = new Ls(args);
        ls.doCmd();
    }


    private void doCmd() {

        printHelpIfHasOption("h");
        if (!hasArg() || hasOption(OPT_PRINT_ALL)) {
            printCmd(getCmdName());
        }
        if (hasOption(OPT_PRINT_SEARCH) || hasOption(LONG_OPT_PRINT_SEARCH)) {
            String filter = getOptionValue(OPT_PRINT_SEARCH);
            printCmd(getCmdName(filter));
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
