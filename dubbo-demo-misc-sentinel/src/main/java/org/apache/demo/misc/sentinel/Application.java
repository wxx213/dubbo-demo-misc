package org.apache.demo.misc.sentinel;

public class Application {
    public static void main(String[] args) throws InterruptedException {
        if (args.length > 0 && "provider".equalsIgnoreCase(args[0])) {
            FooProviderBootstrap.startProvider(args);
        } else {
            FooConsumerBootstrap.startConsumer(args);
        }
    }
}
