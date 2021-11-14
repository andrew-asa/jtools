package com.asa.jtools.alfred;

import com.asa.base.utils.StringUtils;

import java.util.StringJoiner;

/**
 * @author andrew_asa
 * @date 2021/11/14.
 */
public class AlfredItem {

    String uid;

    String args;

    boolean valid = true;

    boolean autocomplete = true;

    String title;

    String subtitle;

    String icon = "icon.png";

    public AlfredItem() {

    }

    public AlfredItem(String uid, String args, String title, String subtitle) {
        this.uid=uid;
        this.args=args;
        this.title=title;
        this.subtitle=subtitle;
    }

    public String getUid() {

        return uid;
    }

    public void setUid(String uid) {

        this.uid = uid;
    }

    public String getArgs() {

        return args;
    }

    public void setArgs(String args) {

        this.args = args;
    }

    public boolean isValid() {

        return valid;
    }

    public void setValid(boolean valid) {

        this.valid = valid;
    }

    public boolean isAutocomplete() {

        return autocomplete;
    }

    public void setAutocomplete(boolean autocomplete) {

        this.autocomplete = autocomplete;
    }

    public String getTitle() {

        return title;
    }

    public void setTitle(String title) {

        this.title = title;
    }

    public String getSubtitle() {

        return subtitle;
    }

    public void setSubtitle(String subtitle) {

        this.subtitle = subtitle;
    }

    public String getIcon() {

        return icon;
    }

    public void setIcon(String icon) {

        this.icon = icon;
    }

    public static AlfredItem build(String uid, String args, String title, String subtitle) {

        return new AlfredItem(uid, args, title, subtitle);
    }

    public static String buildString(String uid, String args, String title, String subtitle) {

        return buildString(uid, args, true, true,
                           title,
                           subtitle,
                           "icon.png");
    }

    public static String buildString(String uid, String args, boolean valid, boolean autocomplete,
                                     String title,
                                     String subtitle,
                                     String icon) {

        StringBuffer sb = new StringBuffer();
        sb.append(StringUtils.messageFormat("<item uid=\"{}\" arg=\"{}\" valid=\"{}\" autocomplete=\"{}\">\n", uid, args, valid, autocomplete));
        sb.append(StringUtils.messageFormat("<title>{}</title>\n", title));
        sb.append(StringUtils.messageFormat("<subtitle>{}</subtitle>\n", subtitle));
        sb.append(StringUtils.messageFormat("<icon>{}</icon>\n", icon));
        sb.append(StringUtils.messageFormat("</item>\n"));
        return sb.toString();
    }

    public String buildString() {

        return buildString(uid, args, valid, autocomplete, title, subtitle, icon);
    }
}
