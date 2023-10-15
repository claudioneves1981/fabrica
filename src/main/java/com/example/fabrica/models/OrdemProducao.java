package com.example.fabrica.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class OrdemProducao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_PRODUTO")
    private Long id;

    @Column(name = "PRODUTO")
    private String produto;

    @Column(name = "QUANTIDADE")
    private Integer quantidade;

    @Column(name = "DATA_ENTREGA")
    private String dataEntrega;

    @Column(name = "STATUS")
    private String status;

}
