package api.tcs.email.dtos.models;

import lombok.Data;

@Data
public class RascunhoDto {
    
    private Integer rascunhoId;
    private String assunto;
    private String emailDestinatario;
    private String corpo;
}
