package cl.utem.ws.domain.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "entrances")
public class Entrance extends PkBaseEntity {

    @JoinColumn(name = "credential_fk", referencedColumnName = "pk", nullable = false)
    @ManyToOne
    private Credential credential = null;

    @Column(name = "user_agent")
    private String userAgent = null;

    @Column(name = "ip")
    private String ip = null;

    public Credential getCredential() {
        return credential;
    }

    public void setCredential(Credential credential) {
        this.credential = credential;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }
}
