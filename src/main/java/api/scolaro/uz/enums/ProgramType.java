package api.scolaro.uz.enums;

public enum ProgramType { // TODO
    BACHELOR("Bakalavr", "Bachelor", "Бакалавр"),
    MASTER("Magistratura", "Master's degree", "Магистратура"),
    PHD("Doktorantura", "Doctorate", "Докторская степень"),
    SCHOLARSHIP("Stipendiya", "Scholarship", "Стипендия"),
    NEWS("Yangiliklar", "News", "Новости");

    private final String nameUz;
    private final String nameEn;
    private final String nameRu;

    ProgramType(String nameUz, String nameEn, String nameRu) {
        this.nameUz = nameUz;
        this.nameEn = nameEn;
        this.nameRu = nameRu;
    }

    public String getNameUz() {
        return nameUz;
    }

    public String getNameEn() {
        return nameEn;
    }

    public String getNameRu() {
        return nameRu;
    }
}
