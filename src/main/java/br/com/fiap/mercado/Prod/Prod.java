package br.com.fiap.mercado.Prod;

import br.com.fiap.mercado.user.User;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Entity
public class Prod {
    
    @Id @GeneratedValue(strategy =  GenerationType.IDENTITY)
    Long id;

    @NotBlank
    String produto;

    @Size(min =5, message = "{prod.descricao.size}")
    String descricao;

    @Min(0) @Max(10)
    Integer status;

    @Positive
    Integer score;

    @ManyToOne
    User user;
    
}
