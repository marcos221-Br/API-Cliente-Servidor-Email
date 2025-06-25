package api.tcs.email.services;

import java.sql.Date;
import java.util.List;
import java.util.NoSuchElementException;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Service;

import api.tcs.email.dtos.mappers.EmailMapper;
import api.tcs.email.dtos.mappers.RascunhoMapper;
import api.tcs.email.dtos.models.EmailDto;
import api.tcs.email.dtos.models.RascunhoDto;
import api.tcs.email.exceptions.DefaultException;
import api.tcs.email.exceptions.IncompleteDtoException;
import api.tcs.email.exceptions.NotFoundException;
import api.tcs.email.models.Email;
import api.tcs.email.models.Status;
import api.tcs.email.models.Usuario;
import api.tcs.email.repositories.EmailRepository;
import api.tcs.email.repositories.UsuarioRepository;

@Service
public class EmailService {
    
    @Autowired
    private EmailRepository emailRepository;

    @Autowired
    private RascunhoMapper rascunhoMapper;

    @Autowired
    private EmailMapper emailMapper;

    @Autowired
    private UsuarioRepository usuarioRepository;

    private Email email;

    public RascunhoDto createRascunho(RascunhoDto rascunhoDto, Usuario remetente){
        rascunhoDto.setRascunhoId(null);
        this.email = rascunhoMapper.rascunhoDtoToEmail(rascunhoDto);
        this.email.setRemetente(remetente);
        this.email.setStatus(Status.rascunho);
        if(StringUtils.isEmpty(this.email.getCorpo()) && StringUtils.isEmpty(this.email.getAssunto()) && this.email.getDestinatario() == null){
            throw new IncompleteDtoException();
        }
        try{
            if(this.email.getDestinatario() != null){
                this.email.setDestinatario(this.usuarioRepository.findByEmail(this.email.getDestinatario().getEmail()).get());
            }
            return rascunhoMapper.emailToRascunhoDto(this.emailRepository.save(this.email));
        }catch(NoSuchElementException e){
            throw new NotFoundException("Email não encontrado");
        }catch(Exception e){
            throw new DefaultException();
        }
    }

    public RascunhoDto updateRascunho(RascunhoDto rascunhoDto, Integer id, Usuario remetente){
        this.email = rascunhoMapper.rascunhoDtoToEmail(rascunhoDto);
        this.email.setRemetente(remetente);
        this.email.setStatus(Status.rascunho);
        this.email.setId(id);
        try{
            if(this.email.getDestinatario() != null){
                this.email.setDestinatario(this.usuarioRepository.findByEmail(this.email.getDestinatario().getEmail()).get());
            }
            if(this.emailRepository.findById(this.email.getId()).get().getRemetente().equals(remetente)){
                return rascunhoMapper.emailToRascunhoDto(this.emailRepository.save(email));
            }
            throw new NotFoundException("Rascunho não encontrado");
        }catch(NoSuchElementException e){
            throw new NotFoundException("Email não encontrado");
        }catch(ObjectOptimisticLockingFailureException e){
            throw new NotFoundException("Rascunho não encontrado");
        }catch(Exception e){
            throw new DefaultException();
        }
    }

    public List<RascunhoDto> getAllRascunhosUsuario(Usuario remetente){
        try{
            return this.rascunhoMapper.emailsToRascunhoDtos(this.emailRepository.getAllRascunhosUsuario(remetente.getId()));
        }catch(NotFoundException e){
            throw new NotFoundException(e.getMessage());
        }catch(Exception e){
            throw new DefaultException();
        }
    }

