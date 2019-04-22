package cn.jeremy.study.pf4j.demo.app;

import java.io.File;
import java.nio.file.Path;
import org.pf4j.DefaultPluginManager;
import org.pf4j.JarPluginLoader;
import org.pf4j.PluginClassLoader;
import org.pf4j.PluginDescriptor;
import org.pf4j.PluginLoader;
import org.pf4j.PluginManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfiguration
{

    @Bean(name = "pluginManager")
    public PluginManager pluginManager()
    {
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
        pluginManager.loadPlugins();
        pluginManager.startPlugins();
        return pluginManager;
    }
}
