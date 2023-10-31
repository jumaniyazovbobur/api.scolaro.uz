package api.scolaro.uz.enums;

public enum ApplicationStepLevelStatus {

    ACCEPTED("Ariza qabul qilindi", "Заявка принята", "Заявка принята"),
    REVISION("Ariza qayta koʻrib chiqish uchun qaytarildi", "Application returned for revision", "Заявка возвращена на доработку"),
    REWORK_COMPLETED("Qayta ko'rish yakunlandi", "Rework completed", "Доработка  завершена"),
    // Одобрено на этапе рассмотрения 1-го этапа
    // Заявка переведена в технической экспертизы
    // Заявка отклонена на стадии технической экспертизы
    STEP_LEVEL_FINISHED("Bosqich yakunlandi", "Stage completed", "Этап завершен"),
    ;

    ApplicationStepLevelStatus(String nameUz, String nameEn, String nameRu) {
        this.nameUz = nameUz;
        this.nameEn = nameEn;
        this.nameRu = nameRu;
    }

    private String nameUz;
    private String nameEn;
    private String nameRu;
}
