# CoopVote
Backend para aplicativo de cooperativismo. No cooperativismo, cada associado possui um voto e as decisões são tomadas em assembleias, por votação.

Obs:A tarefa bonus 1 não pode ser feita, pois o endpoint fornecido não estava funcionando.


# Tecnologias utilizadas:
Java 17
,Spring Boot
,MongoDB
,Swagger
,JUnit 


# Como executar
1- Clone este repositório

2- Execute o comando mvn clean package na raiz do projeto para gerar o arquivo JAR  e depois o arquivo JAR com o comando java -jar target/coop-vote-0.0.1-SNAPSHOT.jar

3- Ou rode o projeto por uma IDE.

4- Acesse o Swagger em http://localhost:8080/swagger-ui/#/



# Endpoints principais:

POST api/v1/pautas - cria uma nova pauta com o tempo escolhido

POST api/v1/votos - registra um novo voto para uma pauta, SIM = true, NÃO = false

POST api/vi/pautas/{id}/resultado - Calcula/mostra o resultado de uma pauta encerrada


# DNS da aplicação na nuvem:

ec2-3-144-165-165.us-east-2.compute.amazonaws.com:8080

Exemplo: ec2-3-144-165-165.us-east-2.compute.amazonaws.com:8080/api/v1/pautas


# Teste de carga: 

Foi feito um teste de carga com Jmeter simulando uma grande quantidade de votos a partir do endpoint "ec2-3-144-165-165.us-east-2.compute.amazonaws.com:8080/api/v1/votos". No body json do Jmeter foi feita uma função que criava um cpf aleatório para assim ser testado um cenário exagerado de 30000 votos em 5 minutos. O resultado foi que 20450 requisições com erro e 9550 com sucesso. Em baixo do pom.xml, também tem o upload do gráfico(teste_de_carga.png) que mostra o tempo de requisição em ms para cada requisição feita.
