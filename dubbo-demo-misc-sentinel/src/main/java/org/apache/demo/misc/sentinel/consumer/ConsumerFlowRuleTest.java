package org.apache.demo.misc.sentinel.consumer;

import com.alibaba.csp.sentinel.adapter.dubbo.config.DubboAdapterGlobalConfig;
import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.SentinelRpcException;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import org.apache.demo.misc.sentinel.FooService;
import org.apache.dubbo.rpc.AsyncRpcResult;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.ArrayList;
import java.util.List;

public class ConsumerFlowRuleTest {
    private static final String INTERFACE_RES_KEY = FooService.class.getName();
    private static final String RES_KEY = INTERFACE_RES_KEY + ":sayHello(java.lang.String)";

    public static void TestFlowRule(String[] args) {
        AnnotationConfigApplicationContext consumerContext = new AnnotationConfigApplicationContext();
        consumerContext.register(ConsumerConfiguration.class);
        consumerContext.refresh();

        FooServiceConsumer service = consumerContext.getBean(FooServiceConsumer.class);

        for (int i = 0; i < 15; i++) {
            try {
                String message = service.sayHello("dubbo");
                System.out.println("Simple test success: " + message);
            } catch (SentinelRpcException ex) {
                System.out.println("Simple test Blocked");
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        // method flowcontrol
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        initFlowRule(60, true);
        for (int i = 0; i < 10; i++) {
            try {
                String message = service.sayHello("Eric");
                System.out.println("Flow rule test success: " + message);
            } catch (SentinelRpcException ex) {
                System.out.println("Flow rule test locked");
                System.out.println("fallback:" + service.doAnother());

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        // fallback to result
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        registryCustomFallback();

        for (int i = 0; i < 10; i++) {
            try {
                String message = service.sayHello("Eric");
                System.out.println("CustomFallback test Result: " + message);
            } catch (SentinelRpcException ex) {
                System.out.println("CustomFallback test Blocked");
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        // fallback to exception
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        registryCustomFallbackForCustomException();

        for (int i = 0; i < 10; i++) {
            try {
                String message = service.sayHello("Eric");
                System.out.println("CustomFallbackForCustomException Result: " + message);
            } catch (SentinelRpcException ex) {
                System.out.println("CustomFallbackForCustomException Blocked");
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        registryCustomFallbackWhenFallbackError();
        for (int i = 0; i < 10; i++) {
            try {
                String message = service.sayHello("Eric");
                System.out.println("CustomFallbackWhenFallbackError Result: " + message);
            } catch (SentinelRpcException ex) {
                System.out.println("CustomFallbackWhenFallbackError Blocked");
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    public static void registryCustomFallback() {
        DubboAdapterGlobalConfig.setConsumerFallback(
                (invoker, invocation, ex) -> AsyncRpcResult.newDefaultAsyncResult("fallback", invocation));

    }

    public static void registryCustomFallbackForCustomException() {
        DubboAdapterGlobalConfig.setConsumerFallback(
                (invoker, invocation, ex) -> AsyncRpcResult.newDefaultAsyncResult(new RuntimeException("fallback"), invocation));
    }

    public static void registryCustomFallbackWhenFallbackError() {
        DubboAdapterGlobalConfig.setConsumerFallback(
                (invoker, invocation, ex) -> {
                    throw new RuntimeException("fallback");
                });
    }


    private static void initFlowRule(int interfaceFlowLimit, boolean method) {
        FlowRule flowRule = new FlowRule(INTERFACE_RES_KEY)
                .setCount(interfaceFlowLimit)
                .setGrade(RuleConstant.FLOW_GRADE_QPS);
        List<FlowRule> list = new ArrayList<>();
        if (method) {
            FlowRule flowRule1 = new FlowRule(RES_KEY)
                    .setCount(60)
                    .setGrade(RuleConstant.FLOW_GRADE_QPS);
            list.add(flowRule1);
        }
        list.add(flowRule);
        FlowRuleManager.loadRules(list);
    }
}
