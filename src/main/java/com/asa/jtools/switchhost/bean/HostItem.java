package com.asa.jtools.switchhost.bean;

import com.asa.base.lang.builder.EqualsBuilder;

import java.util.Map;
import java.util.StringJoiner;

/**
 * @author andrew_asa
 * @date 2021/10/10.
 */
public class HostItem {

    /**
     * id
     */
    private String id;

    /**
     * 名字
     */
    private String name;

    /**
     * 类型
     */
    private HostType type;
    /**
     *路径
     */
    private String path;

    /**
     * 配置
     */
    private Map<String,String> configure;

    public static enum HostType {
        /**
         * 本地
         */
        LOCAL,
        /**
         * 网络
         */
        NET,
        /**
         * 根
         */
        ROOT
    }

    public HostType getType() {

        return type;
    }

    public void setType(HostType type) {

        this.type = type;
    }

    public String getPath() {

        return path;
    }

    public void setPath(String path) {

        this.path = path;
    }

    public Map<String, String> getConfigure() {

        return configure;
    }

    public void setConfigure(Map<String, String> configure) {

        this.configure = configure;
    }


    public String getName() {

        return name;
    }

    public void setName(String name) {

        this.name = name;
    }

    public String getId() {

        return id;
    }

    public void setId(String id) {

        this.id = id;
    }

    @Override
    public String toString() {

        return new StringJoiner(", ", HostItem.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .add("name=" + name)
                .add("type=" + type)
                .add("path='" + path + "'")
                .add("configure=" + configure)
                .toString();
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) return true;

        if (!(o instanceof HostItem)) return false;

        HostItem item = (HostItem) o;

        return new EqualsBuilder()
                .append(id, item.id)
                .append(name, item.name)
                .append(type, item.type)
                .append(path, item.path)
                .append(configure, item.configure)
                .isEquals();
    }
}