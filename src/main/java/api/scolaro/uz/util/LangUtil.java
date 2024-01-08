package api.scolaro.uz.util;

import api.scolaro.uz.entity.ProfileEntity;
import api.scolaro.uz.enums.AppLanguage;

public class LangUtil {
    public static AppLanguage getDefaultLang(ProfileEntity profile) {
        return profile.getLang() == null ? AppLanguage.uz : profile.getLang();
    }
}
