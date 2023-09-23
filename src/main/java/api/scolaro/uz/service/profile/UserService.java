package api.scolaro.uz.service.profile;

import api.scolaro.uz.dto.FilterResultDTO;
import api.scolaro.uz.dto.profile.ProfileDTO;
import api.scolaro.uz.dto.profile.ProfileFilterDTO;
import api.scolaro.uz.dto.profile.ProfileRegDTO;
import api.scolaro.uz.entity.profile.UserEntity;
import api.scolaro.uz.exp.ItemNotFoundException;
import api.scolaro.uz.repository.profile.CustomUserRepository;
import api.scolaro.uz.repository.profile.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CustomUserRepository customUserRepository;

    public ProfileDTO update(String id, ProfileRegDTO dto) {
        Optional<UserEntity> optional = userRepository.findByIdAndVisibleTrue(id);
        if (optional.isEmpty()) {
            log.info("Exception : {} user not found", id);
            throw new ItemNotFoundException("Not found");
        }
      // TODO update
        return null;
    }

    public ProfileDTO getId(String id) {
        Optional<UserEntity> optional = userRepository.findByIdAndVisibleTrue(id);
        if (optional.isEmpty()) {
            log.info("Exception : {} user not found", id);
            throw new ItemNotFoundException("Not found");
        }
        return toDTO(optional.get());
    }

    public PageImpl<ProfileDTO> filter(ProfileFilterDTO dto, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        FilterResultDTO<UserEntity> filterResultDTO = customUserRepository.filterPagination(dto, page, size);
        return new PageImpl<>(filterResultDTO.getContent().stream().map(this::toDTO).toList(), pageable, filterResultDTO.getTotalElement());
    }

    public PageImpl<ProfileDTO> getAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<UserEntity> entityPages = userRepository.findAll(pageable);
        return new PageImpl<>(entityPages.getContent().stream().map(this::toDTO)
                .toList(), pageable, entityPages.getTotalPages());
    }

    public ProfileDTO deleted(String id) {
        Optional<UserEntity> optional = userRepository.findByIdAndVisibleTrue(id);
        if (optional.isEmpty()) {
            log.info("Exception : {} user not found", id);
            throw new ItemNotFoundException("Not found");
        }
        userRepository.deleted(id);
        return toDTO(optional.get());
    }

    private ProfileDTO toDTO(UserEntity entity) {
        ProfileDTO dto = new ProfileDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setSurname(entity.getSurname());
        dto.setPhone(entity.getPhone());
        dto.setCreatedDate(entity.getCreatedDate());
        return dto;
    }
}
