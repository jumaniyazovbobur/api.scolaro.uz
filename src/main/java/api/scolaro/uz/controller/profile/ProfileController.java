package api.scolaro.uz.controller.profile;

import api.scolaro.uz.dto.profile.ProfileDTO;
import api.scolaro.uz.dto.profile.ProfileFilterDTO;
import api.scolaro.uz.dto.profile.ProfileRegDTO;
import api.scolaro.uz.service.profile.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user")
@Slf4j
@Tag(name = "User api list", description = "Api list for user")
public class ProfileController {


    private final UserService userService;

    @PutMapping("/{id}")
    @Operation(summary = "Update api", description = "")
    public ResponseEntity<ProfileDTO> update(@PathVariable("id") String id,
                                             @RequestBody ProfileRegDTO dto) {
        log.info("Update user {}", id);
        return ResponseEntity.ok(userService.update(id, dto));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get by id api", description = "")
    public ResponseEntity<ProfileDTO> getId(@PathVariable("id") String id) {
        log.info("Get user {}", id);
        return ResponseEntity.ok(userService.getId(id));
    }

    @GetMapping("/all")
    @Operation(summary = "Get all api", description = "")
    public ResponseEntity<PageImpl<ProfileDTO>> getAll(@RequestParam(value = "page", defaultValue = "1") int page,
                                                       @RequestParam(value = "size", defaultValue = "30") int size) {
        return ResponseEntity.ok(userService.getAll(page - 1, size));
    }

    @GetMapping("/filter")
    @Operation(summary = "Filter user api", description = "")
    public ResponseEntity<PageImpl<ProfileDTO>> filter(@RequestBody ProfileFilterDTO userFilterDTO,
                                                       @RequestParam(value = "page", defaultValue = "1") int page,
                                                       @RequestParam(value = "size", defaultValue = "30") int size) {
        return ResponseEntity.ok(userService.filter(userFilterDTO, page - 1, size));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deleted user api", description = "")
    public ResponseEntity<ProfileDTO> deleted(@PathVariable("id") String id) {
        log.info("Deleted user {}", id);
        return ResponseEntity.ok(userService.deleted(id));
    }

}
