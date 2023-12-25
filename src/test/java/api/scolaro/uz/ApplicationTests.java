package api.scolaro.uz;


import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import api.scolaro.uz.enums.AppLanguage;
import api.scolaro.uz.service.ResourceMessageService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.socket.WebSocketHttpHeaders;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;
import org.springframework.web.socket.sockjs.client.SockJsClient;
import org.springframework.web.socket.sockjs.client.Transport;
import org.springframework.web.socket.sockjs.client.WebSocketTransport;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ApplicationTests {

    @LocalServerPort
    private Integer port;
    private MockMvc mockMvc;

    private CompletableFuture<Object> completableFuture;
    @Autowired
    WebApplicationContext wac;

    @BeforeEach
    void setUp() {
        completableFuture = new CompletableFuture<>();
        mockMvc = MockMvcBuilders.webAppContextSetup(wac)
                .apply(springSecurity())
                .build();
    }

    @SneakyThrows
    @Test
    void contextLoads() {
        StompHeaders connectHeaders = new StompHeaders();
        connectHeaders.add("X-Auth", "Bearer eyJhbGciOiJIUzUxMiJ9.eyJpYXQiOjE3MDMxNTUyNzIsImlkIjoiODA4MGI3ZWY4YmE0NjIzNjAxOGJhZTU0Njg2MzAwMjgiLCJwaG9uZSI6Ijk5ODkxNTcyMTIxMyIsInJvbGVzIjoiUk9MRV9TVFVERU5UIiwiZXhwIjoxNzAzNzYwMDcyLCJpc3MiOiJKYXZhIGJhY2tlbmQifQ.uK003y6T8LdQQ5pqSkLp1MHPh74pkhU2p6rcGXAw7tJWkZ_kG0zfGwpCkWrWUZl9Z0tiX6MDhcmguFkWkPa4cQ");

        WebSocketStompClient stompClient = new WebSocketStompClient(new SockJsClient(createTransportClient()));
        stompClient.setMessageConverter(new MappingJackson2MessageConverter());

        WebSocketHttpHeaders httpHeaders = new WebSocketHttpHeaders();
        httpHeaders.put("Authorization", Collections.singletonList("Bearer eyJhbGciOiJIUzUxMiJ9.eyJpYXQiOjE3MDMxNTUyNzIsImlkIjoiODA4MGI3ZWY4YmE0NjIzNjAxOGJhZTU0Njg2MzAwMjgiLCJwaG9uZSI6Ijk5ODkxNTcyMTIxMyIsInJvbGVzIjoiUk9MRV9TVFVERU5UIiwiZXhwIjoxNzAzNzYwMDcyLCJpc3MiOiJKYXZhIGJhY2tlbmQifQ.uK003y6T8LdQQ5pqSkLp1MHPh74pkhU2p6rcGXAw7tJWkZ_kG0zfGwpCkWrWUZl9Z0tiX6MDhcmguFkWkPa4cQ"));

        StompSession stompSession = stompClient.connectAsync("ws://localhost:" + port + "/chat-websocket", httpHeaders, connectHeaders,
                new StompSessionHandlerAdapter() {
                }).get(5, SECONDS);

        mockMvc.perform(
                post(
                        "localhost:" + port + "/api/v1/simple-message"
                )
        );

        StompSession.Subscription subscribe = stompSession.subscribe("/topic/messages/8080b7ef8bae722c018bae85c0cf0002", new StompSessionHandlerAdapter() {
        });


        System.out.println(subscribe.getSubscriptionId());
    }

    private List<Transport> createTransportClient() {
        List<Transport> transports = new ArrayList<>(1);
        transports.add(new WebSocketTransport(new StandardWebSocketClient()));
        return transports;
    }
}
