package cn.jeremy.study.pf4j.demo.readplugin;

import cn.jeremy.study.pf4j.demo.api.AppDevice;
import cn.jeremy.study.pf4j.demo.api.domain.AppRequest;
import org.pf4j.Extension;
import org.pf4j.Plugin;
import org.pf4j.PluginWrapper;
import org.pf4j.RuntimeMode;

public class ReadDevicePlugin extends Plugin
{
    public ReadDevicePlugin(PluginWrapper wrapper)
    {
        super(wrapper);
    }

    @Override
    public void start() {
        System.out.println("WelcomePlugin.start()");
        // for testing the development mode
        if (RuntimeMode.DEVELOPMENT.equals(wrapper.getRuntimeMode())) {
            System.out.println("WelcomePlugin");
        }
    }

    @Override
    public void stop() {
        System.out.println("WelcomePlugin.stop()");
    }


    @Extension
    public static class ReadAppDevice implements AppDevice{

        @Override
        public String getId()
        {
            return "read";
        }

        @Override
        public String getResponse(AppRequest request)
        {

            return "阅读app";
        }
    }
}
