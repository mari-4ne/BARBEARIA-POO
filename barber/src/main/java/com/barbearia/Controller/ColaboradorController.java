package com.barbearia.Controller;

import com.barbearia.Model.DiaDaSemana;
import com.barbearia.Service.ColaboradorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/colaborador")
public class ColaboradorController {

    private final ColaboradorService colaboradorService;

    @Autowired
    public ColaboradorController(ColaboradorService colaboradorService) {
        this.colaboradorService = colaboradorService;
    }

    
    //exibe a pagina de gerenciamento de horarios 
    @GetMapping("/gerenciar/{id}")
    public String gerenciarHorarios(@PathVariable int id, Model model) {
        model.addAttribute("colaborador", colaboradorService.buscarPorId(id).orElse(null));
        model.addAttribute("diasDaSemana", DiaDaSemana.values());
        return "gerenciamento-horarios"; // Nome do arquivo HTML
    }

   
     //processa a remoção de um horário.
    @PostMapping("/remover-horario")
    public String removerHorario(@RequestParam int colaboradorId,
                                 @RequestParam DiaDaSemana dia,
                                 @RequestParam String horario) {
        //o Controller chama o Service.
        colaboradorService.removerHorarioDisponivel(colaboradorId, dia, horario);
        return "redirect:/colaborador/gerenciar/" + colaboradorId; //redireciona de volta
    }
    
    //processa a adição de um horário.S
    @PostMapping("/adicionar-horario")
    public String adicionarhorario(@RequestParam int colaboradorId,
                                 @RequestParam DiaDaSemana dia,
                                 @RequestParam String horario) {
        //o Controller chama o Service.
        colaboradorService.adicionarHorarioDisponivel(colaboradorId, dia, horario);
        return "redirect:/colaborador/gerenciar/" + colaboradorId; //redireciona de volta
    }
   
}