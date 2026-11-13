Aplicação de Processamento de Arquivos e Análise de dados de excesso de velocidade
Sobre o projeto

Esta aplicação permite o envio de arquivos para processamento e análise de dados.

O sistema é dividido em duas partes:

Backend: desenvolvido em Java com Spring Boot, responsável por receber os arquivos, processar os dados e retornar os resultados.
Frontend: desenvolvido em Angular, responsável pela interface do usuário e pela visualização dos resultados.
Como funciona
Ao acessar a aplicação, o usuário encontra uma página inicial de upload.
O usuário seleciona e envia um arquivo.
O backend Spring Boot recebe o arquivo e realiza o processamento.
Após finalizar o processamento, o backend retorna os resultados para o frontend.
O Angular apresenta os resultados em tabelas para facilitar a visualização.
Requisitos

Antes de executar a aplicação, certifique-se de ter instalado:

Java (versão compatível com o projeto).
Maven (caso não utilize o Maven Wrapper).
Node.js e NPM.
Angular CLI.
Executando o Backend (Spring Boot)
Acesse a pasta do backend:
bash
cd backend

Execute a aplicação utilizando o Maven Wrapper:

Linux / macOS:

bash
./mvnw spring-boot:run


Windows:

bash
mvnw.cmd spring-boot:run


Alternativamente, caso tenha o Maven instalado:

bash
mvn spring-boot:run


Após iniciar, o backend estará disponível no endereço configurado no projeto (por padrão, http://localhost:8080).

Executando o Frontend (Angular)
Abra outro terminal e acesse a pasta do frontend:
bash
cd ranking-velocidade-client

Instale as dependências:
bash
npm install

Inicie a aplicação:
bash
npm run start


Após iniciar, acesse:

http://localhost:4200

Utilizando a aplicação
Acesse a aplicação pelo navegador.
Na página inicial, selecione o arquivo que deseja processar.
Clique no botão de upload para enviar o arquivo.
Aguarde o processamento realizado pelo backend.
Após a conclusão, você será direcionado para a página de análises, onde poderá visualizar os resultados em tabelas.
Observações
O backend deve estar em execução para que o frontend consiga enviar os arquivos e receber os resultados.
Verifique se a URL configurada no frontend corresponde ao endereço em que o backend está sendo executado.
Caso esteja executando os projetos localmente, certifique-se de que a configuração de CORS do backend permite requisições originadas do frontend.