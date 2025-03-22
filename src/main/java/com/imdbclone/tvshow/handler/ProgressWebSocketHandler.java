package com.imdbclone.tvshow.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Component
@Slf4j
public class ProgressWebSocketHandler extends TextWebSocketHandler {

    private final Map<UUID, WebSocketSession> sessionMap = new ConcurrentHashMap<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        log.info("WebSocket connection established: {}", session.getId());
    }

    @Override
    protected void handleTextMessage(@NonNull WebSocketSession session, TextMessage message) {
        UUID uploadId = UUID.fromString(message.getPayload());
        sessionMap.put(uploadId, session);
    }

    public void sendProgressUpdate(UUID uploadId, AtomicInteger progress) {
        WebSocketSession session = sessionMap.remove(uploadId);
        if (session != null && session.isOpen()) {
            try {
                session.sendMessage(new TextMessage(uploadId + ": " + progress + " %"));
                if (progress.get() >= 100 || progress.get() == -1) {
                    session.close(); // Close session properly
                }
            } catch (IOException e) {
                log.error("Error sending WebSocket message or closing session", e);
            }
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, @NonNull CloseStatus status) {
        log.info("WebSocket connection closed: {}", session.getId());
        sessionMap.values().remove(session);
    }
}

