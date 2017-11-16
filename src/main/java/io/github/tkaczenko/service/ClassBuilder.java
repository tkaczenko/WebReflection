package io.github.tkaczenko.service;


import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateExceptionHandler;
import io.github.tkaczenko.model.MyClass;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * Created by tkaczenko on 07.02.17.
 */
@Service
public class ClassBuilder {
    private static final String TEMPLATE_DIRECTORY = "src/main/resources/templates";
    private static final String CLASS_TEMPLATE = "/class.ftl";

    private static Configuration cfg = null;

    private Separator separator;

    private Class clazz;

    static {
        cfg = new Configuration(Configuration.VERSION_2_3_25);
        try {
            cfg.setDirectoryForTemplateLoading(new File(TEMPLATE_DIRECTORY));
        } catch (IOException e) {
            e.printStackTrace();
        }
        cfg.setDefaultEncoding("UTF-8");
        cfg.setLocale(Locale.ENGLISH);
        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
    }

    public Map<Map<String, Object>, Template> createTemplate()
            throws IOException, InvocationTargetException, IllegalAccessException {
        Map<Map<String, Object>, Template> inputTemplateMap = new HashMap<>();
        Map<String, Object> input = new HashMap<>();

        MyClass buildMyClass = new MyClass(clazz.getSimpleName());
        buildMyClass.setPack(separator.getPackageName());
        buildMyClass.setImports(separator.getImports());
        buildMyClass.setMyAnnotations(separator.getAnnotations(clazz));
        buildMyClass.setModificators(separator.getModifiers());
        buildMyClass.setSuperclasses(separator.getSuperClasses());
        buildMyClass.setInterfaces(separator.getInterfaces());
        buildMyClass.setMyFields(separator.getFields());
        buildMyClass.setMyConstructors(separator.getConstructors());
        buildMyClass.setMyMethods(separator.getMethods());

        input.put("class", buildMyClass);

        Template template = cfg.getTemplate(CLASS_TEMPLATE);
        inputTemplateMap.put(input, template);
        return inputTemplateMap;
    }

    public Separator getSeparator() {
        return separator;
    }

    public void setSeparator(Separator separator) {
        this.separator = separator;
    }

    public Class getClazz() {
        return clazz;
    }

    public void setClazz(Class clazz) {
        this.clazz = clazz;
    }
}
