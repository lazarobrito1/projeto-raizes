# Projeto Raízes - Sistema de Back-End para Rede de Lanchonetes

Este projeto consiste no desenvolvimento do back-end para uma rede de lanchonetes, com foco principal na automação do fluxo de pedidos e pagamentos.

## 1. Pré-requisitos

Para executar esta aplicação, você necessita dos seguintes componentes instalados:
* **Linguagem:** Java (Versão 21)
* **Framework Base:** Spring Boot 4.x
* **Banco de Dados:** MySQL Server (Versão 8.0 ou superior)
* **Dependências de Ecossistema (Maven):** 
  * Spring Boot Starter Web (Exposição da API REST)
  * Spring Boot Starter Data JPA (Persistência com Hibernate)
  * Spring Boot Starter Security (Controle de acessos)
  * Auth0 java-jwt (Geração e validação de tokens JWT)
  * Springdoc-openapi-starter-webmvc-ui (Documentação de Contratos)

## 2. Instalação e Configuração

### Configuração de Variáveis de Ambiente
O arquivo `application.properties` original foi mantido oculto por questões de segurança de dados. 
1. Vá até a pasta `src/main/resources/`.
2. Crie um arquivo chamado `application.properties`.
3. Utilize como base o modelo e as chaves disponibilizadas no arquivo público `application.properties.example` preenchendo com suas credenciais locais do MySQL e sua assinatura do JWT.

### Instalação de Dependências
Para baixar as dependências do ecossistema mapeadas no arquivo `pom.xml`, abra o terminal na raiz do projeto e execute o comando:
```bash
./mvnw clean install
```

## 3. Banco de Dados e Migrations

A criação das tabelas e o relacionamento do banco de dados relacional MySQL foram automatizados via Hibernate Provider do Spring Data. 
* **Criação de Tabelas:** Ao inicializar a aplicação, a propriedade `spring.jpa.hibernate.ddl-auto=update` fará a varredura das classes da camada `Entity` e criará a estrutura física do banco de dados de forma automática.
* **Dados Iniciais (Seeds):** A estrutura das tabelas é gerada dinamicamente pelo Spring Data JPA através do Hibernate (`ddl-auto=update`). Contudo, para que o sistema possua registros para testes e validação dos cenários, é necessário alimentar o banco de dados manualmente.
Execute as queries SQL abaixo no seu gerenciador de banco de dados (ex: MySQL Workbench) para realizar a carga inicial:

```sql
-- 1. Cadastrar as Unidades
INSERT INTO unidade (id_unidade, nome_da_unidade) VALUES 
(1, 'Raízes Nordestinas - Filial'),
(2, 'Raízes Nordestinas - Filial Shopping');

-- 2. Cadastrar os Produtos do Cardápio
INSERT INTO produto (id_produto, nome, preco) VALUES 
(1, 'Buchada de Bode', 35.00),
(2, 'Tapioca com Carne Seca', 15.00);

-- 3. Vincular os Produtos ao Estoque de uma Unidade
INSERT INTO estoque_unidade (id, quantidade, id_produto, id_unidade) VALUES 
(1, 50, 2, 2),
(2, 30, 3, 3);

```

## 4. Tecnologias e Justificativas de Escolha

* **Java 21 e Spring Boot 4:** A escolha do ecossistema Spring foi motivada pela robustez e facilidade de integração entre camadas. O uso do Spring Data JPA acelerou a persistência de dados, enquanto o Spring Security blindou a aplicação.
* **MySQL:** Optou-se por um banco de dados relacional devido à natureza estruturada dos dados de pedidos, clientes e produtos, garantindo consistência e integridade por meio de chaves estrangeiras.
* **JWT (JSON Web Token):** Utilizado para a autenticação e autorização dos usuários. Essa escolha garante o controle de acesso baseado em perfis, protegendo os endpoints sensíveis contra acessos não autorizados.

## 5. Detalhes da Implementação e Arquitetura

O projeto foi estruturado seguindo o padrão de divisão de responsabilidades em camadas, garantindo baixo acoplamento, alta coesão e facilidade de manutenção. Abaixo está a descrição detalhada e a justificativa para cada estrutura e pacote do sistema:

