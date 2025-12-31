package io.github.zerosioo;

import java.lang.reflect.Method;

public class EventSubscription {

    private final EventListener listener;
    private final Method method;
    private final EventPriority priority;
    private final boolean ignoreCancelled;

    public EventSubscription(
            EventListener listener,
            Method method,
            EventPriority priority,
            boolean ignoreCancelled
    ) {
        this.listener = listener;
        this.method = method;
        this.priority = priority;
        this.ignoreCancelled = ignoreCancelled;
        this.method.setAccessible(true);
    }

    public void execute(BaseEvent event) throws Exception {
        if (event instanceof Cancellable &&
            ((Cancellable) event).isCancelled() &&
            ignoreCancelled) {
            return;
        }
        method.invoke(listener, event);
    }

    public EventPriority getPriority() {
        return priority;
    }

    public EventListener getListener() {
        return listener;
    }
}
