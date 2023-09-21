package api.scolaro.uz.controller;

import api.scolaro.uz.dto.attach.AttachDTO;
import api.scolaro.uz.dto.attach.AttachFilterDTO;
import api.scolaro.uz.dto.attach.AttachResponseDTO;
import api.scolaro.uz.service.AttachService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@RestController
@RequestMapping("/api/v1/attach")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Attach api list", description = "Api list for attach")
public class AttachController {

    private final AttachService attachService;

    @PostMapping("/upload")
    @Operation(summary = "upload api", description = "")
    public ResponseEntity<AttachDTO> create(@RequestParam("file") MultipartFile file) {
        log.info("upload attach  ={}", file.getOriginalFilename());
        return ResponseEntity.ok(attachService.upload(file));
    }

    @GetMapping(value = "/open/{fileName}", produces = MediaType.IMAGE_PNG_VALUE)
    @Operation(summary = "open file api", description = "")
    public byte[] open_general(@PathVariable("fileName") String fileName) {
        log.info("open attach  ={}", fileName);
        return attachService.open_general(fileName);
    }

    @GetMapping("/download/{fileName}")
    @Operation(summary = "download file api", description = "")
    public ResponseEntity<Resource> download(@PathVariable("fileName") String fileName) {
        log.info("download attach  ={}", fileName);
        return attachService.download(fileName);
    }

    @DeleteMapping("/delete/{fileName}")
    @Operation(summary = "delete file api", description = "")
    public ResponseEntity<Resource> delete(@PathVariable("fileName") String fileName) {
        log.info("delete attach  ={}", fileName);
        return attachService.delete(fileName);
    }

    @PostMapping("/filter")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Operation(summary = "Attach filter", description = "")
    public ResponseEntity<Page<AttachResponseDTO>> filter(@RequestBody AttachFilterDTO dto,
                                                          @RequestParam(value = "page", defaultValue = "1") int page,
                                                          @RequestParam(value = "size", defaultValue = "15") int size) {
        Page<AttachResponseDTO> response = attachService.filter(dto, page-1, size);
        return ResponseEntity.ok(response);
    }

 /*   @GetMapping(value = "/open/general/{fileName}", produces = MediaType.APPLICATION_PDF_VALUE)
    public byte[] open_pdf(@PathVariable("fileName") String fileName) {
        log.info("open attach  ={}", fileName);
        return attachService.open_general(fileName);
    }*/
}
