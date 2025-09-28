package br.com.fco_romario.loja_ceda_artes.repositories;

import br.com.fco_romario.loja_ceda_artes.domain.Cidade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CidadeRepository extends JpaRepository<Cidade, Integer> {
}
