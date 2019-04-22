# pf4j入门使用介绍
## 定义一个扩展点

    public interface AppDevice extends ExtensionPoint
    {
        String getId();
    
        String getResponse(AppRequest request);
    }
## 定义一个插件

    public class ReadDevicePlugin extends Plugin
    {
        public ReadDevicePlugin(PluginWrapper wrapper)
        {
            super(wrapper);
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
## 插件元数据

### 插件管理器使用PluginDescriptorFinder搜索插件元数据
    
    protected PluginDescriptorFinder createPluginDescriptorFinder() {
        return (new CompoundPluginDescriptorFinder()).add(new PropertiesPluginDescriptorFinder()).add(new ManifestPluginDescriptorFinder());
    }
### 插件元数据可以通过plugin.properties

    plugin.class=org.pf4j.demo.welcome.WelcomePlugin
    plugin.id=welcome-plugin
    plugin.version=0.0.1
    plugin.requires=1.0.0
    plugin.dependencies=x, y, z
    plugin.description=My example plugin
    plugin.provider=Decebal Suiu
    plugin.license=Apache License 2.0

### 或者META-INF/MANIFEST.MF提供

    Plugin-Class: org.pf4j.demo.welcome.WelcomePlugin
    Plugin-Id: welcome-plugin
    Plugin-Version: 0.0.1
    Plugin-Requires: 1.0.0
    Plugin-Dependencies: x, y, z
    Plugin-Description: My example plugin
    Plugin-Provider: Decebal Suiu
    Plugin-License: Apache License 2.0
## 关于插件依赖性的介绍
### 插件可能彼此依赖。如上所述，这些依赖性在插件元数据中指定。要将某个插件作为依赖项引用，您需要提供其指定的插件ID。
- 如果pluginA依赖于另一个pluginB，你可以在pluginA的元数据中设置：

    `Plugin-Dependencies: pluginB` 
- 如果pluginA依赖于版本1.0.0中的另一个pluginB，则可以在pluginA的元数据中设置：

    `Plugin-Dependencies: pluginB@1.0`
- 如果pluginA依赖于从1.0.0版开始的另一个pluginB，你可以在pluginA的元数据中设置：

    `Plugin-Dependencies: pluginB@>=1.0.0`
- 如果pluginA依赖于从版本1.0.0到2.0.0（不包括）的另一个插件B，则可以在pluginA的元数据中设置：
    
    `Plugin-Dependencies: pluginB@>=1.0.0 & <2.0.0`
- 如果pluginA依赖于另一个pluginB从1.0.0版本开始到2.0.0（包括），你可以在元数据集pluginA：
   
    `Plugin-Dependencies: pluginB@>=1.0.0 & <=2.0.0`
- 您还可以使用逗号分隔的相同模式定义多个插件依赖项：
    
    `Plugin-Dependencies: pluginB@>=1.0.0 & <=2.0.0, pluginC@>=0.0.1 & <=0.1.0`
这些依赖性被认为是必需的。如果所有依赖项都已完成，插件管理器将仅在运行时创建一个插件。

### 可选的插件依赖项
或者，您也可以通过在插件ID后面添加问号来定义插件之间的可选依赖项 - 例如：

`Plugin-Dependencies: pluginB?`

要么

`Plugin-Dependencies: pluginB?@1.0`

在这种情况下，即使在运行时未完成依赖项，仍会加载pluginA。
## 插件的管理
插件通过PluginManager进行管理，默认的实现是DefaultPluginManager，我们可以通过复写DefaultPluginManager的方法，定义自己的PluginManager
    
    PluginManager pluginManager = new DefaultPluginManager(new File("C:\\git\\study\\pf4j-demo\\plugins").toPath())
        {
            @Override
            protected PluginLoader createPluginLoader()
            {
                return new JarPluginLoader(this)
                {
                    @Override
                    public ClassLoader loadPlugin(Path pluginPath, PluginDescriptor pluginDescriptor)
                    {
                        PluginClassLoader
                            pluginClassLoader = new PluginClassLoader(this.pluginManager,
                            pluginDescriptor,
                            this.getClass().getClassLoader(),
                            true);
                        pluginClassLoader.addFile(pluginPath.toFile());
                        return pluginClassLoader;
                    }
                };
            }
        };
## 插件的生命周期
 作为PF4J消费者，您的应用程序可以完全控制每个插件（状态）。因此，您可以使用PluginManager（以编程方式）加载（load），卸载（unload），启用（enable），禁用（disable），启动（start），停止（stop）和删除（delete）某个插件。
 - 加载（load）
    ```
    public String load(String fileName) {
       
               if (StringUtils.isEmpty(fileName)) {
                   return "fileName 不能为空";
               }
               String filePath = pluginManager.getPluginsRoot().toString().concat("/").concat(fileName);
               return pluginManager.loadPlugin(new File(filePath).toPath());
           }
 - 卸载（unload）
    ```
    public String unload(String pluginId) {
            return String.valueOf(pluginManager.unloadPlugin(pluginId));
        }
 - 启用（enable）
    ```
    public String enable(String pluginId) {
            return String.valueOf(pluginManager.enablePlugin(pluginId));
        }
 - 禁用（disable）
    ```
    public String disable(String pluginId) {
            return String.valueOf(pluginManager.disablePlugin(pluginId));
        }
 - 启动（start）
    ```
    public String start(String pluginId) {
            return String.valueOf(pluginManager.startPlugin(pluginId));
        }
 - 停止（stop）
    ```
    public String stop(String pluginId) {
            return String.valueOf(pluginManager.stopPlugin(pluginId));
        }
 - 删除（delete）
    ```
    public String delete(String pluginId) {
            return String.valueOf(pluginManager.deletePlugin(pluginId));
        }