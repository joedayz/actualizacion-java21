package pe.joedayz.modulo6.migracion.web;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import pe.joedayz.modulo6.migracion.model.Cliente;
import pe.joedayz.modulo6.migracion.service.ClienteService;

@RestController
public class ClienteController {

    private final ClienteService clienteService;

    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @GetMapping("/api/health")
    public String health() {
        return "legacy-ok inicializado=" + clienteService.isInicializado();
    }

    @PostMapping("/api/clientes")
    public Cliente crear(@Valid @RequestBody ClienteRequest request) {
        return new Cliente(request.nombre());
    }

    public record ClienteRequest(@NotBlank String nombre) {}
}
