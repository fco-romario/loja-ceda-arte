package br.com.fco_romario.loja_ceda_artes.repositories;

import br.com.fco_romario.loja_ceda_artes.domain.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Integer> {

    @Query(value = """
                SELECT
                    cliente.*
                FROM Clientes cliente
                JOIN enderecos endereco ON cliente.id = endereco.cliente_fk
                where endereco.id = :enderecoId;
            """, nativeQuery = true)
    Optional<Cliente> buscarClientePorEnderecoId(@Param("enderecoId") Integer enderecoId);
}
