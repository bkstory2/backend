package com.example.vuebackend.mapper;

import com.example.vuebackend.model.StateStatsDto;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

@Mapper
public interface StateStatsMapper {
    List<StateStatsDto> selectStats();
}
