package cn.jeremy.study.pf4j.demo;

import cn.jeremy.study.pf4j.demo.api.AppDevice;
import cn.jeremy.study.pf4j.demo.api.domain.AppRequest;
import org.apache.commons.lang.StringUtils;
import org.pf4j.Extension;
import org.pf4j.Plugin;
import org.pf4j.PluginWrapper;

public class vedioplugin extends Plugin {
    public vedioplugin(PluginWrapper wrapper) {
        super(wrapper);
    }

    @Extension
    public static class VedioAppDevice implements AppDevice {

        @Override
        public String getId() {
            return "vedio";
        }

        @Override
        public String getResponse(AppRequest request) {
            return "视频app，appId：".concat(request.getAppId()).concat(", text：").concat(StringUtils.defaultString(request.getText()));
        }
    }
}
