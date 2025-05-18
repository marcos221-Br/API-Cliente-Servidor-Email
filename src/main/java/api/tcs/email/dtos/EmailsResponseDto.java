package api.tcs.email.dtos;

import java.util.List;

import api.tcs.email.dtos.models.EmailDto;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EmailsResponseDto {
    
    private String mensagem;
    private List<EmailDto> emails;
}
