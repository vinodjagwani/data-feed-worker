package de.market.feed.worker.kafka;

@FunctionalInterface
public interface KafkaMessageProducer {

    void sendMessageToKafka(final String message);
}
