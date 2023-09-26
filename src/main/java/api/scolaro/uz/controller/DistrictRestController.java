package api.scolaro.uz.controller;

import api.dean.db.dto.DistrictDTO;
import api.dean.db.enums.AppLang;
import api.dean.db.service.DistrictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/district")
public class DistrictRestController {

    @Autowired
    private DistrictService districtService;

    @PostMapping("/")
    public ResponseEntity<DistrictDTO> createDistrict(@RequestBody DistrictDTO dto) {
        return ResponseEntity.ok(districtService.createDistrict(dto, dto.getRegionId()));
    }

    @DeleteMapping("/{id}")
    public Boolean deleteDistrict(@PathVariable("id") Integer id){
        return districtService.deleteById(id);
    }

    @PutMapping("/{id}")
    private ResponseEntity<DistrictDTO> updateDistrict(@PathVariable("id") Integer id, @RequestBody DistrictDTO dto){
        return ResponseEntity.ok(districtService.updateById(id, dto));
    }

    @GetMapping("/region/{id}")
    public List<DistrictDTO> getDistrictByRegionId(@PathVariable("id") Integer regionId) {
        // get profile selected lang from security
        List<DistrictDTO> dtoList = districtService.findAllByRegionId(regionId, AppLang.uz);
        return dtoList;
    }
    @GetMapping("/region/permanent/{id}")
    public List<DistrictDTO> getPermanentDistrictByRegionId(@PathVariable("id") Integer regionId) {
        // get profile selected lang from security
        List<DistrictDTO> dtoList = districtService.findAllByRegionId(regionId, AppLang.uz);
        return dtoList;
    }
}
