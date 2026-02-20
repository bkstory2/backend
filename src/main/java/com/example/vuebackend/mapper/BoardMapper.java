package com.example.vuebackend.mapper;

import com.example.vuebackend.model.Board;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface BoardMapper {

    // 단건 조회
    Board selectBoard(@Param("id") Long id);

    // 다건 조회 (LIKE 검색)
    List<Board> selectList(@Param("userId") String userId,
            @Param("title") String title,
            @Param("body") String body);

    // 등록
    int insertBoard(Board board);

    // 수정
    int updateBoard(Board board);

    // 삭제
    int deleteBoard(@Param("id") Long id);
}
