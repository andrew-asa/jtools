package com.asa.jtools.switchhost.bean;

import com.alibaba.fastjson.annotation.JSONField;
import com.asa.base.utils.MapUtils;
import com.asa.base.utils.StringUtils;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;

/**
 * @author andrew_asa
 * @date 2021/10/11.
 */
public class HostItems {

    public Map<String, HostItem> itemMap;

    public HostItems() {

        ensureItemMapNotNull();
    }

    public HostItems(Map<String, HostItem> itemMap) {

        ensureItemMapNotNull();
        MapUtils.safeAddToMap(this.itemMap, itemMap);
    }

    public Map<String, HostItem> getItemMap() {

        return itemMap;
    }

    public void setItemMap(Map<String, HostItem> itemMap) {

        this.itemMap = itemMap;
    }

    private void ensureItemMapNotNull() {

        if (itemMap == null) {
            itemMap = new HashMap<>();
        }
    }

    public void addItem(HostItem item) {

        ensureItemMapNotNull();
        if (item != null && StringUtils.isNotEmpty(item.getId())) {
            MapUtils.safeAddToMap(itemMap, item.getId(), item);
        }
    }

    public void removeItem(HostItem item) {

        ensureItemMapNotNull();
        if (item != null && StringUtils.isNotEmpty(item.getId())) {
            MapUtils.remove(itemMap, item.getId());
        }
    }

    public void updateItem(HostItem newItem, HostItem oldItem) {
        addItem(newItem);
    }

    public boolean existItem() {

        return MapUtils.isNotEmptyMap(itemMap);
    }

    @JSONField(serialize=false)
    public List<HostItem> getItems(HostItem.HostType type) {

        List<HostItem> ret = new ArrayList<>();
        if (type != null && MapUtils.isNotEmptyMap(itemMap)) {
            itemMap.values().forEach(item -> {
                if (type.equals(item.getType())) {
                    ret.add(item);
                }
            });
        }
        return ret;
    }

    @JsonIgnore
    public HostItem getApplyItem() {
        if ( MapUtils.isNotEmptyMap(itemMap)) {
            for (HostItem item : itemMap.values()) {
                if (item.isApply()) {
                    return item;
                }
            }
        }
        return null;
    }

    @JsonIgnore
    public String getApplyId() {
        if ( MapUtils.isNotEmptyMap(itemMap)) {
            for (HostItem item : itemMap.values()) {
                if (item.isApply()) {
                    return item.getId();
                }
            }
        }
        return null;
    }


    @Override
    public String toString() {

        return new StringJoiner(", ", HostItems.class.getSimpleName() + "[", "]")
                .add("itemMap=" + itemMap)
                .toString();
    }
}
