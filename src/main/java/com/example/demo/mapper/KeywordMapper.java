package com.example.demo.mapper;

import com.example.demo.domain.Keyword;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface KeywordMapper {

    // 多级关键词查询
    @Select("select * from keyword where id=#{id}")
    @Results(id = "all", value = {
            @Result(column = "id", property = "id", id = true),
            @Result(column = "name", property = "name"),
            @Result(column = "rank", property = "rank"),
            @Result(column = "priority", property = "priority"),
            @Result(column = "identity", property = "identity"),
            @Result(column = "count", property = "count"),
            @Result(column = "pid", property = "pid"),
            @Result(column = "id", property = "children", many = @Many(select = "selectByPid"))
    })
    Keyword select(int id);

    @Select("select * from keyword where pid=#{pid}")
    @ResultMap("all")
    List<Keyword> selectByPid(int pid);

    // 根据身份查询id
    @Select("select id from keyword where identity=#{identity}")
    int selectByIdentity(String identity);

    @Select("select * from keyword")
    List<Keyword> selectAll();

    @Update("update keyword set count=#{count} where name=#{keyword}")
    int update(String keyword,int count);
}
