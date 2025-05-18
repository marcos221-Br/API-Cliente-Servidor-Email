package api.tcs.email.exceptions;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RestErrorMessage {
    
    private HttpStatus erro;
    private String mensagem;
}
