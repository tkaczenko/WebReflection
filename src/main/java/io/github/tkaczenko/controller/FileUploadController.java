package io.github.tkaczenko.controller;

import io.github.tkaczenko.service.ClassBuilder;
import io.github.tkaczenko.storage.FileNotFoundException;
import io.github.tkaczenko.storage.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.stream.Collectors;

/**
 * Created by tkaczenko on 14.02.17.
 */
@Controller
public class FileUploadController {
    private final StorageService storageService;
    private final ClassBuilder classBuilder;

    @Autowired
    public FileUploadController(StorageService storageService, ClassBuilder classBuilder) {
        this.storageService = storageService;
        this.classBuilder = classBuilder;
    }

    @GetMapping("/")
    public String listUploadedFiles(Model model) throws IOException {
        model.addAttribute("files", storageService.loadAll()
                .map(path -> MvcUriComponentsBuilder
                        .fromMethodName(FileUploadController.class, "serveFile", path.getFileName().toString())
                        .build().toString())
                .collect(Collectors.toList()));

        Map<String, List<Class>> classes = storageService.loadAll()
                .collect(Collectors.toMap(key -> key.getFileName().toString(), value -> {
                    File localFile = null;
                    localFile = storageService.load(value.getFileName().toString()).toFile();
                    JarFile jarFile = null;
                    try {
                        jarFile = new JarFile(localFile);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Enumeration<JarEntry> e = jarFile.entries();
                    URL[] urls = new URL[0];
                    try {
                        urls = new URL[]{new URL("jar:file:" + localFile.getAbsolutePath() + "!/")};
                    } catch (MalformedURLException e1) {
                        e1.printStackTrace();
                    }
                    URLClassLoader cl = URLClassLoader.newInstance(urls);

                    List<Class> temp = new ArrayList<>();
                    while (e.hasMoreElements()) {
                        JarEntry je = e.nextElement();
                        if (je.isDirectory() || !je.getName().endsWith(".class")) {
                            continue;
                        }
                        String className = je.getName().substring(0, je.getName().length() - 6);
                        className = className.replace("BOOT-INF/classes/", "").replace("/", ".");
                        try {
                            Class c = cl.loadClass(className);
                            if (c != null) {
                                temp.add(c); // classBuilder.createClassModel(c)
                            }
                        } catch (ClassNotFoundException e1) {
                            e1.printStackTrace();
                            return null;
                        }
                    }
                    return temp;
                }));

        model.addAttribute("classes", classes);

        classes.entrySet()
                .forEach(entry -> entry.getValue()
                        .forEach(c -> System.out.println(entry.getKey() + " " + c.getName())));
        return "uploadFile";
    }

    @GetMapping("/files/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> serveFile(@PathVariable String filename) {
        Resource file = storageService.loadAsResource(filename);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename\"", file.getFilename() + "\"")
                .body(file);
    }

    @PostMapping("/")
    public String handleFileUpload(@RequestParam("file") MultipartFile file,
                                   RedirectAttributes redirectAttributes) {
        storageService.store(file);
        redirectAttributes.addFlashAttribute("message",
                "You successfully uploaded " + file.getOriginalFilename() + "!");
        return "redirect:/";
    }

    @ExceptionHandler(FileNotFoundException.class)
    public ResponseEntity handleFileNotFoundException(FileNotFoundException exc) {
        return ResponseEntity.notFound().build();
    }
}
