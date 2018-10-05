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
    PRIMARY KEY (ID)
);

CREATE TABLE PRODUTO(
    ID INT(28) NOT NULL AUTO_INCREMENT,
    ID_TIPO_PRODUTO INT(28) NOT NULL,
    DESCRICAO VARCHAR (80) NOT NULL,
    REFERENCIA VARCHAR (50) NOT NULL,
    PESO DECIMAL(5,2),
    IMG_DESTAQUE VARCHAR(100) NOT NULL,
    IMG_PREVIEW VARCHAR(100) NOT NULL,
    FOREIGN KEY (ID_TIPO_PRODUTO)
          REFERENCES TIPO_PRODUTO(ID),
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
    ID_PRODUTO INT(28) NOT NULL,
    ID_FORNECEDOR INT(28) NOT NULL,
    DATA_COMPRA DATE NOT NULL,
    DATA_ATUALIZACAO DATE NOT NULL,
    PRECO_COMPRA DECIMAL(6,2) NOT NULL ,
    INVALIDO NUMERIC(1),
    OBSERVACAO VARCHAR(60),
    FOREIGN KEY (ID_PRODUTO)
          REFERENCES PRODUTO(ID),
    FOREIGN KEY (ID_FORNECEDOR)
          REFERENCES FORNECEDOR(ID),
    PRIMARY KEY (ID)
);

CREATE TABLE CARRINHO(
    ID INT(28) NOT NULL AUTO_INCREMENT,
    ID_CLIENTE INT(28),
    DEVICE_KEY VARCHAR(255),
    DATA_CRIACAO DATE NOT NULL,
    FOREIGN KEY (ID_CLIENTE)
          REFERENCES CLIENTE(ID),
    PRIMARY KEY (ID)
);

CREATE TABLE ITEM_CARRINHO(
    ID INT(28) NOT NULL AUTO_INCREMENT,
    ID_ESTOQUE INT(28) NOT NULL,
    ID_CARRINHO INT(28) NOT NULL,
    FOREIGN KEY (ID_ESTOQUE)
          REFERENCES ESTOQUE(ID),
    FOREIGN KEY (ID_CARRINHO)
          REFERENCES CARRINHO(ID)
          ON DELETE CASCADE,
    PRIMARY KEY (ID)
);

CREATE TABLE PEDIDO(
    ID INT(28) NOT NULL AUTO_INCREMENT,
    CODIGO_PEDIDO INT(28),
    DATA DATE NOT NULL,
    ID_CLIENTE INT(28) NOT NULL,
    ID_ENDERECO INT(28) NOT NULL,
    ID_CUPOM INT(28),
    VALOR_PRODUTOS DECIMAL(7,2) NOT NULL,
    VALOR_FRETE DECIMAL(6,2) NOT NULL,
    VALOR_DESCONTO DECIMAL(6,2) NOT NULL,
    VALOR_TOTAL DECIMAL(7,2) NOT NULL,
    FOREIGN KEY (ID_CLIENTE)
          REFERENCES CLIENTE(ID),
    FOREIGN KEY (ID_ENDERECO)
          REFERENCES ENDERECO(ID),
    FOREIGN KEY (ID_CUPOM)
          REFERENCES CUPOM_DESCONTO(ID),
    PRIMARY KEY (ID)
);

CREATE TABLE ITEM_PEDIDO(
    ID INT(28) NOT NULL AUTO_INCREMENT,
    ID_PEDIDO INT(28) NOT NULL,
    ID_PRODUTO INT(28) NOT NULL,
    VALOR DECIMAL(6,2) NOT NULL,
    FOREIGN KEY (ID_PEDIDO)
          REFERENCES PEDIDO(ID),
    FOREIGN KEY (ID_PRODUTO)
          REFERENCES PRODUTO(ID),
    PRIMARY KEY (ID)
);

CREATE TABLE BAIXA_ESTOQUE(
    ID INT(28) NOT NULL AUTO_INCREMENT,
    ID_ESTOQUE INT(28) NOT NULL,
    ID_ITEM_PEDIDO INT(28) NOT NULL,
    FOREIGN KEY (ID_ESTOQUE)
          REFERENCES ESTOQUE(ID),
    FOREIGN KEY (ID_ITEM_PEDIDO)
          REFERENCES ITEM_PEDIDO(ID),
    PRIMARY KEY (ID)
);


/*SYSTEM TABLES*/
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

INSERT INTO USERS (`USERNAME`,`PASSWORD`,`ENABLED`) VALUES ('cliente.admin@meuportaretrato.com','$2a$10$YWkzQyIPEa/0CYn4.3usyuQ956exzjNscX6NlMnjiZL3/dPVPv9v6',1);
INSERT INTO USERS (`USERNAME`,`PASSWORD`,`ENABLED`) VALUES ('cliente.mobile@meuportaretrato.com','$2a$10$ILfyRMdP.nqMvvP3beXjbeFXT42Rsx.AzckTtKi2RIhcWT1jBJe6a',1);

INSERT INTO USER_ROLES (`USER_ROLE_ID`,`USERNAME`,`ROLE`) VALUES (1,'cliente.admin@meuportaretrato.com','ROLE_ADMIN');
INSERT INTO USER_ROLES (`USER_ROLE_ID`,`USERNAME`,`ROLE`) VALUES (2,'cliente.mobile@meuportaretrato.com','ROLE_USER');

--EXEMPLO DE ALTER TABLE COM COM CAMPO NOT NULL.
--ALTER TABLE PRODUTO ADD PREVIEW VARCHAR(100) NOT NULL DEFAULT 'AAA.JPG';
--ALTER TABLE PRODUTO ALTER PREVIEW DROP DEFAULT;