package cn.jeremy.study.pf4j.demo.readplugin;

import cn.jeremy.study.pf4j.demo.api.AppDevice;
import cn.jeremy.study.pf4j.demo.api.domain.AppRequest;
import org.apache.commons.lang.StringUtils;
import org.pf4j.Extension;
import org.pf4j.Plugin;
import org.pf4j.PluginWrapper;

public class ReadDevicePlugin extends Plugin {
    public ReadDevicePlugin(PluginWrapper wrapper) {
        super(wrapper);
    }


    @Extension
    public static class ReadAppDevice implements AppDevice {

        @Override
        public String getId() {
            return "read";
        }

        @Override
        public String getResponse(AppRequest request) {
            return "阅读app，appId：".concat(request.getAppId()).concat(", text：").concat(StringUtils.defaultString(request.getText()));
        }
    }
}