* **Entity:** Representa fielmente o modelo de dados e as tabelas do banco de dados relacional. Cada classe mapeia os atributos que serão persistidos pelo Hibernate. É nesta camada que está implementado o campo fundamental de multicanalidade (canalPedido), mapeado via ENUM para identificar e rastrear se a venda originou-se do balcão, do aplicativo ou do totem.
* **DTO (Data Transfer Objects):** Classes criadas especificamente para receber e validar os dados que trafegam nas requisições e respostas da API. O uso de DTOs impede a exposição direta das entidades de banco de dados nos controladores, servindo como uma barreira de segurança e permitindo aplicar validações customizadas antes que os dados alcancem a lógica de negócios.
* **Exceptions:** Centralização do tratamento de erros da aplicação. Esta camada captura exceções de negócio e de sistema, transformando falhas internas em respostas HTTP padronizadas e limpas para o cliente (utilizando um JSON estruturado de erro), evitando o vazamento de stack traces no console do usuário.
* **Repository:** Interface que estende os recursos do Spring Data JPA. Ela abstrai completamente as consultas SQL manuais, fornecendo métodos prontos para operações de CRUD (Criação, Leitura, Atualização e Exclusão) e permitindo a criação de consultas customizadas de forma segura contra SQL Injection.
* **Service:** Concentra toda a lógica e as regras de negócio da lanchonete. É a camada responsável por validar fluxos críticos, tais como a verificação de estoque, o cálculo de totais e a chamada para o mock de pagamento. Os controladores não tomam decisões de negócio; eles apenas delegam as operações para os componentes desta camada.
* **Security:** Agrupa as configurações de segurança do Spring Security e a lógica do JWT (JSON Web Token). Este pacote gerencia a geração, validação e expiração dos tokens de acesso, além de interceptar as requisições para garantir que apenas usuários devidamente autenticados e com perfis autorizados (roles) acessem endpoints sensíveis.
* **Controller (API):** Camada de exposição dos endpoints REST da aplicação. É responsável por receber as requisições HTTP, mapear as rotas, acionar as validações dos DTOs de entrada, delegar o processamento para a camada de Service correspondente e retornar o status HTTP adequado (como 201 Created ou 200 OK).
* **Application (Classe Principal):** Contém a classe que possui o método main e carrega a anotação @SpringBootApplication. Ela funciona como o ponto de partida de todo o sistema, inicializando o servidor embutido Tomcat, varrendo os pacotes em busca de componentes (Component Scan) e subindo o ecossistema do Spring.

## 6. Inicialização da API (Local)

Certifique-se de ter o ambiente Java e o banco de dados MySQL ativos. Você pode iniciar a API de duas formas:

* **Forma 1: Pelo Editor de Código (IDE)**
  Abra o projeto na sua IDE de preferência (IntelliJ IDEA, Eclipse ou VS Code). Localize o arquivo principal da aplicação: `ProjetobackendraizesApplication.java`. Clique com o botão direito sobre o arquivo e selecione a opção **Run** (ou clique no botão de reprodução/seta verde "Play" posicionado na parte superior do editor). A IDE compilará o projeto automaticamente e iniciará o servidor Tomcat embutido.
* **Forma 2: Pelo Terminal**
  Abra o prompt de comando ou terminal na raiz do projeto e execute o comando:
  ```bash
  ./mvnw spring-boot:run
  ```

## 7. Links e Execução de Testes Automatizados

* **Caminho da Coleção de Testes:** O arquivo contendo a suíte de testes automatizados encontra-se disponível na raiz deste repositório com o nome:  
  `Sistema_Pedidos.postman_collection.json'
* **Instruções de Execução:** Toda a validação dos contratos e endpoints expostos pela API foi documentada e testada através do software **Postman**. Para executar os cenários de testes, abra o Postman, clique na opção de importação (*Import*), selecione o arquivo JSON mencionado acima e execute a coleção de requisições contra o servidor local (`http://localhost:8080`).
