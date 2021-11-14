package com.asa.jtools.alfred;

import com.asa.base.utils.ListUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author andrew_asa
 * @date 2021/11/14.
 */
public class AlfredItems {

    private List<AlfredItem> items = new ArrayList<>();

    public AlfredItems() {

    }

    public AlfredItems(List<AlfredItem> items) {

        this.items = items;
    }

    public void addItem(AlfredItem item) {

        ListUtils.safeAdd(items, item);
    }

    public void merge(AlfredItems items) {

        if (items != null) {
            ListUtils.safeAdd(this.items,items.getItems());
        }
    }

    public String buildString() {

        StringBuffer bf = new StringBuffer();
        bf.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>\n");
        bf.append("<items>\n");
        for (AlfredItem item : items) {
            bf.append(item.buildString());
        }
        bf.append("</items>");
        return bf.toString();
    }

    public List<AlfredItem> getItems() {

        return items;
    }

    public void setItems(List<AlfredItem> items) {

        this.items = items;
    }
}
