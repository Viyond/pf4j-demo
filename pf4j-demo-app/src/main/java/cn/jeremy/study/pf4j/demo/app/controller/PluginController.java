package cn.jeremy.study.pf4j.demo.app.controller;

import cn.jeremy.study.pf4j.demo.api.AppDevice;
import cn.jeremy.study.pf4j.demo.api.domain.AppRequest;
import org.apache.commons.lang.StringUtils;
import org.pf4j.PluginManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.util.List;

@RestController
public class PluginController {
    private static Logger logger = LoggerFactory.getLogger(PluginController.class);

    @Autowired
    PluginManager pluginManager;

    @RequestMapping("/find")
    public String find(String appId, String text) {
        List<AppDevice> extensions = pluginManager.getExtensions(AppDevice.class);

        AppRequest request = new AppRequest(appId, text);
        for (AppDevice extension : extensions) {
            if (extension.getId().equals(appId)) {
                return extension.getResponse(request);
            }
        }
        return "设备不存在：" + appId;
    }

    @RequestMapping("/load")
    public String load(String fileName) {

        if (StringUtils.isEmpty(fileName)) {
            return "fileName 不能为空";
        }
        String filePath = pluginManager.getPluginsRoot().toString().concat("/").concat(fileName);
        return pluginManager.loadPlugin(new File(filePath).toPath());
    }

    @RequestMapping("/unload")
    public String unload(String pluginId) {
        return String.valueOf(pluginManager.unloadPlugin(pluginId));
    }

    @RequestMapping("/enable")
    public String enable(String pluginId) {
        return String.valueOf(pluginManager.enablePlugin(pluginId));
    }

    @RequestMapping("/disable")
    public String disable(String pluginId) {
        return String.valueOf(pluginManager.disablePlugin(pluginId));
    }

    @RequestMapping("/start")
    public String start(String pluginId) {
        return String.valueOf(pluginManager.startPlugin(pluginId));
    }

    @RequestMapping("/stop")
    public String stop(String pluginId) {
        return String.valueOf(pluginManager.stopPlugin(pluginId));
    }

    @RequestMapping("/delete")
    public String delete(String pluginId) {
        return String.valueOf(pluginManager.deletePlugin(pluginId));
    }
}
