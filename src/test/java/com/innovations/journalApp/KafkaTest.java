package com.innovations.journalApp;

import com.innovations.journalApp.model.SentimentData;
import com.innovations.journalApp.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.test.context.ActiveProfiles;

import java.time.Duration;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@ActiveProfiles("test")
@SpringBootTest
@RequiredArgsConstructor
public class KafkaTest {


    @Test
    public void testKafka() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "pkc-921jm.us-east-2.aws.confluent.cloud:9092");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "test-group");
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        props.put("spring.json.trusted.packages", "*");
        props.put("spring.json.use.type.headers", "false");
        props.put("spring.json.value.default.type", "com.innovations.journalApp.model.SentimentData");

        props.put("security.protocol", "SASL_SSL");
        props.put("sasl.mechanism", "PLAIN");
        props.put("sasl.jaas.config",
                "org.apache.kafka.common.security.plain.PlainLoginModule required username=\"DMTP47VJCL4PCD2R\" password=\"cfltl08vaeGmMpCnYB/Ijx2gBIU5GFxt5Ag3r5PRGalMNCkux7MT98/aiZLrlzMQ\";");

        JsonDeserializer<SentimentData> deserializer = new JsonDeserializer<>(SentimentData.class);
        deserializer.addTrustedPackages("*"); // or your package com.innovations.journalApp.model


        Consumer<String, SentimentData> consumer =
                new KafkaConsumer<>(props);

        consumer.subscribe(Collections.singletonList("sentimentAnalysisTopic"));

        System.out.println("Subscribed to topic sentimentAnalysisTopic");

        ConsumerRecords<String, SentimentData> records;
        do {
            records = consumer.poll(Duration.ofSeconds(5));
            records.forEach(record -> {System.out.println(record.value());});

        } while (records.isEmpty());
    }
}
