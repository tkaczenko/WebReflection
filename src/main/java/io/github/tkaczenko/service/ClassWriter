package io.github.tkaczenko.service;

import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Map;

/**
 * Created by tkaczenko on 07.02.17.
 */
public class ClassWriter {
    private static final String template = "%s.java";

    public static void writeToJava(Map<Map<String, Object>, Template> input, String className, String path)
            throws IOException, TemplateException {
        if (className == null) {
            return;
        }
        Writer writer = new FileWriter(path + String.format(template, className), true);
        for (Map.Entry<Map<String, Object>, Template> e : input.entrySet()) {
            e.getValue().process(e.getKey(), writer);
        }
    }
}
