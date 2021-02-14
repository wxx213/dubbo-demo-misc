package rpc.dubbo;

import org.apache.dubbo.rpc.Protocol;
import org.apache.dubbo.rpc.ProxyFactory;
import org.apache.dubbo.common.URL;
import org.apache.dubbo.common.extension.ExtensionLoader;

import java.io.IOException;

public class DubboProtocolTest {
    public static final int DEFAULT_PORT = 20880;

    private static void startSever() {
        Protocol protocol = ExtensionLoader.getExtensionLoader(Protocol.class).getAdaptiveExtension();
        ProxyFactory proxy = ExtensionLoader.getExtensionLoader(ProxyFactory.class).getAdaptiveExtension();
        DemoService service = new DemoServiceImpl();
        int port = DEFAULT_PORT;
        protocol.export(proxy.getInvoker(service, DemoService.class, URL.valueOf("dubbo://127.0.0.1:" + port + "/" + DemoService.class.getName() + "server=netty4")));
        try {
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private static void startClient() {
        Protocol protocol = ExtensionLoader.getExtensionLoader(Protocol.class).getAdaptiveExtension();
        ProxyFactory proxy = ExtensionLoader.getExtensionLoader(ProxyFactory.class).getAdaptiveExtension();
        DemoService service = new DemoServiceImpl();
        int port = DEFAULT_PORT;
        service = proxy.getProxy(protocol.refer(DemoService.class, URL.valueOf("dubbo://127.0.0.1:" + port + "/" + DemoService.class.getName()).addParameter("timeout",
                3000L)));
        // service.sayHello("wxx");
    }
    public static void testDubboProtocol(String[] args) {
        if (args.length > 0 && "rpc_dubbo_server".equalsIgnoreCase(args[0])) {
            startSever();
        } else if (args.length > 0 && "rpc_dubbo_client".equalsIgnoreCase(args[0])) {
            startClient();
        } else {
            System.out.println("Invalid request for test dubbo protocol.");
        }
    }
}
