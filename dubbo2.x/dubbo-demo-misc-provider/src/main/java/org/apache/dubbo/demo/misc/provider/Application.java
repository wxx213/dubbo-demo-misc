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
package org.apache.dubbo.demo.misc.provider;

import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ProtocolConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.apache.dubbo.config.ServiceConfig;
import org.apache.dubbo.config.bootstrap.DubboBootstrap;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.concurrent.CountDownLatch;

import org.apache.dubbo.demo.misc.DemoService;

public class Application {
    public static void startWithXmlContext() throws Exception {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("dubbo-provider.xml");
        context.start();
        System.in.read();
    }

    private static boolean isClassic(String[] args) {
        return args.length > 0 && "classic".equalsIgnoreCase(args[0]);
    }

    private static void startWithBootstrap() {
        System.out.println("dubbo service starting with bootstrap");
        ServiceConfig<DemoServiceImpl> service = new ServiceConfig<>();
        service.setInterface(DemoService.class);
        service.setRef(new DemoServiceImpl());
        service.setProtocol(new ProtocolConfig("dubbo", 20881));
        DubboBootstrap bootstrap = DubboBootstrap.getInstance();
        bootstrap.application(new ApplicationConfig("demo-provider"))
                .registry(new RegistryConfig("zookeeper://192.168.2.101:2181"))
                .service(service)
                .start()
                .await();
    }

    private static void startWithExport() throws InterruptedException {
        System.out.println("dubbo service starting with export");
        ServiceConfig<DemoServiceImpl> service = new ServiceConfig<>();
        service.setInterface(DemoService.class);
        service.setRef(new DemoServiceImpl());
        service.setApplication(new ApplicationConfig("demo-provider"));
        service.setRegistry(new RegistryConfig("zookeeper://192.168.2.101:2181"));
        service.setProtocol(new ProtocolConfig("dubbo", 20881));
        service.export();

        new CountDownLatch(1).await();
    }
    public static void starWithApiContext(String[] args) throws InterruptedException {
        if (isClassic(args)) {
            startWithExport();
        } else {
            startWithBootstrap();
        }
    }
    public static void main(String[] args) throws Exception {
        startWithXmlContext();
        // starWithApiContext(args);
    }
}

