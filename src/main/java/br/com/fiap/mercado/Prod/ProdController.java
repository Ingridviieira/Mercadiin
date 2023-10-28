package br.com.fiap.mercado.Prod;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.fiap.mercado.user.User;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/prod")
public class ProdController {

    @Autowired
    ProdService service;

    @Autowired
    MessageSource message;

    @GetMapping
    public String index(Model model, @AuthenticationPrincipal OAuth2User user){
        model.addAttribute("username", user.getAttribute("name"));
        model.addAttribute("avatar_url", user.getAttribute("avatar_url"));
        model.addAttribute("prods", service.findAll());
        return "prod/index";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id, RedirectAttributes redirect){
        if(service.delete(id)){
            redirect.addFlashAttribute("success", getMessage("prod.delete.success") );
        }else{
            redirect.addFlashAttribute("error", getMessage("prod.notfound"));
        }
        return "redirect:/prod";
    }

    @GetMapping("new")
        public String form(Prod prod, Model model, @AuthenticationPrincipal OAuth2User user) {
        model.addAttribute("username", user.getAttribute("name"));
        model.addAttribute("avatar_url", user.getAttribute("avatar_url"));
        return "prod/form";
    }
    
    @PostMapping
    public String create(@Valid Prod prod, BindingResult result, RedirectAttributes redirect){
        if (result.hasErrors())
        return "prod/form";
        service.save(prod);
        redirect.addFlashAttribute("sucess", getMessage("prod.create.success"));
        return "redirect:/prod";
    }

    private String getMessage(String code){
        return message.getMessage(code, null, LocaleContextHolder.getLocale());
    }

    @GetMapping("dec/{id}")
    public String decrement(@PathVariable Long id){
        service.decrement(id);
        return "redirect:/prod";
    }

    @GetMapping("inc/{id}")
    public String increment(@PathVariable Long id) {
        service.increment(id);
        return "redirect:/prod";
    }

    @GetMapping("catch/{id}")
    public String catchProd(@PathVariable Long id, @AuthenticationPrincipal OAuth2User user) {
        service.catchProd(id, User.convert(user));
        return "redirect:/prod";
    }
    
    @GetMapping("drop/{id}")
    public String dropProd(@PathVariable Long id, @AuthenticationPrincipal OAuth2User user) {
        service.dropProd(id, User.convert(user));
        return "redirect:/prod";
    }


}
