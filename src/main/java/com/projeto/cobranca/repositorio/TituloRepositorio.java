
package com.projeto.cobranca.repositorio;

import com.projeto.cobranca.modelo.Titulo;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TituloRepositorio extends JpaRepository<Titulo,Long> {
    
  public List<Titulo> findByDescricaoContaining(String descricao);  
    
}
