package com.example.demo.controller;

import com.example.demo.domain.LawCase;
import com.example.demo.domain.LawCaseResponse;
import com.example.demo.mapper.KeywordMapper;
import com.example.demo.mapper.LawCaseMapper;
import com.example.demo.web.AjaxResult;
import com.example.demo.web.BaseController;
import com.spire.doc.Document;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/keyword")
@Api(value = "关键词")
public class KeywordController extends BaseController {

    private static Logger logger = LoggerFactory.getLogger(KeywordController.class);

    @Autowired
    private KeywordMapper keywordMapper;

    @Autowired
    private LawCaseMapper lawCaseMapper;

    // 根据身份模式进行展示
    @ApiOperation(value = "根据身份查询关键词")
    @GetMapping(value = "/select")
    public AjaxResult select(@RequestParam String identity){
        int id=keywordMapper.selectByIdentity(identity);
        return AjaxResult.success(keywordMapper.select(id));
    }

    @ApiOperation(value = "根据关键词显示案例")
    @GetMapping(value = "/cases")
    public AjaxResult getCase(@RequestParam String keyword){
        List<LawCase> list=lawCaseMapper.selectByKeyword(keyword);
        if (list == null) {
            return AjaxResult.error("该关键词没有对应的案例！");
        }
        List<LawCaseResponse> res=new ArrayList<>();
        for (LawCase l : list) {
            LawCaseResponse lawCaseResponse=new LawCaseResponse(l.getId(),l.getPath(),l.getKeyword());
            lawCaseResponse.setContent(showCaseDescription(l.getPath()));
            res.add(lawCaseResponse);
        }
        return AjaxResult.success(res);
    }

    // 根据文件路径读取文件
    public String showCaseDescription(String path){
        Document document = new Document(path);
        return document.getText();
    }

    /*@GetMapping(value = "/test")
    public void test(){
        List<LawCase> list=lawCaseMapper.selectAll();
        for (LawCase lawCase : list) {
            String path=lawCase.getPath();
            String newPath=path.substring(1,path.length());
            lawCaseMapper.update(lawCase.getId(),newPath);
        }
    }*/

    /*// 计算关键词对于的案例数
    @GetMapping(value = "/count")
    public void count(){
        List<Keyword> keywordList=keywordMapper.selectAll();
        for (Keyword k : keywordList) {
            List<LawCase> list=lawCaseMapper.selectByKeyword(k.getName());
            keywordMapper.update(k.getName(),list.size());
        }
    }*/
}
