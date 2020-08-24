package com.projeto.cobranca.servico;

import com.projeto.cobranca.modelo.StatusTitulo;
import com.projeto.cobranca.modelo.Titulo;
import com.projeto.cobranca.repositorio.TituloRepositorio;
import com.projeto.cobranca.repositorio.filter.TituloFilter;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
public class CadastroTituloService {

    
    @Autowired
    private TituloRepositorio tituloRepositorio;

    
    public void salvar(Titulo titulo) {
        try {
            tituloRepositorio.save(titulo);
        } catch (DataIntegrityViolationException e) {
            throw new IllegalArgumentException("Formato de data inválido");
        }
    }

    
    public void excluir(Long id) {
        tituloRepositorio.deleteById(id);
    }

    
    public String receber(Long id) {
       
       Titulo titulo = tituloRepositorio.getOne(id);
        titulo.setStatus(StatusTitulo.RECEBIDO);
        tituloRepositorio.save(titulo);

        return StatusTitulo.RECEBIDO.getDescricao();
         
    }

    
    public List<Titulo> filtrar(TituloFilter filtro) {
		String descricao = filtro.getDescricao() == null ? "%" : filtro.getDescricao();
		return tituloRepositorio.findByDescricaoContaining(descricao);
	}
    
    
}
