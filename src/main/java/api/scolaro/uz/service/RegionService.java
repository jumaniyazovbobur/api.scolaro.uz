package api.scolaro.uz.service;


import api.scolaro.uz.dto.RegionDTO;
import api.scolaro.uz.entity.RegionEntity;
import api.scolaro.uz.exp.ItemNotFoundException;
import api.scolaro.uz.repository.DistrictRepository;
import api.scolaro.uz.repository.RegionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
@Slf4j
public class RegionService {

    @Autowired
    private RegionRepository regionRepository;

    @Autowired
    private DistrictRepository districtRepository;


    public List<RegionDTO> findAll() {
        List<RegionEntity> all = regionRepository.getAllByVisibleIsTrueOrderByCreatedDateDesc();
        List<RegionDTO> regionDTOS = new ArrayList<>();
        for (RegionEntity regionEntity : all) {
            RegionDTO regionDTO = toDTO(regionEntity);
            regionDTOS.add(regionDTO);
        }

        return regionDTOS;
    }


    public RegionDTO toDTO(RegionEntity entity) {
        RegionDTO regionDTO = new RegionDTO();
        regionDTO.setId(entity.getId());
        regionDTO.setNameUz(entity.getNameUz());
        regionDTO.setNameEn(entity.getNameEn());
        regionDTO.setNameRu(entity.getNameRu());
        regionDTO.setVisible(entity.isVisible());
        regionDTO.setCreatedDate(entity.getCreatedDate());
        return regionDTO;
    }

    public RegionEntity toEntity(RegionDTO dto) {
        RegionEntity entity = new RegionEntity();
        if (dto.getId()!=null){
            entity.setId(dto.getId());
        }
        entity.setNameUz(dto.getNameUz());
        entity.setNameEn(dto.getNameEn());
        entity.setNameRu(dto.getNameRu());
        entity.setVisible(dto.isVisible());
        entity.setCreatedDate(dto.getCreatedDate());
        return entity;
    }


    public RegionDTO createRegion(RegionDTO dto) {
        RegionEntity entity = toEntity(dto);
        regionRepository.save(entity);
        dto.setId(entity.getId());
        return dto;
    }

    public RegionDTO updateRegion(RegionDTO dto, Integer id){
        Optional<RegionEntity> optional = regionRepository.findById(id);
        if (optional.isEmpty()){
            log.info("Region not found");
            throw new ItemNotFoundException("Bunaqa region mavjud emas");
        }
        RegionEntity entity = optional.get();
        entity = toEntity(dto);
        entity.setId(id);
        regionRepository.save(entity);
        dto.setId(entity.getId());
        return dto;
    }

    @Transactional
    public Boolean deleteById(Integer id) {
        Optional<RegionEntity> optional = regionRepository.findById(id);
        if (optional.isEmpty()){
            log.info("Region not found");
            throw new ItemNotFoundException("Bunaqa region mavjud emas");
        }
        RegionEntity entity = optional.get();
        entity.setVisible(false);
        regionRepository.save(entity);
        return true;
    }

    public RegionDTO findById(Integer id) {
        Optional<RegionEntity> byId = regionRepository.findById(id);
        if (byId.isEmpty()){
            return null;
        }
        return toDTO(byId.get());
    }


    public RegionEntity getRegion(String stringCellValue) {
        String result = stringCellValue.toLowerCase().trim();
        List<RegionEntity> region= regionRepository.findAll();
        for (RegionEntity entity : region) {
            String name = entity.getNameUz().toLowerCase().trim();
            if (name.equals(result)){
                return entity;
            }
        }
        return null;
    }
}
