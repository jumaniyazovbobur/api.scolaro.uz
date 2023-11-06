package api.scolaro.uz.mapper;

import api.scolaro.uz.enums.StepLevelStatus;

import java.time.LocalDateTime;

public interface ConsultingStepLevelMapper {
    String getId();

    Integer getOrderNumber();

    String getDescription();

    LocalDateTime getStartedDate();

    LocalDateTime getFinishedDate();

    StepLevelStatus getStepLevelStatus();

    String getName();

    String getLevelStatusList();

    String getLevelAttachList();
}
