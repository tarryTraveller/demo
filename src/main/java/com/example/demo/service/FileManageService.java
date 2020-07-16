package com.example.demo.service;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;

@Service
@Slf4j
public class FileManageService {

    @Value("${file.hostpath}")
    private String fileHostPath;

    private static final Logger LOGGER = LoggerFactory.getLogger(FileManageService.class);

    public String upload(MultipartFile file) {
        File test = new File(fileHostPath+file.getOriginalFilename());
        if (!test.exists()){
            test.mkdirs();
        }
        try {
            file.transferTo(test);
        }catch (IOException e){
            LOGGER.error(file.getOriginalFilename()+"文件上传失败", e);
        }
        return "文件上传成功";
    }

    public String download(String fileName, HttpServletResponse response){
        File file = new File(fileHostPath + fileName);
        if (!file.exists()){
            return "文件不存在";
        }
        byte[] bytes = new byte[1024];
        BufferedInputStream bufferedInputStream = null;
        OutputStream outputStream = null;
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream(file);
            bufferedInputStream = new BufferedInputStream(fileInputStream);

            response.setContentType(MediaType.APPLICATION_OCTET_STREAM.toString());
            response.addHeader("Content-Disposition", "attachment;fileName=" + URLEncoder.encode(fileName, "UTF-8"));
            outputStream = response.getOutputStream();
            int length;
            while ((length = bufferedInputStream.read(bytes)) != -1){
                outputStream.write(bytes, 0, length);
            }
            outputStream.flush();
        }catch (Exception e){
            LOGGER.error("文件下载失败", e);
        }finally {
            try {
                if (bufferedInputStream != null){
                    bufferedInputStream.close();
                }

                if (outputStream != null){
                    outputStream.close();
                }

                if (fileInputStream != null){
                    fileInputStream.close();
                }
            }catch (IOException e){
                LOGGER.error(e.getMessage(), e);
            }
        }
        return "文件下载成功";
    }
}
