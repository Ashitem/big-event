package com.ashitem.controller;

import com.ashitem.pojo.Result;
import com.ashitem.utils.ImgBedUtil;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
public class FileUploadController {

    @PostMapping("/upload")
    public Result<String> upload(MultipartFile file) throws IOException {
        String pre="https://imgbed.ash123.xyz";
        String url = ImgBedUtil.uploadImg(file);
       String targetUrl=pre+url;
//        System.out.println(targetUrl);
        return Result.success(targetUrl);
    }
}
//不用直接用图床