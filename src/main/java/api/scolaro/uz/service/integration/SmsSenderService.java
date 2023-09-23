package api.scolaro.uz.service.integration;



import api.scolaro.uz.entity.sms.SmsHistoryEntity;

public interface SmsSenderService {

    void sendSmsHTTP(SmsHistoryEntity smsHistory);

    String getSmsToken();

     String myEskizUzLogin();
}
