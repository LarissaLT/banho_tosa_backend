package br.com.atos.larissa.banho_tosa_api.repository;

import br.com.atos.larissa.banho_tosa_api.model.Funcionario;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FuncionarioRepository extends JpaRepository<Funcionario, Long> {
//    @Query("FROM Funcionario f WHERE f.deletedAt=null")
    List<Funcionario> findAllByDeletedAtIsNull();

//    @Query("FROM Funcionario f WHERE f.deletedAt=null and f.id =:id")
    Optional<Funcionario> findByIdAndDeletedAtIsNull(Long id);
    @Transactional
    @Modifying
    @Query("UPDATE Funcionario f SET f.deletedAt = CURRENT_TIMESTAMP WHERE f.id = :id")
    void softDeleteById(@Param("id") Long id);
}
