package com.example.vuebackend.controller;

import com.example.vuebackend.model.Board;
import com.example.vuebackend.service.BoardService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.io.IOException;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Iterator;

@RestController
@RequestMapping("/api/board")
@CrossOrigin(origins = "*")
public class BoardController {

    private final BoardService boardService;
    private static final Logger logger = LoggerFactory.getLogger(BoardController.class);

    public BoardController(BoardService boardService) {
        this.boardService = boardService;
    }

    // 단건 조회
    @GetMapping("/{id}")
    public ResponseEntity<Board> selectBoard(@PathVariable(name = "id") Long id) {
        Board board = boardService.selectBoard(id);
        return ResponseEntity.ok(board);
    }

    // 다건 조회 (board_id, 검색)
    @GetMapping
    public ResponseEntity<List<Board>> selectList(
            @RequestParam(name = "board_id", required = false) String board_id,
            @RequestParam(name = "userId", required = false) String userId,
            @RequestParam(name = "title", required = false) String title,
            @RequestParam(name = "body", required = false) String body) {
        List<Board> boards = boardService.selectList(board_id, userId, title, body);
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
    public ResponseEntity<Integer> deleteBoard(@PathVariable(name = "id") Long id) {
        int result = boardService.deleteBoard(id);
        return ResponseEntity.ok(result);
    }

    // 파일 업로드
    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(MultipartHttpServletRequest request) {
        try {
            logger.info("파일 업로드 요청 수신");
            logger.info("파라미터 이름들: {}", request.getParameterNames());

            // 모든 파일 파라미터 이름 출력
            Iterator<String> fileNames = request.getFileNames();
            logger.info("파일 파라미터 목록:");
            while (fileNames.hasNext()) {
                String paramName = fileNames.next();
                logger.info("  - 파라미터명: {}", paramName);
                List<MultipartFile> files = request.getFiles(paramName);
                logger.info("    파일 개수: {}", files.size());
                for (MultipartFile f : files) {
                    logger.info("    파일명: {}, 크기: {}", f.getOriginalFilename(), f.getSize());
                }
            }

            // 첫 번째 파일 찾기 (파라미터명이 뭐든 상관없이)
            MultipartFile file = null;
            Iterator<String> paramIter = request.getFileNames();
            if (paramIter.hasNext()) {
                String paramName = paramIter.next();
                List<MultipartFile> files = request.getFiles(paramName);
                if (!files.isEmpty()) {
                    file = files.get(0);
                }
            }

            if (file == null || file.isEmpty()) {
                logger.error("업로드할 파일이 없습니다");
                return ResponseEntity.status(400).body("파일 필요: " + request.getFileNames());
            }

            logger.info("파일 업로드 시작: {}", file.getOriginalFilename());
            logger.info("파일 크기: {} bytes", file.getSize());

            String fileName = boardService.uploadFile(file);

            logger.info("파일 업로드 완료: {}", fileName);
            return ResponseEntity.ok(fileName);
        } catch (IOException e) {
            logger.error("파일 업로드 실패", e);
            return ResponseEntity.status(500).body("파일 업로드 실패: " + e.getMessage());
        } catch (Exception e) {
            logger.error("예상치 못한 오류 발생", e);
            return ResponseEntity.status(500).body("오류: " + e.getMessage());
        }
    }

    // 파일 삭제
    @DeleteMapping("/{id}/delete-file/{fileName}")
    public ResponseEntity<String> deleteFile(
            @PathVariable(name = "id") Long id,
            @PathVariable(name = "fileName") String fileName) {
        try {
            logger.info("게시글 {}의 파일 삭제 요청: {}", id, fileName);
            boardService.deleteFileAndUpdateBoard(id, fileName);
            logger.info("게시글 {}의 파일 및 DB 삭제 완료", id);
            return ResponseEntity.ok("파일이 삭제되고 DB에 반영되었습니다");
        } catch (Exception e) {
            logger.error("파일 삭제 실패", e);
            return ResponseEntity.status(500).body("파일 삭제 실패: " + e.getMessage());
        }
    }

    // 파일 다운로드
    @GetMapping("/download/{fileName}")
    public ResponseEntity<?> downloadFile(@PathVariable(name = "fileName") String fileName) {
        try {
            Path filePath = Paths.get("uploads/board/" + fileName);
            File file = filePath.toFile();

            if (!file.exists()) {
                return ResponseEntity.notFound().build();
            }

            byte[] fileContent = Files.readAllBytes(filePath);
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"")
                    .body(fileContent);
        } catch (IOException e) {
            return ResponseEntity.status(500).body("파일 다운로드 실패");
        }
    }
}
