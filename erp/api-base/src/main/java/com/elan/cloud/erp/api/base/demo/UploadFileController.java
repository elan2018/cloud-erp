package com.elan.cloud.erp.api.base.demo;

import com.elan.common.response.ResponseResult;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
public class UploadFileController {
    private Logger logger = LoggerFactory.getLogger(UploadFileController.class);

    @Value("${upload.path:e:/upload}")
    private String uploadPath;

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    @HystrixCommand(fallbackMethod = "uploadFallback")
    public ResponseResult upload(@RequestParam("file") MultipartFile[] files,
                                 @RequestParam("file_filename") String[] fileList,
                                 @RequestParam("name")String[] name) throws IOException {
        logger.debug("name="+name);
        if (files.length>0 && files.length==fileList.length) {
            List<String> fileNameList = new ArrayList<>();
            int i=0;
            for(MultipartFile file:files) {
                String fileName = fileList[i];
                logger.debug("文件名称："
                        + fileName
                        + ",文件大小：" + file.getSize());
                fileName = fileName.replaceAll("\\.", "_" + System.currentTimeMillis() + ".");
                byte[] bytes = file.getBytes();
                BufferedOutputStream buffStream =
                        new BufferedOutputStream(new FileOutputStream(new File(uploadPath, fileName)));
                buffStream.write(bytes);
                buffStream.close();
                fileNameList.add(fileName);
                i++;
            }
            return new ResponseResult(fileNameList);

        } else {
            logger.warn("没有文件上传！");
            throw new IOException("不能上传，文件是空的!");
        }

    }

    public String uploadFallback(@RequestParam("file") MultipartFile file) {
        logger.error("文件上传失败！");
        return "noFile";
    }
}
