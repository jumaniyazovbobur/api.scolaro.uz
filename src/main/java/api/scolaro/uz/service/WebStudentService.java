package api.scolaro.uz.service;

import api.scolaro.uz.dto.ApiResponse;
import api.scolaro.uz.dto.program.ProgramCreateDTO;
import api.scolaro.uz.dto.webStudent.WebStudentCreateDTO;
import api.scolaro.uz.dto.webStudent.WebStudentResponseDTO;
import api.scolaro.uz.entity.ProgramEntity;
import api.scolaro.uz.entity.WebStudentEntity;
import api.scolaro.uz.enums.AppLanguage;
import api.scolaro.uz.exp.ItemNotFoundException;
import api.scolaro.uz.repository.WebStudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class WebStudentService {
    private final WebStudentRepository repository;
    private final AttachService attachService;

    public ApiResponse<String> create(WebStudentCreateDTO request) {
        WebStudentEntity entity = toEntity(request);
        WebStudentEntity save = repository.save(entity);
        return new ApiResponse<>("Create web student : " + save.getId(), 201, false);
    }

    public ApiResponse<String> update(String id, WebStudentCreateDTO request) {
        WebStudentEntity entity = getId(id);
        entity.setFullName(request.getFullName());
        entity.setAboutUz(request.getAboutUz());
        entity.setAboutRu(request.getAboutRu());
        entity.setAboutEn(request.getAboutEn());
        entity.setPhotoId(request.getPhotoId());
        entity.setOrderNumber(request.getOrderNumber());
        WebStudentEntity save = repository.save(entity);
        return new ApiResponse<>("Update web student : " + save.getId(), 200, false);
    }

    public ApiResponse<WebStudentResponseDTO> getById(String id) {
        WebStudentEntity entity = getId(id);
        return new ApiResponse<>(200, false, toDTO(entity));

    }

    public ApiResponse<List<WebStudentResponseDTO>> getAll() {
        List<WebStudentEntity> list = repository.findByVisibleTrueOrderByOrderNumber();
        return new ApiResponse<>(200, false, list.stream().map(this::toDTO).toList());
    }

    public ApiResponse<String> delete(String id) {
        WebStudentEntity entity = getId(id);
        entity.setVisible(false);
        repository.save(entity);
        return new ApiResponse<>("Delete web student : " + id, 200, false);
    }

    public ApiResponse<List<WebStudentResponseDTO>> getAllLanguage(AppLanguage language) {
        List<Map<String, Object>> rawlist = repository.findAllByLang(language.name());
        List<WebStudentResponseDTO> list = rawlist.stream().map(map -> {
            WebStudentResponseDTO dto = new WebStudentResponseDTO();
            dto.setId((String) map.get("id"));
            dto.setFullName((String) map.get("full_name"));
            dto.setAbout((String) map.get("about"));
            dto.setAttachDTO(attachService.getResponseAttach((String) map.get("photo_id")));
            dto.setOrderNumber(map.get("order_number") != null ? ((Number) map.get("order_number")).intValue() : null);
            return dto;
        }).toList();
        return new ApiResponse<>(200, false, list);

    }

    public WebStudentEntity toEntity(WebStudentCreateDTO dto) {
        WebStudentEntity entity = new WebStudentEntity();
        entity.setFullName(dto.getFullName());
        entity.setAboutUz(dto.getAboutUz());
        entity.setAboutRu(dto.getAboutRu());
        entity.setAboutEn(dto.getAboutEn());
        entity.setPhotoId(dto.getPhotoId());
        entity.setOrderNumber(dto.getOrderNumber());
        return entity;
    }

    public WebStudentResponseDTO toDTO(WebStudentEntity entity) {
        WebStudentResponseDTO dto = new WebStudentResponseDTO();
        dto.setId(entity.getId());
        dto.setFullName(entity.getFullName());
        dto.setAboutUz(entity.getAboutUz());
        dto.setAboutRu(entity.getAboutRu());
        dto.setAboutEn(entity.getAboutEn());
        dto.setAttachDTO(attachService.getResponseAttach(entity.getPhotoId()));
        dto.setOrderNumber(entity.getOrderNumber());
        return dto;
    }

    public WebStudentEntity getId(String id) {
        return repository.findByIdAndVisibleTrue(id).orElseThrow(() ->
                new ItemNotFoundException("Web student  not found  " + id));
    }


}
