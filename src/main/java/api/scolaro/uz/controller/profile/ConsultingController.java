package api.scolaro.uz.controller.profile;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/consulting")
@Slf4j
@Tag(name = "Consulting api list", description = "Api list for consulting")
public class ConsultingController {

    @PutMapping("/{consulId}")
    @Operation(summary = "Update api", description = "")
    public ResponseEntity<?> update(@PathVariable("consulId") String consulId){
        return null;
    }
    @PutMapping("/{consulId}")
    @Operation(summary = "Get by id api", description = "")
    public ResponseEntity<?> getId(@PathVariable("consulId")String consulId){
        return null;
    }
    @PutMapping("/all")
    @Operation(summary = "Get all api", description = "")
    public ResponseEntity<?> getAll(){
        return null;
    }
    @PutMapping("/filter")
    @Operation(summary = "Filter api", description = "")
    public ResponseEntity<?> filter(){
        return null;
    }
    @PutMapping("/{consulId}")
    @Operation(summary = "Deleted consulting api", description = "")
    public ResponseEntity<?> deleted(@PathVariable("consulId") String consulId){
        return null;
    }

}
