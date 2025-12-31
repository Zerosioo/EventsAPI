package io.github.zerosioo;

public interface Cancellable {
    boolean isCancelled();
    void setCancelled(boolean cancel);
}
