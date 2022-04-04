package de.market.feed.worker.kafka.config;

import io.quarkus.arc.config.ConfigProperties;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@ConfigProperties(prefix = "kafka")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class KafkaConfigProperties {

    Bootstrap bootstrap;

    Key key;

    Value value;

    String topicName;

    String acks;

    String retries;

    @Data
    @FieldDefaults(level = AccessLevel.PRIVATE)
    public static class Bootstrap {
        List<String> servers;
    }

    @Data
    @FieldDefaults(level = AccessLevel.PRIVATE)
    public static class Key {
        String serializer;
    }

    @Data
    @FieldDefaults(level = AccessLevel.PRIVATE)
    public static class Value {
        String serializer;
    }
}
