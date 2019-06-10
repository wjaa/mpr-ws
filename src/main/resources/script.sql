/*

  NAO ESQUECER DE FAZER O PROCEDIMENTO ABAIXO QUANDO FOR UM BANCO NOVO.
  O MYSQL POR PADRAO VEM COM AS TABELAS CASE SENSITIVE, PRECISAMOS FAZER O PROCEDIMENTO ABAIXO PARA ELE
  ACEITAR QUERIES COM TABELAS EM MINUSCULO.

  ****ESSE PROCEDIMENTO TEM QUE SER FEITO ANTES DE CRIAR AS TABELAS. PORQUE O MYSQL CRIARÁ ELAS COM NOME EM MINUSCULO

  1. Locate file at /etc/mysql/mysql.conf.d/mysqld.cnf

  2. Edit the file by adding the following lines:

    [mysqld]
    lower_case_table_names=1

  3. sudo /etc/init.d/mysql restart


  ########################################################
  Procedimento para liberar o acesso remoto no my mysql

  1. Criando uma senha de root

    docker exec -it mysql_db_1 mysql -uroot -p

    CREATE USER 'root'@'%' IDENTIFIED BY '753951';
    GRANT ALL PRIVILEGES ON *.* TO 'root'@'%';

  2. Abrir o arquivo /etc/mysql/mysql.conf.d/mysqld.cnf

  3. Comentar as linhas
        #bind-address           = 127.0.0.1
        #skip-external-locking
  #########################################################

*/

DROP DATABASE mpr;
CREATE DATABASE mpr;
use mpr;

CREATE TABLE CLIENTE(
    ID INT(28) NOT NULL AUTO_INCREMENT,
    NOME VARCHAR (60) NOT NULL,
    EMAIL VARCHAR(100) NOT NULL,
    CPF VARCHAR(11) NOT NULL,
    CELULAR VARCHAR(13) NOT NULL,
    ANIVERSARIO DATE,
    GENERO CHAR(1),
    ATIVO NUMERIC(1) NOT NULL,
    ID_LOGIN INT(28) NOT NULL,
    FOREIGN KEY (ID_LOGIN)
             REFERENCES LOGIN(ID)
                  ON DELETE CASCADE,
    PRIMARY KEY (ID)
);


CREATE TABLE LOGIN(
    ID INT(28) NOT NULL AUTO_INCREMENT,
    DATA_CRIACAO DATE NOT NULL,
    DATA_ULTIMO_ACESSO DATE NOT NULL,
    SENHA VARCHAR(64),
    SOCIAL_KEY VARCHAR(64),
    LOGIN_TYPE INT(1) NOT NULL,
    URL_FOTO VARCHAR(1000),
    KEY_DEVICE_GCM VARCHAR(255),
    HASH_TROCA_SENHA VARCHAR(64),
    DATA_EXPIRATION_TROCA_SENHA DATE,
    PRIMARY KEY(ID)

);


CREATE TABLE ENDERECO(
    ID INT(28) NOT NULL AUTO_INCREMENT,
    ID_CLIENTE INT(28) NOT NULL,
    CEP VARCHAR(8) NOT NULL,
    LOGRADOURO VARCHAR(80) NOT NULL,
    NUMERO VARCHAR(10) NOT NULL,
    COMPLEMENTO VARCHAR(60),
    BAIRRO VARCHAR(60) NOT NULL,
    CIDADE VARCHAR(60) NOT NULL,
    UF VARCHAR(2) NOT NULL,
    ATIVO NUMERIC(1) NOT NULL,
    DESCRICAO VARCHAR(50) NOT NULL,
    OBSERVACAO VARCHAR(150),
    PRINCIPAL NUMERIC(1) NOT NULL,
    FOREIGN KEY (ID_CLIENTE)
          REFERENCES CLIENTE(ID)
          ON DELETE CASCADE,
    PRIMARY KEY (ID)
);


