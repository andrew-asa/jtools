package com.asa.jtools.switchhost.bean;

import java.util.Map;

/**
 * @author andrew_asa
 * @date 2021/10/12.
 */
public class SwitchHostSettings {

    /**
     * 额外，留给没有定义的配置
     */
    private Map<String,String> extra;
    private String currentHostId ;

    public Map<String, String> getExtra() {

        return extra;
    }

    public void setExtra(Map<String, String> extra) {

        this.extra = extra;
    }

    public String getCurrentHostId() {

        return currentHostId;
    }

    public void setCurrentHostId(String currentHostId) {

        this.currentHostId = currentHostId;
    }
}
