package com.codecrafters.ccbackend.dto.response;

import lombok.*;

@Data @AllArgsConstructor @NoArgsConstructor @Builder
public class UserSummaryDTO {
    private Long id;
    private String username;
    
}
