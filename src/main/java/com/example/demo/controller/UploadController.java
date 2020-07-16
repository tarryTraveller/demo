package com.example.demo.controller;

import com.example.demo.service.FileManageService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.util.Objects;

@Controller
@RequestMapping("/fileUp")
public class UploadController {
    @Autowired
    private FileManageService fileManageService;

    @PostMapping("/upload")
    @ResponseBody
    public String fileUpload(@RequestParam("file") MultipartFile file){
        if (Objects.isNull(file)==true?true:StringUtils.isBlank(file.getOriginalFilename())){
            return "文件上传失败，请重新选择文件";
        }
        return fileManageService.upload(file);
    }
}
