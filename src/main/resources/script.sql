DROP TABLE IF EXISTS CLIENTE;
DROP TABLE IF EXISTS CUPOM_DESCONTO;


CREATE TABLE CLIENTE(
    ID BIGINT NOT NULL AUTO_INCREMENT,
    NOME VARCHAR (60) NOT NULL,
    EMAIL VARCHAR(100) NOT NULL,
    CPF VARCHAR(11) NOT NULL,
    CELULAR VARCHAR(13) NOT NULL,
    ANIVERSARIO DATE,
    KEY_DEVICE VARCHAR(255)
);

CREATE TABLE ENDERECO(
    ID BIGINT NOT NULL AUTO_INCREMENT,
    CEP VARCHAR(8) NOT NULL,
    LOGRADOURO VARCHAR(80) NOT NULL,
    NUMERO VARCHAR(10) NOT NULL,
    COMPLEMENTO VARCHAR(60),
    BAIRRO VARCHAR(60) NOT NULL,
    CIDADE VARCHAR(60) NOT NULL,
    UF VARCHAR(2) NOT NULL
);

 
CREATE TABLE TIPO_PRODUTO(
    ID BIGINT NOT NULL AUTO_INCREMENT,
    DESCRICAO VARCHAR (80) NOT NULL
);

CREATE TABLE PRODUTO(
    ID BIGINT NOT NULL AUTO_INCREMENT,
    ID_TIPO_PRODUTO BIGINT NOT NULL,
    DESCRICAO VARCHAR (80) NOT NULL,
    PESO DECIMAL(5,2)
);


CREATE TABLE CUPOM_DESCONTO(
    ID BIGINT NOT NULL AUTO_INCREMENT,
    DESCRICAO VARCHAR(50) NOT NULL,
    HASH VARCHAR(8) NOT NULL,
    DATA_INICIO DATE NOT NULL,
    DATA_FIM DATE NOT NULL,
    PROMOCAO NUMERIC(1) NOT NULL,
    PORCENTAGEM DECIMAL(3,2) NOT NULL
);

CREATE TABLE TABELA_PRECO(
    ID BIGINT NOT NULL AUTO_INCREMENT,
    ID_PRODUTO BIGINT NOT NULL,
    DATA_VIGENCIA DATE NOT NULL,
    PRECO DECIMAL(6,2) NOT NULL,
    DESCRICAO VARCHAR(50) NOT NULL

);

CREATE TABLE FORNECEDOR(
    ID BIGINT NOT NULL AUTO_INCREMENT,
    NOME VARCHAR (60) NOT NULL,
    EMAIL VARCHAR(100) NOT NULL,
    CNPJ VARCHAR(14),
    ENDERECO VARCHAR(200) NOT NULL,
    TELEFONE_PRINCIPAL VARCHAR(13) NOT NULL,
    TELEFONE_SECUNDARIO VARCHAR(13)
);


CREATE TABLE ESTOQUE(
    ID BIGINT NOT NULL AUTO_INCREMENT,
    ID_PRODUTO BIGINT NOT NULL,
    ID_FORNECEDOR BIGINT NOT NULL,
    DATA_COMPRA DATE NOT NULL,
    DATA_ATUALIZACAO NOT NULL,
    PRECO_COMPRA NOT NULL DECIMAL(6,2),
    INVALIDO NUMERIC(1),
    OBSERVACAO VARCHAR(60)
);

CREATE TABLE CARRINHO(
    ID BIGINT NOT NULL AUTO_INCREMENT,
    ID_CLIENTE BIGINT,
    DEVICE_KEY VARCHAR(255),
    DATA_CRIACAO DATE NOT NULL
);

CREATE TABLE ITEM_CARRINHO(
    ID BIGINT NOT NULL AUTO_INCREMENT,
    ID_ESTOQUE BIGINT NOT NULL,
    ID_CARRINHO BIGINT NOT NULL
);

CREATE TABLE PEDIDO(
    ID BIGINT NOT NULL AUTO_INCREMENT,
    CODIGO_PEDIDO BIGINT,
    DATA DATE NOT NULL,
    ID_CLIENTE BIGINT NOT NULL,
    ID_ENDERECO BIGINT NOT NULL,
    ID_CUPOM BIGINT,
    VALOR_PRODUTOS DECIMAL(7,2) NOT NULL,
    VALOR_FRETE DECIMAL(6,2) NOT NULL,
    VALOR_DESCONTO DECIMAL(6,2) NOT NULL,
    VALOR_TOTAL DECIMAL(7,2) NOT NULL
);

CREATE TABLE ITEM_PEDIDO(
    ID BIGINT NOT NULL AUTO_INCREMENT,
    ID_PEDIDO BIGINT NOT NULL,
    ID_PRODUTO BIGINT NOT NULL,
    VALOR DECIMAL(6,2) NOT NULL
);

CREATE TABLE BAIXA_ESTOQUE(
    ID BIGINT NOT NULL AUTO_INCREMENT,
    ID_ESTOQUE BIGINT NOT NULL,
    ID_ITEM_PEDIDO BIGINT NOT NULL,

);

CREATE TABLE ANEXO_PRODUTO(
    ID BIGINT NOT NULL AUTO_INCREMENT,
    PATH_HI VARCHAR(200) NOT NULL,
    PATH_PREVIEW VARCHAR(200) NOT NULL,
    PATH_LOW VARCHAR(200) NOT NULL,
    ID_PRODUTO BIGINT NOT NULL,
    ID_CLIENTE BIGINT NOT NULL

);