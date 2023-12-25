package api.scolaro.uz.controller;

import api.scolaro.uz.dto.ApiResponse;
import api.scolaro.uz.dto.appApplication.AppApplicationLevelAttachDTO;
import api.scolaro.uz.dto.attach.AttachDTO;
import api.scolaro.uz.dto.attach.AttachFilterDTO;
import api.scolaro.uz.enums.AttachType;
import api.scolaro.uz.service.AttachService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
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

    /**
     * FOR PUBLIC AUTH
     */
    @PostMapping("/upload")
    @Operation(summary = "upload api", description = "")
    public ResponseEntity<AttachDTO> create(@RequestParam("file") MultipartFile file) {
        log.info("upload attach  = {}", file.getOriginalFilename());
        return ResponseEntity.ok(attachService.upload(file));
    }

    @PostMapping("/upload/application/step_level")
    @Operation(summary = "upload file to application step level", description = "")
    public ResponseEntity<ApiResponse<AppApplicationLevelAttachDTO>> createApplicationLevelAttach(@RequestParam("file") MultipartFile file,
                                                                                                  @RequestParam("stepLevelId") String stepLevelId,
                                                                                                  @RequestParam(value = "attachType", defaultValue = "OTHER") AttachType attachType) {
        log.info("application level attach  = {}", file.getOriginalFilename());
        return ResponseEntity.ok(attachService.createApplicationLevelAttach(file, stepLevelId, attachType));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/delete/application/step-level/{attachId}")
    @Operation(summary = "Delete file from step level", description = "")
    public ResponseEntity<ApiResponse<Boolean>> deleteAttachFromStepLevel(@PathVariable("attachId") String attachId) {
        log.info("Delete file from step level {}", attachId);
        return ResponseEntity.ok(attachService.stepLevelDeleteAttach(attachId));
    }


    @PreAuthorize("hasRole('ROLE_CONSULTING')")
    @PostMapping("/upload/simp-message/consulting")
    @Operation(summary = "Upload file to simple-message as consulting", description = "")
    public ResponseEntity<ApiResponse<String>> createSimpleMessageAttachAsConsulting(@RequestParam("file") MultipartFile file,
                                                                                     @RequestParam("applicationId") String applicationId) {
        log.info("simple message attach as consulting  = {}", file.getOriginalFilename());
        return ResponseEntity.ok(attachService.createSimpleMessageAttachAsConsulting(file, applicationId));
    }

    @PreAuthorize("hasRole('ROLE_STUDENT')")
    @PostMapping("/upload/simp-message/student")
    @Operation(summary = "upload file to simple-message as student", description = "")
    public ResponseEntity<ApiResponse<String>> createSimpleMessageAttachAsStudent(@RequestParam("file") MultipartFile file,
                                                                                  @RequestParam("applicationId") String applicationId) {
        log.info("simple message attach  as student = {}", file.getOriginalFilename());
        return ResponseEntity.ok(attachService.createSimpleMessageAttachAsStudent(file, applicationId));
    }

    /**
     * PUBLIC
     */
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

    @GetMapping("/download/extension/{id}")
    @Operation(summary = "Download file with extension ", description = "")
    public ResponseEntity<Resource> downloadForMobile(@PathVariable("id") String id) {
        log.info("download attach  ={}", id);
        return attachService.downloadFileWithExtension(id);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/delete/{fileName}")
    @Operation(summary = "delete file api", description = "")
    public ResponseEntity<Boolean> delete(@PathVariable("fileName") String fileName) {
        log.info("delete attach  ={}", fileName);
        return ResponseEntity.ok(attachService.delete(fileName));
    }

    /**
     * FOR ADMIN
     */
    @GetMapping("/get-all")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Operation(summary = "File get all api", description = "")
    public ResponseEntity<PageImpl<AttachDTO>> getAll(@RequestParam(value = "page", defaultValue = "1") int page,
                                                      @RequestParam(value = "size", defaultValue = "15") int size) {
        return ResponseEntity.ok(attachService.getAll(page - 1, size));
    }

}
