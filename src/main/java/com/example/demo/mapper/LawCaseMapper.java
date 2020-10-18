package com.example.demo.mapper;

import com.example.demo.domain.LawCase;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface LawCaseMapper {

    @Select({
            "select id,path,keyword",
            "from law_case where keyword=#{keyword}"
    })
    List<LawCase> selectByKeyword(String keyword);
}
