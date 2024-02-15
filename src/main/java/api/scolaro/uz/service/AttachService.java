package api.scolaro.uz.service;

import api.scolaro.uz.config.details.EntityDetails;
import api.scolaro.uz.dto.ApiResponse;
import api.scolaro.uz.dto.appApplication.AppApplicationLevelAttachDTO;
import api.scolaro.uz.dto.attach.AttachDTO;
import api.scolaro.uz.dto.attach.AttachResponseDTO;
import api.scolaro.uz.dto.simpleMessage.SimpleMessageRequestDTO;
import api.scolaro.uz.entity.AttachEntity;
import api.scolaro.uz.enums.AttachType;
import api.scolaro.uz.exp.ItemNotFoundException;
import api.scolaro.uz.mapper.SimpleMessageMapperDTO;
import api.scolaro.uz.repository.attach.AttachRepository;
import api.scolaro.uz.service.application.AppApplicationLevelAttachService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Calendar;
import java.util.Objects;
import java.util.Optional;


@Slf4j
@Service
public class AttachService {

    @Value("${attach.upload.folder}")
    private String folderName;
    @Value("${attach.url}")
    private String attachUrl;

    @Autowired
    private AttachRepository attachRepository;
    @Autowired
    private AppApplicationLevelAttachService applicationLevelAttachService;
    @Autowired
    private SimpleMessageService simpleMessageService;

    public AttachDTO upload(MultipartFile file) {
        if (file.isEmpty()) {
            log.warn("Attach error : file not found");
            throw new ItemNotFoundException("File not found");
        }

        String pathFolder = getYmDString();
        File folder = new File(folderName + "/" + pathFolder);
        if (!folder.exists()) {
            boolean t = folder.mkdirs();
        }

        String extension = getExtension(Objects.requireNonNull(file.getOriginalFilename()));
        try {
            AttachEntity entity = new AttachEntity();
            entity.setCreateId(EntityDetails.getCurrentUserId());
            entity.setPath(pathFolder);
            entity.setSize(file.getSize());
            entity.setOrigenName(file.getOriginalFilename());
            entity.setExtension(extension);
            entity.setVisible(true);
            attachRepository.save(entity);

            byte[] bytes = file.getBytes();
            Path path = Paths.get(folderName + "/" + pathFolder + "/" + entity.getId() + "." + extension);
            Files.write(path, bytes);

            return toDTO(entity);
        } catch (IOException e) {
            log.warn("Attach error : {}", e.getMessage());
            e.printStackTrace();
            throw new RuntimeException(e);
        }

    }

    public ApiResponse<AppApplicationLevelAttachDTO> createApplicationLevelAttach(MultipartFile file, String stepLevelId, AttachType attachType) {
        AttachDTO dto = upload(file); // Upload file
        AppApplicationLevelAttachDTO applicationLevelAttachDTO = applicationLevelAttachService.createAppLevelAttach(stepLevelId, dto, attachType); // save application level attach
        return ApiResponse.ok(applicationLevelAttachDTO);
    }

    public ApiResponse<SimpleMessageMapperDTO> createSimpleMessageAttachAsConsulting(MultipartFile file, String applicationId) {
        AttachDTO dto = upload(file); // Upload file
        SimpleMessageRequestDTO simpleMessageRequestDTO = new SimpleMessageRequestDTO();
        simpleMessageRequestDTO.setAttachId(dto.getId());
        simpleMessageRequestDTO.setApplicationId(applicationId);
        simpleMessageRequestDTO.setExtension(dto.getExtension());
        return simpleMessageService.createForConsultingWithAttach(simpleMessageRequestDTO, dto.getOriginName(), dto.getExtension());
    }

    public ApiResponse<SimpleMessageMapperDTO> createSimpleMessageAttachAsStudent(MultipartFile file, String applicationId) {
        AttachDTO dto = upload(file); // Upload file
        SimpleMessageRequestDTO simpleMessageRequestDTO = new SimpleMessageRequestDTO();
        simpleMessageRequestDTO.setAttachId(dto.getId());
        simpleMessageRequestDTO.setApplicationId(applicationId);
        simpleMessageRequestDTO.setExtension(dto.getExtension());
        return simpleMessageService.createForStudentWithAttach(simpleMessageRequestDTO, dto.getOriginName(), dto.getExtension());
    }

