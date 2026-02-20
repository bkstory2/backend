package com.example.vuebackend.mapper;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SampleMapper {
    String selectNow();

    String select2();
}
