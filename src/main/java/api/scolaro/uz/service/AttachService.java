package api.scolaro.uz.service;

import api.scolaro.uz.config.details.EntityDetails;
import api.scolaro.uz.dto.attach.AttachDTO;
import api.scolaro.uz.dto.attach.AttachFilterDTO;
import api.scolaro.uz.dto.attach.AttachResponseDTO;
import api.scolaro.uz.entity.AttachEntity;
import api.scolaro.uz.exp.ItemNotFoundException;
import api.scolaro.uz.repository.AttachRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Page;
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
import java.util.UUID;

@Service
public class AttachService {

    @Value("${attach.upload.folder}")
    private String folderName;
    @Value("${attach.url}")
    private String attachUrl;
    @Autowired
    private AttachRepository attachRepository;

    public AttachDTO upload(MultipartFile file) {
        if (file.isEmpty()) {
            throw new ItemNotFoundException("File not found");
        }

        String pathFolder = getYmDString();
        File folder = new File(folderName + "/" + pathFolder);
        if (!folder.exists()) {
            boolean t = folder.mkdirs();
        }
        String key = UUID.randomUUID().toString();
        String extension = getExtension(Objects.requireNonNull(file.getOriginalFilename()));   // jpg

        try {
            byte[] bytes = file.getBytes();
            Path path = Paths.get(folderName + "/" + pathFolder + "/" + key + "." + extension);

            Files.write(path, bytes);
            AttachEntity entity = new AttachEntity();
            entity.setId(key);
            entity.setCreateId(EntityDetails.getCurrentUserId());
            entity.setPath(pathFolder);
            entity.setSize(file.getSize());
            entity.setOrigenName(file.getOriginalFilename());
            entity.setExtension(extension);
            attachRepository.save(entity);

            AttachDTO attachDTO = new AttachDTO();
            attachDTO.setId(key);
            attachDTO.setOriginName(entity.getOrigenName());
            attachDTO.setSize(entity.getSize());
            attachDTO.setExtension(entity.getExtension());
            attachDTO.setCreatedData(entity.getCreatedDate());
            attachDTO.setUrl(getUrl(entity.getId()));
            return attachDTO;
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

    }

    public byte[] open_general(String fileName) {
        AttachEntity entity = getEntity(fileName);
        try {
            BufferedImage originalImage = ImageIO.read(new File(getPath(entity)));
            ByteArrayOutputStream boas = new ByteArrayOutputStream();
            ImageIO.write(originalImage, entity.getExtension(), boas);
            boas.flush();
            byte[] imageInByte = boas.toByteArray();
            boas.close();
            return imageInByte;
        } catch (Exception e) {
            return new byte[0];
        }
    }

    public ResponseEntity<Resource> download(String fileName) {
        AttachEntity entity = getEntity(fileName);
        try {
            Path file = Paths.get(getPath(entity));
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" + entity.getOrigenName() + "\"").body(resource);
            } else {
                throw new RuntimeException("Could not read the file!");
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("Error: " + e.getMessage());
        }
    }


    public ResponseEntity<Resource> delete(String fileName) {
        //TODO deleted
        return null;
    }

    public Page<AttachResponseDTO> filter(AttachFilterDTO dto, int i, int size) {
        //TODO filter
        return null;
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

    private AttachEntity getEntity(String fileName) {
        Optional<AttachEntity> optional = attachRepository.findByIdAndVisibleTrue(fileName);
        if (optional.isEmpty()) throw new ItemNotFoundException("File not found");
        return optional.get();
    }

    private String getPath(AttachEntity entity) {
        return folderName + "/" + entity.getPath() + "/" + entity.getId() + "." + entity.getExtension();
    }

    private String getUrl(String fileName) {
        return attachUrl + "/open/" + fileName;
    }

    public String toOpenUrl(String id) {
        return attachUrl + "/api/v1/attach/open/" + id;
    }

}
