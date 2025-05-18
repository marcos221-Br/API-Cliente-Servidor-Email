package api.tcs.email.dtos;

import java.util.List;

import api.tcs.email.dtos.models.RascunhoDto;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RascunhosResponseDto {
    
    private String mensagem;
    private List<RascunhoDto> rascunhos;
}
