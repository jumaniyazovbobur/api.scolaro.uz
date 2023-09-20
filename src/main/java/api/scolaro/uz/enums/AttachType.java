package api.scolaro.uz.enums;

public enum AttachType {
    PASSPORT("Passport", "Passport", "Паспорт"),
    REGISTRATION("Ro'yxatdan o'tgan joy", "Registration", "Прописка"),
    OTHER("Boshqa", "Other", "Другой");

    private String nameUz;
    private String nameEn;
    private String nameRu;

    AttachType(String nameUz, String nameEn, String nameRu) {
        this.nameUz = nameUz;
        this.nameEn = nameEn;
        this.nameRu = nameRu;
    }
}
