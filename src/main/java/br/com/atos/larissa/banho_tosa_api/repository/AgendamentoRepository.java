package br.com.atos.larissa.banho_tosa_api.repository;

import br.com.atos.larissa.banho_tosa_api.model.Agendamento;
import br.com.atos.larissa.banho_tosa_api.model.Cachorro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AgendamentoRepository extends JpaRepository<Agendamento,Long> {
    List<Agendamento> findByTutor_Id(long id);

    @Query(value = "SELECT * fROM Agendamento a  WHERE a.id = :id and a.tutor_id = :tutorId",nativeQuery = true)
    Optional<Agendamento> findByIdAndTutorId(Long id, Long tutorId);
}
