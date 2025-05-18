package api.tcs.email.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import api.tcs.email.dtos.EmailResponseDto;
import api.tcs.email.dtos.EmailsResponseDto;
import api.tcs.email.dtos.models.EmailDto;
import api.tcs.email.models.Usuario;
import api.tcs.email.services.EmailService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/emails")
public class EmailController {
    
    @Autowired
    private EmailService emailService;

    @PostMapping
    public ResponseEntity<?> sendEmail(@RequestBody @Valid EmailDto emailDto, Authentication authentication){
        return ResponseEntity.ok().body(new EmailResponseDto("Email enviado com sucesso", this.emailService.sendEmail(emailDto, ((Usuario)authentication.getPrincipal()))));
    }

    @PostMapping("/{id}")
    public ResponseEntity<?> sendRascunho(@PathVariable Integer id, Authentication authentication){
        return ResponseEntity.ok().body(new EmailResponseDto("Email enviado com sucesso", this.emailService.sendRascunho(id, ((Usuario)authentication.getPrincipal()))));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> getEmail(@PathVariable Integer id, Authentication authentication){
        return ResponseEntity.ok().body(new EmailResponseDto("Email marcado como lido", this.emailService.getEmail(id, ((Usuario)authentication.getPrincipal()))));
    }

    @GetMapping
    public ResponseEntity<?> getAllEmails(Authentication authentication){
        return ResponseEntity.ok().body(new EmailsResponseDto("Email encontrado", this.emailService.getAllEmailsUsuario(((Usuario)authentication.getPrincipal()))));
    }
}
