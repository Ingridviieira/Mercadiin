package br.com.fiap.mercado.Prod;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import br.com.fiap.mercado.user.User;
import br.com.fiap.mercado.user.UserService;

@Service
public class ProdService {

    @Autowired
    ProdRepository repository;

    @Autowired
    UserService userService;

    public List<Prod> findAll(){
        return repository.findAll();
    }

    public boolean delete(Long id){
        var prod = repository.findById(id);
        if(prod.isEmpty()) return false;
        repository.deleteById(id);
        return true;
    }

    public void save(Prod prod){
        repository.save(prod);
    }

    public void decrement(Long id) {
        var opt = repository.findById(id);
        if (opt.isEmpty())
            throw new RuntimeException("Produto não encontrado");

        var prod = opt.get();
        if (prod.getStatus() == 0)
            throw new RuntimeException("status não pode ser negativo");

        prod.setStatus(prod.getStatus() - 10);
        repository.save(prod);
    }

    public void increment(Long id) {
        var opt = repository.findById(id);
        if (opt.isEmpty())
            throw new RuntimeException("Produto não encontrado");

        var prod = opt.get();
        if (prod.getStatus() == 100)
            throw new RuntimeException("status não pode ser maior do que 100%");

        prod.setStatus(prod.getStatus() + 10);

        if (prod.getStatus() == 100){
            var user = (OAuth2User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            userService.addScore(User.convert( user), prod.getScore());
        }

        repository.save(prod);
    }

    public void catchProd(Long id, User user) {
        var opt = repository.findById(id);
        if (opt.isEmpty())
            throw new RuntimeException("Produto não encontrado");

        var prod = opt.get();
        
        if ( prod.getUser() != null && prod.getUser().equals(user))
            throw new RuntimeException("você já selecionou esse produto");

        if (prod.getUser() != null)
            throw new RuntimeException("Produto já atribuída");

        prod.setUser(user);
        repository.save(prod);

    }

    public void dropProd(Long id, User user) {
        var opt = repository.findById(id);
        if (opt.isEmpty())
            throw new RuntimeException("Produto não encontrado");

        var prod = opt.get();
        if (prod.getUser() == null)
            throw new RuntimeException("Produto não atribuída");
        
        if (!prod.getUser().equals(user))
            throw new RuntimeException("você não pode largar o produto de outra pessoa");

        prod.setUser(null);
        repository.save(prod);
    }
    
}
