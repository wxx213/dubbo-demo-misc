/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.dubbo.demo.misc.consumer;

import com.sun.net.httpserver.HttpServer;
import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ReferenceConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.apache.dubbo.config.bootstrap.DubboBootstrap;
import org.apache.dubbo.config.utils.ReferenceConfigCache;
import org.apache.dubbo.demo.misc.DemoService;

import org.apache.dubbo.rpc.service.GenericService;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.CompletableFuture;

public class Application {
    public static void startWithXmlContext() {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("dubbo-consumer.xml");
        context.start();
        DemoService demoService = context.getBean("demoService", DemoService.class);
        // CompletableFuture<String> hello = demoService.sayHelloAsync("world");
        // System.out.println("result: " + hello.get());
        String hello = demoService.sayHello("world");
        System.out.println("result: " + hello);
    }

    private static boolean isClassic(String[] args) {
        return args.length > 0 && "classic".equalsIgnoreCase(args[0]);
    }

    private static void runWithBootstrap() {
        System.out.println("dubbo comsumer starting with bootstrap");
        ReferenceConfig<DemoService> reference = new ReferenceConfig<>();
        reference.setInterface(DemoService.class);
        reference.setGeneric("true");

        DubboBootstrap bootstrap = DubboBootstrap.getInstance();
        bootstrap.application(new ApplicationConfig("demo-consumer"))
                .registry(new RegistryConfig("zookeeper://192.168.2.101:2181"))
                .reference(reference)
                .start();

        DemoService demoService = ReferenceConfigCache.getCache().get(reference);
        String message = demoService.sayHello("dubbo");
        System.out.println(message);

        // generic invoke
        GenericService genericService = (GenericService) demoService;
        Object genericInvokeResult = genericService.$invoke("sayHello", new String[] { String.class.getName() },
                new Object[] { "dubbo generic invoke" });
        System.out.println(genericInvokeResult);
    }

    private static void runWithRefer() {
        System.out.println("dubbo comsumer starting with refer");
        ReferenceConfig<DemoService> reference = new ReferenceConfig<>();
        reference.setApplication(new ApplicationConfig("demo-consumer"));
        reference.setRegistry(new RegistryConfig("zookeeper://192.168.2.101:2181"));
        reference.setInterface(DemoService.class);
        DemoService service = reference.get();
        String message = service.sayHello("dubbo");
        System.out.println(message);
    }

    public static void startWithApiContext(String[] args) {
        if (isClassic(args)) {
            runWithRefer();
        } else {
            runWithBootstrap();
        }
    }
    public static void startWithXmlContextHttpServer() {
        // //监听8087端口，第二个参数小于等于0使用默认值，表示可同时接受请求的个数
        HttpServer server = null;
        try {
            server = HttpServer.create(new InetSocketAddress(8087), 0);
        } catch (IOException e) {
            e.printStackTrace();
        }
        server.createContext("/test", new TestHandler());
        server.start();
    }
    /**
     * In order to make sure multicast registry works, need to specify '-Djava.net.preferIPv4Stack=true' before
     * launch the application
     */
    public static void main(String[] args) throws Exception {
        //startWithXmlContext();
        // startWithApiContext(args);
        startWithXmlContextHttpServer();
    }
}
