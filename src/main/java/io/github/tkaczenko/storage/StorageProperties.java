package io.github.tkaczenko.storage;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Created by tkaczenko on 14.02.17.
 */
@ConfigurationProperties("web/storage")
public class StorageProperties {
    private String location = "desktop";

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
