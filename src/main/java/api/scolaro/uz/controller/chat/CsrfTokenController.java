package api.scolaro.uz.controller.chat;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.security.web.csrf.CsrfToken;

/**
 * @author 'Mukhtarov Sarvarbek' on 21.12.2023
 * @project api.scolaro.uz
 * @contact @sarvargo
 */
@Controller
public class CsrfTokenController {
    @GetMapping("/csrf")
    public @ResponseBody
    String getCsrfToken(HttpServletRequest request) {
        CsrfToken csrf = (CsrfToken) request.getAttribute(CsrfToken.class.getName());
        return csrf.getToken();
    }
}
