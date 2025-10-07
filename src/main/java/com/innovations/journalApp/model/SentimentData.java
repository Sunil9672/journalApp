package com.innovations.journalApp.model;

import lombok.*;
import org.springframework.data.repository.NoRepositoryBean;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SentimentData {
    private String sentiment;
    private String email;
}
