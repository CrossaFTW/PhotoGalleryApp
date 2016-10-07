package com.kapanlagi.web.controllers;


import com.kapanlagi.web.services.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.propertyeditors.StringArrayPropertyEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.Map;

/**
 * Created by Crossa on 10/3/2016.
 */
@Controller
public class UploadController {
    @Autowired
    private ImageService imageService;



    @Value("${uploadDirT}")
    private String uploadDirT;

    @Value("${uploadDirM}")
    private String uploadDirM;

    @Value("${uploadDirL}")
    private String uploadDirL;

    @Value("${thumb.width}")
    private int thumbw;

    @Value("${medium.width}")
    private int mediumw;

    @Value("${large.width}")
    private int largew;

    @RequestMapping(value = "/upload", method = RequestMethod.GET)
    public String uploadForm() {
        return "upload";
    }


    @RequestMapping(value = "/doupload", method = RequestMethod.POST)
    public String doUpload(@RequestParam("file") MultipartFile file,
                           @RequestParam("msg") String msg,
                           Map<String,Object> model ) {
        String filename = file.getOriginalFilename().toLowerCase();
        String filetype = file.getContentType();
        model.put("file_name", filename);
        model.put("file_type", filetype);
        model.put("upload_dirT", uploadDirT);
        model.put("upload_dirM", uploadDirM);
        model.put("upload_dirL", uploadDirL);
        model.put("msg", msg);
        //validasi
        if (!validasiFileUpload(file)) {
            model.put("error_message", "File yang diupload tidak valid");
        } else {
            try {
                resizeImageAndSave( file );

                //redirect to home
                return "redirect:/?ok=1";
            } catch(IOException ioe) {
                model.put("error_message", "Error: " + ioe);
            }
        }
        return "upload";
    }

    protected boolean validasiFileUpload(MultipartFile file) {
        //@TODO implement validasi file, harus berupa image
        String filetype = file.getContentType();
        String filename = filetype.toLowerCase();

        if(filename.endsWith(".png")) {
            return false;
        }else if(filename.endsWith(".jpg")){
            return false;
        }else{
            return true;
        }
    }

    protected void resizeImageAndSave(MultipartFile file) throws IOException {
        String nama = file.getOriginalFilename().toLowerCase();
        byte[] uploadData = file.getBytes();
        BufferedImage originalImage = ImageIO.read(new ByteArrayInputStream(uploadData));

        BufferedImage bimageT = imageService.resizeImage(originalImage, thumbw, thumbw);
        BufferedImage bimageM = imageService.resizeImage(originalImage, mediumw, 0);
        BufferedImage bimageL = imageService.resizeImage(originalImage, largew, 0);

        ImageIO.write(bimageT, "png", new File(uploadDirT+nama));
        ImageIO.write(bimageM, "png", new File(uploadDirM+nama));
        ImageIO.write(bimageL, "png", new File(uploadDirL+nama));

        //@TODO implement resize image and save

    }

}



