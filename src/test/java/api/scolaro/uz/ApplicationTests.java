package api.scolaro.uz;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ApplicationTests {
//    @Autowired
//    private UniversityService universityService;

    //    private AppApplicationFilterRepository appApplicationFilterRepository;
//    private ResourceMessageService resourceMessageService;
    @Test
    void contextLoads() {
//        universityService.getTopUniversity(AppLanguage.uz);
//        AppApplicationFilterDTO dto = new AppApplicationFilterDTO();
//        appApplicationFilterRepository.filterForAdmin(dto, 0, 30);
//        System.out.println(MD5Util.getMd5("123456"));
//        for (int i=0; i<5; i++){
//            System.out.println(UUID.randomUUID().toString());
//        }
       /* StompHeaders connectHeaders = new StompHeaders();
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


        System.out.println(subscribe.getSubscriptionId());*/

    }
}
