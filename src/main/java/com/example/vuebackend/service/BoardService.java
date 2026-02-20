package com.example.vuebackend.service;

import com.example.vuebackend.mapper.BoardMapper;
import com.example.vuebackend.model.Board;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardMapper boardMapper;

    // 단건 조회
    public Board selectBoard(Long id) {
        return boardMapper.selectBoard(id);
    }

    // 다건 조회 (LIKE 검색)
    public List<Board> selectList(String userId, String title, String body) {
        return boardMapper.selectList(userId, title, body);
    }

    // 등록
    public int insertBoard(Board board) {
        return boardMapper.insertBoard(board);
    }

    // 수정
    public int updateBoard(Board board) {
        return boardMapper.updateBoard(board);
    }

    // 삭제
    public int deleteBoard(Long id) {
        return boardMapper.deleteBoard(id);
    }
}
