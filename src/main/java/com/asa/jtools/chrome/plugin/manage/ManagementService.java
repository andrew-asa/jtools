package com.asa.jtools.chrome.plugin.manage;

import com.asa.base.utils.StringUtils;
import com.asa.jtools.chrome.plugin.base.ChromeConstant;
import com.asa.jtools.chrome.plugin.base.ExtensionInfo;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @author andrew_asa
 * @date 2021/12/13.
 */
public class ManagementService {

    public List<ExtensionInfo> scan(String path) {

        List<ExtensionInfo> ret = new ArrayList<>();
        if (StringUtils.isNotEmpty(path)) {
            File p = new File(path);
            if (p.exists() && p.isDirectory()) {
                for (File f : p.listFiles()) {
                    if (f.isDirectory()) {
                        parse(f);
                    }
                }
            }
        }
        return ret;
    }

    public ExtensionInfo parse(String path) {

        if (StringUtils.isNotEmpty(path)) {
            return parse(new File(path));
        }
        return null;
    }

    public ExtensionInfo parse(File file) {

        if (isExtensionDir(file)) {

        }
        return null;
    }

    public boolean isExtensionDir(String path) {

        return StringUtils.isNotEmpty(path) && isExtensionDir(new File(path));
    }

    public boolean isExtensionDir(File file) {

        if (file != null && file.exists() && file.isDirectory()) {
            for (File f : file.listFiles()) {
                if (f.isFile()) {
                    if (StringUtils.equals(f.getName(), ChromeConstant.MANIFEST)) {
                        return true;
                    }
                } else if (f.isDirectory()) {

                }
            }
        }
        return false;
    }

    public List<ExtensionInfo> getAll() {

        List<ExtensionInfo> ret = new ArrayList<>();
        return ret;
    }

    public ExtensionInfo get(String id) {

        return null;
    }
}
