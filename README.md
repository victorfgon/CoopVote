# CoopVote
Backend para aplicativo de cooperativismo. No cooperativismo, cada associado possui um voto e as decisões são tomadas em assembleias, por votação.

Tecnologias utilizadas
Java 17
Spring Boot
MongoDB
Swagger
JUnit 

Acesse o Swagger em http://localhost:8080/swagger-ui/#/

Endpoints principais:

POST api/v1/pautas - cria uma nova pauta com o tempo escolhido
POST api/v1/votos - registra um novo voto para uma pauta, SIM = true, NÃO = false
POST api/vi/pautas/{id}/resultado - Calcula/mostra o resultado de uma pauta encerrada
