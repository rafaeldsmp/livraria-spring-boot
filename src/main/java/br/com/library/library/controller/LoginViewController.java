package br.com.library.library.controller;

import br.com.library.library.config.CustomAuthentication;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@Tag(name = "Logins")
public class LoginViewController {
    @GetMapping("/logins")
    public String paginaLogin() {
        return "login";
    }

    @GetMapping("/")
    @ResponseBody
    @Operation(summary = "obter informações do usuário cadastrado",
            description = "Retorna o nome de login e as permissões (autoridades) do usuário atualmente autenticado no sistema.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Sucesso."),

    })
    public String paginaHome(Authentication authentication) {
        if (authentication instanceof CustomAuthentication customAuth) {
            System.out.println(customAuth.getUsuario());
        }
        return authentication.getName() + authentication.getAuthorities();
    }

    @GetMapping("/authorized")
    @ResponseBody
    @Operation(summary = "Receber o authorization code",
            description = "Endpoint de callback que recebe o authorization code após autenticação via OAuth2.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Sucesso."),

    })
    public String getAuthorizationCode(@RequestParam("code") String code) {
        return "Seu authorization code: " + code;
    }
}
