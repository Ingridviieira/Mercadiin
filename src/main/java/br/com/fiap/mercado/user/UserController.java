package br.com.fiap.mercado.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserController{

    @Autowired
    UserRepository repository;

    @GetMapping("/users")
    public String index(Model model) {
        model.addAttribute("users", repository.findAll());
        return "auth/users";
    }

    @GetMapping("/login")
    public String loginPage(){
        return "auth/login";
    }

    @GetMapping("/logout")
    public String logoutPage(){
        return "auth/logout";
    }
}