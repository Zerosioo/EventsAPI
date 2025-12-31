# EventsAPI
A lightweight, annotation-based event system for Java applications.  
Designed similarly to Bukkit/Spigot events, with a simple API and zero dependencies.

## Features

- Base `BaseEvent` class
- `event.fire()` dispatching
- `@EventSubscriber` annotation
- Event priorities
- Cancellable events
- Singleton `EventRegistry`
- No external libraries

## Installation
Add this inside your repositories
```xml
<repository>
    <id>github</id>
    <url>https://maven.pkg.github.com/Zerosioo/EventsAPI</url>
</repository>
```
And add this inside your dependencies
```xml
<dependency>
    <groupId>com.zerosio.events</groupId>
    <artifactId>EventsAPI</artifactId>
    <version>1.0.0</version>
</dependency>
```

## Examples
### Creating a Event
```java
public class ProductOrderEvent extends BaseEvent {

    private final Product product;
    private final User user;

    public ProductOrderEvent(Product product, User user) {
        this.product = product;
        this.user = user;
    }

    public Product getProduct() {
        return product;
    }

    public User getUser() {
        return user;
    }
}
```

### Creating a Cancellable Event
```java
public class ProductCancelEvent extends BaseEvent implements Cancellable {

    private final Product product;
    private final User user;
    private boolean cancelled;

    public ProductCancelEvent(Product product, User user) {
        this.product = product;
        this.user = user;
    }

    public Product getProduct() {
        return product;
    }

    public User getUser() {
        return user;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean cancel) {
        this.cancelled = cancel;
    }
}
```

### Fire an Event
```java
new ProductOrderEvent(product, user).fire();

ProductCancelEvent event = new ProductCancelEvent(product, user);
event.fire();

if (event.isCancelled()) {
    return;
}

```

### Create a Listener
```java
public class ProductListener implements EventListener {

    @EventSubscriber(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void onOrder(ProductOrderEvent event) {
        Product product = event.getProduct();
        User user = event.getUser();
    }

    @EventSubscriber
    public void onCancel(ProductCancelEvent event) {
        Product product = event.getProduct();
        User user = event.getUser();

        event.setCancelled(true);
    }
}

```

### Register a Listener
```java
// must be in main method

EventRegistry registry = EventRegistry.getInstance();

registry.register(new ProductListener());
```
