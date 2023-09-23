package api.scolaro.uz.service.profile;

import api.scolaro.uz.dto.FilterResultDTO;
import api.scolaro.uz.dto.profile.UserDTO;
import api.scolaro.uz.dto.profile.UserFilterDTO;
import api.scolaro.uz.dto.profile.UserUpdateDTO;
import api.scolaro.uz.entity.profile.UserEntity;
import api.scolaro.uz.exp.ItemNotFoundException;
import api.scolaro.uz.repository.profile.CustomUserRepository;
import api.scolaro.uz.repository.profile.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.User;
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

    public UserDTO update(String id, UserUpdateDTO updateDTO) {
        Optional<UserEntity> optional = userRepository.findByIdAndVisibleTrue(id);
        if (optional.isEmpty()) {
            log.info("Exception : {} user not found", id);
            throw new ItemNotFoundException("Not found");
        }
      // TODO update
        return null;
    }

    public UserDTO getId(String id) {
        Optional<UserEntity> optional = userRepository.findByIdAndVisibleTrue(id);
        if (optional.isEmpty()) {
            log.info("Exception : {} user not found", id);
            throw new ItemNotFoundException("Not found");
        }
        return toDTO(optional.get());
    }

    public PageImpl<UserDTO> filter(UserFilterDTO userFilterDTO, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        FilterResultDTO<UserEntity> filterResultDTO = customUserRepository.filterPagination(userFilterDTO, page, size);
        return new PageImpl<>(filterResultDTO.getContent().stream().map(this::toDTO).toList(), pageable, filterResultDTO.getTotalElement());
    }

    public PageImpl<UserDTO> getAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<UserEntity> entityPages = userRepository.findAll(pageable);
        return new PageImpl<>(entityPages.getContent().stream().map(this::toDTO)
                .toList(), pageable, entityPages.getTotalPages());
    }

    public UserDTO deleted(String id) {
        Optional<UserEntity> optional = userRepository.findByIdAndVisibleTrue(id);
        if (optional.isEmpty()) {
            log.info("Exception : {} user not found", id);
            throw new ItemNotFoundException("Not found");
        }
        userRepository.deleted(id);
        return toDTO(optional.get());
    }

    private UserDTO toDTO(UserEntity entity) {
        UserDTO dto = new UserDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setSurname(entity.getSurname());
        dto.setPhone(entity.getPhone());
        dto.setCreatedDate(entity.getCreatedDate());
        return dto;
    }
}
