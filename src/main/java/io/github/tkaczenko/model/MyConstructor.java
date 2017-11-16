package io.github.tkaczenko.model;

import java.util.List;

/**
 * Created by tkaczenko on 09.02.17.
 */
public class MyConstructor {
    private final String name;
    private List<String> modificators;
    private List<String> params;

    public MyConstructor(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public List<String> getModificators() {
        return modificators;
    }

    public void setModificators(List<String> modificators) {
        this.modificators = modificators;
    }

    public List<String> getParams() {
        return params;
    }

    public void setParams(List<String> params) {
        this.params = params;
    }
}
