package de.market.feed.worker.kafka.impl;

import de.market.feed.worker.kafka.KafkaMessageProducer;
import de.market.feed.worker.kafka.config.KafkaConfigProperties;
import io.vertx.reactivex.kafka.client.producer.KafkaProducer;
import io.vertx.reactivex.kafka.client.producer.KafkaProducerRecord;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

import javax.enterprise.context.ApplicationScoped;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class KafkaDataFeedProducer implements KafkaMessageProducer {


    KafkaProducer<String, String> kafkaProducer;

    KafkaConfigProperties kafkaConfigProperties;


    @Override
    public void sendMessageToKafka(final String message) {
        final KafkaProducerRecord<String, String> record = KafkaProducerRecord.create(kafkaConfigProperties.getTopicName(), null, message);
        kafkaProducer.send(record, done ->
                log.info("Message {} written on topic={}, partition={}, key={}", record.value(), record.topic(), record.partition(), record.key()));
    }


}
