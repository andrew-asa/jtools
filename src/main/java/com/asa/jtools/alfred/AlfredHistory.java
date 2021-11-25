package com.asa.jtools.alfred;

import com.asa.base.utils.ListUtils;
import com.asa.base.utils.StringUtils;
import com.asa.base.utils.io.FilenameUtils;
import com.asa.jtools.base.lang.DefaultArgumentJtoolsBin;
import com.asa.jtools.base.utils.ShellUtils;
import org.apache.commons.cli.Options;

import java.util.ArrayList;
import java.util.List;

/**
 * @author andrew_asa
 * @date 2021/11/14.
 */
public class AlfredHistory extends DefaultArgumentJtoolsBin {

    public static String USER_HOME = System.getProperty("user.home");

    public AlfredHistory() {

        super(new String[]{});

    }

    public AlfredHistory(String[] args) {

        super(args);
    }

    public static void main(String[] args) {

        AlfredHistory history = new AlfredHistory(args);
        history.doCmd();
    }

    public void doCmd() {

        String s = StringUtils.EMPTY;
        if (hasOption(OPT_SEARCH)) {
            s = getOptionValue(OPT_SEARCH);
        }
        AlfredItems items = new AlfredItems();
        int index = 0;
        List<String> history = getOptHistory(s);
        if (StringUtils.isNotEmpty(s)) {
            s = s.trim();
            items.addItem(createItem(s, s));
        }
        for (String h : history) {
            items.addItem(createItem(String.valueOf(index++), h));
        }
        if (!hasOption(OPT_QUIET)) {
            System.out.println(items.buildString());
        }
    }

    private AlfredItem createItem(String id, String cmd) {

        return new AlfredItem(id, cmd, cmd, "enter to exec history");
    }

    public List<String> getOptHistory(String search) {

        List<String> history = getHistory(search);
        List<String> opt = new ArrayList<>();
        for (String h : history) {
            if (StringUtils.isNotEmpty(h)) {
                h = h.trim();
            }
            String exec = parseHistoryItem(h);
            if (StringUtils.contains(exec, search) && !ListUtils.contain(opt, exec)) {
                opt.add(exec);
            }
        }
        return opt;
    }

    /**
     * 解析出一个合理的命令
     *
     * @param item
     * @return
     */
    public String parseHistoryItem(String item) {

        if (StringUtils.isNotEmpty(item) && item.startsWith(": ")
                && item.contains(";") && !item.endsWith("\\")) {
            String[] is = item.split(";");
            if (is.length == 2) {
                return is[1];
            }
        }
        return StringUtils.EMPTY;
    }

    public List<String> getHistory(String search) {

        return ShellUtils.execForStringList(getHistoryCmd(search));
    }

    public String getHistoryCmd(String search) {

        if (StringUtils.isNotEmpty(search)) {
            search = search.trim();
            return StringUtils.messageFormat("cat {} | grep -i \"{}\" | tail -100", getHistoryPath(), search);
        } else {
            return StringUtils.messageFormat("tail -100 {}", getHistoryPath());
        }
    }

    public String getHistoryPath() {

        return FilenameUtils.concat(USER_HOME, ".zsh_history");
    }

    public static final String OPT_SEARCH = "s";

    public static final String LONG_OPT_SEARCH = "search";

    public static final String OPT_QUIET = "q";

    public static final String LONG_OPT_QUIET = "quiet";

    protected void createOptions(Options options) {

        options.addOption("h", "help", false, "打印帮助信息");
        options.addOption(OPT_SEARCH, LONG_OPT_SEARCH, true, "搜索命令");
        options.addOption(OPT_QUIET, LONG_OPT_QUIET, false, "不输出");
    }
}
