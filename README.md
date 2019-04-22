# pf4j使用介绍
1.定义一个扩展点

(```)

    public interface AppDevice extends ExtensionPoint
    {
        String getId();
    
        String getResponse(AppRequest request);
    }
(```)