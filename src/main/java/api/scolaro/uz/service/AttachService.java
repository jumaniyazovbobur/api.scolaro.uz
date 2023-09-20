package api.scolaro.uz.service;

import api.scolaro.uz.dto.attach.AttachDTO;
import api.scolaro.uz.dto.attach.AttachFilterDTO;
import api.scolaro.uz.dto.attach.AttachResponseDTO;
import api.scolaro.uz.enums.FileType;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class AttachService {

    public AttachDTO upload(MultipartFile file, FileType fileType) {
        return null;
    }

    public byte[] open_general(String fileName) {
        return null;
    }
    public ResponseEntity<Resource> download(String fileName) {
        return null;
    }

    public Page<AttachResponseDTO> filter(AttachFilterDTO dto, int i, int size) {
        return null;
    }
}
