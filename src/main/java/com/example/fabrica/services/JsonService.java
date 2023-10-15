package com.example.fabrica.services;

import com.example.fabrica.models.OrdemProducao;
import com.fasterxml.jackson.databind.util.JSONPObject;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;



public class JsonService {

    public void ordens(){
        List<OrdemProducao> ordensproducao = new ArrayList<>();
        try {
            URL endereco = new URL("http://localhost:8080/ordens");
            BufferedReader br = new BufferedReader(new InputStreamReader(endereco.openStream()));

            String line = br.readLine();
            line = line.replace("},{","}\n{");
            System.out.println(line);


        } catch(Exception e){
            e.printStackTrace();
        }
    }

    public OrdemProducao findByOrdem(Long id){
        OrdemProducao ordemProducao = new OrdemProducao();
        try {
            URL endereco = new URL("http://localhost:8080/ordens/"+id);
            BufferedReader br = new BufferedReader(new InputStreamReader(endereco.openStream()));

            String line = br.readLine();
            ordemProducao.setId(Long.parseLong(parseSemGson(line,"id")));
            ordemProducao.setQuantidade(Integer.parseInt(parseSemGson(line,"quantidade")));
            ordemProducao.setProduto(parseSemGson(line,"produto"));
            ordemProducao.setDataEntrega(parseSemGson(line, "dataEntrega"));
            ordemProducao.setStatus(parseSemGson(line,"status"));
            System.out.println(line);

        } catch(Exception e){
            e.printStackTrace();
        }

        return ordemProducao;
    }

    public void findByStatus(String status){
        try {
            URL endereco = new URL("http://localhost:8080/ordens/status/"+status);
            BufferedReader br = new BufferedReader(new InputStreamReader(endereco.openStream()));

            String line = br.readLine();
            String[] json = line.split("},\\{");
            System.out.println(Arrays.toString(json));
        } catch(Exception e){
            e.printStackTrace();
        }
    }


    public String parseSemGson(String linha,String campo){
        linha = linha.replaceAll("\"","");
        int indice = linha.indexOf(campo);
        int ultimo_char = linha.indexOf(",", indice)!=-1?linha.indexOf(",", indice):linha.indexOf("}", indice);
        String[] vetor = linha.substring(indice,ultimo_char).split(":");
        return vetor[1];
    }

    public void createOrdem(OrdemProducao ordemProducao) throws IOException {

        String postUrl = "http://localhost:8080/ordens";
        Gson gson = new Gson();
        HttpClient httpClient = HttpClientBuilder.create().build();
        HttpPost post = new HttpPost(postUrl);
        StringEntity postingString = new StringEntity(gson.toJson(ordemProducao));
        post.setEntity(postingString);
        post.setHeader("Content-Type","application/json");
        HttpResponse response = httpClient.execute(post);

       // String command = "curl -H \"Content-Type: application/json\" -d '{\"produto\":\""+ordemProducao.getProduto()+"\", \"quantidade\":\""+ordemProducao.getQuantidade()+"\", \"dataEntrega\":\""+ordemProducao.getDataEntrega()+"\", \"status\":\""+ordemProducao.getStatus()+"\"}' -X POST http://localhost:8080/ordens";
        //Process process = Runtime.getRuntime().exec(command);
    }

    public void atualizarOrdem(OrdemProducao ordemProducao, Long id, String status) throws IOException {
        String putUrl = "http://localhost:8080/ordens/"+id;
        OrdemProducao atualizada = new OrdemProducao();
        atualizada.setId(id);
        atualizada.setStatus(status);
        atualizada.setProduto(ordemProducao.getProduto());
        atualizada.setDataEntrega(ordemProducao.getDataEntrega());
        atualizada.setQuantidade(ordemProducao.getQuantidade());
        Gson gson = new Gson();
        HttpClient httpClient = HttpClientBuilder.create().build();
        HttpPut put = new HttpPut(putUrl);
        StringEntity puttingString = new StringEntity(gson.toJson(atualizada));
        put.setEntity(puttingString);
        put.setHeader("Content-Type","application/json");
        HttpResponse response = httpClient.execute(put);
    }

}
