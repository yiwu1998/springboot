package com.example.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.demo.entity.People;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;


@Mapper
public interface PeopleMapper extends BaseMapper<People> {

    People mapperTest(@Param("req") People people);
}
