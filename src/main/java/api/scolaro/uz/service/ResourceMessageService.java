package api.scolaro.uz.service;



import api.scolaro.uz.enums.AppLanguage;
import api.scolaro.uz.enums.LanguageEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
public class ResourceMessageService {
    @Autowired
    private ResourceBundleMessageSource messageSource;

    public String getMessage(String code, AppLanguage lang) {
        messageSource.setDefaultEncoding("windows-1251");
        return messageSource.getMessage(code, null, new Locale(lang.name()));
    }

    public String getMessage(String code) {
        messageSource.setDefaultEncoding("windows-1251");
        return messageSource.getMessage(code, null, new Locale(LanguageEnum.uz.name()));
    }
}
