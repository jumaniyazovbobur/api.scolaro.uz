package api.scolaro.uz.enums;

public enum StudyFormat {

    FULL_TIME("Full-time", "Очное обучение", "Kunduzgi ta’lim"),
    EVENING_CLASSES("Evening Classes", "Вечерние занятия", "Kechki ta’lim"),
    PART_TIME("Part-time", "Заочное обучение", "Sirtqi ta’lim"),
    DISTANCE_LEARNING("Distance Learning", "Дистанционное обучение", "Masofaviy ta’lim"),
    BLENDED_LEARNING("Blended Learning", "Смешанное обучение", "Aralash ta’lim");

    private final String nameEn;
    private final String nameRu;
    private final String nameUz;

    StudyFormat(String nameEn, String nameRu, String nameUz) {
        this.nameEn = nameEn;
        this.nameRu = nameRu;
        this.nameUz = nameUz;
    }

    public String getNameEn() {
        return nameEn;
    }

    public String getNameRu() {
        return nameRu;
    }

    public String getNameUz() {
        return nameUz;
    }
}
