package api.tcs.email.dtos.mappers;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import api.tcs.email.dtos.models.EmailDto;
import api.tcs.email.models.Email;
import api.tcs.email.models.Usuario;

@Mapper(componentModel = "spring")
public interface EmailMapper {
    
    @Mapping(target="id", ignore=true)
    @Mapping(target="envio", ignore=true)
    @Mapping(target="remetente", ignore=true)
    @Mapping(target="status", ignore=true)
    @Mapping(source="emailDestinatario", target="destinatario", qualifiedByName="emailToUsuario")
    Email emailDtoToEmail(EmailDto emailDto);

    @Mapping(source="id", target="emailId")
    @Mapping(source="destinatario", target="emailDestinatario", qualifiedByName="UsuarioToEmail")
    @Mapping(source="remetente", target="emailRemetente", qualifiedByName="UsuarioToEmail")
    @Mapping(source="envio", target="dataEnvio", qualifiedByName="FormatDate")
    EmailDto emailToEmailDto(Email email);

    @Mapping(source="id", target="emailId")
    @Mapping(source="destinatario", target="emailDestinatario", qualifiedByName="UsuarioToEmail")
    @Mapping(source="remetente", target="emailRemetente", qualifiedByName="UsuarioToEmail")
    @Mapping(source="envio", target="dataEnvio", qualifiedByName="FormatDate")
    List<EmailDto> emailsToEmailDtos(List<Email> emails);

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

    @Named("FormatDate")
    default String mapEnvioToDataEnvio(Date envio){
        return new SimpleDateFormat("dd-MM-yyyy").format(envio);
    }
}
