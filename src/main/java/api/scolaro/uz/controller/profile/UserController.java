package api.scolaro.uz.controller.profile;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
@Slf4j
@Tag(name = "User api list", description = "Api list for user")
public class UserController {
    @PutMapping("/{userId}")
    @Operation(summary = "Update api", description = "")
    public ResponseEntity<?> update(@PathVariable("userId") String userId){
        return null;
    }
    @GetMapping("/{userId}")
    @Operation(summary = "Get by id api", description = "")
    public ResponseEntity<?> getId(){
        return null;
    }
    @GetMapping("/all")
    @Operation(summary = "Get all api", description = "")
    public ResponseEntity<?> getAll(){
        return null;
    }

    @GetMapping("/filter")
    @Operation(summary = "Filter user api", description = "")
    public ResponseEntity<?> filter(){
        return null;
    }
    @GetMapping("/{userId}")
    @Operation(summary = "Deleted user api", description = "")
    public ResponseEntity<?> deleted(){
        return null;
    }

}
