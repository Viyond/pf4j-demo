package cn.jeremy.study.pf4j.demo.app.controller;

import cn.jeremy.study.pf4j.demo.api.AppDevice;
import cn.jeremy.study.pf4j.demo.api.domain.AppRequest;
import java.io.File;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.pf4j.PluginManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PluginController
{
    private static Logger logger = LoggerFactory.getLogger(PluginController.class);

    @Autowired
    PluginManager pluginManager;

    @RequestMapping("/find")
    public String find(String appId, String text)
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

    @RequestMapping("/load")
    public String load()
    {
        pluginManager.loadPlugins();
        pluginManager.startPlugins();
        return "ok";
    }

    @RequestMapping("/unload")
    public String unload(String pluginId)
    {
        pluginManager.unloadPlugin(pluginId);
        return "ok";
    }
}
