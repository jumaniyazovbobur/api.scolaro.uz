package api.scolaro.uz.enums;

public enum ApplicationStepLevelStatus {

    ACCEPTED("Ariza qabul qilindi", "Accepted", "Заявка принята"),
    REVISION("Ariza qayta koʻrib chiqish uchun qaytarildi", "Application returned for revision", "Заявка возвращена на доработку"),
    REWORK_COMPLETED("Qayta ko'rish yakunlandi", "Rework completed", "Доработка  завершена"),
    // Одобрено на этапе рассмотрения 1-го этапа
    // Заявка переведена в технической экспертизы
    // Заявка отклонена на стадии технической экспертизы
    STEP_LEVEL_FINISHED("Bosqich yakunlandi", "Stage completed", "Этап завершен"), // #35AD17
    REJECTED("Rad etildi","Rejected","Отклонено");
    ;
    // ACCEPTED,REWORK_COMPLETED,STEP_LEVEL_FINISHED -  #35AD17 - yashil
    // REVISION va  qolgan barchalarida - #EDC409.
    // REJECTED - #F24724 - qizil
    // #A3A9AE - ko'k - ?

    ApplicationStepLevelStatus(String nameUz, String nameEn, String nameRu) {
        this.nameUz = nameUz;
        this.nameEn = nameEn;
        this.nameRu = nameRu;
    }

    private String nameUz;
    private String nameEn;
    private String nameRu;

    public String getName(AppLanguage language) {
        switch (language) {
            case uz -> {
                return nameUz;
            }
            case en -> {
                return nameEn;
            }
            default -> {
                return nameRu;
            }
        }
    }
}
