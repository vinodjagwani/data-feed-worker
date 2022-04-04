package de.market.feed.worker.kafka.config;


import io.vertx.reactivex.core.Vertx;
import io.vertx.reactivex.kafka.client.producer.KafkaProducer;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.apache.zookeeper.common.StringUtils;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import java.util.HashMap;
import java.util.Map;

import static org.apache.kafka.clients.producer.ProducerConfig.ACKS_CONFIG;
import static org.apache.kafka.clients.producer.ProducerConfig.BOOTSTRAP_SERVERS_CONFIG;
import static org.apache.kafka.clients.producer.ProducerConfig.CLIENT_ID_CONFIG;
import static org.apache.kafka.clients.producer.ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG;
import static org.apache.kafka.clients.producer.ProducerConfig.RETRIES_CONFIG;
import static org.apache.kafka.clients.producer.ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG;


@ApplicationScoped
@AllArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class KafkaConfig {

    Vertx vertx;

    KafkaConfigProperties kafkaConfigProp;

    @Produces
    KafkaProducer<String, String> kafkaProducer() {
        return KafkaProducer.create(vertx, kafkaProducerConfigMap());
    }

    private Map<String, String> kafkaProducerConfigMap() {
        Map<String, String> config = new HashMap<>();
        config.put(BOOTSTRAP_SERVERS_CONFIG, StringUtils.joinStrings(kafkaConfigProp.getBootstrap().getServers(), ","));
        config.put(KEY_SERIALIZER_CLASS_CONFIG, kafkaConfigProp.getKey().getSerializer());
        config.put(VALUE_SERIALIZER_CLASS_CONFIG, kafkaConfigProp.getKey().getSerializer());
        config.put("topic", kafkaConfigProp.getTopicName());
        config.put(CLIENT_ID_CONFIG, "client-" + kafkaConfigProp.getTopicName());
        config.put(ACKS_CONFIG, kafkaConfigProp.getAcks());
        config.put(RETRIES_CONFIG, kafkaConfigProp.getRetries());
        return config;
    }
}
