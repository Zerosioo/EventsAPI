package io.github.zerosioo;

public abstract class BaseEvent {

    private final String name;
    private boolean async;

    protected BaseEvent() {
        this(false);
    }

    protected BaseEvent(boolean async) {
        this.name = getClass().getSimpleName();
        this.async = async;
    }

    public String getEventName() {
        return name;
    }

    public boolean isAsynchronous() {
        return async;
    }

    protected void setAsync(boolean async) {
        this.async = async;
    }

    public final void fire() {
        EventRegistry.getInstance().fireEvent(this);
    }
}
