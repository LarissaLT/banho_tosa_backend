package br.com.atos.larissa.banho_tosa_api.repository;

import br.com.atos.larissa.banho_tosa_api.model.Agendamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AgendamentoRepository extends JpaRepository<Agendamento,Long> {
    List<Agendamento> findByTutor_Id(long id);
}
