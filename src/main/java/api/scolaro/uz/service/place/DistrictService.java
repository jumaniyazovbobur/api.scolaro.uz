package api.scolaro.uz.service.place;


import api.scolaro.uz.dto.DistrictDTO;
import api.scolaro.uz.entity.place.DistrictEntity;
import api.scolaro.uz.entity.place.RegionEntity;
import api.scolaro.uz.enums.AppLanguage;
import api.scolaro.uz.exp.ItemNotFoundException;
import api.scolaro.uz.repository.place.DistrictRepository;
import api.scolaro.uz.repository.place.RegionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
@Slf4j
public class DistrictService {

    @Autowired
    private DistrictRepository districtRepository;
    @Autowired
    private RegionRepository regionRepository;

    public DistrictDTO createDistrict(DistrictDTO dto, Integer id) {
        DistrictEntity districtEntity=new DistrictEntity();
        districtEntity.setCounty(dto.getCounty());
        districtEntity.setVisible(dto.isVisible());
        districtEntity.setNameEn(dto.getNameEn());
        districtEntity.setNameUz(dto.getNameUz());
        districtEntity.setNameRu(dto.getNameRu());
        districtEntity.setRegionId(id);
        Optional<RegionEntity> byId = regionRepository.findById(id);
        if (byId.isEmpty()){
            log.info("Region not found");
            throw new ItemNotFoundException("Bunaqa region mavjud emas");
        }
        districtEntity.setRegion(byId.get());
        districtEntity.setCreatedDate(LocalDateTime.now());
        districtRepository.save(districtEntity);
        return dto;
    }


    public List<DistrictDTO> findByRegionId(Integer id) {
        List<DistrictEntity> allByRegionId = districtRepository.findAllByRegionId(id);
        List<DistrictDTO> dtoList = new ArrayList<>();
        // findByRegionId list return atadiku
        for (DistrictEntity districtEntity : allByRegionId) {
            DistrictDTO districtDTO = new DistrictDTO();
            districtDTO.setId(districtEntity.getId());
            districtDTO.setNameUz(districtEntity.getNameUz());
            districtDTO.setNameRu(districtEntity.getNameRu());
            districtDTO.setNameEn(districtEntity.getNameEn());
            districtDTO.setVisible(districtEntity.isVisible());
            districtDTO.setCounty(districtEntity.getCounty());
            districtDTO.setCreatedDate(districtEntity.getCreatedDate());
            districtDTO.setRegionId(districtEntity.getRegionId());
            dtoList.add(districtDTO);
        }
        return dtoList;
    }
    public DistrictDTO toDTO(DistrictEntity districtEntity){
        DistrictDTO districtDTO = new DistrictDTO();
        districtDTO.setId(districtEntity.getId());
        districtDTO.setNameUz(districtEntity.getNameUz());
        districtDTO.setNameRu(districtEntity.getNameRu());
        districtDTO.setNameEn(districtEntity.getNameEn());
        districtDTO.setVisible(districtEntity.isVisible());
        districtDTO.setCounty(districtEntity.getCounty());
        districtDTO.setCreatedDate(districtEntity.getCreatedDate());
        districtDTO.setRegionId(districtEntity.getRegionId());
        return districtDTO;
    }

    public List<DistrictDTO> findAllByRegionId(Integer regionId, AppLanguage lang) {
        List<DistrictEntity> all = districtRepository.findAllByRegionId(regionId);
        List<DistrictDTO> dtoList = new ArrayList<>();
        all.forEach(entity -> {
            DistrictDTO dto = new DistrictDTO();
            dto.setId(entity.getId());
            switch (lang) {
                case en -> dto.setName(entity.getNameEn());
                case uz -> dto.setName(entity.getNameUz());
                case ru -> dto.setName(entity.getNameRu());
            }
            dtoList.add(dto);
        });
        return dtoList;
    }

    @Transactional
    public Boolean deleteById(Integer id) {
        Optional<DistrictEntity> optional = districtRepository.findById(id);
        if (optional.isEmpty()){
            log.info("Region not found");
            throw new ItemNotFoundException("Bunaqa tuman mavjud emas");
        }
        DistrictEntity entity = optional.get();
        entity.setVisible(false);
        districtRepository.save(entity);
        return true;
    }

    public DistrictDTO findById(Integer birthDistrictId) {
        Optional<DistrictEntity> byId = districtRepository.findById(birthDistrictId);
        if (byId.isEmpty()){
            return null;
        }
        DistrictDTO districtDTO = toDTO(byId.get());
        return districtDTO;

    }

    public DistrictDTO updateById(Integer id, DistrictDTO dto) {
        Optional<DistrictEntity> optional = districtRepository.findById(id);
        if (optional.isEmpty()){
            log.info("district not found ");
            throw new ItemNotFoundException("Bunaqa district mavjud emas");
        }
        DistrictEntity entity = optional.get();
        entity = toEntity(dto);
        entity.setId(id);
        districtRepository.save(entity);
        dto.setId(entity.getId());
        return dto;
    }

    public DistrictEntity toEntity(DistrictDTO dto) {
        DistrictEntity entity = new DistrictEntity();
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

    public DistrictEntity getDistrict(String stringCellValue) {
        String result = stringCellValue.toLowerCase().trim();
        List<DistrictEntity> district= districtRepository.findAll();
        for (DistrictEntity entity : district) {
            String name = entity.getCounty().toLowerCase().trim();
            if (name.equals(result)){
                return entity;
            }
        }

        DistrictEntity entity = new DistrictEntity();
        entity.setCounty(result);
        districtRepository.save(entity);
        return getDistrict(entity.getCounty());
    }
}
