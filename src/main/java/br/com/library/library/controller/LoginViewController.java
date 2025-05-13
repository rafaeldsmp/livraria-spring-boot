package br.com.library.library.controller;

import br.com.library.library.config.CustomAuthentication;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class LoginViewController {
    @GetMapping("/login")
    public String paginaLogin() {
        return "login";
    }

    @GetMapping("/")
    @ResponseBody
    public String paginaHome(Authentication authentication) {
        if (authentication instanceof CustomAuthentication customAuth) {
            System.out.println(customAuth.getUsuario());
        }
        return authentication.getName() + authentication.getAuthorities();
    }
}
