package br.com.zup.bootcamp.proposta.repository;

import br.com.zup.bootcamp.proposta.model.Cartao;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CartaoRepository extends CrudRepository<Cartao, String> {

    Optional<Cartao> findById(UUID idCartao);
}
