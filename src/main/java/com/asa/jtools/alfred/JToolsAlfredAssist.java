package com.asa.jtools.alfred;

import com.asa.base.log.LoggerFactory;
import com.asa.base.utils.StringUtils;
import com.asa.base.utils.io.FileSystemResource;
import com.asa.base.utils.io.IOUtils;
import com.asa.jtools.base.lang.DefaultArgumentJtoolsBin;
import com.asa.jtools.base.utils.ObjectMapperUtils;
import com.asa.jtools.bin.Ls;
import org.apache.commons.cli.Options;

import java.io.InputStream;
import java.util.List;

/**
 * @author andrew_asa
 * @date 2021/11/12.
 */
public class JToolsAlfredAssist extends DefaultArgumentJtoolsBin {

    public JToolsAlfredAssist(){
        super(new String[0]);
    }


    public JToolsAlfredAssist(String[] args) {

        super(args);
    }

    public static void main(String[] args) {

        JToolsAlfredAssist ls = new JToolsAlfredAssist(args);
        ls.doCmd();
    }

    private void printAlfredInfo(AlfredItems... items) {

        AlfredItems all = new AlfredItems();
        for (AlfredItems item : items) {
            all.merge(item);
        }
        System.out.println(all.buildString());
    }

    public AlfredItems getBuiltInCmdAlfredItems(String search) {

        AlfredItems items = new AlfredItems();
        Ls ls = new Ls();
        List<String> cmds = ls.getCmdName(search);
        for (String cmd : cmds) {
            String uid = cmd;
            String args = "jtools_exec " + cmd;
            String title = "exec:" + cmd;
            String subTitle = "Press Enter to exec";
            items.addItem(new AlfredItem(uid, args, title, subTitle));
        }
        return items;
    }

    public AlfredItems getCustomAlfredItems(String path, String search) {

        AlfredItems ret = new AlfredItems();
        if (StringUtils.isNotEmpty(path)) {
            InputStream in = null;
            try {
                FileSystemResource resource = new FileSystemResource(path);
                AlfredItems extraItem = ObjectMapperUtils.getDefaultMapper().readValue(resource.getInputStream(), AlfredItems.class);
                extraItem.getItems().forEach(item -> {
                    if (StringUtils.isEmpty(search) ||
                            StringUtils.contains(item.getTitle().toLowerCase(), search.toLowerCase())) {
                        ret.addItem(item);
                    }
                });
            } catch (Exception e) {
                LoggerFactory.getLogger().error("error parse extra", e);
            } finally {
                IOUtils.closeQuietly(in);
            }
        }
        return ret;
    }


    private void doCmd() {

        printHelpIfHasOption("h");
        Ls ls = new Ls();
        String search = StringUtils.EMPTY;
        if (hasOption(OPT_PRINT_SEARCH)) {
            search = getOptionValue(OPT_PRINT_SEARCH);
        }
        String customPath = getOptionValue(LONG_OPT_EXTRA_CUSTOM_BIN, StringUtils.EMPTY);
        printAlfredInfo(getBuiltInCmdAlfredItems(search), getCustomAlfredItems(customPath, search));
    }


    public static final String OPT_PRINT_ALL = "a";

    public static final String LONG_OPT_PRINT_ALL = "list_all";

    public static final String OPT_PRINT_SEARCH = "s";

    public static final String LONG_OPT_PRINT_SEARCH = "search";

    public static final String OPT_EXTRA_CUSTOM_BIN = "e";

    public static final String LONG_OPT_EXTRA_CUSTOM_BIN = "extra";


    protected void createOptions(Options options) {

        options.addOption("h", "help", false, "打印帮助信息");
        options.addOption(OPT_PRINT_ALL, LONG_OPT_PRINT_ALL, false, "列出所有命令");
        options.addOption(OPT_PRINT_SEARCH, LONG_OPT_PRINT_SEARCH, true, "搜索命令");
        options.addOption(OPT_EXTRA_CUSTOM_BIN, LONG_OPT_EXTRA_CUSTOM_BIN, true, "额外指定命令");
    }
}
