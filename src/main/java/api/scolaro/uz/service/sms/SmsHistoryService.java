package api.scolaro.uz.service.sms;

import api.scolaro.uz.dto.ApiResponse;
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
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class SmsHistoryService {

    private final SmsHistoryRepository smsHistoryRepository;
    private final ResourceMessageService resourceMessageService;
    private final SmsSenderService smsSenderService;
    private static final int smsCountLimit = 3;

    public void sendRegistrationSms(String phoneNumber, String signature) {
        if (signature == null) {
            signature = "";
        }
        String smsCode = RandomUtil.getRandomSmsCode();
//        String text = "Scolaro.uz partali. Ro'yxatdan o'tish tasdiqlash kodi: " + smsCode + "\n" + signature;
//        String text = "<#>kitabu.uz partali. Ro'yxatdan o'tish uchun tasdiqlash kodi: " + smsCode + "\n" + signature;
        String text = "<#>Scolaro.uz partali. Ro'yxatdan o'tish uchun tasdiqlash kodi: " + smsCode + "\n" + signature;
        sendMessage(phoneNumber, text, SmsType.REGISTRATION, smsCode);
    }


    public void sendMessage(String phone, String text, SmsType type, String smsCode) {
        Long countSms = smsHistoryRepository.countByPhoneAndCreatedDateBetween(phone,
                LocalDateTime.now().minusMinutes(2),
                LocalDateTime.now());
        if (countSms < smsCountLimit) {

            SmsHistoryEntity smsHistory = new SmsHistoryEntity();
            smsHistory.setSmsCode(smsCode);
            smsHistory.setType(type);
            smsHistory.setSmsCount(0);
            smsHistory.setStatus(SmsStatus.SEND);
            smsHistory.setPhone(phone);
            smsHistory.setSmsText(text);
            /*SmsHistoryEntity build = SmsHistoryEntity
                    .builder()
                    .smsCode(smsCode)
                    .type(type)
                    .status(SmsStatus.SEND)
                    .phone(newPhone)
                    .smsText(text)
                    .smsCount(0)
                    .build();*/
            SmsHistoryEntity sms = smsHistoryRepository.save(smsHistory);
            smsSenderService.sendSmsHTTP(sms);
            return;
        }
        throw new SmsLimitOverException(resourceMessageService.getMessage("sms.limit.over"));
    }

    public ApiResponse<String> checkSmsCode(String phone, String code) {
        Optional<SmsHistoryEntity> optional = smsHistoryRepository.findTopByPhoneOrderByCreatedDateDesc(phone);
        if (optional.isEmpty()) {
            log.warn("Phone Incorrect! Phone = {}, code = {}", phone, code);
            return new ApiResponse<>(resourceMessageService.getMessage("sms.code.incorrect"), 400, true);
        }

        SmsHistoryEntity entity = optional.get();
        if (entity.getCreatedDate().plusMinutes(2L).isBefore(LocalDateTime.now())) {
            log.warn("Sms Code Incorrect! Phone = {}, code = {}", phone, code);
            smsHistoryRepository.updateStatus(entity.getId(), SmsStatus.USED_WITH_TIMEOUT);
            return new ApiResponse<>(resourceMessageService.getMessage("sms.time-out"), 400, true);
        }
        if (!entity.getSmsCode().equals(code)) {
            return new ApiResponse<>(resourceMessageService.getMessage("sms.code.incorrect"), 400, true);
        }
        smsHistoryRepository.updateStatus(entity.getId(), SmsStatus.IS_USED);
        return new ApiResponse<>("Success!", 200, false);
    }

    public void sendResetSms(String phone, String signature) {
        if (signature == null) {
            signature = "";
        }
        String smsCode = RandomUtil.getRandomSmsCode();
        String text = "<#>Scolaro.uz partali - parolni qayta tiklash uchun tasdiqlash kodi: " + smsCode + "\n" + signature;
//        String text = "<#>kitabu.uz partali. Ro'yxatdan o'tish uchun tasdiqlash kodi: " + smsCode + "\n" + signature;
        sendMessage(phone, text, SmsType.RESET_PASSWORD, smsCode);
    }
}
