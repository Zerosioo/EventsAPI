package com.zerosio.events;

public abstract class BaseEvent {

    private final String name;
    private final boolean async;

    public BaseEvent() {
        this(false);
    }

    public BaseEvent(boolean async) {
        this.name = getClass().getSimpleName();
        this.async = async;
    }

    public String getEventName() {
        return name;
    }

    public boolean isAsynchronous() {
        return async;
    }

    public final void fire() {
        EventRegistry.getInstance().fireEvent(this);
    }
}
