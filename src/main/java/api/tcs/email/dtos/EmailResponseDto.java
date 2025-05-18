package api.tcs.email.dtos;

import api.tcs.email.dtos.models.EmailDto;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EmailResponseDto {
    
    private String mensagem;
    private EmailDto email;
}
