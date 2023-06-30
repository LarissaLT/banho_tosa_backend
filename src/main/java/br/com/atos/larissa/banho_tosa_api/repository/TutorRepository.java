package br.com.atos.larissa.banho_tosa_api.repository;

import br.com.atos.larissa.banho_tosa_api.model.Funcionario;
import br.com.atos.larissa.banho_tosa_api.model.Tutor;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TutorRepository extends JpaRepository<Tutor,Long> {
    Optional<Tutor> findByEmail(String email);

    //    @Query("FROM Tutor f WHERE f.deletedAt=null")
    List<Tutor> findAllByDeletedAtIsNull();

    //    @Query("FROM Tutor f WHERE f.deletedAt=null and f.id =:id")
    Optional<Tutor> findByIdAndDeletedAtIsNull(Long id);
    @Transactional
    @Modifying
    @Query("UPDATE Tutor f SET f.deletedAt = CURRENT_TIMESTAMP WHERE f.id = :id")
    void softDeleteById(@Param("id") Long id);
}
