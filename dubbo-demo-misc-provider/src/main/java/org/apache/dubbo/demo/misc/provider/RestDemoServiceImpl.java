package org.apache.dubbo.demo.misc.provider;


import org.apache.dubbo.demo.misc.RestDemoService;
import org.apache.dubbo.rpc.RpcContext;

import java.util.Map;

public class RestDemoServiceImpl implements RestDemoService {
    private static Map<String, Object> context;
    private boolean called;

    public String sayHello(String name) {
        called = true;
        return "Hello, " + name;
    }


    public boolean isCalled() {
        return called;
    }

    @Override
    public Integer hello(Integer a, Integer b) {
        context = RpcContext.getServerAttachment().getObjectAttachments();
        return a + b;
    }

    @Override
    public String error() {
        throw new RuntimeException();
    }

    public static Map<String, Object> getAttachments() {
        return context;
    }

    @Override
    public String getRemoteApplicationName() {
        return RpcContext.getServiceContext().getRemoteApplicationName();
    }
}

