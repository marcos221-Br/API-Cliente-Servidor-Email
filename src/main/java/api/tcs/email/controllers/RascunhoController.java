package api.tcs.email.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import api.tcs.email.dtos.RascunhoResponseDto;
import api.tcs.email.dtos.RascunhosResponseDto;
import api.tcs.email.dtos.models.RascunhoDto;
import api.tcs.email.models.JsonMessage;
import api.tcs.email.models.Usuario;
import api.tcs.email.services.EmailService;

@RestController
@RequestMapping("/rascunhos")
public class RascunhoController {
    
    @Autowired
    private EmailService emailService;

    @PostMapping
    public ResponseEntity<?> createRascunho(@RequestBody RascunhoDto rascunhoDto, Authentication authentication){
        return ResponseEntity.ok().body(new RascunhoResponseDto("Rascunho criado",this.emailService.createRascunho(rascunhoDto, ((Usuario)authentication.getPrincipal()))));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateRascunho(@RequestBody RascunhoDto rascunhoDto, Authentication authentication, @PathVariable Integer id){
        return ResponseEntity.ok().body(new RascunhoResponseDto("Rascunho salvo com sucesso",this.emailService.updateRascunho(rascunhoDto, id, ((Usuario)authentication.getPrincipal()))));
    }

    @GetMapping
    public ResponseEntity<?> getAllRascunhos(Authentication authentication){
        return ResponseEntity.ok().body(new RascunhosResponseDto("Rascunho localizado", this.emailService.getAllRascunhosUsuario(((Usuario)authentication.getPrincipal()))));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getRascunho(@PathVariable Integer id, Authentication authentication){
        return ResponseEntity.ok().body(new RascunhoResponseDto("Rascunho localizado", this.emailService.getRascunho(id, ((Usuario)authentication.getPrincipal()))));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteRascunho(@PathVariable Integer id, Authentication authentication){
        this.emailService.deleteRascunho(id, ((Usuario)authentication.getPrincipal()));
        return ResponseEntity.ok().body(new JsonMessage("Rascunho deletado com sucesso"));
    }
}
