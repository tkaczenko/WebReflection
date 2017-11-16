package io.github.tkaczenko.model;

import java.util.List;
import java.util.Set;

/**
 * Created by tkaczenko on 07.02.17.
 */
public class MyClass {
    private final String name;
    private String pack;
    private Set<String> imports;
    private List<MyAnnotation> myAnnotations;
    private List<String> modificators;
    private List<String> superclasses;
    private List<String> interfaces;
    private List<MyMethod> myMethods;
    private List<MyField> myFields;
    private List<MyConstructor> myConstructors;

    public MyClass(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getPack() {
        return pack;
    }

    public void setPack(String pack) {
        this.pack = pack;
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

    public List<String> getSuperclasses() {
        return superclasses;
    }

    public void setSuperclasses(List<String> superclasses) {
        this.superclasses = superclasses;
    }

    public List<String> getInterfaces() {
        return interfaces;
    }

    public void setInterfaces(List<String> interfaces) {
        this.interfaces = interfaces;
    }

    public Set<String> getImports() {
        return imports;
    }

    public void setImports(Set<String> imports) {
        this.imports = imports;
    }

    public List<MyField> getMyFields() {
        return myFields;
    }

    public void setMyFields(List<MyField> myFields) {
        this.myFields = myFields;
    }

    public List<MyConstructor> getMyConstructors() {
        return myConstructors;
    }

    public void setMyConstructors(List<MyConstructor> myConstructors) {
        this.myConstructors = myConstructors;
    }

    public List<MyMethod> getMyMethods() {
        return myMethods;
    }

    public void setMyMethods(List<MyMethod> myMethods) {
        this.myMethods = myMethods;
    }
}
