package server.application;

import com.google.inject.Singleton;

import java.io.*;
import java.util.Properties;

public class ApplicationProperties extends Properties {
    public ApplicationProperties() throws IOException {

        String propertiesConfig =System.getProperty("applicationPropertiesConfig");
        this.init("/application.properties");
    }

    public ApplicationProperties(String propertiesConfig) {
        this.init(propertiesConfig);
    }

    private void init(String applicationPropertiesConfig) {
        try {
            this.load(this.getClass().getResourceAsStream(applicationPropertiesConfig));
        }
        catch(IOException e) {
            System.out.print("\n Can not load properties file -> " + applicationPropertiesConfig);
        }
    }

    public String s(String key) {
        return this.getProperty(key);
    }

    public int i(String key) {
        return Integer.valueOf(this.getProperty(key));
    }

    public boolean b(String key) {
        return Boolean.valueOf(this.getProperty(key));
    }
}
