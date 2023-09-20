package api.scolaro.uz.service.sms;


import api.scolaro.uz.entity.sms.SmsEntity;

public interface SmsSenderService {

    void sendSmsHTTP(SmsEntity smsHistory);

    String getSmsToken();

    public String myEskizUzLogin();
}