    public RascunhoDto getRascunho(Integer id, Usuario remetente){
        try{
            this.email = this.emailRepository.findById(id).get();
            if(this.email.getRemetente().equals(remetente) && this.email.getStatus().ordinal() == 0){
                return this.rascunhoMapper.emailToRascunhoDto(this.email);
            }
            throw new NotFoundException("Rascunho não encontrado");
        }catch(NotFoundException e){
            throw new NotFoundException("Rascunho não encontrado");
        }catch(NoSuchElementException e){
            throw new NotFoundException("Rascunho não encontrado");
        }catch(Exception e){
            throw new DefaultException();
        }
    }

    public void deleteRascunho(Integer id, Usuario remetente){
        try{
            this.email = this.emailRepository.findById(id).get();
            if(this.email.getRemetente().equals(remetente) && this.email.getStatus().ordinal() == 0){
                this.emailRepository.deleteById(id);
            }else{
                throw new NotFoundException("Rascunho não encontrado");
            }
        }catch(NoSuchElementException e){
            throw new NotFoundException("Rascunho não encontrado");
        }catch(Exception e){
            throw new DefaultException();
        }
    }

    public EmailDto sendEmail(EmailDto emailDto, Usuario remetente){
        this.email = this.emailMapper.emailDtoToEmail(emailDto);
        this.email.setRemetente(remetente);
        this.email.setEnvio(new Date(System.currentTimeMillis()));
        this.email.setStatus(Status.enviado);
        try{
            this.email.setDestinatario(this.usuarioRepository.findByEmail(this.email.getDestinatario().getEmail()).get());
            return this.emailMapper.emailToEmailDto(this.emailRepository.save(this.email));
        }catch(NoSuchElementException e){
            throw new IncompleteDtoException();
        }catch(Exception e){
            throw new DefaultException();
        }
    }

    public EmailDto sendRascunho(Integer id, Usuario remetente){
        try{
            this.email = this.emailRepository.findById(id).get();
            if(this.email.getRemetente().equals(remetente)){
                this.email.setEnvio(new Date(System.currentTimeMillis()));
                this.email.setStatus(Status.enviado);
                this.email.setDestinatario(this.usuarioRepository.findByEmail(this.email.getDestinatario().getEmail()).get());
                return this.emailMapper.emailToEmailDto(this.emailRepository.save(this.email));
            }
            throw new NotFoundException("Rascunho não encontrado");
        }catch(NotFoundException e){
            throw new NotFoundException("Rascunho não encontrado");
        }catch(NoSuchElementException e){
            if(this.email == null){
                throw new NotFoundException("Rascunho não encontrado");
            }
            throw new NotFoundException("Email não encontrado");
        }catch(NullPointerException e){
            throw new NotFoundException("Email não encontrado");
        }catch(Exception e){
            System.out.println("\n\n\n\n" + e);
            throw new DefaultException();
        }
    }

    public EmailDto getEmail(Integer id, Usuario usuario){
        try{
            this.email = this.emailRepository.findById(id).get();
            if(this.email.getDestinatario().equals(usuario) && this.email.getStatus().ordinal() > 0){
                this.email.setStatus(Status.lido);
                return this.emailMapper.emailToEmailDto(this.emailRepository.save(this.email));
            }else if(this.email.getRemetente().equals(usuario) && this.email.getStatus().ordinal() > 0){
                return this.emailMapper.emailToEmailDto(this.emailRepository.save(this.email));
            }
            throw new NotFoundException("Rascunho não encontrado");
        }catch(NotFoundException e){
            throw new NotFoundException("Rascunho não encontrado");
        }catch(NoSuchElementException e){
            throw new NotFoundException("Rascunho não encontrado");
        }catch(Exception e){
            throw new DefaultException();
        }
    }

    public List<EmailDto> getAllEmailsUsuario(Usuario usuario){
        try{
            return this.emailMapper.emailsToEmailDtos(this.emailRepository.getAllEmailsUsuario(usuario.getId()));
        }catch(NotFoundException e){
            throw new NotFoundException(e.getMessage());
        }catch(Exception e){
            System.out.println("\n\n\n\n" + e);
            throw new DefaultException();
        }
    }
}