    public byte[] open_general(String fileName) {
        AttachEntity entity = getEntity(fileName);
        try {
            BufferedImage originalImage = ImageIO.read(new File(getPath(entity)));
            ByteArrayOutputStream boas = new ByteArrayOutputStream();
            ImageIO.write(originalImage, getTempExtension(entity.getExtension()), boas);
//            ImageIO.write(originalImage, entity.getExtension(), boas);
            boas.flush();
            byte[] imageInByte = boas.toByteArray();
            boas.close();
            return imageInByte;
        } catch (Exception e) {
            log.warn("Attach error : {}", e.getMessage());
            return new byte[0];
        }
    }

    public String getTempExtension(String extension) {
        String extSml = extension.toLowerCase();
        return extSml.equals("jpg") || extSml.equals("png") ? "png" : extension;
    }

    public ResponseEntity<Resource> download(String fileName) {
        AttachEntity entity = getEntity(fileName);
        try {
            Path file = Paths.get(getPath(entity));
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + entity.getOrigenName() + "\"").body(resource);
            } else {
                log.warn("Attach error : Could not read the file!");
                throw new RuntimeException("Could not read the file!");
            }
        } catch (MalformedURLException e) {
            log.warn("Attach error : {}", e.getMessage());
            throw new RuntimeException("Error: " + e.getMessage());
        }
    }

    public ResponseEntity<Resource> downloadFileWithExtension(String id) {
        // remove .extension from id
        id = id.substring(0, id.lastIndexOf("."));

        AttachEntity entity = getEntity(id);
        try {
            Path file = Paths.get(getPath(entity));
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + entity.getOrigenName() + "\"").body(resource);
            } else {
                log.warn("Attach error : Could not read the file!");
                throw new RuntimeException("Could not read the file!");
            }
        } catch (MalformedURLException e) {
            log.warn("Attach error : {}", e.getMessage());
            throw new RuntimeException("Error: " + e.getMessage());
        }
    }


    public boolean delete(String fileName) {
        AttachEntity entity = getEntity(fileName);
        attachRepository.delete(entity);
        File file = new File(getPath(entity));
        boolean b = false;
        if (file.exists()) {
            b = file.delete();
        }
        return b;
    }

    public PageImpl<AttachDTO> getAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<AttachEntity> entityPages = attachRepository.findAll(pageable);
        return new PageImpl<>(entityPages.stream().map(this::toDTO).toList(), pageable, entityPages.getTotalElements());
    }

    private String getYmDString() {
        int year = Calendar.getInstance().get(Calendar.YEAR);
        int month = Calendar.getInstance().get(Calendar.MONTH) + 1;
        int day = Calendar.getInstance().get(Calendar.DATE);
        return year + "/" + month + "/" + day;
    }

    private String getExtension(String fileName) {
        int lastIndex = fileName.lastIndexOf(".");
        return fileName.substring(lastIndex + 1);
    }

    public AttachEntity getEntity(String id) {
        Optional<AttachEntity> optional = attachRepository.findById(id);
        if (optional.isEmpty()) {
            log.warn("Attach error : file not found");
            throw new ItemNotFoundException("File not found");
        }
        return optional.get();
    }

    private String getPath(AttachEntity entity) {
        return folderName + "/" + entity.getPath() + "/" + entity.getId() + "." + entity.getExtension();
    }


    public String getUrl(String fileName) {
        return attachUrl + "/open/" + fileName;
    }

    private AttachDTO toDTO(AttachEntity entity) {
        AttachDTO attachDTO = new AttachDTO();
        attachDTO.setId(entity.getId());
        attachDTO.setOriginName(entity.getOrigenName());
        attachDTO.setSize(entity.getSize());
        attachDTO.setExtension(entity.getExtension());
        attachDTO.setCreatedData(entity.getCreatedDate());
        attachDTO.setUrl(getUrl(entity.getId()));
        return attachDTO;
    }

    public AttachDTO getResponseAttach(String id) {
        return id == null ? null : new AttachDTO(id, getUrl(id));
    }

    public AttachDTO getResponseAttachWithExtension(String id, String extension) {
        return id == null ? null : new AttachDTO(id, getUrl(id), extension);
    }

    public ApiResponse<Boolean> stepLevelDeleteAttach(String attachId) {
        applicationLevelAttachService.deleteLevelAttachByAttachId(attachId);
        return ApiResponse.ok();
    }
}
