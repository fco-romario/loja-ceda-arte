package br.com.fco_romario.loja_ceda_artes.repositories;

import br.com.fco_romario.loja_ceda_artes.domain.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Integer> {

    @Query(value = """
            SELECT
                cliente.*
            FROM ceda_arte.clientes cliente
            JOIN ceda_arte.enderecos endereco ON cliente.id = endereco.cliente_fk
            where endereco.id = :enderecoId;
        """, nativeQuery = true)
    Optional<Cliente> buscarClientePorEnderecoId(@Param("enderecoId") Integer enderecoId);

    @Modifying(clearAutomatically = true)
    @Query(value = """
                UPDATE ceda_arte.clientes
                SET ATIVO = 0
                WHERE id = :id;
            """
            , nativeQuery = true)
    void inativarCliente(@Param("id") Integer id);
}
