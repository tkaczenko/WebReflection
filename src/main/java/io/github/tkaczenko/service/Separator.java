package io.github.tkaczenko.service;


import io.github.tkaczenko.model.MyAnnotation;
import io.github.tkaczenko.model.MyConstructor;
import io.github.tkaczenko.model.MyField;
import io.github.tkaczenko.model.MyMethod;

import java.lang.reflect.*;
import java.util.*;

/**
 * Created by tkaczenko on 16.02.17.
 */
public class Separator {
    private Class clazz;

    private Set<String> imports = new HashSet<>();

    public String getPackageName() {
        return clazz.getPackage().getName();
    }

    public List<MyAnnotation> getAnnotations(Class clazz)
            throws InvocationTargetException, IllegalAccessException {
        List<MyAnnotation> arr = new ArrayList<>();
        java.lang.annotation.Annotation[] annotations = clazz.getAnnotations();
        for (java.lang.annotation.Annotation item : annotations) {
            Class<? extends java.lang.annotation.Annotation> type = item.annotationType();
            MyAnnotation annotation = new MyAnnotation(type.getSimpleName());
            imports.add(type.getName());

            HashMap<String, String> values = new HashMap<>();
            Method[] methods = type.getDeclaredMethods();
            for (Method method : methods) {
                Object value = method.invoke(item, (Object[]) null);
                values.put(method.getName(), value.toString());
            }

            annotation.setValues(values);

            arr.add(annotation);
        }
        return arr;
    }

    public List<String> getSuperClasses() {
        List<String> arr = new ArrayList<>();
        Class subclass = clazz.getClass();
        Class superclass = subclass.getSuperclass();
        while (superclass != null) {
            String className = superclass.getName();
            if (!className.equals("java.lang.Object")) {
                imports.add(className);
                arr.add(className);
            }
            subclass = superclass;
            superclass = subclass.getSuperclass();
        }
        return arr;
    }

    public List<String> getModifiers() {
        return getModifiers(clazz.getModifiers());
    }

    public List<String> getInterfaces() {
        List<String> arr = new ArrayList<>();
        Class[] interfaces = clazz.getInterfaces();
        for (Class item : interfaces) {
            arr.add(item.getSimpleName());
            imports.add(item.getName());
        }
        return arr;
    }

    public List<MyField> getFields() throws InvocationTargetException, IllegalAccessException {
        List<MyField> arr = new ArrayList<>();
        Field[] fields = clazz.getDeclaredFields();
        for (Field item : fields) {
            MyField field = new MyField(item.getName());
            field.setModificators(getModifiers(item.getModifiers()));

            String generic = "";
            Type genericFieldType = item.getGenericType();
            if (genericFieldType instanceof ParameterizedType) {
                ParameterizedType aType = (ParameterizedType) genericFieldType;
                Type[] fieldArgTypes = aType.getActualTypeArguments();
                for (Type type : fieldArgTypes) {
                    Class fieldArgClass = (Class) type;
                    generic = "<" + fieldArgClass.getSimpleName() + ">";
                }
            }
            field.setType(getType(item.getType()) + generic);
            field.setMyAnnotations(getAnnotations(item.getClass()));
            arr.add(field);
        }
        return arr;
    }

    public List<MyConstructor> getConstructors() {
        List<MyConstructor> arr = new ArrayList<>();
        Constructor[] constructors = clazz.getDeclaredConstructors();
        String name = clazz.getSimpleName();
        for (Constructor item : constructors) {
            MyConstructor constructor = new MyConstructor(name);
            constructor.setModificators(getModifiers(item.getModifiers()));
            constructor.setParams(getParameters(item.getParameterTypes()));

            arr.add(constructor);
        }
        return arr;
    }

    public List<MyMethod> getMethods() throws InvocationTargetException, IllegalAccessException {
        List<MyMethod> arr = new ArrayList<>();
        Method[] methods = clazz.getDeclaredMethods();
        for (Method item : methods) {
            MyMethod method = new MyMethod(item.getName());
            method.setType(getType(item.getReturnType()));
            method.setModificators(getModifiers(item.getModifiers()));
            method.setParams(getParameters(item.getParameterTypes()));
            method.setMyAnnotations(getAnnotations(method.getClass()));
            arr.add(method);
        }
        return arr;
    }

    private List<String> getParameters(Class[] params) {
        List<String> arr = new ArrayList<>();
        for (int i = 0; i < params.length; i++) {
            String p = getType(params[i]) + " param" + i;
            arr.add(p);
        }
        return arr;
    }

    private String getType(Class field) {
        String type = field.isArray() ? field.getComponentType().getSimpleName() : field.getSimpleName();
        if (field.isArray()) {
            type += "[]";
        }
        String typePath = field.getName();
        if (!typePath.equals("void") && !typePath.equals("int") && !typePath.equals("double")
                && !typePath.equals("float") && !typePath.equals("short") && !typePath.equals("boolean")
                && !typePath.equals("java.lang.Object") && !typePath.equals("java.lang.String")) {
            imports.add(typePath);
        }
        return type;
    }

    private List<String> getModifiers(int m) {
        List<String> modifiers = new ArrayList<>();
        if (Modifier.isPublic(m))
            modifiers.add("public");
        if (Modifier.isProtected(m))
            modifiers.add("protected");
        if (Modifier.isPrivate(m))
            modifiers.add("private");
        if (Modifier.isStatic(m))
            modifiers.add("static");
        if (Modifier.isAbstract(m))
            modifiers.add("abstract");
        if (Modifier.isVolatile(m))
            modifiers.add("volatile");
        if (Modifier.isTransient(m))
            modifiers.add("transient");
        return modifiers;
    }

    public Set<String> getImports() {
        return imports;
    }

    public Class getClazz() {
        return clazz;
    }

    public void setClazz(Class clazz) {
        this.clazz = clazz;
    }
}
