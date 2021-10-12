package com.learn.springboot.upload;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * springboot文件上传
 */
//@Controller
@RestController  //@ResponseBody + @Controller 结果集返回json
public class FileUploadController {

    /**
     * 文件上传
     * @param uploadFileName  需要和页面 type = "file"对应的name值相同。
     * @return  文件上传有默认限制大小，可在application.properties中配置。
     * @throws IOException
     */
    @RequestMapping("/fileUploadController")
    public Map<String,Object> fileUpload(MultipartFile uploadFileName) throws IOException {
        System.out.println(uploadFileName.getOriginalFilename());
        uploadFileName.transferTo(new File("d:/" + uploadFileName.getOriginalFilename()));
        Map<String,Object> map = new HashMap<>();
        map.put("msg","ok");
        return map;
    }

}