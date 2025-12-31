package com.zerosio.events;

public interface Cancellable {
    boolean isCancelled();
    void setCancelled(boolean cancel);
}
