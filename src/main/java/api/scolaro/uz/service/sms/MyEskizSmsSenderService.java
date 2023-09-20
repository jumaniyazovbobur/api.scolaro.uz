package api.scolaro.uz.service.sms;



import api.scolaro.uz.entity.sms.SmsEntity;
import api.scolaro.uz.entity.sms.SmsTokenEntity;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;

@Slf4j
@Service
public class MyEskizSmsSenderService implements SmsSenderService {

    @Value("${my.eskiz.url}")
    private String url;

    @Value("${my.eskiz.uz.email}")
    private String email;

    @Value("${my.eskiz.uz.password}")
    private String password;
    private final SmsTokenRepository smsTokenRepository;

    @Autowired
    public MyEskizSmsSenderService(SmsTokenRepository smsTokenRepository) {
        this.smsTokenRepository = smsTokenRepository;
    }

    public void sendSmsHTTP(SmsEntity smsHistory) {
        String token = "Bearer " + getSmsToken();
        OkHttpClient client = new OkHttpClient();
        String prPhone = smsHistory.getPhone();
        if (prPhone.startsWith("+")) {
            prPhone = prPhone.substring(1);
        }

        RequestBody body = new MultipartBody.Builder().setType(MultipartBody.FORM)
                .addFormDataPart("mobile_phone", prPhone)
                .addFormDataPart("message", smsHistory.getContent())
                .addFormDataPart("from", "4546")

                .build();
        Request request = new Request.Builder()
                .url(url + "/api/message/sms/send")
                .method("POST", body)
                .header("Authorization", token)
                .build();

        Thread thread = new Thread(() -> {
            try {
                Response response = client.newCall(request).execute();
                if (response.isSuccessful()) {
                    System.out.println(response);
                } else {
                    log.info("Sms Token Exeption");
                    throw new IOException();
                }
            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException();
            }
        });

        Thread.UncaughtExceptionHandler uncaughtExceptionHandler = (th, ex) -> {
            ex.printStackTrace();
        };
        thread.setUncaughtExceptionHandler(uncaughtExceptionHandler);
        thread.start();
    }

    public String getSmsToken() {
        SmsTokenEntity smsToken = smsTokenRepository.findByEmail(email);

        if (smsToken == null) {
            String token = myEskizUzLogin();
            SmsTokenEntity smsEntity = new SmsTokenEntity();
            smsEntity.setEmail(email);
            smsEntity.setCreatedDate(LocalDate.now());
            smsEntity.setToken(token);
            smsTokenRepository.save(smsEntity);
            return token;
        }
        if (!smsToken.getCreatedDate().plusDays(25).equals(LocalDate.now())) {
            return smsToken.getToken();
        } else { // REFLESH TOKEN
            OkHttpClient client = new OkHttpClient().newBuilder()
                    .build();
            MediaType mediaType = MediaType.parse("text/plain");
            RequestBody body = RequestBody.create(mediaType, "");
            Request request = new Request.Builder()
                    .url(url + "/api/auth/refresh")
                    .method("PATCH", body)
                    .header("Authorization", "Bearer " + smsToken.getToken())
                    .build();

            Response response;
            try {
                response = client.newCall(request).execute();  // TODO
                if (!response.isSuccessful()) {
                    log.info("Sms Token Exeption");
                    throw new IOException();
                } else {
                    return smsToken.getToken();
                }
            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException();
            }
        }

    }

    public String myEskizUzLogin() {
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        RequestBody body = new MultipartBody.Builder().setType(MultipartBody.FORM)
                .addFormDataPart("email", email)
                .addFormDataPart("password", password)
                .build();
        Request request = new Request.Builder()
                .url("https://notify.eskiz.uz/api/auth/login")
                .method("POST", body)
                .build();

        Response response;
        try {
            response = client.newCall(request).execute();
            if (!response.isSuccessful()) {
                log.info("Sms Token Exeption");
                throw new IOException();
            } else {
                JSONObject object = new JSONObject(response.body().string());
                JSONObject data = object.getJSONObject("data");
                Object token = data.get("token");
                return token.toString();
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }

    }

}
