package com.example.demo.mapper;

import com.example.demo.domain.Keyword;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface KeywordMapper {

    @Insert({
            "insert into keyword(name,rank,priority,identity,pid)",
            "value(#{name},#{rank},#{priority},#{identity},#{pid})"
    })
    @Options(useGeneratedKeys = true,keyProperty = "id")
    int insertKeyword(Keyword keyword);

    // 多级关键词查询
    @Select("select * from keyword where id=#{id}")
    @Results(id = "all", value = {
            @Result(column = "id", property = "id", id = true),
            @Result(column = "name", property = "name"),
            @Result(column = "rank", property = "rank"),
            @Result(column = "priority", property = "priority"),
            @Result(column = "identity", property = "identity"),
            @Result(column = "pid", property = "pid"),
            @Result(column = "id", property = "children", many = @Many(select = "selectByPid"))
    })
    Keyword select(int id);

    @Select("select * from keyword where pid=#{pid}")
    @ResultMap("all")
    List<Keyword> selectByPid(int pid);
}
