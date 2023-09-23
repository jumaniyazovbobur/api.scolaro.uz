package api.scolaro.uz.service.sms;

import api.scolaro.uz.entity.sms.SmsHistoryEntity;
import api.scolaro.uz.enums.sms.SmsStatus;
import api.scolaro.uz.enums.sms.SmsType;
import api.scolaro.uz.exp.SmsLimitOverException;
import api.scolaro.uz.repository.sms.SmsHistoryRepository;
import api.scolaro.uz.service.ResourceMessageService;
import api.scolaro.uz.service.integration.SmsSenderService;
import api.scolaro.uz.util.RandomUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@Slf4j
@RequiredArgsConstructor
public class SmsHistoryService {

    private final SmsHistoryRepository smsHistoryRepository;
    private final ResourceMessageService resourceMessageService;
    private final SmsSenderService smsSenderService;
    private static final int smsCountLimit = 3;

    public void sendRegistrationSms(String phoneNumber) {
        String smsCode = RandomUtil.getRandomSmsCode();
        String text = "Scolaro ro'yhatdan o'tish tasdiqlash kodi: \n" + smsCode;
        sendMessage(phoneNumber,text,SmsType.CHANGE_PHONE,smsCode);
    }


    public void sendMessage(String phone, String text, SmsType type, String smsCode) {
        Long countSms = smsHistoryRepository.countByPhoneAndCreatedDateBetween(phone,
                LocalDateTime.now().minusMinutes(2),
                LocalDateTime.now());
        if (countSms < smsCountLimit) {
            SmsHistoryEntity build = SmsHistoryEntity
                    .builder()
                    .smsCode(smsCode)
                    .type(type)
                    .status(SmsStatus.SEND)
                    .phone(phone)
                    .smsText(text)
                    .smsCount(0)
                    .build();
            SmsHistoryEntity smsHistory = smsHistoryRepository.save(build);
            smsSenderService.sendSmsHTTP(smsHistory);
            return;
        }
        throw new SmsLimitOverException(resourceMessageService.getMessage("sms.limit.over"));
    }
}