CREATE TABLE TIPO_PRODUTO(
    ID INT(28) NOT NULL AUTO_INCREMENT,
    DESCRICAO VARCHAR (80) NOT NULL,
    ACESSORIO INT(1) NOT NULL,
    PRIMARY KEY (ID)
);

CREATE TABLE PRODUTO(
    ID INT(28) NOT NULL AUTO_INCREMENT,
    ID_TIPO_PRODUTO INT(28) NOT NULL,
    DESCRICAO VARCHAR (80) NOT NULL,
    DESCRICAO_DETALHADA VARCHAR(1000) NOT NULL,
    REFERENCIA VARCHAR (50) NOT NULL,
    PESO DECIMAL(5,2),
    IMG_PREVIEW VARCHAR(100) NOT NULL,
    IMG_DESTAQUE VARCHAR(100) NOT NULL,
    NOME_COR VARCHAR(50),
    HEXA_COR VARCHAR(7),
    ESTOQUE_MINIMO INT(10) NOT NULL,
    ATIVO INT(1) NOT NULL,
    COMP DECIMAL(5,2) NOT NULL,
    LARG DECIMAL(5,2) NOT NULL,
    ALT DECIMAL(5,2) NOT NULL,
    LANCAMENTO INT(1),
    POPULAR INT(1),
    DESTAQUE INT(1),
    FOREIGN KEY (ID_TIPO_PRODUTO)
          REFERENCES TIPO_PRODUTO(ID),
    PRIMARY KEY (ID)
);

CREATE TABLE PRODUTO_IMAGEM_DESTAQUE(
    ID INT(28) NOT NULL AUTO_INCREMENT,
    ID_PRODUTO INT (28) NOT NULL,
    IMG VARCHAR(100) NOT NULL,
    FOREIGN KEY (ID_PRODUTO)
              REFERENCES PRODUTO(ID),
    PRIMARY KEY (ID)
);


CREATE TABLE CUPOM_DESCONTO(
    ID INT(28) NOT NULL AUTO_INCREMENT,
    DESCRICAO VARCHAR(50) NOT NULL,
    HASH VARCHAR(8) NOT NULL,
    DATA_INICIO DATE NOT NULL,
    DATA_FIM DATE NOT NULL,
    PROMOCAO NUMERIC(1) NOT NULL,
    PORCENTAGEM DECIMAL(4,2) NOT NULL,
    QUANTIDADE INT(10) NOT NULL,
    PRIMARY KEY (ID)
);

CREATE TABLE TABELA_PRECO(
    ID INT(28) NOT NULL AUTO_INCREMENT,
    ID_PRODUTO INT(28) NOT NULL,
    DATA_VIGENCIA DATE NOT NULL,
    PRECO DECIMAL(6,2) NOT NULL,
    DESCRICAO VARCHAR(50) NOT NULL,
    FOREIGN KEY (ID_PRODUTO)
          REFERENCES PRODUTO(ID),
    PRIMARY KEY (ID)

);

CREATE TABLE FORNECEDOR(
    ID INT(28) NOT NULL AUTO_INCREMENT,
    NOME VARCHAR (60) NOT NULL,
    EMAIL VARCHAR(100) NOT NULL,
    CNPJ VARCHAR(14),
    ENDERECO VARCHAR(200) NOT NULL,
    TELEFONE_PRINCIPAL VARCHAR(13) NOT NULL,
    TELEFONE_SECUNDARIO VARCHAR(13),
    ATIVO NUMERIC(1) NOT NULL,
    OBSERVACAO VARCHAR(255)
    PRIMARY KEY (ID)
);


