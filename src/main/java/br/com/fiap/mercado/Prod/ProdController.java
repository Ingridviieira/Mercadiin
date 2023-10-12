package br.com.fiap.mercado.Prod;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/prod")
public class ProdController {

    @Autowired
    ProdService service;

    @GetMapping
    public String index(Model model){
        model.addAttribute("prods", service.findAll());
        return "prod/index";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id, RedirectAttributes redirect){
        if(service.delete(id)){
            redirect.addFlashAttribute("success", "Produto apagada com sucesso");
        }else{
            redirect.addFlashAttribute("error", "Produto não foi encontrado");
        }
        return "redirect:/prod";
    }

    @GetMapping("new")
    public String form(Prod prod){
        return "prod/form";
    }
    
    @PostMapping
    public String create(@Valid Prod prod, BindingResult result, RedirectAttributes redirect){
        if (result.hasErrors())
        return "prod/form";
        service.save(prod);
        redirect.addFlashAttribute("sucess", "Produto cadastrado com Sucesso!");
        return "redirect:/prod";
    }
}
