package com.example.vuebackend.model;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class Board {

    private String boardId;
    private Long id;
    private String userId;
    private String title;
    private String body;
    private String fileNm;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
