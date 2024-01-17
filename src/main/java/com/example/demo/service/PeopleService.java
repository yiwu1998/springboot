package com.example.demo.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.example.demo.entity.People;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public interface PeopleService  extends IService<People> {


    People mapperTest(People people);
}
