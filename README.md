# Application-teste

### Gerenciador de pessoas e endereços, teste para candidatura na empresa

**Sistema para cadastro de pessoas e endereços**

#### Tecnologias utilizadas:

- Java 17
- Spring boot 3
- H2 para registro em memória, conforme solicitado
- Swagger para documentação
- AWS ec2 para deploy
- Docker para conteiner da aplicação

#### Acessando sitema na AWS

**O sistema pode ser acessado no seguintes endereços:**

- <a href="http://ec2-54-211-16-205.compute-1.amazonaws.com:8080/docs"> Documentação Swagger</a>
- <a href="http://ec2-54-211-16-205.compute-1.amazonaws.com:8080/pessoas"> Api Pessoas</a>
- <a href="http://ec2-54-211-16-205.compute-1.amazonaws.com:8080/endereços"> Api Endereços</a>

#### Rodando Sistema em Docker

- **Digite o comando da raiz do projeto:**

  ```docker-compose up```


#### Documentação

**Após iniciar o sistema, a documentação com os endpoints pode ser visualizada no endereço:**
```
http://localhost:8080/docs
```


#### Acesso H2

**Após a inicialização do sistema, o banco de dados em memória do H2 pode ser acessado em:**
```
http://localhost:8080/h2-console
```
**Com os dados**
```
- password: password
- usuario: sa
- url: jdbc:h2:mem:test
```
