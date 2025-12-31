package io.github.zerosioo;

import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class EventRegistry {

    private static EventRegistry instance;

    private final Map<Class<? extends BaseEvent>, List<EventSubscription>> subscriptions =
            new ConcurrentHashMap<>();

    private EventRegistry() {}

    public static EventRegistry getInstance() {
        if (instance == null) {
            instance = new EventRegistry();
        }
        return instance;
    }

    public void register(EventListener listener) {
        for (Method method : listener.getClass().getDeclaredMethods()) {

            EventSubscriber sub = method.getAnnotation(EventSubscriber.class);
            if (sub == null) continue;

            Class<?>[] params = method.getParameterTypes();
            if (params.length != 1 || !BaseEvent.class.isAssignableFrom(params[0])) {
                throw new IllegalArgumentException(
                        "Method " + method.getName() +
                        " must have exactly one BaseEvent parameter"
                );
            }

            @SuppressWarnings("unchecked")
            Class<? extends BaseEvent> eventClass =
                    (Class<? extends BaseEvent>) params[0];

            EventSubscription subscription = new EventSubscription(
                    listener,
                    method,
                    sub.priority(),
                    sub.ignoreCancelled()
            );

            subscriptions
                .computeIfAbsent(eventClass, k -> new ArrayList<>())
                .add(subscription);

            subscriptions.get(eventClass)
                .sort(Comparator.comparingInt(s -> s.getPriority().getSlot()));
        }
    }

    public void unregister(EventListener listener) {
        subscriptions.values()
                .forEach(list -> list.removeIf(s -> s.getListener() == listener));
    }

    void fireEvent(BaseEvent event) {
        List<EventSubscription> list = subscriptions.get(event.getClass());
        if (list == null) return;

        for (EventSubscription sub : list) {
            try {
                sub.execute(event);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public List<EventSubscription> getSubscriptions(Class<? extends BaseEvent> eventClass) {
        return subscriptions.getOrDefault(eventClass, Collections.emptyList());
    }
}
