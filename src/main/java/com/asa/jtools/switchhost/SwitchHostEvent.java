package com.asa.jtools.switchhost;

import com.asa.jtools.switchhost.bean.HostItem;
import javafx.event.Event;
import javafx.event.EventType;

/**
 * @author andrew_asa
 * @date 2021/10/20.
 */
public class SwitchHostEvent extends Event {

    /**
     * 增
     */
    public static final EventType<SwitchHostEvent> SWITCH_HOST_ADD_EVENT =
            new EventType<>(Event.ANY, "SWITCH_HOST_ADD_EVENT");

    /**
     * 删
     */
    public static final EventType<SwitchHostEvent> SWITCH_HOST_REMOVE_EVENT =
            new EventType<>(Event.ANY, "SWITCH_HOST_REMOVE_EVENT");

    /**
     * 改
     */
    public static final EventType<SwitchHostEvent> SWITCH_HOST_UPDATE_EVENT =
            new EventType<>(Event.ANY, "SWITCH_HOST_UPDATE_EVENT");

    private HostItem item;

    public SwitchHostEvent(final Object source,
                           final EventType<SwitchHostEvent> eventType, HostItem item) {

        super(source, Event.NULL_SOURCE_TARGET, eventType);
        this.item = item;
    }

    @Override
    public EventType<SwitchHostEvent> getEventType() {

        return (EventType<SwitchHostEvent>) super.getEventType();
    }

    public Object getItem() {

        return item;
    }

    public void setItem(HostItem item) {

        this.item = item;
    }
}
