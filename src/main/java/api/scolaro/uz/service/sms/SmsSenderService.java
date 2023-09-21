package api.scolaro.uz.service.sms;


import api.scolaro.uz.entity.sms.SmsEntity;
import api.scolaro.uz.entity.sms.SmsHistoryEntity;

public interface SmsSenderService {

    void sendSmsHTTP(SmsHistoryEntity smsHistory);

    String getSmsToken();

    public String myEskizUzLogin();
}
