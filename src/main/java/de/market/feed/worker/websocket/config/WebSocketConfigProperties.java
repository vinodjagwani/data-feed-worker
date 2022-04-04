package de.market.feed.worker.websocket.config;

import io.quarkus.arc.config.ConfigProperties;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@ConfigProperties(prefix = "websocket")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class WebSocketConfigProperties {

    Source source;

    @Data
    @FieldDefaults(level = AccessLevel.PRIVATE)
    public static class Source {

        String name;

        String url;

        String message = "";
    }


}
