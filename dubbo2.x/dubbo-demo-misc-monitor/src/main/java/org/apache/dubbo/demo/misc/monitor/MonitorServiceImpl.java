package org.apache.dubbo.demo.misc.monitor;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.dubbo.monitor.MonitorService;
import org.apache.dubbo.common.URL;

public class MonitorServiceImpl implements MonitorService {
    @Override
    public void collect(URL statistics) {
        if (statistics!=null){
            String application = statistics.getParameter(MonitorService.APPLICATION);
            String service = statistics.getParameter(MonitorService.INTERFACE);
            String method = statistics.getParameter(MonitorService.METHOD);
            String group = statistics.getParameter(MonitorService.GROUP);
            String version = statistics.getParameter(MonitorService.VERSION);
            String client = statistics.getParameter(MonitorService.CONSUMER);
            String server = statistics.getParameter(MonitorService.PROVIDER);
            String timestamp = statistics.getParameter(MonitorService.TIMESTAMP);
            String success = statistics.getParameter(MonitorService.SUCCESS);
            String failure = statistics.getParameter(MonitorService.FAILURE);
            String input = statistics.getParameter(MonitorService.INPUT);
            String output= statistics.getParameter( MonitorService.OUTPUT);
            String elapsed = statistics.getParameter(MonitorService.ELAPSED);
            String concurrent = statistics.getParameter(MonitorService.CONCURRENT);
            String maxInput = statistics.getParameter(MonitorService.MAX_INPUT);
            String maxOutput = statistics.getParameter(MonitorService.MAX_OUTPUT);
            String maxElapsed = statistics.getParameter(MonitorService.MAX_ELAPSED);
            String maxConcurrent = statistics.getParameter(MonitorService.MAX_CONCURRENT);
            String sd = "";
            Date d = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            sd = sdf.format(new Date(Long.parseLong(timestamp))); // 时间戳转换日期
            System.out.println("application:"+application
                    +",service:"+service+",method:"+method+",group:"+group+",version:"+version+",client:"+client+",server:"+server
                    +",time:"+sd+",success:"+success+",failure:"+failure+",input:"+input+",output:"+output
                    +",elapsed:"+elapsed+",concurrent:"+concurrent+",maxInput:"+maxInput+",maxOutput:"+maxOutput+",maxElapsed:"+maxElapsed
                    +",maxConcurrent:"+maxConcurrent
            );


        }

    }

    @Override
    public List<URL> lookup(URL query) {
        return null;
    }
}