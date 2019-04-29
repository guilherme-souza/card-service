# Saque em Cartão Pré Pago

Aplicação desenvolvida que representa o serviço de saque em um cartão pré pago.
A aplicação é composta por um `endpoint` responsável por realizar **saques** em cartões pré pago no sistema e por um `endpoint` responsável por retornar o extrato do cartão fornecido.

Para facilitar o desenvolvimento esta aplicação não foi quebrada de forma a representar um micro-serviço, pois neste caso em minha opinião, deveríamos criar pelo menos um serviço para *saque*, um serviço para *extrato* e um serviço para *configurações*. Neste caso eu trabalharia com o framework Spring Cloud.

Durante o desenvolvimento desta aplicação, mantive o foco de desenvolvimento para ambiente *cloud*, por esta razão, para facilitar a execução da aplicação estou usando um banco *MongoDB embedded*.

## Rodando a Aplicação

Para rodar a aplicação é necessário a instalação de JDK Java 8 e Gradle 5

### Passo a passo
Os comandos abaixo devem ser executados em um shell (prompt de comando).
1. `gradle startMongoDb`
A primeira exeucução pode demorar um pouco pois um plugin do Gradle irá fazer o download do MongoDB embedded.
Resultado:
```
[mongod output] 
> Task :startMongoDb
Mongod started.

BUILD SUCCESSFUL in 20s
1 actionable task: 1 execute
```

2. `gradle bootRun`
Resultado:
```
2019-04-28 21:46:04.024  INFO 14996 --- [           main] c.p.cardservice.CardServiceApplication   : Started CardServiceApplication in 7.732 seconds (JVM running for 8.58)
INICIALIZANDO A BASE
2019-04-28 21:46:04.140  INFO 14996 --- [           main] org.mongodb.driver.connection            : Opened connection [connectionId{localValue:2, serverValue:2}] to localhost:27017



[mongod output] 
> Task :bootRun
BASE INICIALIZADA COM OS DOIS CARTÕES
```
Nesse momento é feita a carga inicial do banco de dados com 2 cartões de números *1234567890* e *9876543210* com saldo de 1000.00.

## Página de Testes
O endpoint para **saques** é uma implementação de um *WebSocket*. Na raíz do projeto há um HTML e JS para auxiliar na execução de testes no endpoint de saque.
Para executar o teste basta abrir o arquivo `index.html`. Na página há um botão para fazer a conexão com o **socket** para saques. 
Após clicar em conectar, há três campos para adicionar a ação, o valor para saque e número do cartão e enviar para o servidor.
A resposta será exibida em uma tabela abaixo dos campos.

O endpoint para os dados de **extrato** é uma implementação de um *RestController* que pode ser acionado no endereço: http://localhost:8080/statements/1234567890 onde 1234567890 é o número do cartão.

## Observações
Há muito espaço para melhoria em relação a testes unitários/integração. Meus próximos passos serão a criação de testes de integração para os controllers e melhoria dos testes unitários de serviço.

Escolhi usar um banco de dados no-sql pensando na performance da aplicação. Com esta decisão em mente, escolhi o MongoDB entre outros bancos não-relacionais por ter a disposição um banco embutido, facilitando a execução. Desta forma, para uma versão de produção, é possível apenas alterar a configuração apontando o banco correto sem a necessidade de alterar a aplicação.
A carga inicial é realizada através de um `ApplicationRunner` do *Spring Boot* na classe `DataLoader.java`. 

Para evitar problemas de concorrência, *race condition*, a operação de saque é feita dentro de um bloco *synchronized*.

Após o uso da aplicação é possível executar o comando `gradle stopMongoDb` para parar a execução do banco de dados