package de.market.feed.worker.websocket;

import de.market.feed.worker.websocket.config.WebSocketConfigProperties;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import javax.enterprise.context.RequestScoped;
import javax.websocket.ClientEndpointConfig;
import javax.websocket.Session;
import javax.websocket.WebSocketContainer;
import java.net.URI;


@Slf4j
@RequestScoped
@AllArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class WebSocketClient {

    WebSocketEndpoint webSocketEndpoint;

    WebSocketContainer webSocketContainer;

    WebSocketConfigProperties webSocketConfigProperties;

    public Session connectSocket() {
        log.info("WebSocket creating connection with url: {}", webSocketConfigProperties.getSource().getUrl());
        final ClientEndpointConfig clientEndpointConfig = ClientEndpointConfig.Builder.create().build();
        try {
            return webSocketContainer.connectToServer(webSocketEndpoint, clientEndpointConfig, new URI(webSocketConfigProperties.getSource().getUrl()));
        } catch (Exception ex) {
            log.error("WebSocket connection error: ", ex);
        }
        return null;
    }
}
