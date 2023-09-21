package api.scolaro.uz.service;


import api.scolaro.uz.config.details.EntityDetails;
import api.scolaro.uz.dto.ApiResponse;
import api.scolaro.uz.dto.client.ClientRequestDTO;
import api.scolaro.uz.dto.profile.*;
import api.scolaro.uz.entity.profile.UserEntity;
import api.scolaro.uz.enums.GeneralStatus;
import api.scolaro.uz.enums.RoleEnum;
import api.scolaro.uz.exp.AppBadRequestException;
import api.scolaro.uz.exp.ItemNotFoundException;
import api.scolaro.uz.repository.ProfileRepository;
import api.scolaro.uz.util.MD5Util;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class ProfileService {
    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private PersonRoleService personRoleService;

    public ProfileDTO getCurrentProfileDetail() {
        UserEntity profile = get(EntityDetails.getCurrentUserId());
        return toDto(profile);
    }

    public ProfileDTO addProfile(CreateProfileDTO dto) {
        UserEntity entity = new UserEntity();
        entity.setName(dto.getName());
        entity.setSurname(dto.getSurname());
        entity.setPassword(MD5Util.getMd5(dto.getPassword()));
        entity.setPhone(dto.getPhone());
//        if (hasRole(dto.getRoles(), RoleEnum.ROLE_FACULTY)) {
//            if (dto.getFacultyId() == null) {
//                log.info("Faculty required");
//                throw new ItemNotFoundException("Faculty required");
//            }
//            entity.setFacultyId(dto.getFacultyId());
//        }
        entity.setStatus(GeneralStatus.ACTIVE);
        entity.setCreatedDate(LocalDateTime.now());
        profileRepository.save(entity);// save profile
        personRoleService.create(entity.getId(), dto.getRoles()); // save profile roles
        return toDto(entity);
    }

    public ProfileDTO getById(String id) {
        UserEntity entity = get(id);
        if (entity == null) {
            log.info("Such id not" + id);
            throw new ItemNotFoundException("Such id not" + id);
        }
        ProfileDTO dto = toDto(entity);
        dto.setRoles(personRoleService.getProfileRoleList(id));
        return dto;
    }

    public Boolean deleteById(String id) {
        Optional<UserEntity> optional = profileRepository.findById(id);
        if (optional.isEmpty()) {
            log.info("Profile not found");
            throw new ItemNotFoundException("Bunaqa profile mavjud emas");
        }
        UserEntity entity = optional.get();
        entity.setVisible(false);
        profileRepository.save(entity);
        return true;
    }

    public ProfileDTO updateProfile(String id, CreateProfileDTO dto) {
        UserEntity entity = get(id);
        entity.setName(dto.getName());
        entity.setSurname(dto.getSurname());
        entity.setPhone(dto.getPhone());
//        if (hasRole(dto.getRoles(), RoleEnum.ROLE_FACULTY)) {
//            if (dto.getFacultyId() == null) {
//                throw new ItemNotFoundException("Faculty required");
//            }
//            entity.setFacultyId(dto.getFacultyId());
//        }
        profileRepository.save(entity);
        return toDto(entity);
    }

    public ApiResponse<?> updateDetail(UpdateProfileDetailDTO dto) {
        String profileId = EntityDetails.getCurrentUserId();
        Optional<UserEntity> optional = profileRepository.findById(profileId);
        if (optional.isEmpty()) {
            log.info("Profile not found.");
            throw new ItemNotFoundException("Profile not found.");
        }
        profileRepository.updateDetail(profileId, dto.getName(), dto.getSurname());
        return ApiResponse.ok();
    }

    public ApiResponse<?> updatePassword(UpdatePasswordDTO dto) {
        String profileId = EntityDetails.getCurrentUserId();
        UserEntity entity = get(profileId);
        if (!entity.getPassword().equals(MD5Util.getMd5(dto.getOldPassword()))) {
            log.info("Wrong password");
            throw new AppBadRequestException("Wrong password");
        }
        profileRepository.updatePassword(profileId, MD5Util.getMd5(dto.getOldPassword()));
        return ApiResponse.ok();
    }

    public PageImpl<ProfileDTO> filter(ProfileFilterRequestDTO dto) {
        // bitta sql queryda profile role larni ham olib kelish kerka. ularni , bilan ajratish kerak. ROLE_ADMIN, ROLE_FACULTY ...
        // builder.append(" ,(select string_agg(role,', ') from profile_role where profile_id = p.id) as roleList");
        return null;
    }

    public UserEntity get(String id) {
        Optional<UserEntity> optional = profileRepository.findById(id);
        if (optional.isEmpty()) {
            log.info("Profile not found: " + id);
            throw new ItemNotFoundException("Profile not found: " + id);
        }
        return optional.get();
    }

    public ProfileDTO toDto(UserEntity profileEntity) {
        ProfileDTO profileDTO = new ProfileDTO();
        profileDTO.setId(profileEntity.getId());
        profileDTO.setName(profileEntity.getName());
        profileDTO.setSurname(profileEntity.getSurname());
        profileDTO.setPhone(profileEntity.getPhone());
        profileDTO.setCreatedDate(profileEntity.getCreatedDate());
        return profileDTO;
    }

    private boolean hasRole(List<RoleEnum> roleList, RoleEnum requiredRole) {
        if (roleList == null || roleList.isEmpty()) {
            return false;
        }
        for (RoleEnum roleEnum : roleList) {
            if (roleEnum.equals(requiredRole)) {
                return true;
            }
        }
        return false;
    }

    public void registration(ClientRequestDTO dto) {
    }
}
