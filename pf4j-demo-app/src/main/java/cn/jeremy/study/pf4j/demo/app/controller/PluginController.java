package cn.jeremy.study.pf4j.demo.app.controller;

import cn.jeremy.study.pf4j.demo.api.AppDevice;
import cn.jeremy.study.pf4j.demo.api.domain.AppRequest;
import org.apache.commons.lang.StringUtils;
import org.pf4j.PluginManager;
import org.pf4j.PluginWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.File;
import java.util.List;

@Controller
public class PluginController {
    private static Logger logger = LoggerFactory.getLogger(PluginController.class);

    @Autowired
    PluginManager pluginManager;

    @RequestMapping("/index")
    public String index(Model model) {
        loadPlugins(model);
        return "index";
    }

    @RequestMapping("/find")
    public String find(String appId, String text, Model model) {

        model.addAttribute("pluginResponse", "设备不存在：" + appId);
        model.addAttribute("appId", appId);
        model.addAttribute("text", text);
        List<AppDevice> extensions = pluginManager.getExtensions(AppDevice.class);
        AppRequest request = new AppRequest(appId, text);
        for (AppDevice extension : extensions) {
            if (extension.getId().equals(appId)) {
                model.addAttribute("pluginResponse", extension.getResponse(request));
            }
        }
        loadPlugins(model);
        return "index";
    }

    @RequestMapping("/load")
    public String load(String fileName, Model model) {
        model.addAttribute("fileName", fileName);
        if (StringUtils.isEmpty(fileName)) {
            model.addAttribute("loadPluginId", "fileName 不能为空");
            loadPlugins(model);
            return "index";
        }
        String filePath = pluginManager.getPluginsRoot().toString().concat("/").concat(fileName.trim());
        model.addAttribute("loadPluginId", pluginManager.loadPlugin(new File(filePath).toPath()));
        loadPlugins(model);
        return "index";
    }

    @RequestMapping("/unload")
    public String unload(String pluginId, Model model) {
        model.addAttribute("unloadPluginId", pluginId);
        pluginManager.unloadPlugin(pluginId);
        loadPlugins(model);
        return "index";
    }

    @RequestMapping("/enable")
    public String enable(String pluginId, Model model) {
        model.addAttribute("enablePluginId", pluginId);
        pluginManager.enablePlugin(pluginId);
        loadPlugins(model);
        return "index";
    }

    @RequestMapping("/disable")
    public String disable(String pluginId, Model model) {
        model.addAttribute("disablePluginId", pluginId);
        pluginManager.disablePlugin(pluginId);
        loadPlugins(model);
        return "index";
    }

    @RequestMapping("/start")
    public String start(String pluginId, Model model) {
        model.addAttribute("startPluginId", pluginId);
        pluginManager.startPlugin(pluginId);
        loadPlugins(model);
        return "index";
    }

    @RequestMapping("/stop")
    public String stop(String pluginId, Model model) {
        model.addAttribute("stopPluginId", pluginId);
        pluginManager.stopPlugin(pluginId);
        loadPlugins(model);
        return "index";
    }

    @RequestMapping("/delete")
    public String delete(String pluginId, Model model) {
        model.addAttribute("deletePluginId", pluginId);
        pluginManager.deletePlugin(pluginId);
        loadPlugins(model);
        return "index";
    }

    private void loadPlugins(Model model) {
        List<PluginWrapper> plugins = pluginManager.getPlugins();
        model.addAttribute("plugins", plugins);
    }
}
