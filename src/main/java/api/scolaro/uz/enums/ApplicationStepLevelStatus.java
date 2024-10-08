package api.scolaro.uz.enums;

public enum ApplicationStepLevelStatus {
    PROCESS_STARTED("Jarayon boshlandi","The process has started","Процесс начался"),
    ACCEPTED("Ariza qabul qilindi", "Accepted", "Заявка принята"),
    REVISION("Ariza qayta koʻrib chiqish uchun qaytarildi", "Application returned for revision", "Заявка возвращена на доработку"),
    REWORK_COMPLETED("Qayta ko'rish yakunlandi", "Rework completed", "Доработка  завершена"),
    // Одобрено на этапе рассмотрения 1-го этапа
    // Заявка переведена в технической экспертизы
    // Заявка отклонена на стадии технической экспертизы
    STEP_LEVEL_FINISHED("Bosqich yakunlandi", "Stage completed", "Этап завершен"), // #35AD17
    REJECTED("Rad etildi", "Rejected", "Отклонено"),
    PAYMENT("To'lov", "Payment", "Оплата"), // no color used only in dropdown
    PAYMENT_SUCCESS("To'lov amalga oshirildi", "Payment success", "Оплата была произведена"), // #35AD17 - yashil // used if transaction successful
    PAYMENT_FAILED("To'lov amalga oshirilmadi", "Payment failed", "Платеж не был произведен"); //  #EDC409 - waring // used if transaction failed
    // ACCEPTED,REWORK_COMPLETED,STEP_LEVEL_FINISHED -  #35AD17 - yashil
    // REVISION va  qolgan barchalarida - #EDC409 - waring.
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
