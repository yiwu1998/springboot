package com.example.demo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.entity.People;
import com.example.demo.mapper.PeopleMapper;
import com.example.demo.service.PeopleService;
import com.example.demo.util.TimeOperateUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class PeopleServiceImpl extends ServiceImpl<PeopleMapper, People> implements PeopleService {

    @Autowired
    public PeopleMapper peopleMapper;

    @Autowired
    private SqlSession sqlSession;

    LocalTime targetTime = LocalTime.of(17, 51);


    @Override
    public People mapperTest(People people) {
        return peopleMapper.mapperTest(people);
    }

    @Async(value = "admitExecutorr")
    @Override
    public void admitUpdate() {
        //自动任务，1分钟调一次准入查询
        ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
        executor.scheduleAtFixedRate(() -> {
            log.info("admitUpdate正在执行,执行多次" + LocalTime.now()); //业务代码执行
            if (TimeOperateUtil.currentTimeIsAfter(targetTime)) { //实际业务判断
                log.info("线程关闭,最后执行" + LocalTime.now());
                executor.shutdown(); // 关闭定时任务
            }
        }, 0, 1, TimeUnit.MINUTES);
        log.info("admitUpdate执行结束,第一次执行时，执行一次" + LocalTime.now());
    }

    @Override
    public void delTable(String[] tables) {
        if(tables.length == 0){
            return;
        }
        for(String table : tables){
            sqlSession.update("truncate table " + table);
        }
    }

}
