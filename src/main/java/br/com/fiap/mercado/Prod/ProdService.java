package br.com.fiap.mercado.Prod;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProdService {

    @Autowired
    ProdRepository repository;

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
    
}