CREATE TABLE ESTOQUE(
    ID INT(28) NOT NULL AUTO_INCREMENT,
    ID_FORNECEDOR INT(28) NOT NULL,
    DATA_COMPRA DATE NOT NULL,
    DATA_ATUALIZACAO DATE NOT NULL,
    PRECO_COMPRA DECIMAL(6,2) NOT NULL ,
    QUANTIDADE INT(10) NOT NULL,
    OBSERVACAO VARCHAR(1000),
    FOREIGN KEY (ID_PRODUTO)
          REFERENCES PRODUTO(ID),
    FOREIGN KEY (ID_FORNECEDOR)
          REFERENCES FORNECEDOR(ID),
    PRIMARY KEY (ID)
);


CREATE TABLE ESTOQUE_ITEM(
    ID INT(28) NOT NULL AUTO_INCREMENT,
    ID_ESTOQUE INT(28) NOT NULL,
    ID_PRODUTO INT(28) NOT NULL,
    INVALIDO NUMERIC(1),
    FOREIGN KEY (ID_PRODUTO)
              REFERENCES PRODUTO(ID),
    FOREIGN KEY (ID_ESTOQUE)
              REFERENCES ESTOQUE(ID),
     PRIMARY KEY (ID)
);


CREATE TABLE carrinho(
    ID INT(28) NOT NULL AUTO_INCREMENT,
    ID_CLIENTE INT(28),
    KEY_DEVICE VARCHAR(255),
    DATA_CRIACAO DATETIME NOT NULL,
    FOREIGN KEY (ID_CLIENTE)
          REFERENCES CLIENTE(ID),
    PRIMARY KEY (ID)
);

CREATE TABLE item_carrinho(
    ID INT(28) NOT NULL AUTO_INCREMENT,
    ID_ESTOQUE_ITEM INT(28) NOT NULL UNIQUE,
    ID_CARRINHO INT(28) NOT NULL,
    FOREIGN KEY (ID_ESTOQUE_ITEM)
          REFERENCES estoque_item(ID)
          ON DELETE CASCADE,
    FOREIGN KEY (ID_CARRINHO)
          REFERENCES carrinho(ID)
          ON DELETE CASCADE,
    PRIMARY KEY (ID)
);


CREATE TABLE item_carrinho_anexo(
    ID INT(28) NOT NULL AUTO_INCREMENT,
    ID_ITEM_CARRINHO INT(28) NOT NULL UNIQUE,
    FOTO VARCHAR(200),
    ID_CATALOGO INT(28),
    FOREIGN KEY (ID_ITEM_CARRINHO)
          REFERENCES item_carrinho(ID)
          ON DELETE CASCADE,
    FOREIGN KEY (ID_CATALOGO)
          REFERENCES catalogo(ID),
    PRIMARY KEY (ID)
);


CREATE TABLE checkout(
    ID INT(28) NOT NULL AUTO_INCREMENT,
    ID_CARRINHO INT(28) NOT NULL UNIQUE,
    ID_CUPOM INT(28),
    VALOR_PRODUTOS DECIMAL(6,2) NOT NULL,
    VALOR_FRETE DECIMAL(6,2) NOT NULL,
    VALOR_DESCONTO DECIMAL(6,2) NOT NULL,
    VALOR_TOTAL DECIMAL(6,2) NOT NULL,
    ID_ENDERECO INT(28) NOT NULL,
    DIAS_ENTREGA INT(10) NOT NULL,
    FRETE INT(1) NOT NULL,
    TOKEN VARCHAR(64) NOT NULL,
    FOREIGN KEY (ID_CARRINHO)
          REFERENCES carrinho(ID),
    FOREIGN KEY (ID_CUPOM)
          REFERENCES cupom_desconto(ID),
    FOREIGN KEY (ID_ENDERECO)
          REFERENCES endereco(ID),
    PRIMARY KEY (ID)
);

CREATE TABLE checkout_frete(
    ID INT(28) NOT NULL AUTO_INCREMENT,
    ID_CHECKOUT INT(28) NOT NULL,
    FRETE INT(1) NOT NULL,
    VALOR DECIMAL(6,2) NOT NULL,
    DIAS_UTEIS INT(10) NOT NULL,
    PREVISAO_ENTREGA DATE NOT NULL,
    MESSAGE_ERROR VARCHAR(300),
    FOREIGN KEY (ID_CHECKOUT)
          REFERENCES checkout(ID),
    PRIMARY KEY (ID)
);



