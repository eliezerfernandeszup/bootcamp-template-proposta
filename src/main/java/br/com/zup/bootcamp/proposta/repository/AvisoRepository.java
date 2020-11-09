package br.com.zup.bootcamp.proposta.repository;

import br.com.zup.bootcamp.proposta.model.Aviso;
import org.springframework.data.repository.CrudRepository;

public interface AvisoRepository extends CrudRepository<Aviso, String> {
}
