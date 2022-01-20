package rpc.dubbo;

public class DemoServiceImpl implements DemoService {
    public DemoServiceImpl() {
        super();
    }

    public void sayHello(String name) {
        System.out.println("hello " + name);
    }
}
