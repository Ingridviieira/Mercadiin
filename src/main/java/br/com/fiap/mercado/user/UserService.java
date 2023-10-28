package br.com.fiap.mercado.user;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    UserRepository repository;

    public void addScore(User githubuser, Integer score) {
        Optional<User> opt = repository.findById(githubuser.getId());

        if (opt.isEmpty())
            throw new RuntimeException("usuário não encontrado");
        var user = opt.get();
        user.setScore(user.getScore() + score);
        repository.save(user);
    }
    
}
