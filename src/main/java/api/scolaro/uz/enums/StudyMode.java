package api.scolaro.uz.enums;

public enum StudyMode {
    ONLINE("Online", "Онлайн обучение", "Onlayn ta’lim"),
    OFFLINE("Offline", "Оффлайн обучение", "Offlayn ta’lim"),
    SELF_PACED("Self-Paced", "Самостоятельный режим", "Mustaqil o‘qish rejimi"),
    MODULAR("Modular", "Модульный режим", "Modulli ta’lim rejimi"),
    FLEXIBLE("Flexible", "Гибкий режим", "Moslashuvchan ta’lim rejimi"),
    INTENSIVE("Intensive", "Интенсивный режим", "Intensiv ta’lim rejimi"),
    WEEKEND("Weekend", "Обучение по выходным", "Dam olish kunlari ta’lim rejimi");

    private final String nameEn;
    private final String nameRu;
    private final String nameUz;

    StudyMode(String nameEn, String nameRu, String nameUz) {
        this.nameEn = nameEn;
        this.nameRu = nameRu;
        this.nameUz = nameUz;
    }

    public String getNameEn() { return nameEn; }
    public String getNameRu() { return nameRu; }
    public String getNameUz() { return nameUz; }
}
