package org.ritsuka.youji.util.yaconfig;

import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Map;
import java.util.Set;

public final class YaConfig {
    private static final String CFG_DEFAULT_PATH = "conf/config.yml";
    private static final String CFG_DEFAULT_PROPERTY = "app.configFile";
    private static final String CFG_JVMPROPS_SECTION = "jvm.properties";
    private static final Boolean verbose = true;
    private static Map parsed = null;

    static public boolean loadConfig() {
        String configPath = System.getProperty(CFG_DEFAULT_PROPERTY);

        if (configPath == null) {
            configPath = CFG_DEFAULT_PATH;
        }

        Yaml yaml = new Yaml();
        try {
            InputStream input = new FileInputStream(new File(configPath));

            parsed = (Map) yaml.load(input);
            if (parsed.containsKey(CFG_JVMPROPS_SECTION))
                setProperties((Map) parsed.get(CFG_JVMPROPS_SECTION), "");
            else if (verbose)
                System.out.println("Config doesn't contain JVM properties");

            return true;
        } catch (final Exception e) {
            System.err.println(String.format("YaConfig: Can't load config file. Use -D%s=relative/path/to/cfg.yml. Exception: %s", CFG_DEFAULT_PROPERTY, e.toString()));
            e.printStackTrace();
            //throw new ExceptionInInitializerError(e);
        }
        return false;
    }

    // set jvm properties using tree structure
    static private void setProperties(Map props, String basepath) {
        if (null != props) {
            Set<Map.Entry> entries = props.entrySet();
            for (Map.Entry entry:entries) {
                Object value = entry.getValue();
                String key = entry.getKey().toString();
                if (!basepath.isEmpty())
                    key = basepath + "." + key;

                if (!(value instanceof Map)) {
                    System.setProperty(key, value.toString());
                    if (verbose)
                        System.out.println(String.format("YaConfig: %s:=%s", key, value));
                }
                else
                    setProperties((Map)value, key);
            }
        }
    }


    @SuppressWarnings("unchecked")
    public static <T> T get(ITypedConfigKey<T> key) {
        Object val = parsed;
        for (String entry : key.getPath()) {
            if (val instanceof Map) {
                val = ((Map) val).get(entry);
            } else {
                /* we reached end earlier?.. */
                return key.getDefaultValue();
            }
        }

        if (val == null) {
            return key.getDefaultValue();
        }

        try {
            return (T) val;
        } catch (final ClassCastException e) {
            return null;
        }
    }
}
