package com.kapanlagi.web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.FilenameFilter;
import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Created by Crossa on 10/3/2016.
 */
@Controller
public class HelloController {

    private int counter = 0;
    private int tomboldelete = 0;

    @Value("${app.title}")
    private String title;

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String loginForm() {
        return "login";
    }

    @RequestMapping(value = "/dologin", method = RequestMethod.POST)
    public String doLogin(@RequestParam("username") String username,
                          @RequestParam("password") String password,
                          Map<String, Object> model){
        String user = username.toLowerCase();
        String pass = password.toLowerCase();

        if(user.equals("admin") && pass.equals("admin")){
            tomboldelete = 1;

            return "redirect:/";
        }else{
            model.put("error_message", "wrong username or password");
        }

        return "login";
    }

    @RequestMapping(value = "/dodelete")
    public String indexdelete(@RequestParam(required = false, value = "page") Integer page,
                        @RequestParam(required = false, value = "limit") Integer limit,
                        @RequestParam("thumbnail") String thumbnail,
                        Map<String,Object> modelMap) {
        counter++;

        if (page == null) {
            page = 0;
        }

        if (limit == null || limit == 0) {
            limit = 8;
        }


        int start = page * limit;
        String thumbP = thumbnail.toLowerCase();
        String path = imageDir + "/thumb/" + thumbP;
        Path pathpath = FileSystems.getDefault().getPath(path);
        try {
            Files.delete(pathpath);
        } catch (IOException | SecurityException e) {
            System.err.println(e);
        }

        modelMap.put("start", start);
        modelMap.put("page", page);
        modelMap.put("limit", limit);
        modelMap.put("title", title);
        modelMap.put("counter", counter);

        modelMap.put("tomboldelete", tomboldelete);

        return "redirect:/";

    }

    @RequestMapping(value = "/")
    public String index(@RequestParam(required = false, value = "page") Integer page,
                        @RequestParam(required = false, value = "limit") Integer limit,
                        Map<String,Object> modelMap) {
        counter++;

        if (page == null) {
            page = 0;
        }

        if (limit == null || limit == 0) {
            limit = 8;
        }

        int start = page * limit;
        modelMap.put("start", start);
        modelMap.put("page", page);
        modelMap.put("limit", limit);
        modelMap.put("title", title);
        modelMap.put("counter", counter);
        modelMap.put("items", listUploadedPhotos(start, limit));
        modelMap.put("tomboldelete", tomboldelete);

        return "index";

    }


    @Value("${imageDir}")
    private String imageDir;


    private List<String> listUploadedPhotos(int start, final int limit) {
        String thumbnailDir = imageDir + "/thumb/";
        File dir = new File(thumbnailDir);
        List<String> result = new ArrayList<>();
        if (dir.exists()) {
            String[] files = dir.list(new FilenameFilter() {
                @Override
                public boolean accept(File dir, String name) {
                    return name.toLowerCase().matches("^.*?\\.(jpg|gif|png)$");
                }
            });
            result.addAll(Arrays.asList(files));
        }

        if (start >= result.size()) {
            result.clear();
        } else {
            int limit1 = limit+1;
            int end = start+limit1 >= result.size() ? result.size() : start+limit1;
            result = result.subList(start, end);
        }


        return result;
    }

}
