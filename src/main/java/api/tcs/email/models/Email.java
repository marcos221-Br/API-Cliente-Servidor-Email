package api.tcs.email.models;

import java.io.Serializable;
import java.sql.Date;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "emails")
@Data
public class Email implements Serializable {
    
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Basic(optional = true)
    @Column(name = "assunto")
    private String assunto;

    @Basic(optional = true)
    @Column(name = "corpo", columnDefinition = "LONGTEXT")
    private String corpo;

    @Basic(optional = false)
    @ManyToOne
    @JoinColumn(name = "remetente", referencedColumnName = "id")
    private Usuario remetente;

    @Basic(optional = true)
    @ManyToOne
    @JoinColumn(name = "destinatario", referencedColumnName = "id")
    private Usuario destinatario;

    @Basic(optional = false)
    @Column(name = "status")
    private Status status;

    @Basic(optional = true)
    @Column(name = "envio")
    private Date envio;
}
