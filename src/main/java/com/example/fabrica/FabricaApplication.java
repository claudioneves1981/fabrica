package com.example.fabrica;

import com.example.fabrica.models.OrdemProducao;
import com.example.fabrica.services.JsonService;
import com.example.fabrica.services.OrdemProducaoService;
import com.example.fabrica.utils.ValidaData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

@SpringBootApplication
public class FabricaApplication {

	@Autowired
	private static JsonService jsonService;

	public static void main(String[] args) throws IOException {
		SpringApplication.run(FabricaApplication.class, args);
		menuPrincipal();
	}

	public static void menuPrincipal() throws IOException {
		Scanner scanner = new Scanner(System.in);

		int opcao;

		do{
			System.out.println("--MENU FABRICA --");
			System.out.println("1 Nova Ordem Produção");
			System.out.println("2 Listar Todas as Ordens");
			System.out.println("3 Atualizar Status");
			System.out.println("4 Relatorios");
			System.out.print("Digite uma opção -- >");
			opcao = scanner.nextInt();
			 if(opcao == 1){
				 novaOrdem();
			 }else if(opcao == 2){
				 listarOrdens();
			 }else if(opcao == 3){
				atualizarOrdens();
			 }else if(opcao == 4){
				 relatorios();
			 }
		}while(opcao == 0 || opcao > 4);

	}

	public static void novaOrdem() throws IOException {
		Scanner scanner = new Scanner(System.in);
		OrdemProducao ordemProducao = new OrdemProducao();
		JsonService jsonService = new JsonService();
		int opcao;
		String dataentrega;
		System.out.println("-- NOVA ORDEM --");
		System.out.print("PRODUTO --> ");
		String produto = scanner.nextLine();
		System.out.println("QUANTIDADE --> ");
		int quantidade = scanner.nextInt();
			do {
				System.out.println("DATA DE ENTREGA --> Formato dd/mm/aaaa");
				dataentrega = scanner.next();
				if (ValidaData.isValidDate(dataentrega, true)) {
					ordemProducao.setDataEntrega(dataentrega);
				} else {
					System.out.println("Data Invalida");
				}
			}while(!ValidaData.isValidDate(dataentrega, true));
			ordemProducao.setProduto(produto);
			ordemProducao.setQuantidade(quantidade);
			jsonService.createOrdem(ordemProducao);
		   System.out.println("deseja salvar nova ordem? 1 Sim , 2 - Nao- Voltar ao menu principal");
		   int newopcao;
		   do {
			   System.out.print("--> ");
			   newopcao = scanner.nextInt();
			   if (newopcao == 1) {
				   novaOrdem();
			   } else if (newopcao == 2) {
				   menuPrincipal();
			   }else {
				   System.out.print("Opcao invalida tente novamente");
			   }
		   }while(newopcao > 2);
	}

	public static void listarOrdens() throws IOException {

		JsonService jsonService = new JsonService();
		jsonService.ordens();
		menuPrincipal();
	}

	public static void atualizarOrdens() throws IOException {
		System.out.println("Digite a ordem a ser atualizada -->");
		Scanner scanner = new Scanner(System.in);
		Long ordem = scanner.nextLong();
		JsonService jsonService = new JsonService();
		OrdemProducao ordemProducao = jsonService.findByOrdem(ordem);
		System.out.println("Concluida? 1-Sim, 2-Não");
		System.out.print("-->");
		int opcao;
		do{
			opcao = scanner.nextInt();
			if(opcao == 1){
				jsonService.atualizarOrdem(ordemProducao,ordem,"Concluida");
				jsonService.findByOrdem(ordem);
				menuPrincipal();
			}else if(opcao == 2){
				jsonService.atualizarOrdem(ordemProducao,ordem, "Andamento");
				jsonService.findByOrdem(ordem);
				menuPrincipal();
			}else{
				System.out.println("Opcao Invalida");
			}
		}while(opcao == 0 || opcao > 2);
	}

	public static void relatorios() throws IOException {
		Scanner scanner = new Scanner(System.in);
		OrdemProducaoService ordemProducaoService = new OrdemProducaoService();
        JsonService jsonService = new JsonService();
		System.out.print("RELATORIOS");
		System.out.println("1 - Andamento");
		System.out.println("2 - Concluido");
		System.out.println("0 - Menu Principal");
		int opcao;
		do{
			opcao = scanner.nextInt();
			if(opcao == 1){
				jsonService.findByStatus("Andamento");
				relatorios();
			}else if(opcao == 2){
				jsonService.findByStatus("Concluida");
				relatorios();
			}else if(opcao == 0){
				menuPrincipal();
			}
			System.out.println("Opcao Invalida");
		}while(opcao > 2);


	}

}
