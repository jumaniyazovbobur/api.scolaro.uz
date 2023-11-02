package api.scolaro.uz.service;


import api.scolaro.uz.config.details.EntityDetails;
import api.scolaro.uz.dto.ApiResponse;
import api.scolaro.uz.dto.FilterResultDTO;
import api.scolaro.uz.dto.consultingComment.ConsultingCommentFilterDTO;
import api.scolaro.uz.dto.feeback.FeedbackDTO;
import api.scolaro.uz.dto.feeback.FeedbackFilterDTO;
import api.scolaro.uz.entity.FeedbackEntity;
import api.scolaro.uz.entity.PersonRoleEntity;
import api.scolaro.uz.enums.FeedBackType;
import api.scolaro.uz.enums.RoleEnum;
import api.scolaro.uz.exp.AppBadRequestException;
import api.scolaro.uz.exp.ItemNotFoundException;
import api.scolaro.uz.mapper.ConsultingCommentFilterMapperDTO;
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

//    public ApiResponse<Page<FeedbackFilterResponseDTO>> pagination(FeedbackFilterDTO filter, int page, int size) {
//        Sort sort = Sort.by(Sort.Direction.DESC, "createdDate");
//        Pageable pageable = PageRequest.of(page - 1, size, sort);
//
//        Page<FeedbackFilterMapperDTO> pageObj = new PageImpl<>(filterResult.getContent(), PageRequest.of(page, size), filterResult.getTotalElement());
//        Long totalCount = pageObj.getTotalElements();
//
//        List<FeedbackFilterMapperDTO> entityList = pageObj.getContent();
//        List<FeedbackFilterResponseDTO> dtoList = new LinkedList<>();
//
//        for (FeedbackFilterMapperDTO filterMapperDTO : entityList) {
//            FeedbackFilterResponseDTO dto = new FeedbackFilterResponseDTO();
//            dto.setId(filterMapperDTO.getId());
//            dto.setContent(filterMapperDTO.getContent());
//            dto.setFeedBackType(filterMapperDTO.getFeedBackType());
//            dtoList.add(dto);
//        }
//        Page<FeedbackFilterResponseDTO> response = new PageImpl<FeedbackFilterResponseDTO>(dtoList, pageable, totalCount);
//        return ApiResponse.ok(response);
//    }

        public ApiResponse<Page<FeedbackFilterMapperDTO>> pagination(int page, int size) {
        Sort sort = Sort.by(Sort.Direction.DESC, "createdDate");
        Pageable pageable = PageRequest.of(page - 1, size, sort);

        Page<FeedbackEntity> pageObj = feedbackRepository.getAllByVisibleIsTrueOrderByCreatedDateDesc(pageable);
        Long totalCount = pageObj.getTotalElements();

        List<FeedbackEntity> entityList = pageObj.getContent();
        List<FeedbackFilterMapperDTO> dtoList = new LinkedList<>();

        for (FeedbackEntity entity : entityList) {
            FeedbackFilterMapperDTO dto = new FeedbackFilterMapperDTO();
            dto.setId(entity.getId());
            dto.setContent(entity.getContent());
            dto.setCreatedDate(entity.getCreatedDate());
            dto.setFeedBackType(entity.getFeedbackType());
            dtoList.add(dto);
        }
        Page<FeedbackFilterMapperDTO> response = new PageImpl<FeedbackFilterMapperDTO>(dtoList, pageable, totalCount);
        return ApiResponse.ok(response);
    }

    public ApiResponse<Page<FeedbackFilterMapperDTO>> filterForAdmin(FeedbackFilterDTO filter, int page, int size) {
        FilterResultDTO<FeedbackFilterMapperDTO> filterResult = customFeedbackRepository.filterForAdmin(filter, page, size);
        Page<FeedbackFilterMapperDTO> pageObj = new PageImpl<>(filterResult.getContent(), PageRequest.of(page, size), filterResult.getTotalElement());
        return ApiResponse.ok(pageObj);
    }




}
