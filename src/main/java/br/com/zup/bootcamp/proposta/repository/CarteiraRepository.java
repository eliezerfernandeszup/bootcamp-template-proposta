package br.com.zup.bootcamp.proposta.repository;

import br.com.zup.bootcamp.proposta.model.Carteira;
import org.springframework.data.repository.CrudRepository;

public interface CarteiraRepository extends CrudRepository<Carteira, String> {
}
