package api.scolaro.uz.service;


import api.scolaro.uz.config.details.EntityDetails;
import api.scolaro.uz.dto.ApiResponse;
import api.scolaro.uz.dto.FeedbackDTO;
import api.scolaro.uz.entity.FeedbackEntity;
import api.scolaro.uz.entity.PersonRoleEntity;
import api.scolaro.uz.entity.place.CountryEntity;
import api.scolaro.uz.enums.FeedBackType;
import api.scolaro.uz.enums.RoleEnum;
import api.scolaro.uz.exp.AppBadRequestException;
import api.scolaro.uz.exp.ItemNotFoundException;
import api.scolaro.uz.repository.FeedbackRepository;
import api.scolaro.uz.repository.profile.PersonRoleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class FeedbackService {
    private final PersonRoleRepository personRoleRepository;
    private final ResourceMessageService resourceMessageService;
    private final FeedbackRepository feedbackRepository;


    public ApiResponse<FeedbackEntity> create(FeedbackDTO dto) {
        if (dto.getFeedbackType().equals(FeedBackType.ROLE_STUDENT)) {
            PersonRoleEntity personRoleEntity = feedbackRepository.findPersonRoleEnumList(EntityDetails.getCurrentUserId(), RoleEnum.valueOf(dto.getFeedbackType().name()));
            if (personRoleEntity == null){
                return new ApiResponse<>(resourceMessageService.getMessage("feedback.type.not.correct"), 400, true);

            }
        } else if (dto.getFeedbackType().equals(FeedBackType.ROLE_CONSULTING)) {
            PersonRoleEntity personRoleEntity = feedbackRepository.findPersonRoleEnumList(EntityDetails.getCurrentUserId(), RoleEnum.valueOf(dto.getFeedbackType().name()));
            if (personRoleEntity == null){
                return new ApiResponse<>(resourceMessageService.getMessage("feedback.type.not.correct"), 400, true);
            }
        }

        FeedbackEntity feedbackEntity = new FeedbackEntity();
        feedbackEntity.setContent(dto.getContent());
        feedbackEntity.setPersonId(EntityDetails.getCurrentUserId());
        feedbackEntity.setFeedbackType(dto.getFeedbackType());

        feedbackRepository.save(feedbackEntity);
        return new ApiResponse<>(200, false, feedbackEntity);
    }

    public ApiResponse<Boolean> delete(String id) {
        FeedbackEntity entity = get(id);
        if (entity.getVisible().equals(Boolean.FALSE)) {
            log.warn("Is visible false");
            throw new AppBadRequestException("Is visible false");
        }
        int i = feedbackRepository.deleted(id, EntityDetails.getCurrentUserId(), LocalDateTime.now());
        return new ApiResponse<>(200, false, i > 0);

    }

    public FeedbackEntity get(String id) {
        return feedbackRepository.findById(id).orElseThrow(() -> {
            log.warn("Country not found");
            return new ItemNotFoundException("Country not found");
        });
    }
}
