package api.scolaro.uz.controller.profile;

import api.scolaro.uz.dto.profile.UserDTO;
import api.scolaro.uz.dto.profile.UserFilterDTO;
import api.scolaro.uz.dto.profile.UserUpdateDTO;
import api.scolaro.uz.service.profile.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
@Slf4j
@Tag(name = "User api list", description = "Api list for user")
public class UserController {

    @Autowired
    private UserService userService;

    @PutMapping("/{id}")
    @Operation(summary = "Update api", description = "")
    public ResponseEntity<UserDTO> update(@PathVariable("id") String id,
                                    @RequestBody UserUpdateDTO updateDTO) {
        log.info("Update user {}", id);
        return ResponseEntity.ok(userService.update(id, updateDTO));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get by id api", description = "")
    public ResponseEntity<UserDTO> getId(@PathVariable("id") String id) {
        log.info("Get user {}", id);
        return ResponseEntity.ok(userService.getId(id));
    }

    @GetMapping("/all")
    @Operation(summary = "Get all api", description = "")
    public ResponseEntity<PageImpl<UserDTO>> getAll(@RequestParam(value = "page", defaultValue = "1") int page,
                                                    @RequestParam(value = "size", defaultValue = "30") int size) {
        return ResponseEntity.ok(userService.getAll(page - 1, size));
    }

    @GetMapping("/filter")
    @Operation(summary = "Filter user api", description = "")
    public ResponseEntity<PageImpl<UserDTO>> filter(@RequestBody UserFilterDTO userFilterDTO,
                                                    @RequestParam(value = "page", defaultValue = "1") int page,
                                                    @RequestParam(value = "size", defaultValue = "30") int size) {
        return ResponseEntity.ok(userService.filter(userFilterDTO, page - 1, size));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deleted user api", description = "")
    public ResponseEntity<UserDTO> deleted(@PathVariable("id") String id) {
        log.info("Deleted user {}", id);
        return ResponseEntity.ok(userService.deleted(id));
    }

}
