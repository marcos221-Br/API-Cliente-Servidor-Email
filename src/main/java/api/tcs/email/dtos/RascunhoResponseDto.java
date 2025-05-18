package api.tcs.email.dtos;

import api.tcs.email.dtos.models.RascunhoDto;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RascunhoResponseDto {
    
    private String mensagem;
    private RascunhoDto rascunho;
}
