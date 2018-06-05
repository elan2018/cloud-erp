package com.elan.cloud.erp.frontend.demo;

import com.elan.common.response.ResponseResult;
import com.elan.common.utils.RestTemplateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
public class DemoController {

    @Autowired
    RestTemplate restTemplate;

    @Value("${service.url:http://service-zuul:8767/}")
    private String url;

    @GetMapping("/test/error")
    public ResponseResult testError() {
        Map<String, String> params = new HashMap<>();
        params.put("name", "2");
        ResponseResult responseResult = new RestTemplateUtil(restTemplate).get(url + "base/err", params);
        return responseResult;
    }


    @PostMapping("/add")
    public ResponseResult add(@RequestParam("id")int id){
        return new ResponseResult(String.valueOf(++id));
    }


//    @PostMapping("/upload")
//    public ResponseResult uploadFile(@RequestParam("file") MultipartFile jarFile,@RequestParam("name") String name) throws IOException {
//        String api_url =url + "zuul/base/upload";
//        HttpHeaders headers = new HttpHeaders();
//
//        String tempFileName = jarFile.getOriginalFilename();
//        String tempFilePath = "d:\\temp\\" + tempFileName;
//        File tempFile = new File(tempFilePath);
//        jarFile.transferTo(tempFile);
//        FileSystemResource fileSystemResource = new FileSystemResource(tempFilePath);
//        MediaType type = MediaType.parseMediaType("multipart/form-data");
//
//        headers.setContentType(type);
//        headers.add("x-access-token","27FCF5C1FB16F29B998D74786AC6830052567A190FDDEDA9E35052164C35EDF52DF295102F9E6DC15CABC4DE9CCD4CFB");
//        String cd = "filename=\"" + jarFile.getOriginalFilename() + "\"";
//        headers.add("Content-Disposition", cd);
//        MultiValueMap<String, Object> form = new LinkedMultiValueMap<String, Object>();
//        form.add("file", fileSystemResource);
//        form.add("file", fileSystemResource);
//        HttpHeaders p_headers = new HttpHeaders();
//        p_headers.setContentType(new MediaType("text" ,"plain", Charset.forName("UTF-8")));
//
//        HttpEntity<String> entity = new HttpEntity<String>(tempFileName, p_headers);
//
//        form.add("filename",entity);
//        form.add("filename",entity);
//
//        HttpEntity<MultiValueMap<String, Object>> files = new HttpEntity<>(form, headers);
//        ResponseEntity<ResponseResult> responseEntity = null;
//        responseEntity = restTemplate.postForEntity(api_url, files, ResponseResult.class);
//        try {
//            tempFile.delete();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return new ResponseResult(responseEntity);
//    }

    //文件上传，可以多个文件，文件名参数有RestTemplateUtil自动生成，参数名称为key_filename,这个key的值就是MultipartFile的参数名称
    //在后端服务中，可以直接取源文件名（注意原文件名已经被改为系统时间，由于中文会乱码而使用的替代方法）
    //需要在接口路径前加上zuul(在上传大文件时交由微服务处理）
    @PostMapping("/upload1")
    public ResponseResult upload1(@RequestParam("file") MultipartFile[] files,@RequestParam("name")String[] name) throws IOException {
        String api_url =url + "zuul/base/upload";
        Map<String, Object> params = new HashMap<>();
        params.put("file", files);
        params.put("name",name);
        return new RestTemplateUtil(restTemplate).upload(url + "zuul/base/upload", params);
    }

    //同名参数
    @GetMapping("/sameParams")
    public ResponseResult sameParams(@RequestParam("a") String[] a) {
        Map<String, Object> r = new HashMap<>();
        r.put("a", a);

        return new RestTemplateUtil(restTemplate).get(url + "base/test/params", r);
    }
    //post请求
    @PostMapping("/info")
    public ResponseResult postInfo(@RequestParam("id") String id) {
        Map<String, Object> r = new HashMap<>();
        r.put("id", id);

        return new RestTemplateUtil(restTemplate).post(url + "base/test/postInfo", r);
    }

    //get请求
    @GetMapping("/info")
    public ResponseResult getInfo(@RequestParam("id") String id){
        Map<String, Object> r = new HashMap<>();
        r.put("id", id);
        return new RestTemplateUtil(restTemplate).get(url + "base/test/getInfo", r);
    }
}
