package api.scolaro.uz.controller.profile;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/user")
@Slf4j
@Tag(name = "User api list", description = "Api list for user")
public class UserController {

    public ResponseEntity<?> update(){
        return null;
    }

}
