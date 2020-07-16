package com.example.demo.controller;

import com.example.demo.service.FileManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

@Controller
@RequestMapping("/fileDownload")
public class DownLoadController {

    @Autowired
    private FileManageService fileManageService;

    @GetMapping("/download")
    @ResponseBody
    public String fileDownload(@RequestParam("fileName") String fileName, HttpServletResponse response){
        if (Objects.isNull(fileName)){
            return "文件下载失败，请选择文件要下载的文件";
        }
        return fileManageService.download(fileName, response);
    }
}