CREATE TABLE catalogo_grupo(
    ID INT(28) NOT NULL AUTO_INCREMENT,
    NOME VARCHAR(50) NOT NULL,
    PRIMARY KEY (ID)
);

CREATE TABLE catalogo(
    ID INT(28) NOT NULL AUTO_INCREMENT,
    ID_CATALOGO_GRUPO INT(28) NOT NULL,
    DESCRICAO VARCHAR(60) NOT NULL,
    IMG VARCHAR(100) NOT NULL,
    ATIVO NUMERIC(1) NOT NULL,
    FOREIGN KEY (ID_CATALOGO_GRUPO)
              REFERENCES catalogo_grupo(ID)
              ON DELETE CASCADE,
     PRIMARY KEY (ID)
);

CREATE TABLE pedido(
    ID INT(28) NOT NULL AUTO_INCREMENT,
    CODIGO_PEDIDO VARCHAR(16),
    DATA DATETIME NOT NULL,
    ID_CLIENTE INT(28) NOT NULL,
    ID_ENDERECO INT(28) NOT NULL,
    ID_CUPOM INT(28),
    VALOR_PRODUTOS DECIMAL(7,2) NOT NULL,
    VALOR_FRETE DECIMAL(6,2) NOT NULL,
    VALOR_DESCONTO DECIMAL(6,2) NOT NULL,
    VALOR_TOTAL DECIMAL(7,2) NOT NULL,
    TIPO_FRETE INT(1),
    TIPO_PAGAMENTO INT(1) NOT NULL,
    DATA_ENTREGA DATE NOT NULL,
    CODIGO_RASTREIO VARCHAR(64),
    CODIGO_TRANSACAO VARCHAR(64),
    URL_BOLETO VARCHAR(1000),
    FOREIGN KEY (ID_CLIENTE)
          REFERENCES cliente(ID),
    FOREIGN KEY (ID_ENDERECO)
          REFERENCES endereco(ID),
    FOREIGN KEY (ID_CUPOM)
          REFERENCES cupom_desconto(ID),
    PRIMARY KEY (ID)
);

CREATE TABLE item_pedido(
    ID INT(28) NOT NULL AUTO_INCREMENT,
    ID_PEDIDO INT(28) NOT NULL,
    ID_PRODUTO INT(28) NOT NULL,
    ID_ESTOQUE INT(28) NOT NULL,
    VALOR DECIMAL(6,2) NOT NULL,
    FOREIGN KEY (ID_PEDIDO)
          REFERENCES pedido(ID),
    FOREIGN KEY (ID_PRODUTO)
          REFERENCES produto(ID),
    FOREIGN KEY (ID_ESTOQUE)
              REFERENCES estoque(ID),
    PRIMARY KEY (ID)
);

CREATE TABLE item_pedido_anexo(
    ID INT(28) NOT NULL AUTO_INCREMENT,
    ID_ITEM_PEDIDO INT(28) NOT NULL,
    FOTO VARCHAR(200),
    ID_CATALOGO INT(28),
    FOREIGN KEY (ID_ITEM_PEDIDO)
          REFERENCES item_pedido(ID),
    FOREIGN KEY (ID_CATALOGO)
          REFERENCES catalogo(ID),
    PRIMARY KEY (ID)
);

CREATE TABLE historico_pedido(
    ID INT(28) NOT NULL AUTO_INCREMENT,
    ID_PEDIDO INT(28) NOT NULL,
    ID_STATUS_PEDIDO INT(10) NOT NULL,
    DATA TIMESTAMP NOT NULL,
    FOREIGN KEY (ID_PEDIDO)
          REFERENCES pedido(ID),
    FOREIGN KEY (ID_STATUS_PEDIDO)
          REFERENCES status_pedido(ID),
    PRIMARY KEY (ID)
);

