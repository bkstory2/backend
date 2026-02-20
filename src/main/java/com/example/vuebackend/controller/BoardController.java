package com.example.vuebackend.controller;

import com.example.vuebackend.model.Board;
import com.example.vuebackend.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/board")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class BoardController {

    private final BoardService boardService;

    // 단건 조회
    @GetMapping("/{id}")
    public ResponseEntity<Board> selectBoard(@PathVariable Long id) {
        Board board = boardService.selectBoard(id);
        return ResponseEntity.ok(board);
    }

    // 다건 조회 (LIKE 검색)
    @GetMapping
    public ResponseEntity<List<Board>> selectList(
            @RequestParam(required = false) String userId,
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String body) {
        List<Board> boards = boardService.selectList(userId, title, body);
        return ResponseEntity.ok(boards);
    }

    // 등록
    @PostMapping
    public ResponseEntity<Integer> insertBoard(@RequestBody Board board) {
        int result = boardService.insertBoard(board);
        return ResponseEntity.ok(result);
    }

    // 수정
    @PutMapping
    public ResponseEntity<Integer> updateBoard(@RequestBody Board board) {
        int result = boardService.updateBoard(board);
        return ResponseEntity.ok(result);
    }

    // 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<Integer> deleteBoard(@PathVariable Long id) {
        int result = boardService.deleteBoard(id);
        return ResponseEntity.ok(result);
    }
}
