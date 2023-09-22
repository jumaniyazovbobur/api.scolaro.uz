package api.scolaro.uz.controller.profile;

import api.scolaro.uz.dto.profile.UserUpdateDTO;
import api.scolaro.uz.service.profile.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
    public ResponseEntity<?> update(@PathVariable("id") String id,
                                    @RequestBody UserUpdateDTO updateDTO) {
        log.info("Update user {}", updateDTO);
        return ResponseEntity.ok(userService.update(id,updateDTO));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get by id api", description = "")
    public ResponseEntity<?> getId(@PathVariable("id") String id) {
        log.info("Get user {}", id);
        return null;
    }

    @GetMapping("/all")
    @Operation(summary = "Get all api", description = "")
    public ResponseEntity<?> getAll() {
        return null;
    }

    @GetMapping("/filter")
    @Operation(summary = "Filter user api", description = "")
    public ResponseEntity<?> filter() {
        return null;
    }

    @GetMapping("/{id}")
    @Operation(summary = "Deleted user api", description = "")
    public ResponseEntity<?> deleted(@PathVariable("id") String id) {
        return null;
    }

}
