package com.example.vuebackend.service;

import com.example.vuebackend.mapper.SampleMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SampleService {

    private final SampleMapper sampleMapper;

    public String now() {
        return sampleMapper.selectNow();
    }

    public String now22() {
        return sampleMapper.select2();
    }
}
