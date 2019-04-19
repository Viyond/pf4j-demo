package cn.jeremy.study.pf4j.demo.app.controller;

import cn.jeremy.study.pf4j.demo.api.AppDevice;
import cn.jeremy.study.pf4j.demo.api.domain.AppRequest;
import org.pf4j.DefaultPluginManager;
import org.pf4j.PluginManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.io.File;
import java.util.List;

@RestController
public class HelloWorldController
{
    private static Logger logger = LoggerFactory.getLogger(HelloWorldController.class);

    final String pluginRoot = "J:\\git\\study\\pf4j-demo\\plugins";

    PluginManager pluginManager;

    @PostConstruct
    public void init()
    {
        pluginManager = new DefaultPluginManager(new File(pluginRoot).toPath());
        pluginManager.loadPlugins();
        pluginManager.startPlugins();
        List<AppDevice> extensions = pluginManager.getExtensions(AppDevice.class);
        System.out.println(extensions.size());
    }

    @RequestMapping("/hello")
    public String index(String appId,String text)
    {
        List<AppDevice> extensions = pluginManager.getExtensions(AppDevice.class);

        AppRequest request = new AppRequest(appId, text);

        for (AppDevice extension : extensions)
        {
            if (extension.getId().equals(appId))
            {
                return extension.getResponse(request);
            }
        }
        return "设备不存在：" + appId;
    }
}
