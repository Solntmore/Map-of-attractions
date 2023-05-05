package ru.digitalchief.Map.of.attractions.authApi.auth.service;
public interface ClientService {
    void register(String clientId, String clientSecret);
    void checkCredentials(String clientId, String clientSecret);
}
