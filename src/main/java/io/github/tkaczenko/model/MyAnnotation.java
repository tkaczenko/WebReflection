package io.github.tkaczenko.model;

import java.util.HashMap;

/**
 * Created by tkaczenko on 07.02.17.
 */
public class MyAnnotation {
    private final String name;
    private HashMap<String, String> values;

    public MyAnnotation(String name) {
        this.name = name;
    }

    public HashMap<String, String> getValues() {
        return values;
    }

    public String getName() {
        return name;
    }

    public void setValues(HashMap<String, String> values) {
        this.values = values;
    }
}
