# CoopVote
Backend para aplicativo de cooperativismo. No cooperativismo, cada associado possui um voto e as decisões são tomadas em assembleias, por votação.


# Tecnologias utilizadas:
Java 17
,Spring Boot
,MongoDB
,Swagger
,JUnit 


#Como executar
1- Clone este repositório

2- Execute o comando mvn clean package na raiz do projeto para gerar o arquivo JAR  e depois o arquivo JAR com o comando java -jar target/coop-vote-0.0.1-SNAPSHOT.jar

3- Ou rode o projeto por uma IDE.

4- Acesse o Swagger em http://localhost:8080/swagger-ui/#/



#Endpoints principais:

POST api/v1/pautas - cria uma nova pauta com o tempo escolhido

POST api/v1/votos - registra um novo voto para uma pauta, SIM = true, NÃO = false

POST api/vi/pautas/{id}/resultado - Calcula/mostra o resultado de uma pauta encerrada
