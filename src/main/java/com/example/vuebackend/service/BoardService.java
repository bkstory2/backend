package com.example.vuebackend.service;

import com.example.vuebackend.mapper.BoardMapper;
import com.example.vuebackend.model.Board;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardMapper boardMapper;
    private static final Logger logger = LoggerFactory.getLogger(BoardService.class);

    @Value("${app.upload.dir:c:/vuejs/backend/uploads/board}")
    private String uploadDir;

    // 단건 조회
    public Board selectBoard(Long id) {
        return boardMapper.selectBoard(id);
    }

    // 다건 조회 (board_id, LIKE 검색)
    public List<Board> selectList(String board_id, String userId, String title, String body) {
        return boardMapper.selectList(board_id, userId, title, body);
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
        // 파일 삭제 (파일이 있으면)
        Board board = boardMapper.selectBoard(id);
        if (board != null && board.getFileNm() != null) {
            deleteFile(board.getFileNm());
        }
        return boardMapper.deleteBoard(id);
    }

    // 파일 업로드
    public String uploadFile(MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            throw new IOException("파일이 비어있습니다");
        }

        // 파일명 생성 (원본파일명_날짜시간.확장자)
        String originalFileName = file.getOriginalFilename();

        // 확장자 추출
        int lastDotIndex = originalFileName.lastIndexOf(".");
        String nameWithoutExtension = lastDotIndex > 0 ? originalFileName.substring(0, lastDotIndex) : originalFileName;
        String extension = lastDotIndex > 0 ? originalFileName.substring(lastDotIndex) : "";

        // 현재 날짜시간 (yyyyMMdd_HHmmss 형식)
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");
        String dateTime = now.format(formatter);

        // 새 파일명: 원본파일명_날짜시간.확장자
        String newFileName = nameWithoutExtension + "_" + dateTime + extension;

        // 파일 저장
        String filePath = uploadDir + "/" + newFileName;
        logger.info("업로드 디렉토리: {}", uploadDir);
        logger.info("파일 경로: {}", filePath);
        logger.info("원본 파일명: {}", originalFileName);
        logger.info("변환된 파일명: {}", newFileName);

        Files.createDirectories(Paths.get(uploadDir));
        Files.write(Paths.get(filePath), file.getBytes(), StandardOpenOption.CREATE_NEW);

        logger.info("파일 업로드 성공: {}", newFileName);
        return newFileName;
    }

    // 파일 삭제
    public void deleteFile(String fileNm) {
        try {
            if (fileNm != null && !fileNm.isEmpty()) {
                String filePath = uploadDir + "/" + fileNm;
                Files.deleteIfExists(Paths.get(filePath));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 파일 및 DB 삭제 (파일명을 NULL로 업데이트)
    public void deleteFileAndUpdateBoard(Long id, String fileNm) {
        // 1. 서버 파일 삭제
        deleteFile(fileNm);

        // 2. DB 파일명 NULL로 업데이트
        boardMapper.updateFileNmToNull(id);
        logger.info("게시글 {}의 파일정보 DB 삭제 완료", id);
    }
}
