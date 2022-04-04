package de.market.feed.worker;

import de.market.feed.worker.kafka.KafkaMessageProducer;
import de.market.feed.worker.websocket.WebSocketClient;
import io.quarkus.runtime.StartupEvent;
import io.quarkus.scheduler.Scheduled;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.health.Liveness;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.websocket.MessageHandler;
import javax.websocket.Session;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Objects;

@Slf4j
@Liveness
@ApplicationScoped
public class DataFeedStarter {

    private Session session;

    @Inject
    WebSocketClient webSocketClient;

    @Inject
    KafkaMessageProducer kafkaMessageProducer;

    public void onStart(@Observes final StartupEvent event) {
        log.info("------ Data Feed worker started ------");
        try {
            session = webSocketClient.connectSocket();
            session.addMessageHandler(getMessageHandler());
        } catch (Exception ex) {
            log.error("Unable to start web socket from start even: ", ex);
        }
    }

    private MessageHandler getMessageHandler() {
        return new MessageHandler.Whole<String>() {
            @Override
            public void onMessage(final String o) {
                kafkaMessageProducer.sendMessageToKafka(o);
            }
        };
    }

    @Scheduled(every = "10s")
    public void reconnectSocket() throws IOException {
        session = Objects.isNull(session) ? webSocketClient.connectSocket() : session;
        if (Objects.nonNull(session) && !session.isOpen()) {
            session = webSocketClient.connectSocket();
            session.getAsyncRemote().sendPong(ByteBuffer.allocate(Long.BYTES));
        }
    }
}
