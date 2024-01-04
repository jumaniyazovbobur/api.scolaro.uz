package api.scolaro.uz;

import api.scolaro.uz.enums.AppLanguage;
import api.scolaro.uz.service.ResourceMessageService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ApplicationTests {
    @Autowired
    private ResourceMessageService resourceMessageService;
    @Test
    void contextLoads() {
        System.out.println("BIZNING KOMPANIYAMIZ 2017 YILDA TASHKIL ETILGAN. SHU VAQT ICHIDA YAQIN VA UZOQ XORIJDAGI ENG YAXSHI OLIY O'QUV YURTLARIGA QABUL QILISH BO'YICHA XIZMATLAR KO'RSATILDI.".toLowerCase());

    }
}
