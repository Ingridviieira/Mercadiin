package br.com.fiap.mercado.config;

import java.io.IOException;
import java.util.Optional;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.filter.OncePerRequestFilter;

import br.com.fiap.mercado.user.User;
import br.com.fiap.mercado.user.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class LoginFilter extends OncePerRequestFilter{
    
    private UserRepository userRepository;

    public LoginFilter(UserRepository userRepository){
        this.userRepository = userRepository;
    }
    
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

                // pegar a auth
                Authentication auth = SecurityContextHolder.getContext().getAuthentication();

                //se estiver logado
                if (auth != null){
                    //pegar o usuario
                    var principal = (OAuth2User) auth.getPrincipal();

                    //se não existe no bd
                    Optional<User> opt = userRepository.findById(Long.valueOf(principal.getName()));
                    if (opt.isEmpty()){
                        //crio no bd
                        userRepository.save(User.convert(principal));

                    }


                }
        

                filterChain.doFilter(request, response);
    }
    
}
