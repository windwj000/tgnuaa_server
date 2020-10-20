package com.example.demo.mapper;

import com.example.demo.domain.LawCase;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface LawCaseMapper {

    @Select({
            "select id,path,keyword",
            "from law_case where keyword=#{keyword}"
    })
    List<LawCase> selectByKeyword(String keyword);

    @Select({"select * from law_case"})
    List<LawCase> selectAll();

    @Update({"update law_case set path=#{path} where id=#{id}"})
    int update(int id,String path);
}
