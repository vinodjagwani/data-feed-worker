package de.market.feed.worker.websocket;

import de.market.feed.worker.websocket.config.WebSocketConfigProperties;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import javax.enterprise.context.ApplicationScoped;
import javax.websocket.CloseReason;
import javax.websocket.Endpoint;
import javax.websocket.EndpointConfig;
import javax.websocket.Session;


@Slf4j
@ApplicationScoped
@AllArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class WebSocketEndpoint extends Endpoint {

    WebSocketConfigProperties webSocketConfigProp;

    @Override
    public void onOpen(final Session session, final EndpointConfig endpointConfig) {
        if (!StringUtils.isEmpty(webSocketConfigProp.getSource().getMessage())) {
            session.getAsyncRemote().sendText(webSocketConfigProp.getSource().getMessage());
        }
    }

    @Override
    public void onClose(final Session session, final CloseReason closeReason) {
        log.warn("WebSocket closed reason: {}", closeReason.getReasonPhrase());
    }

    @Override
    public void onError(final Session session, final Throwable e) {
        log.error("Error occurred in web socket: ", e);
    }
}
