package ru.t1.java.demo.service;

import ru.t1.java.demo.model.Client;

import java.io.IOException;
import java.util.List;

public interface ClientService {
    void registerClients(List<Client> clients);
    List<Client> parseJson() throws IOException;
}
