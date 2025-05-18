package api.tcs.email.dtos.models;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class RascunhoDto {
    
    @NotNull
    private Integer rascunhoId;
    private String assunto;
    private String emailDestinatario;
    private String corpo;
}
