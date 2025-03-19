package api.scolaro.uz.enums;

public enum ProgramType { // TODO
    BACHELOR("Bakalavr", "Bachelor", "Бакалавр"),
    Master("Magistratura", "Master's degree", "Магистратура"),
    PhD("Doktorantura", "Doctorate", "Докторская степень"),
    Scholarship("", "", ""),
    News("", "", "");

    private String nameUz;
    private String nameEn;
    private String nameRu;

    ProgramType(String nameUz, String nameEn, String nameRu) {
        this.nameUz = nameUz;
        this.nameEn = nameEn;
        this.nameRu = nameRu;
    }
}
