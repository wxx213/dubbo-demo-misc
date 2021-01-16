package org.apache.dubbo.demo.misc.consumer;

import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;
import org.apache.dubbo.demo.misc.DemoService;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;

public class TestHandler implements HttpHandler {
    public static DemoService demoService;

    TestHandler() {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("dubbo-consumer.xml");
        context.start();
        demoService = context.getBean("demoService", DemoService.class);
    }
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String hello = demoService.sayHello("world");
        // System.out.println("result: " + hello);
        //len>0：响应体必须发送指定长度；len=0：可发送任意长度，关闭OutputStream即可停止；len=-1：不会发响应体
        exchange.sendResponseHeaders(200, 0);
        String body = "result: " + hello + "\n";
        exchange.getResponseBody().write(body.getBytes());
        //先关闭打开的InputStream，再关闭打开的OutputStream
        exchange.close();
    }
}
