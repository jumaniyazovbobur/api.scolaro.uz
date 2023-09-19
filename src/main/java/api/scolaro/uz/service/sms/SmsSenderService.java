package api.scolaro.uz.service.sms;


import api.dean.db.entity.sms.SmsEntity;

public interface SmsSenderService {

    void sendSmsHTTP(SmsEntity smsHistory);

    String getSmsToken();

    public String myEskizUzLogin();
}
