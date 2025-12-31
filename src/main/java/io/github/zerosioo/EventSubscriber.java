package io.github.zerosioo;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface EventSubscriber {

    EventPriority priority() default EventPriority.NORMAL;

    boolean ignoreCancelled() default false;
}
