package com.asa.jtools.switchhost.bean;

import com.asa.base.utils.MapUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;

/**
 * @author andrew_asa
 * @date 2021/10/11.
 */
public class HostItems {

    public Map<String,HostItem> itemMap;

    public Map<String, HostItem> getItemMap() {

        return itemMap;
    }

    public void setItemMap(Map<String, HostItem> itemMap) {

        this.itemMap = itemMap;
    }

    public boolean existItem() {

        return MapUtils.isNotEmptyMap(itemMap);
    }

    public List<HostItem> getItems(HostItem.HostType type) {

        List<HostItem> ret = new ArrayList<>();
        if (type != null && MapUtils.isNotEmptyMap(itemMap)) {
           itemMap.values().forEach(item->{
               if (type.equals(item.getType())) {
                   ret.add(item);
               }
           });
        }
        return ret;
    }


    @Override
    public String toString() {

        return new StringJoiner(", ", HostItems.class.getSimpleName() + "[", "]")
                .add("itemMap=" + itemMap)
                .toString();
    }
}
