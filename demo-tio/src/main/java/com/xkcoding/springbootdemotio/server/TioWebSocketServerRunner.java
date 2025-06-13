package com.xkcoding.springbootdemotio.server;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.tio.core.Tio;
import org.tio.core.ChannelContext;
import org.tio.http.common.HttpRequest;
import org.tio.http.common.HttpResponse;
import org.tio.websocket.common.WsRequest;
import org.tio.websocket.common.WsResponse;
import org.tio.websocket.server.WsServerStarter;
import org.tio.websocket.server.handler.IWsMsgHandler;

/**
 * Start a simple Tio WebSocket server on application startup.
 */
@Slf4j
@Component
public class TioWebSocketServerRunner implements CommandLineRunner {

    @Value("${tio.websocket.port:9876}")
    private int port;

    @Override
    public void run(String... args) throws Exception {
        WsServerStarter starter = new WsServerStarter(port, new SimpleWsMsgHandler());
        starter.start();
        log.info("Tio WebSocket server started on port {}", port);
    }

    /**
     * Very simple WebSocket message handler that echoes received text messages.
     */
    private static class SimpleWsMsgHandler implements IWsMsgHandler {
        @Override
        public HttpResponse handshake(HttpRequest request, HttpResponse response, ChannelContext ctx) {
            return response;
        }

        @Override
        public void onAfterHandshaked(HttpRequest request, HttpResponse response, ChannelContext ctx) {
            log.info("WebSocket handshake completed: {}", ctx.getClientNode());
        }

        @Override
        public Object onBytes(WsRequest request, byte[] bytes, ChannelContext ctx) {
            return null;
        }

        @Override
        public Object onClose(WsRequest request, byte[] bytes, ChannelContext ctx) {
            return null;
        }

        @Override
        public Object onText(WsRequest request, String text, ChannelContext ctx) {
            log.info("Received text: {}", text);
            WsResponse resp = WsResponse.fromText(text, "utf-8");
            Tio.send(ctx, resp);
            return null;
        }
    }
}
