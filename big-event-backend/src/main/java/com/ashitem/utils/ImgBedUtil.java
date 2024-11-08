package com.ashitem.utils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


public class ImgBedUtil {
    private static final String url="https://imgbed.ash123.xyz/upload?authCode=ashitem&serverCompress=true";
    public static String uploadImg(MultipartFile file) throws IOException {
        if (file == null) {
            throw new RuntimeException("上传的图片为空");
        }
        if (file.getSize() > 1024 * 1024 * 20) {
            throw new RuntimeException("文件大小不能大于20M");
        }
        //获取文件后缀
        String originalFilename = file.getOriginalFilename();
        String suffix =  originalFilename .substring( originalFilename .lastIndexOf(".") + 1);
        if (!"jpg,jpeg,gif,png,ico,tiff,bmp".toUpperCase().contains(suffix.toUpperCase())) {
            throw new RuntimeException("请选择jpg,jpeg,gif,png,ico,tiff,bmp格式的图片");
        }
        RestTemplate restTemplate=new RestTemplate();
        // 创建包含图片的MultiValueMap
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("file", new ByteArrayResource(file.getBytes()) {
            @Override
            public String getFilename() {
                return originalFilename; // 设置文件名
            }
        });
        // 设置请求头
        HttpHeaders headers=new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        // 创建HttpEntity
        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);
        // 发送POST请求
        ResponseEntity<String> response = restTemplate.postForEntity(url, requestEntity, String.class);

        // 使用Fastjson解析JSON字符串为JSONArray
        JSONArray jsonArray = JSON.parseArray(response.getBody());


        // 遍历JSONArray并打印每个元素的'src'值
        String srcValue="";
        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            srcValue = jsonObject.getString("src");
        }
        return srcValue;
    }
}



