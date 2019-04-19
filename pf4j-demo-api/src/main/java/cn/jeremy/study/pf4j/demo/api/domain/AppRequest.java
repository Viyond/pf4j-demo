package cn.jeremy.study.pf4j.demo.api.domain;

public class AppRequest
{

    private String appId;

    private String text;

    public AppRequest()
    {
    }

    public AppRequest(String appId, String text)
    {
        this.appId = appId;
        this.text = text;
    }

    public String getAppId()
    {
        return appId;
    }

    public void setAppId(String appId)
    {
        this.appId = appId;
    }

    public String getText()
    {
        return text;
    }

    public void setText(String text)
    {
        this.text = text;
    }

    @Override
    public String toString()
    {
        final StringBuffer sb = new StringBuffer("AppRequest{");
        sb.append("appId='").append(appId).append('\'');
        sb.append(", text='").append(text).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
