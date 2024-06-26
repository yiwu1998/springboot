package com.example.demo.controller;


import com.example.demo.entity.People;
import com.example.demo.service.PeopleService;
import com.example.demo.util.GenerateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/people")
public class PeopleController {

    @Autowired
    private PeopleService peopleService;



    @PostMapping("/getPeopleList")
    public List<People> getPeopleList() {
        return peopleService.list();
    }

    @PostMapping("/mapperTest")
    public People mapperTest(@RequestBody People people) {
        return peopleService.mapperTest(people);
    }

    @PostMapping("/saveBatch")
    public Boolean saveBatch() {
        List<People> people = new ArrayList<>();

        for(int i = 1; i < 20; i++){
            People people1 = new People();
            people1.setAge(GenerateUtil.generateRandomAge(11,66));
            people1.setName(GenerateUtil.generateName());
            people.add(people1);
        }
        return peopleService.saveBatch(people);
    }
    /**
     * 异步更新
     * @return
     */
    @PostMapping("/admitUpdate")
    public void admitUpdate() {
        peopleService.admitUpdate();
    }


    @PostMapping("/delTable")
    public void delTable() {
        String[] tables = {"department_tree", "user_menu_warrant"};
         peopleService.delTable(tables);
        System.out.println("删除成功");
    }

}
