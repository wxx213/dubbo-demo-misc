package io.seata.samples.dubbo;

import io.seata.samples.dubbo.starter.DubboAccountServiceStarter;
import io.seata.samples.dubbo.starter.DubboBusinessTester;
import io.seata.samples.dubbo.starter.DubboOrderServiceStarter;
import io.seata.samples.dubbo.starter.DubboStorageServiceStarter;

public class Application {
    public static void main(String[] args) {
        if (args.length > 0) {
            if ("account".equalsIgnoreCase(args[0])) {
                DubboAccountServiceStarter.start(args);
            } else if ("business".equalsIgnoreCase(args[0])) {
                DubboBusinessTester.start(args);
            } else if ("order".equalsIgnoreCase(args[0])) {
                DubboOrderServiceStarter.start(args);
            } else if ("storage".equalsIgnoreCase(args[0])) {
                DubboStorageServiceStarter.start(args);
            }
        } else {
            System.out.println("Invalid input, exiting.");
        }
    }
}
