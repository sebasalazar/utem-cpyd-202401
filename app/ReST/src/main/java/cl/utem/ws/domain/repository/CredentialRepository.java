package cl.utem.ws.domain.repository;

import cl.utem.ws.domain.model.Credential;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CredentialRepository extends JpaRepository<Credential, Long> {

    public Credential findByCodeIgnoreCase(String code);

    public Credential findByTokenAndKey(String token, String key);
}
