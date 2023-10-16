package com.example.fabrica;

import com.example.fabrica.models.OrdemProducao;
import com.example.fabrica.services.JsonService;
import com.example.fabrica.services.OrdemProducaoService;
import com.example.fabrica.utils.ValidaData;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Objects;
import java.util.Scanner;

@SpringBootApplication
public class FabricaApplication {

	public static void main(String[] args) throws IOException {
		SpringApplication.run(FabricaApplication.class, args);
		menuPrincipal();
	}

	public static void menuPrincipal() throws IOException {
			Scanner scanner = new Scanner(System.in);
			String opcao;
			System.out.println("--MENU FABRICA --");
			System.out.println("1 Nova Ordem Produção");
			System.out.println("2 Listar Todas as Ordens");
			System.out.println("3 Atualizar Status");
			System.out.println("4 Relatorios");
			System.out.print("Digite uma opção -- >");
			opcao = scanner.nextLine();
				if (Objects.equals(opcao, "1")) {
					novaOrdem();
				} else if (Objects.equals(opcao, "2")) {
					listarOrdens();
				} else if (Objects.equals(opcao, "3")) {
					atualizarOrdens();
				} else if (Objects.equals(opcao, "4")) {
					relatorios();
				}else {
					menuPrincipal();
				}
	}

	public static void novaOrdem() throws IOException {
		Scanner scanner = new Scanner(System.in);
		OrdemProducao ordemProducao = new OrdemProducao();
		JsonService jsonService = new JsonService();
		String dataentrega;
		System.out.println("-- NOVA ORDEM --");
		System.out.print("PRODUTO --> ");
		String produto = scanner.nextLine();
		String quantidade;

		    do {
				System.out.print("QUANTIDADE --> ");
				quantidade = scanner.next();
				if (quantidade.matches("[0-9]+$")) {
					ordemProducao.setQuantidade(Integer.parseInt(quantidade));
				} else {
					System.out.println("dados invalidos");
				}
			}while(!quantidade.matches("[0-9]+$"));

			do {
				System.out.print("DATA DE ENTREGA --> Formato dd/mm/aaaa --> ");
				dataentrega = scanner.next();
				if (ValidaData.isValidDate(dataentrega, true)) {
					ordemProducao.setDataEntrega(dataentrega);
				} else {
					System.out.println("Data Invalida");
				}
			}while(!ValidaData.isValidDate(dataentrega, true));
			ordemProducao.setProduto(produto);
			jsonService.createOrdem(ordemProducao);
		   System.out.print("deseja salvar nova ordem? 1 Sim , 2 - Nao- Voltar ao menu principal");
		   String newopcao;
		   do {
			   System.out.print("--> ");

				   newopcao = scanner.next();
				   if (Objects.equals(newopcao, "1")) {
					   novaOrdem();
				   }else if (Objects.equals(newopcao, "2")) {
					   menuPrincipal();
				   }else{
					   System.out.print("Opcao Invalida tente novamente");
				   }

		   }while(!Objects.equals(newopcao, "1") && !Objects.equals(newopcao, "2"));
	}

	public static void listarOrdens() throws IOException {

		JsonService jsonService = new JsonService();
		jsonService.ordens();
		menuPrincipal();
	}

	public static void atualizarOrdens() throws IOException {
		System.out.println("Digite a ordem a ser atualizada -->");
		Scanner scanner = new Scanner(System.in);
		JsonService jsonService = new JsonService();
		OrdemProducao ordemProducao = new OrdemProducao();
		Long ordem = 0L;
		try {
			ordem = scanner.nextLong();
			ordemProducao = jsonService.findByOrdem(ordem);
		}catch(InputMismatchException e){
			atualizarOrdens();
		}
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
		System.out.println("RELATORIOS");
		System.out.println("1 - Andamento");
		System.out.println("2 - Concluido");
		System.out.println("0 - Menu Principal");
			String opcao = scanner.nextLine();
			if(Objects.equals(opcao, "1")){
				jsonService.findByStatus("Andamento");
				relatorios();
			}else if(Objects.equals(opcao, "2")){
				jsonService.findByStatus("Concluida");
				relatorios();
			}else if(Objects.equals(opcao, "0")){
				menuPrincipal();
			}else{
			System.out.println("Opcao Invalida");
			relatorios();
			}
	}

}
