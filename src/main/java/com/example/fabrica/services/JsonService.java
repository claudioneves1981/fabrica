package com.example.fabrica.services;

import com.example.fabrica.models.OrdemProducao;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class JsonService {

    public List<OrdemProducao> ordens(){
        List<OrdemProducao> ordensproducao = new ArrayList<>();
        try {
            URL endereco = new URL("http://localhost:8080/ordens");
            BufferedReader br = new BufferedReader(new InputStreamReader(endereco.openStream()));

            String line = br.readLine();
            String[] json = line.split("},\\{");
            System.out.println(json[0]);
            OrdemProducao ordemProducao = new OrdemProducao();
            for (String s : json) {
                ordemProducao.setQuantidade(Integer.parseInt(parseSemGson(s, "quantidade")));
                ordemProducao.setStatus(parseSemGson(s, "status"));
                ordemProducao.setProduto(parseSemGson(s, "produto"));
                ordemProducao.setDataEntrega(parseSemGson(s, "dataEntrega"));
                ordemProducao.setId(Long.parseLong(parseSemGson(s, "id")));
                ordensproducao.add(ordemProducao);
            }

           // System.out.println("id = " + parseSemGson(data,"id"));
           // System.out.println("name = " + parseSemGson(data,"name"));
           // System.out.println("profileIconId = " + parseSemGson(data,"profileIconId"));
           // System.out.println("revisionDate = " + parseSemGson(data,"revisionDate"));
           // System.out.println("summonerLevel = " + parseSemGson(data,"summonerLevel"));
        } catch(Exception e){
            e.printStackTrace();
        }
        return ordensproducao;
    }

    public OrdemProducao findByOrdem(Long id){
        OrdemProducao ordemProducao = new OrdemProducao();

        try {
            URL endereco = new URL("http://localhost:8080/ordens/"+id);
            BufferedReader br = new BufferedReader(new InputStreamReader(endereco.openStream()));

            String line = br.readLine();
            String[] json = line.split("},\\{");
           // for (String s : json) {
                ordemProducao.setQuantidade(Integer.parseInt(parseSemGson(line, "quantidade")));
                ordemProducao.setStatus(parseSemGson(line, "status"));
                ordemProducao.setProduto(parseSemGson(line, "produto"));
                ordemProducao.setDataEntrega(parseSemGson(line, "dataEntrega"));
                ordemProducao.setId(Long.parseLong(parseSemGson(line, "id")));

        } catch(Exception e){
            e.printStackTrace();
        }
        return ordemProducao;
    }

    public List<OrdemProducao> findByStatus(String status){
        List<OrdemProducao> ordensproducao = new ArrayList<>();
        try {
            URL endereco = new URL("http://localhost:8080/ordens/status/"+status);
            BufferedReader br = new BufferedReader(new InputStreamReader(endereco.openStream()));

            String line = br.readLine();
            String[] json = line.split("},\\{");
            OrdemProducao ordemProducao = new OrdemProducao();
            System.out.println(json[0]);
            for (String s : json) {
                ordemProducao.setQuantidade(Integer.parseInt(parseSemGson(s, "quantidade")));
                ordemProducao.setStatus(parseSemGson(s, "status"));
                ordemProducao.setProduto(parseSemGson(s, "produto"));
                ordemProducao.setDataEntrega(parseSemGson(s, "dataEntrega"));
                ordemProducao.setId(Long.parseLong(parseSemGson(s, "id")));
                ordensproducao.add(ordemProducao);
            }

            // System.out.println("id = " + parseSemGson(data,"id"));
            // System.out.println("name = " + parseSemGson(data,"name"));
            // System.out.println("profileIconId = " + parseSemGson(data,"profileIconId"));
            // System.out.println("revisionDate = " + parseSemGson(data,"revisionDate"));
            // System.out.println("summonerLevel = " + parseSemGson(data,"summonerLevel"));
        } catch(Exception e){
            e.printStackTrace();
        }
        return ordensproducao;
    }


    public String parseSemGson(String linha,String campo){
        linha = linha.replaceAll("\"","");
        int indice = linha.indexOf(campo) + 3 + campo.length();
        int ultimo_char = linha.indexOf(",", indice)!=-1?linha.indexOf(",", indice):linha.indexOf("}", indice);
        return linha.substring(indice,ultimo_char);
    }

    public void createOrdem(OrdemProducao ordemProducao) throws IOException {

        String command = "curl -H \"Content-Type: application/json\" -d '{\"produto\":\""+ordemProducao.getProduto()+"\", \"quantidade\":\""+ordemProducao.getQuantidade()+"\", \"dataEntrega\":\""+ordemProducao.getDataEntrega()+"\", \"status\":\""+ordemProducao.getStatus()+"\"}' -X POST http://localhost:8080/ordens";
        Process process = Runtime.getRuntime().exec(command);
    }

    public void atualizarOrdem(OrdemProducao ordemProducao, Long id) throws IOException {

        String command = "curl -X PUT http://localhost:8080/ordens/"+id+" -H \"Content-Type: application/json\" -d '{\"produto\":\""+ordemProducao.getProduto()+"\", \"quantidade\":\""+ordemProducao.getQuantidade()+"\", \"dataEntrega\":\""+ordemProducao.getDataEntrega()+"\", \"status\":\""+ordemProducao.getStatus()+"\"}'";
        Process process = Runtime.getRuntime().exec(command);
    }

}
