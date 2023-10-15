package com.example.fabrica.controllers;

import com.example.fabrica.models.OrdemProducao;
import com.example.fabrica.services.OrdemProducaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class OrdemProducaoController {

    @Autowired
    private OrdemProducaoService ordemProducaoService;

    @GetMapping("/ordens")
    public List<OrdemProducao> ordens(){
        return ordemProducaoService.findAll();
    }

    @PostMapping("/ordens")
    public void createOrdem(@RequestBody OrdemProducao ordemProducao){
      ordemProducaoService.createOrdem(ordemProducao);
    }

    @PutMapping("/ordens/{id}")
    public void atualizar(@RequestBody OrdemProducao ordemProducao, @PathVariable("id") Long id){
        if(ordemProducao.getId() != null){
            ordemProducaoService.atualizar(ordemProducao,id);
        }
    }

    @GetMapping("/ordens/{id}")
    public OrdemProducao findByOrdem(@PathVariable("id") Long id){
        return ordemProducaoService.findById(id);
    }

    @GetMapping("/ordens/status/{status}")
    public List<OrdemProducao> findByStatus(@PathVariable("status") String status){
        return ordemProducaoService.findByStatus(status);
    }
}
