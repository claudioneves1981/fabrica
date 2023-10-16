Para clonar o projeto(com GitHub Cli)
```
gh repo clone claudioneves1981/cadastro
```
Para executar o projeto no terminal, digite o seguinte comando:

```shell script
mvn spring-boot:run 
```

Após executar o comando acima, irá abrir o seguinte menu apos carregar  o spring e visualizará o seguinte menu do projeto:

```
--MENU FABRICA --
1 Nova Ordem Produção
2 Listar Todas as Ordens
3 Atualizar Status
4 Relatorios
Digite uma opção -- >1
```

O primeiro menu é o de nova ordem 

```
`-- NOVA ORDEM --
PRODUTO --> copo
QUANTIDADE --> 40
DATA DE ENTREGA --> Formato dd/mm/aaaa --> 23/12/2022
```
Se tudo ocorrer corretamente vira uma mensagem logo em seguida.

```
Hibernate: insert into ordem_producao (data_entrega,produto,quantidade,status,id_produto) values (?,?,?,?,default)
Salvo com sucesso
deseja salvar nova ordem? 1 Sim , 2 - Nao- Voltar ao menu principal--> 2
```
Caso não aparecerá a seguinte mensagem.

```
Não é possivel produzir por falta de materiais
deseja salvar nova ordem? 1 Sim , 2 - Nao- Voltar ao menu principal--> 1

```

o segunda opcão é listar todas as ordens
```
--MENU FABRICA --
1 Nova Ordem Produção
2 Listar Todas as Ordens
3 Atualizar Status
4 Relatorios
Digite uma opção -- >2
```

Se tudo ocorrer corretamente irá exibir a lista de ordens.

```
Hibernate: select o1_0.id_produto,o1_0.data_entrega,o1_0.produto,o1_0.quantidade,o1_0.status from ordem_producao o1_0
[{"id":1,"produto":"copo","quantidade":40,"dataEntrega":"23/12/2022","status":"Andamento"}
{"id":2,"produto":"copo","quantidade":50,"dataEntrega":"23/12/2023","status":"Andamento"}]
```

a opção 3 a atualizar status 

```
--MENU FABRICA --
1 Nova Ordem Produção
2 Listar Todas as Ordens
3 Atualizar Status
4 Relatorios
Digite uma opção -- >3
```

Logo embaixo vai surgir uma outra opcao:

```
Digite a ordem a ser atualizada -->
```

você vai digitar o id da ordem a ser atualizada 
vai aparecer o seguinte tela

```
Hibernate: select o1_0.id_produto,o1_0.data_entrega,o1_0.produto,o1_0.quantidade,o1_0.status from ordem_producao o1_0 where o1_0.id_produto=?
{"id":2,"produto":"copo","quantidade":50,"dataEntrega":"23/12/2023","status":"Andamento"}
Concluida? 1-Sim, 2-Não
```

caso esteja concluida digite a opcao 1 e o status vai se alterar exibindo a informação na tela

```
Hibernate: select o1_0.id_produto,o1_0.data_entrega,o1_0.produto,o1_0.quantidade,o1_0.status from ordem_producao o1_0 where o1_0.id_produto=?
Hibernate: update ordem_producao set data_entrega=?,produto=?,quantidade=?,status=? where id_produto=?
Hibernate: select o1_0.id_produto,o1_0.data_entrega,o1_0.produto,o1_0.quantidade,o1_0.status from ordem_producao o1_0 where o1_0.id_produto=?
{"id":2,"produto":"copo","quantidade":50,"dataEntrega":"23/12/2023","status":"Concluida"}

```

o ultimo menu e de relatorios:

```
--MENU FABRICA --
1 Nova Ordem Produção
2 Listar Todas as Ordens
3 Atualizar Status
4 Relatorios
Digite uma opção -- >4
```

abrira um submenu

```
RELATORIOS
1 - Andamento
2 - Concluido
0 - Menu Principal
```

caso tenha ordens em andamento ira exibir 

```
Hibernate: select o1_0.id_produto,o1_0.data_entrega,o1_0.produto,o1_0.quantidade,o1_0.status from ordem_producao o1_0 where o1_0.status=?
[[{"id":1,"produto":"copo","quantidade":40,"dataEntrega":"23/12/2022","status":"Andamento"}]]
```

caso vc deseja ver os concluidos acesse a opção 2

```
Hibernate: select o1_0.id_produto,o1_0.data_entrega,o1_0.produto,o1_0.quantidade,o1_0.status from ordem_producao o1_0 where o1_0.status=?
[[{"id":2,"produto":"copo","quantidade":50,"dataEntrega":"23/12/2023","status":"Concluida"}]]
```
São necessários os seguintes pré-requisitos para a execução do projeto:

* Java 17 ou versões superiores.
* Maven