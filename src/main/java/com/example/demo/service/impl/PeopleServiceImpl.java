package com.example.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.entity.People;
import com.example.demo.mapper.PeopleMapper;
import com.example.demo.service.PeopleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Function;

@Service
public class PeopleServiceImpl extends ServiceImpl<PeopleMapper,People> implements PeopleService {

    @Autowired
    public PeopleMapper peopleMapper;


    @Override
    public People mapperTest(People people) {
        return peopleMapper.mapperTest(people);
    }

}
