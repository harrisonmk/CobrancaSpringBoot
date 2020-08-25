package com.projeto.cobranca.controle;

import com.projeto.cobranca.modelo.StatusTitulo;
import com.projeto.cobranca.modelo.Titulo;
import com.projeto.cobranca.repositorio.TituloRepositorio;
import com.projeto.cobranca.repositorio.filter.TituloFilter;
import com.projeto.cobranca.servico.CadastroTituloService;
import java.util.Arrays;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/titulos")
public class TituloControle {

    @Autowired
    private TituloRepositorio tituloRepositorio;

    
    @Autowired
    private CadastroTituloService cadastroTituloService;

    
    
    //Metodo para retornar a pagina de index
    /* @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String index() {

        return "index";

    } */
    
    
    
    //metodo para renderizar a tela de cadastro de titulo
    @RequestMapping(value = "/novo", method = RequestMethod.PUT)
    public ModelAndView salvar() {

        ModelAndView resultado = new ModelAndView("cadastrotitulo");
        resultado.addObject("titulo", new Titulo());

        return resultado;

    }

    
    
    //Metodo para salvar um titulo no banco de dados 
    @RequestMapping(value = "/novo", method = RequestMethod.POST)
    public String salvar(@Valid Titulo titulo, Errors erros, RedirectAttributes attr) {

        if (erros.hasErrors()) {
            return "cadastrotitulo";
        }

        try {

            cadastroTituloService.salvar(titulo);
            attr.addFlashAttribute("mensagem", "Titulo salvo com sucesso!");

            return "redirect:/titulos/novo";

        } catch (IllegalArgumentException e) {
            erros.rejectValue("dataVencimento", null, e.getMessage());
            return "cadastrotitulo";
        }
    }

    
    
    //Metodo para retornar todos os titulos
    @RequestMapping(value = "/listagem") 
    public ModelAndView pesquisar(@ModelAttribute("filtro") TituloFilter filtro) {
        
        List<Titulo> todosTitulos = cadastroTituloService.filtrar(filtro);
        ModelAndView mv = new ModelAndView("pesquisatitulos"); //nome do html entre as aspas
        mv.addObject("titulos", todosTitulos);
        return mv;

    }

    
    
    @RequestMapping("{id}")
    public ModelAndView edicao(@PathVariable("id") Titulo titulo) {

        ModelAndView mv = new ModelAndView("cadastrotitulo");
        mv.addObject(titulo);

        return mv;

    }

    
    
    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    public String excluir(@PathVariable("id") Long id, RedirectAttributes attributes) {
        cadastroTituloService.excluir(id);

        attributes.addFlashAttribute("mensagem", "Título excluído com sucesso!");
        return "redirect:/titulos/listagem";
    }

    
    
    @RequestMapping(value = "listagem/{id}/receber", method = RequestMethod.GET)
    public @ResponseBody String receber(@PathVariable("id") Long id) {
        return cadastroTituloService.receber(id);
          
         
    }

    
    
    @ModelAttribute("todosStatusTitulo")
    public List<StatusTitulo> todosStatusTitulo() {

        return Arrays.asList(StatusTitulo.values());

    }

    
    
    
}
