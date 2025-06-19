package br.com.library.controller;

import br.com.library.model.Client;
import br.com.library.service.ClientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("clients")
@RequiredArgsConstructor
@Tag(name = "Clients")
@Slf4j
public class ClientController {

    private final ClientService clientService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('GERENTE')")
    @Operation(summary = "Criar client", description = "Cadastrar client")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Client cadastrado."),
    })
    public void salvar(@RequestBody Client client) {
        log.info("Registrando novo client: {} com scope: {}", client.getClientId(), client.getScope());
        clientService.salvar(client);
    }

}
