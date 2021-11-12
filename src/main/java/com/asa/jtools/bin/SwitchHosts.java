package com.asa.jtools.bin;

import com.asa.base.log.LoggerFactory;
import com.asa.base.utils.StringUtils;
import com.asa.jtools.bin.intr.ConsoleSupport;
import com.asa.jtools.bin.intr.GuiSupport;
import com.asa.jtools.switchhost.SwitchHostApp;
import com.asa.jtools.switchhost.SwitchHostService;
import com.asa.jtools.switchhost.bean.HostItem;
import javafx.application.Application;
import org.apache.commons.cli.Options;

import java.nio.file.AccessDeniedException;

/**
 * @author andrew_asa
 * @date 2021/10/5.
 * 切换host文件
 */
public class SwitchHosts extends DefaultArgumentJtoolsBin implements ConsoleSupport, GuiSupport {

    public SwitchHosts(String[] args) {

        super(args);

    }

    public static void main(String[] args) {

        if (args.length == 0) {
            System.out.println("--start switch host gui--");
            Application.launch(SwitchHostApp.class, args);
        } else {
            SwitchHosts hosts = new SwitchHosts(args);
            System.out.println("--start switch host --");
            hosts.doCmd();
        }
    }

    public void doCmd() {

        SwitchHostService hostService = new SwitchHostService();
        hostService.init();
        printHelpIfHasOption("h");
        System.out.println();
        if (hasOption("r")) {
            System.out.println("refresh remote");
            HostItem item = hostService.getApplyItem();
            String content = hostService.getRemoteContent(item);
            if (StringUtils.isNotEmpty(content)) {
                hostService.saveContent(item, content);
                try {
                    hostService.replaceSystemHostsContent(content);
                } catch (AccessDeniedException e) {
                    // 无法访问文件，无法替换/etc/hosts文件
                    LoggerFactory.getLogger().error("没有权限修改/etc/hosts文件，请用超管权限重新打开");
                } catch (Exception e2) {
                    LoggerFactory.getLogger().error(e2, "error apply \n {} \nto /etc/hosts", content);
                }
            }
        }
    }

    protected void createOptions(Options options) {

        options.addOption("h", "help", false, "打印帮助信息");
        options.addOption("r", "refresh", false, "刷新远程");
    }
}
