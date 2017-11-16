package io.github.tkaczenko.model;

import java.util.List;

/**
 * Created by tkaczenko on 07.02.17.
 */
public class MyMethod {
    private List<MyAnnotation> myAnnotations;
    private List<String> modificators;
    private String type;
    private final String name;
    private List<String> exceptions;
    private List<String> params;

    public MyMethod(String name) {
        this.name = name;
    }

    public List<MyAnnotation> getMyAnnotations() {
        return myAnnotations;
    }

    public void setMyAnnotations(List<MyAnnotation> myAnnotations) {
        this.myAnnotations = myAnnotations;
    }

    public List<String> getModificators() {
        return modificators;
    }

    public void setModificators(List<String> modificators) {
        this.modificators = modificators;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public List<String> getParams() {
        return params;
    }

    public void setParams(List<String> params) {
        this.params = params;
    }
}
