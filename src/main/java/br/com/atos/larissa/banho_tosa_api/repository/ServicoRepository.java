package br.com.atos.larissa.banho_tosa_api.repository;

import br.com.atos.larissa.banho_tosa_api.model.Servico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServicoRepository extends JpaRepository<Servico,Long> {
}