CREATE TABLE status_pedido(
    ID INT(10) NOT NULL AUTO_INCREMENT,
    NOME VARCHAR(40) NOT NULL,
    NOME_CLIENTE VARCHAR(40) NOT NULL,
    SYS_CODE VARCHAR(4),
    PRIMARY KEY (ID)
);

CREATE TABLE embalagem(
    ID INT(10) NOT NULL AUTO_INCREMENT,
    DESCRICAO VARCHAR(50) NOT NULL,
    COMP DECIMAL(6,2) NOT NULL,
    LARG DECIMAL(6,2) NOT NULL,
    ALT DECIMAL(6,2) NOT NULL,
    ATIVO NUMERIC(1) NOT NULL,
    PRIMARY KEY (ID)
);

CREATE TABLE cep(
    CEP VARCHAR(8) NOT NULL,
    LOGRADOURO VARCHAR(100),
    BAIRRO VARCHAR(100),
    CIDADE VARCHAR(60) NOT NULL,
    UF VARCHAR(2) NOT NULL,
    PRIMARY KEY (CEP)
);

CREATE TABLE frete_cep(
   ID INT (10) NOT NULL AUTO_INCREMENT,
   CEP VARCHAR(8) NOT NULL,
   TIPO_FRETE INT(1) NOT NULL,
   VALOR DECIMAL(6,2) NOT NULL,
   DATA_CALCULO DATE NOT NULL,
   PESO DECIMAL(6,2) NOT NULL,
   DIAS INT(10) NOT NULL,
   PRIMARY KEY (ID)
);

CREATE INDEX idx_cep ON frete_cep(cep);

/******************* SYSTEM TABLES ***************************/
CREATE TABLE USERS(
    USERNAME VARCHAR(64) NOT NULL,
    PASSWORD VARCHAR(64) NOT NULL,
    ENABLED TINYINT NOT NULL DEFAULT 1,
    PRIMARY KEY (USERNAME)
);

CREATE TABLE USER_ROLES(
  USER_ROLE_ID INT(10) NOT NULL AUTO_INCREMENT,
  USERNAME VARCHAR(64) NOT NULL,
  ROLE VARCHAR(64) NOT NULL,
  PRIMARY KEY (USER_ROLE_ID),
  UNIQUE KEY UNI_USERNAME_ROLE (ROLE,USERNAME),
  KEY FK_USERNAME_IDX (USERNAME),
  CONSTRAINT FK_USERNAME FOREIGN KEY (USERNAME) REFERENCES USERS (USERNAME)
);

create table oauth_client_details (
  client_id VARCHAR(255) PRIMARY KEY,
  resource_ids VARCHAR(255),
  client_secret VARCHAR(255),
  scope VARCHAR(255),
  authorized_grant_types VARCHAR(255),
  web_server_redirect_uri VARCHAR(255),
  authorities VARCHAR(255),
  access_token_validity INTEGER,
  refresh_token_validity INTEGER,
  additional_information VARCHAR(4096),
  autoapprove VARCHAR(255)
);

create table oauth_client_token (
  token_id VARCHAR(255),
  token LONG VARBINARY,
  authentication_id VARCHAR(255) PRIMARY KEY,
  user_name VARCHAR(255),
  client_id VARCHAR(255)
);

create table oauth_access_token (
  token_id VARCHAR(255),
  token LONG VARBINARY,
  authentication_id VARCHAR(255) PRIMARY KEY,
  user_name VARCHAR(255),
  client_id VARCHAR(255),
  authentication LONG VARBINARY,
  refresh_token VARCHAR(255)
);

create table oauth_refresh_token (
  token_id VARCHAR(255),
  token LONG VARBINARY,
  authentication LONG VARBINARY
);

