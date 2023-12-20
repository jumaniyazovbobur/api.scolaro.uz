package api.scolaro.uz.service.transaction;

import api.scolaro.uz.dto.ApiResponse;
import api.scolaro.uz.dto.transaction.PaymeCallBackRequestDTO;
import api.scolaro.uz.dto.transaction.TransactionResponseDTO;
import api.scolaro.uz.dto.transaction.request.TransactionFilterAsAdminDTO;
import api.scolaro.uz.dto.transaction.request.TransactionFilterAsStudentDTO;
import api.scolaro.uz.dto.transaction.request.WithdrawMoneyFromStudentDTO;
import api.scolaro.uz.dto.transaction.response.TransactionResponseAsAdminDTO;
import api.scolaro.uz.dto.transaction.response.TransactionResponseAsStudentDTO;
import api.scolaro.uz.dto.transaction.response.payme.PaymeResponseDTO;
import api.scolaro.uz.enums.LanguageEnum;
import org.springframework.data.domain.PageImpl;

import java.util.Map;

/**
 * @author 'Mukhtarov Sarvarbek' on 04.12.2023
 * @project api.scolaro.uz
 * @contact @sarvargo
 */
public interface TransactionService {

    ApiResponse<TransactionResponseDTO> createTransactionForFillBalance(String currentUserId, Long amount);

    PaymeResponseDTO callBackPayme(PaymeCallBackRequestDTO body);

    PageImpl<TransactionResponseAsStudentDTO> filterAsStudent(TransactionFilterAsStudentDTO dto, int page, int size);

    PageImpl<TransactionResponseAsAdminDTO> filterAsAdmin(TransactionFilterAsAdminDTO dto, int page, int size);


    ApiResponse<TransactionResponseDTO> withdrawMoneyFromStudentAsConsulting(WithdrawMoneyFromStudentDTO dto, LanguageEnum lang);
}
