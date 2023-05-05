package ru.digitalchief.Map.of.attractions.authApi.auth.repository;

import org.springframework.data.repository.CrudRepository;
import ru.digitalchief.Map.of.attractions.authApi.auth.model.ClientEntity;

public interface ClientRepository extends CrudRepository<ClientEntity, String> {
}
