package api.scolaro.uz.service;



import api.scolaro.uz.dto.ResetPasswordConfirmDTO;
import api.scolaro.uz.dto.ResetPasswordRequestDTO;
import api.scolaro.uz.dto.profile.ProfileResponseDTO;

import api.scolaro.uz.entity.UserEntity;
import api.scolaro.uz.enums.GeneralStatus;
import api.scolaro.uz.exp.ItemNotFoundException;
import api.scolaro.uz.repository.PersonRoleRepository;
import api.scolaro.uz.repository.ProfileRepository;
import api.scolaro.uz.util.PhoneUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class AuthService {
    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private PersonRoleRepository personRoleRepository;

//    public ProfileResponseDTO login(AuthDTO dto) {
//        Optional<ProfileEntity> optional = profileRepository.findByPhone(dto.getPhone());
//        if (optional.isEmpty()) {
//            log.info("User not found.");
//            throw new ItemNotFoundException("User not found.");
//        }
//        ProfileEntity entity = optional.get();
//        if (!entity.getStatus().equals(GeneralStatus.ACTIVE)) {
//            log.info("Wrong status!");
//            throw new ArithmeticException("Wrong status!");
//        }
//        if (!entity.getPassword().equals(MD5Util.getMd5(dto.getPassword()))) {
//            log.info("User not found.");
//            throw new ItemNotFoundException("User not found.");
//        }
//        ProfileResponseDTO response = new ProfileResponseDTO();
//        response.setName(entity.getName());
//        response.setSurname(entity.getSurname());
//        response.setPhone(entity.getPhone());
//        List<RoleEnum> roleList = personRoleRepository.findPersonRoleEnumList(entity.getId());
//        response.setRoles(roleList);
//        response.setJwt(JwtUtil.encode(entity.getId(), entity.getPhone(), roleList));
//        return null;
//    }

    public void resetPasswordRequest(ResetPasswordRequestDTO dto) {
        if (!PhoneUtil.isValidPhone(dto.getPhone())) {
            log.info("Not valid phone number");
            throw new ItemNotFoundException("Not valid phone number");
        }
        Optional<UserEntity> optional = profileRepository.findByPhone(dto.getPhone());
        if (optional.isEmpty()) {
            log.info("Phone number not found");
            throw new ItemNotFoundException("Phone number not found");
        }
        UserEntity profile = optional.get();
        if (!profile.getStatus().equals(GeneralStatus.ACTIVE)) {
            log.info("Profile not active.");
            throw new ItemNotFoundException("Profile not active.");
        }
//        smsService.sendResetPasswordSms(dto.getPhone());
    }

    public void resetPasswordConfirm(ResetPasswordConfirmDTO dto) {
//        if (!PhoneUtil.isValidPhone(dto.getPhone())) {
//            log.info("Not valid phone number");
//            throw new ItemNotFoundException("Not valid phone number");
//        }
//        Optional<UserEntity> optional = profileRepository.findByPhone(dto.getPhone());
//        if (optional.isEmpty()) {
//            log.info("Phone number not found");
//            throw new ItemNotFoundException("Phone number not found");
//        }
//        UserEntity profile = optional.get();
//        if (!profile.getStatus().equals(GeneralStatus.ACTIVE)) {
//            log.info("Profile not active.");
//            throw new ItemNotFoundException("Profile not active.");
//        }
//        if (!smsService.checkSmsCode(dto.getPhone(), dto.getSmsCode())) {
//            log.info("Not valid sms code.");
//            throw new AppBadRequestException("Not valid sms code.");
//        }
//        if (!dto.getNewPassword().equals(dto.getRepeatNewPassword()) || dto.getNewPassword().length()<5) {
//            log.info("Not valid password");
//            throw new AppBadRequestException("Not valid password");
//        }
//        profileRepository.updatePassword(profile.getId(), MD5Util.getMd5(dto.getNewPassword()));
    }
}
