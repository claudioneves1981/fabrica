package com.example.fabrica.services;

import com.example.fabrica.models.OrdemProducao;
import com.example.fabrica.repositories.OrdemProducaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class OrdemProducaoService {

    @Autowired
    private OrdemProducaoRepository ordemProducaoRepository;


    public void createOrdem(OrdemProducao ordemProducao){

        Random random = new Random();
        boolean material = random.nextBoolean();
        if(material){
            ordemProducao.setStatus("Andamento");
            ordemProducaoRepository.save(ordemProducao);
            System.out.println("Salvo com sucesso");
        }else{
            System.out.println("Não é possivel produzir por falta de materiais");
        }
    }

    public List<OrdemProducao> findAll() {
        return ordemProducaoRepository.findAll();
    }

    public List<OrdemProducao> findByStatus(String status){
        return ordemProducaoRepository.findByStatus(status);
    }

    public void atualizar(OrdemProducao ordemProducao, Long id){
        Optional<OrdemProducao> optuser = ordemProducaoRepository.findById(id);
        if(optuser.isPresent()){
            ordemProducaoRepository.save(ordemProducao);
        }
    }

    public OrdemProducao findById(Long id){
       return ordemProducaoRepository.findById(id).get();
    }

}
