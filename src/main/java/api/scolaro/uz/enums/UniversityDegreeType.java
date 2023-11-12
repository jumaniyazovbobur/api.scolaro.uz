package api.scolaro.uz.enums;

import lombok.Getter;
import lombok.Setter;


public enum UniversityDegreeType {
    Bachelor("Bakalavr", "Bachelor", "Бакалавр"),
    MasterDegree("Magistratura", "Master's degree", "Магистратура"),
    Doctorate("Doktorantura", "Doctorate", "Докторская степень");

    UniversityDegreeType(String nameUz, String nameEn, String nameRu) {
        this.nameUz = nameUz;
        this.nameEn = nameEn;
        this.nameRu = nameRu;
    }

    private String nameUz;
    private String nameEn;
    private String nameRu;

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
