package ru.digitalchief.auth.service;

public interface TokenCheckService {
    boolean checkToken(String token);
}