create table oauth_code (
  code VARCHAR(255), authentication LONG VARBINARY
);

create table oauth_approvals (
    userId VARCHAR(255),
    clientId VARCHAR(255),
    scope VARCHAR(255),
    status VARCHAR(10),
    expiresAt TIMESTAMP,
    lastModifiedAt TIMESTAMP
);

create table ClientDetails (
  appId VARCHAR(255) PRIMARY KEY,
  resourceIds VARCHAR(255),
  appSecret VARCHAR(255),
  scope VARCHAR(255),
  grantTypes VARCHAR(255),
  redirectUrl VARCHAR(255),
  authorities VARCHAR(255),
  access_token_validity INTEGER,
  refresh_token_validity INTEGER,
  additionalInformation VARCHAR(4096),
  autoApproveScopes VARCHAR(255)
);



/***************************************************************************/

CREATE TABLE mpr_parameter(
    ID INT(10) NOT NULL AUTO_INCREMENT,
    CHAVE VARCHAR(64) NOT NULL,
    VALOR VARCHAR(128) NOT NULL,
    DATA_ATUALIZACAO TIMESTAMP NOT NULL,
    PRIMARY KEY (ID)
);

INSERT INTO USERS (`USERNAME`,`PASSWORD`,`ENABLED`) VALUES ('cliente.admin@meuportaretrato.com','$2a$10$YWkzQyIPEa/0CYn4.3usyuQ956exzjNscX6NlMnjiZL3/dPVPv9v6',1);
INSERT INTO USERS (`USERNAME`,`PASSWORD`,`ENABLED`) VALUES ('cliente.mobile@meuportaretrato.com','$2a$10$ILfyRMdP.nqMvvP3beXjbeFXT42Rsx.AzckTtKi2RIhcWT1jBJe6a',1);

INSERT INTO USER_ROLES (`USER_ROLE_ID`,`USERNAME`,`ROLE`) VALUES (1,'cliente.admin@meuportaretrato.com','ROLE_ADMIN');
INSERT INTO USER_ROLES (`USER_ROLE_ID`,`USERNAME`,`ROLE`) VALUES (2,'cliente.mobile@meuportaretrato.com','ROLE_USER');


insert into status_pedido values (null,'Pedido criado.','Pedido recebido (aguardando pagto).','PECR');
insert into status_pedido values (null,'Aguardando Pagto.','Pedido recebido (aguardando pgto).','AGPG');
insert into status_pedido values (null,'Pagto. confirmado.','Pedido recebido (aguardando pgto).','PGCF');
insert into status_pedido values (null,'Em confeccção.','Confeccionando pedido.','CFPE');
insert into status_pedido values (null,'Confeccionado.','Confeccionando pedido.','CNFC');
insert into status_pedido values (null,'Embalado.','Confeccionando pedido.','PEEB');
insert into status_pedido values (null,'Despachado.','Despachado.','PEDP');
insert into status_pedido values (null,'Em transito.','Despachado.','ETRS');
insert into status_pedido values (null,'Entregue.','Entregue.','ETRG');
insert into status_pedido values (null,'Cancelado.','Pedido cancelado.','CACL');
insert into status_pedido values (null,'Recusado.','Recusado.','RCSD');
insert into status_pedido values (null,'Pagto não confirmado.','Recusado','PGNC');
insert into status_pedido values (null,'Devolução','Pedido cancelado.','DVLC');
insert into status_pedido values (null,'Erro no pedido','Pedido recebido (aguardando pagto).','ERRO');

--EXEMPLO DE ALTER TABLE COM COM CAMPO NOT NULL.
--ALTER TABLE PRODUTO ADD PREVIEW VARCHAR(100) NOT NULL DEFAULT 'AAA.JPG';
--ALTER TABLE PRODUTO ALTER PREVIEW DROP DEFAULT;

--EXEMPLO PARA REMOVER COLUNA
--ALTER TABLE table DROP COLUMN column;