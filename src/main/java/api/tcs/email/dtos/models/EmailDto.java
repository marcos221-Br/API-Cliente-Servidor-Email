package api.tcs.email.dtos.models;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class EmailDto {
    
    private Integer emailId;
    @NotBlank
    private String assunto;
    private String emailRemetente;
    @NotBlank
    private String emailDestinatario;
    @NotBlank
    private String corpo;
    private String status;
    private String dataEnvio;
}
