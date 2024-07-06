package cl.utem.ws.domain.repository;

import cl.utem.ws.domain.model.Faculty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FacultyRepository extends JpaRepository<Faculty, Long> {

    public Faculty findByCodeIgnoreCase(String code);
}
