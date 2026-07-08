package pe.joedayz.modulo6.migracion.service;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Service;

@Service
public class ClienteService {

    private boolean inicializado;

    @PostConstruct
    void init() {
        inicializado = true;
    }

    public boolean isInicializado() {
        return inicializado;
    }
}
