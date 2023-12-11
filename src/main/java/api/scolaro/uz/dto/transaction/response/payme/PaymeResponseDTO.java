package api.scolaro.uz.dto.transaction.response.payme;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Admin on 08.12.2023
 * @project api.scolaro.uz
 * @package api.scolaro.uz.dto.transaction.response.payme
 * @contact @sarvargo
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PaymeResponseDTO {

    private Long id;
    private String method;
    private String jsonrpc;
    private Object result;
    private PaymeResponseErrorDTO error;

    public PaymeResponseDTO(Long id,
                            String method,
                            String jsonrpc) {
        this.id = id;
        this.method = method;
        this.jsonrpc = jsonrpc;
    }

    public PaymeResponseDTO(Long id, String method, String jsonrpc, Object result) {
        this.id = id;
        this.method = method;
        this.jsonrpc = jsonrpc;
        this.result = result;
    }

    public PaymeResponseDTO(Long id, String method, String jsonrpc, PaymeResponseErrorDTO error) {
        this.id = id;
        this.method = method;
        this.jsonrpc = jsonrpc;
        this.error = error;
    }
}
