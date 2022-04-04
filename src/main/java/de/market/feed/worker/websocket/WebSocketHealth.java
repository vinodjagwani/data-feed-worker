package de.market.feed.worker.websocket;

import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.Liveness;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;


@Liveness
@ApplicationScoped
public class WebSocketHealth implements HealthCheck {


    @Inject
    WebSocketClient webSocketClient;

    @Override
    public HealthCheckResponse call() {
        return HealthCheckResponse.named("WebSocket").state(webSocketClient.connectSocket().isOpen()).build();
    }
}
