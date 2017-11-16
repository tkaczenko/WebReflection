package io.github.tkaczenko.model;

import java.util.List;

/**
 * Created by tkaczenko on 07.02.17.
 */
public class MyField {
    private final String name;
    private List<MyAnnotation> myAnnotations;
    private List<String> modificators;
    private String type;
    private String value;

    public MyField(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
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
}
