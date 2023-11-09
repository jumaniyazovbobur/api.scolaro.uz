package api.scolaro.uz.service;


import api.scolaro.uz.config.details.EntityDetails;
import api.scolaro.uz.dto.ApiResponse;
import api.scolaro.uz.dto.FilterResultDTO;
import api.scolaro.uz.dto.feeback.FeedbackDTO;
import api.scolaro.uz.dto.feeback.FeedbackFilterDTO;
import api.scolaro.uz.entity.FeedbackEntity;
import api.scolaro.uz.enums.FeedBackType;
import api.scolaro.uz.enums.RoleEnum;
import api.scolaro.uz.exp.AppBadRequestException;
import api.scolaro.uz.exp.ItemNotFoundException;
import api.scolaro.uz.mapper.FeedbackFilterMapperDTO;
import api.scolaro.uz.repository.feedback.CustomFeedbackRepository;
import api.scolaro.uz.repository.feedback.FeedbackRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class FeedbackService {
    private final CustomFeedbackRepository customFeedbackRepository;
    private final ResourceMessageService resourceMessageService;
    private final FeedbackRepository feedbackRepository;


    public ApiResponse<FeedbackEntity> create(FeedbackDTO dto) {
        if (EntityDetails.hasRole(RoleEnum.ROLE_ADMIN, EntityDetails.getCurrentProfileRoleList())) {
            throw new AppBadRequestException("Admin can not create feddback");
        }
        FeedbackEntity feedbackEntity = new FeedbackEntity();
        feedbackEntity.setContent(dto.getContent());
        feedbackEntity.setPersonId(EntityDetails.getCurrentUserId());
        feedbackEntity.setFeedbackType(FeedBackType.valueOf(EntityDetails.getCurrentProfileRoleList().get(0).substring(5)));
        feedbackRepository.save(feedbackEntity);
        return new ApiResponse<>(200, false);
    }

    public ApiResponse<Boolean> delete(String id) {
        int i = feedbackRepository.deleted(id, EntityDetails.getCurrentUserId(), LocalDateTime.now());
        return new ApiResponse<>(200, false, i > 0);

    }

    public FeedbackEntity get(String id) {
        return feedbackRepository.findById(id).orElseThrow(() -> {
            log.warn("Country not found");
            return new ItemNotFoundException("Country not found");
        });
    }

    public ApiResponse<Page<FeedbackFilterMapperDTO>> filterForAdmin(FeedbackFilterDTO filter, int page, int size) {
        FilterResultDTO<FeedbackFilterMapperDTO> filterResult = customFeedbackRepository.filterForAdmin(filter, page, size);
        Page<FeedbackFilterMapperDTO> pageObj = new PageImpl<>(filterResult.getContent(), PageRequest.of(page, size), filterResult.getTotalElement());
        return ApiResponse.ok(pageObj);
    }

}
