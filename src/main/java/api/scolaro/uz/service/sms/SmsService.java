//package api.scolaro.uz.service.sms;
//
//
//import api.scolaro.uz.entity.sms.SmsEntity;
//import api.scolaro.uz.enums.sms.SmsStatus;
//import api.scolaro.uz.enums.sms.SmsType;
//import api.scolaro.uz.exp.SmsLimitOverException;
//import api.scolaro.uz.util.RandomUtil;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.stereotype.Service;
//
//import java.time.LocalDateTime;
//import java.util.Optional;
//
//@Service
//@Slf4j
//@RequiredArgsConstructor
//public class SmsService {
//    private final SmsRepository smsRepository;
//
//    private final SmsSenderService smsSenderService;
//    private static final int smsCountLimit = 3;
//
//    public void sendRegistrationSms(String phone) {
//        String smsCode = RandomUtil.getRandomSmsCode();
//        String text = "dasturlash.uz. Ro'yhatdan o'tish uchun tasdiqlash kodi: \n" + smsCode;
//        sendMessage(phone, text, SmsType.REGISTRATION, smsCode);
//    }
//
//    public void sendChangePhoneNumberSms(String phone) {
//        String smsCode = RandomUtil.getRandomSmsCode();
//        String text = "dasturlash.uz. Telefon raqamni o'zgartirish uchun tastiqlash kodi:\n" + smsCode;
//        sendMessage(phone, text, SmsType.CHANGE_PHONE, smsCode);
//    }
//
//    public String sendResetPasswordSms(String phone) {
//        String code = RandomUtil.getRandomString(6);
//        String text = "Parolni o'zgartirish uchun tastiqlash kodi: " + code;
//        sendMessage(phone, text, SmsType.RESET_PASSWORD, code);
//        return code;
//    }
//
//
//    public void sendMessage(String phone, String text, SmsType type, String code) {
//        Long countSms = smsRepository.countByPhoneAndCreatedDateBetween(phone, LocalDateTime.now().minusMinutes(2), LocalDateTime.now());
//        if (countSms < smsCountLimit) {
//            SmsEntity smsEntity = new SmsEntity();
//            smsEntity.setStatus(SmsStatus.SEND);
//            smsEntity.setType(type);
//            smsEntity.setType(type);
//            smsEntity.setPhone(phone);
//            smsEntity.setContent(text);
//            smsEntity.setSmsCode(code);
//
//            smsRepository.save(smsEntity);
//            log.warn("Sms was send to phone = {}, code = {}", phone, text);
//            smsSenderService.sendSmsHTTP(smsEntity);
//            return;
//        }
//        throw new SmsLimitOverException("Eme limit tugadi. Kuting...");
//    }
//
//    public Boolean checkSmsCode(String phone, String code) {
//        Optional<SmsEntity> optional = smsRepository.findTopByPhoneOrderByCreatedDateDesc(phone);
//        if (optional.isEmpty()) {
//            log.warn("Phone Incorrect! Phone = {}, code = {}", phone, code);
//            return false;
//        }
//        SmsEntity entity = optional.get();
//        if (entity.getCreatedDate().plusMinutes(2L).isBefore(LocalDateTime.now())) {
//            log.warn("Sms Code Incorrect! Phone = {}, code = {}", phone, code);
//            smsRepository.updateStatus(entity.getId(), SmsStatus.USED_WITH_TIMEOUT);
//            return false;
//        }
//        if (!entity.getSmsCode().equals(code)) {
//            return false;
//        }
//        smsRepository.updateStatus(entity.getId(), SmsStatus.IS_USED);
//        return true;
//    }
//
//}
