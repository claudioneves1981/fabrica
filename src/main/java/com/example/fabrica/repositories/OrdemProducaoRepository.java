package com.example.fabrica.repositories;

import com.example.fabrica.models.OrdemProducao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrdemProducaoRepository extends JpaRepository<OrdemProducao, Long> {

    List<OrdemProducao> findByStatus(String status);
}
