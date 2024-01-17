package com.example.demo.config;


import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.read.listener.ReadListener;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

@Slf4j
public class GeoPageReadListener<T> implements ReadListener<T> {

    public static int BATCH_COUNT = 1000;

    private List<T> cacheList = new ArrayList<T>(BATCH_COUNT);

    private final Consumer<List<T>> consumer;

    public GeoPageReadListener(Consumer<List<T>> consumer) {
        this.consumer = consumer;
    }


    @Override
    public void invoke(T data, AnalysisContext analysisContext) {
        cacheList.add(data);
        if(cacheList.size() >= BATCH_COUNT){
            log.info("读取数据量:{} " + cacheList.size());
            consumer.accept(cacheList);
            cacheList = new ArrayList<T>(BATCH_COUNT);
        }

    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        if(cacheList.size() > 0){
            log.info("读取数据量:{} " + cacheList.size());
            consumer.accept(cacheList);
        }
    }


}
