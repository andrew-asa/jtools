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

    /**
     * 刷新
     */
    public static final EventType<SwitchHostEvent> SWITCH_HOST_REFRESH_EVENT =
            new EventType<>(Event.ANY, "SWITCH_HOST_REFRESH_EVENT");

    /**
     * 父节点请求添加叶子节点
     */
    public static final EventType<SwitchHostEvent> SWITCH_HOST_ADD_CHILD_EVENT =
            new EventType<>(Event.ANY, "SWITCH_HOST_ADD_CHILD_EVENT");

    private HostItem value;

    private HostItem oldValue;

    public SwitchHostEvent(final Object source,
                           final EventType<SwitchHostEvent> eventType, HostItem value) {

        super(source, Event.NULL_SOURCE_TARGET, eventType);
        this.value = value;
    }

    public SwitchHostEvent(final Object source,
                           final EventType<SwitchHostEvent> eventType, HostItem value, HostItem oldValue) {

        super(source, Event.NULL_SOURCE_TARGET, eventType);
        this.value = value;
        this.oldValue = oldValue;
    }

    @Override
    public EventType<SwitchHostEvent> getEventType() {

        return (EventType<SwitchHostEvent>) super.getEventType();
    }

    public HostItem getValue() {

        return value;
    }

    public void setValue(HostItem value) {

        this.value = value;
    }

    public HostItem getOldValue() {

        return oldValue;
    }

    public void setOldValue(HostItem oldValue) {

        this.oldValue = oldValue;
    }
}
