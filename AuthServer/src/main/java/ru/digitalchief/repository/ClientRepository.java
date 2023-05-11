package ru.digitalchief.repository;

import org.springframework.data.repository.CrudRepository;
import ru.digitalchief.model.ClientEntity;

public interface ClientRepository extends CrudRepository<ClientEntity, String> {
}
