package com.innovations.journalApp.schedulers;

import com.innovations.journalApp.entity.JournalEntry;
import com.innovations.journalApp.entity.User;
import com.innovations.journalApp.model.SentimentData;
import com.innovations.journalApp.repository.UserRepositoryImpl;
import com.innovations.journalApp.service.EmailService;
import com.innovations.journalApp.service.SentimentAnalysisService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
public class UserScheduler {

    private final UserRepositoryImpl userRepository;
    private final EmailService emailService;
    private final SentimentAnalysisService sentimentAnalysisService;

    private final KafkaTemplate<String, SentimentData> kafkaTemplate;

    @Scheduled(cron = "0 36 18 * * SUN")
    public void sendMailToSaUser() {
        List<User> users = userRepository.getUserForSA();
        for (User user : users) {
            List<String> entries = user.getJournalEntries().stream().filter(x -> LocalDateTime.now().minusDays(7).isBefore(x.getCreatedTime())).map(JournalEntry::getContent).collect(Collectors.toList());
            String sentiment = sentimentAnalysisService.sentimentAnalysis(String.join(" ", entries));

            SentimentData sentimentData = SentimentData.builder().email(user.getEmailId()).sentiment(sentiment).build();

            System.out.println(sentimentData.toString());

            kafkaTemplate.send("sentimentAnalysisTopic", user.getEmailId(), sentimentData);

        }
    }

}
