package api.tcs.email.dtos.mappers;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import api.tcs.email.dtos.models.RascunhoDto;
import api.tcs.email.models.Email;
import api.tcs.email.models.Usuario;

@Mapper(componentModel = "spring")
public interface RascunhoMapper {
    
    @Mapping(source="rascunhoId", target="id")
    @Mapping(target="envio", ignore=true)
    @Mapping(target="remetente", ignore=true)
    @Mapping(target="status", ignore=true)
    @Mapping(source = "emailDestinatario", target = "destinatario", qualifiedByName = "emailToUsuario")
    Email rascunhoDtoToEmail(RascunhoDto rascunhoDto);

    @Mapping(source="id", target="rascunhoId")
    @Mapping(source = "destinatario", target = "emailDestinatario", qualifiedByName = "UsuarioToEmail")
    RascunhoDto emailToRascunhoDto(Email email);

    @Mapping(source="emails.id", target="rascunhoId")
    @Mapping(source = "destinatario", target = "destinatario", qualifiedByName = "UsuarioToEmail")
    List<RascunhoDto> emailsToRascunhoDtos(List<Email> emails);

    @Named("emailToUsuario")
    default Usuario mapEmailToUsuario(String email) {
        if(email == null || email.isEmpty()){
            return null;
        }
        Usuario usuario = new Usuario();
        usuario.setEmail(email);
        return usuario;
    }

    @Named("UsuarioToEmail")
    default String mapUsuarioToEmail(Usuario usuario) {
        if(usuario == null){
            return null;
        }
        return usuario.getEmail();
    }
}
