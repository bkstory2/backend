package com.example.vuebackend.service;

import com.example.vuebackend.mapper.StateStatsMapper;
import com.example.vuebackend.model.StateStatsDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class StateStatsService {
    @Autowired
    private StateStatsMapper stateStatsMapper;

    public List<StateStatsDto> getStats() {
        return stateStatsMapper.selectStats();
    }
}
