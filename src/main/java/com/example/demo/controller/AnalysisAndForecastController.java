package com.example.demo.controller;

import com.example.demo.domain.Keyword;
import com.example.demo.mapper.KeywordMapper;
import com.example.demo.web.AjaxResult;
import com.spire.doc.Document;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/analysis")
@Api(value = "分析预测")
public class AnalysisAndForecastController {

    private static Logger logger = LoggerFactory.getLogger(AnalysisAndForecastController.class);

    @Autowired
    private KeywordMapper keywordMapper;

    @Autowired
    private RedisTemplate redisTemplate;

    private static final String uploadPath="upload/";

    @ApiOperation(value = "用户上传案例")
    @PostMapping(value = "/upload")
    public AjaxResult upload(@RequestParam("file") MultipartFile multipartFile) throws IOException {
        if (multipartFile.isEmpty()) {
            return AjaxResult.error("上传文件为空！");
        }
        String filePath=uploadPath+multipartFile.getOriginalFilename();
        File file = new File(filePath);
        if(!file.exists()){
            file.createNewFile();
        }
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(filePath);
            fos.write(multipartFile.getBytes());
//            file.transferTo(dest);
        } catch (IOException e) {
            return AjaxResult.error("上传文件发生错误："+e.getMessage());
        }finally {
            fos.close();
        }
        return AjaxResult.success(multipartFile.getOriginalFilename());
    }

    @ApiOperation(value = "调用算法")
    @GetMapping(value = "/algorithm")
    public AjaxResult algorithm(@RequestParam String identity,
                                @RequestParam String fileName) throws IOException {
        String res=null;
        Socket socket=new Socket("127.0.0.1", 2341);

        String testName="keywords/"+fileName;
        File file = new File(testName);
        if(!file.exists()){
            file.createNewFile();
        }
        List<String> keywords = getKeywordArray(identity, fileName);
        BufferedWriter bw = null;
        OutputStream os= null;
        InputStream is = null;
        try {
            bw = new BufferedWriter(new FileWriter(testName));
            for (String k : keywords) {
                bw.write(k);
                bw.newLine();
                bw.flush();
            }

            os= socket.getOutputStream();
            fileName="testData.csv";
//            String msg=fileName+" "+getCsv(identity);
            String msg=fileName+" "+"2.csv";
            os.write(msg.getBytes());

            is =socket.getInputStream();
            byte[] ba=new byte[is.available()];
            is.read(ba);
            res=new String(ba);
            System.out.println(res);
        } catch (IOException e) {
            return AjaxResult.error("文件io错误："+e.getMessage());
        }finally {
            bw.close();
            os.close();
            socket.close();
        }
        return AjaxResult.success(1);
    }

    // 根据文件路径读取文件
    public String showCaseDescription(String path){
        Document document = new Document(path);
        return document.getText();
    }

    // 根据案例读取关键词
    public List<String> getKeywordArray(String identity,String fileName){
        List<String> sa = new ArrayList<>();
        int id=keywordMapper.selectByIdentity(identity);
        String key="keyword"+identity;
        Keyword keyword=(Keyword) redisTemplate.opsForValue().get(key);
        List<String> list = new ArrayList<>();
        for (Keyword keyword1 : keyword.getChildren()) {
            for (Keyword keyword2 : keyword1.getChildren()) {
                if(keyword2.getPriority()>0){
                    list.add(keyword2.getName());
                }
            }
        }

        Document document = new Document(uploadPath+fileName);
        String s=document.getText();
        for (String str : list) {
            if (s.contains(str)) {
                sa.add(str);
            }
        }
        return sa;
    }

    // 根据用户身份查对应csv
    public String getCsv(String identity){
        switch (identity) {
            case "运输公司":
                return "航空货物运输合同纠纷.csv";
            case "航空公司":
                return "人身、财产侵权责任.csv";
            case "托运人":
                return "物权纠纷.csv";
            case "保险公司":
                return "保险纠纷.csv";
            case "旅客":
                return "航空旅客运输合同.csv";
            default:
                return null;
        }
    }
}
