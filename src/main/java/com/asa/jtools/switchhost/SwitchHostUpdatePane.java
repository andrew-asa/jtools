package com.asa.jtools.switchhost;

import com.asa.jtools.switchhost.bean.HostItem;

/**
 * @author andrew_asa
 * @date 2021/10/22.
 */
public class SwitchHostUpdatePane extends SwitchHostAddPane {

    public SwitchHostUpdatePane() {

        super();
    }

    public void onSureAction(HostItem oldItem, HostItem newItem) {

        SwitchHostEvent event = new SwitchHostEvent(SwitchHostUpdatePane.this,
                                                       SwitchHostEvent.SWITCH_HOST_UPDATE_EVENT,
                                                       newItem, oldItem);
        fireEvent(event);
    }
}
