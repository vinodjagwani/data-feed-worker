package de.market.feed.worker.websocket.config;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.websocket.ContainerProvider;
import javax.websocket.WebSocketContainer;

@ApplicationScoped
public class WebSocketConfig {

    @Produces
    public WebSocketContainer webSocketContainer() {
        return ContainerProvider.getWebSocketContainer();
    }
}
