package br.com.livino.apicorreios.repository;

import br.com.livino.apicorreios.model.Address;
import org.springframework.data.repository.CrudRepository;

public interface AddressRepository extends CrudRepository<Address, String> {
}
