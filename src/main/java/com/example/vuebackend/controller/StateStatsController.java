package com.example.vuebackend.controller;

import com.example.vuebackend.model.StateStatsDto;
import com.example.vuebackend.service.StateStatsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.List;

@RestController
@RequestMapping("/api")
public class StateStatsController {
    @Autowired
    private StateStatsService stateStatsService;

    @GetMapping("/stats")
    public List<StateStatsDto> getStats() {
        return stateStatsService.getStats();
    }
}
