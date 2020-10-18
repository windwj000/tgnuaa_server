package com.example.demo.controller;

import com.example.demo.mapper.KeywordMapper;
import com.example.demo.web.AjaxResult;
import com.example.demo.web.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/keyword")
@Api(value = "关键词")
public class KeywordController extends BaseController {

    private static Logger logger = LoggerFactory.getLogger(KeywordController.class);

    @Autowired
    private KeywordMapper keywordMapper;

    @ApiOperation(value = "查询关键词")
    @GetMapping(value = "/select")
    public AjaxResult select(){
        return AjaxResult.success(keywordMapper.select(10000));
    }
}
