package br.com.atos.larissa.banho_tosa_api.repository;

import br.com.atos.larissa.banho_tosa_api.model.Tutor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TutorRepository extends JpaRepository<Tutor,Long> {

    Optional<Tutor> findByEmail(String email);
}
