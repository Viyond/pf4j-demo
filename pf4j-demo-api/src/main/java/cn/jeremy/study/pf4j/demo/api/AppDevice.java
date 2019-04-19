package cn.jeremy.study.pf4j.demo.api;

import cn.jeremy.study.pf4j.demo.api.domain.AppRequest;
import org.pf4j.ExtensionPoint;

public interface AppDevice extends ExtensionPoint
{
    String getId();

    String getResponse(AppRequest request);
}
