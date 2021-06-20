package org.apache.dubbo.demo.misc.provider;


import org.apache.dubbo.demo.misc.GreetingService;

/**
 *
 */
public class GreetingServiceImpl implements GreetingService {
    @Override
    public String hello() {
        return "Greetings!";
    }
}

