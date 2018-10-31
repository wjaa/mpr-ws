/*TABELAS PARA TESTES DE NEGOCIOS*/


--CLIENTES
insert into CLIENTE (ID, ANIVERSARIO, CELULAR, CPF, EMAIL, NOME, ATIVO, GENERO)
values (null, '1983-09-30', '11999999999', '11111111111', 'wag182@gmail.com', 'Wagner', 1, 'M');
insert into CLIENTE (ID, ANIVERSARIO, CELULAR, CPF, EMAIL, NOME, ATIVO, GENERO)
values (null, '1983-09-30', '11999999999', '22222222222', 'wag183@gmail.com', 'Wagner', 1, 'M');
insert into CLIENTE (ID, ANIVERSARIO, CELULAR, CPF, EMAIL, NOME, ATIVO, GENERO)
values (null, '1983-09-30', '11999999999', '33333333333', 'wag184@gmail.com', 'Wagner', 1, 'M');
insert into CLIENTE (ID, ANIVERSARIO, CELULAR, CPF, EMAIL, NOME, ATIVO, GENERO)
values (null, '1983-09-30', '11999999999', '44444444444', 'wag185@gmail.com', 'CLIENTE REMOVER', 1, 'M');

--ENDERECO DO CLIENTE 1
INSERT INTO ENDERECO (ID,CEP,LOGRADOURO,NUMERO,COMPLEMENTO,BAIRRO,CIDADE,UF,ID_CLIENTE,ATIVO, DESCRICAO,OBSERVACAO,PRINCIPAL)
VALUES(NULL,"07093090","Rua das porpetas malucas","132a","sem complemento","bairro da pizza","guarulhos","sp",1,1,"principal","perto da rua das coxinhas",1);

--FORNECEDORES
insert into FORNECEDOR (ID, NOME, EMAIL, CNPJ, ENDERECO, TELEFONE_PRINCIPAL, TELEFONE_SECUNDARIO, ATIVO)
values (null, 'CASA CASTRO', 'casa@castro.com.br', '11111111111111', 'rua do riacho verde, 4444,centro sao paulo - sp', '111111111111', null, 1);
insert into FORNECEDOR (ID, NOME, EMAIL, CNPJ, ENDERECO, TELEFONE_PRINCIPAL, TELEFONE_SECUNDARIO, ATIVO)
values (null, 'REMOVER', 'casa@castro.com.br', '11111111111111', 'rua do riacho verde, 4444,centro sao paulo - sp', '111111111111', null, 1);

--TIPO DE PRODUTO
insert into TIPO_PRODUTO (ID, DESCRICAO)
VALUES (1,'PORTA RETRATO');

--PRODUTOS
insert into PRODUTO (ID, ID_TIPO_PRODUTO, DESCRICAO,REFERENCIA,PESO,IMG_PREVIEW, ESTOQUE_MINIMO, DESCRICAO_DETALHADA, IMG_DESTAQUE)
values (1, 1,'PORTA RETRATO','xrp2',10.10,'IMG2.JPG', 10, 'TESTES TESTE TESTE TESTE','IMG2.JPG');
insert into PRODUTO (ID, ID_TIPO_PRODUTO, DESCRICAO,REFERENCIA,PESO,IMG_PREVIEW, ESTOQUE_MINIMO, DESCRICAO_DETALHADA,IMG_DESTAQUE)
values (2, 1,'PORTA RETRATO 2','btc1',20.10,'IMG2.JPG',10, 'TESTES TESTE TESTE TESTE','IMG2.JPG');
insert into PRODUTO (ID, ID_TIPO_PRODUTO, DESCRICAO,REFERENCIA,PESO,IMG_PREVIEW, ESTOQUE_MINIMO, DESCRICAO_DETALHADA,IMG_DESTAQUE)
values (3, 1,'PRODUTO TESTE ESTOQUE','TESTE',20.10,'IMG2.JPG',10, 'TESTES TESTE TESTE TESTE','IMG2.JPG');

--TABELA DE PRECO
insert into TABELA_PRECO (ID, ID_PRODUTO, DATA_VIGENCIA, PRECO, DESCRICAO)
values (null, 1, '2018-06-01', '80.50', 'VALOR INICIAL DE PRECO DO PRODUTO 10');
insert into TABELA_PRECO (ID, ID_PRODUTO, DATA_VIGENCIA, PRECO, DESCRICAO)
values (null, 1, '2018-06-15', '85.50', 'VALOR INICIAL DE PRECO DO PRODUTO 20');
insert into TABELA_PRECO (ID, ID_PRODUTO, DATA_VIGENCIA, PRECO, DESCRICAO)
values (null, 1, '2018-06-28', '100.50', 'VALOR INICIAL DE PRECO DO PRODUTO 20');

--ESTOQUE
insert into ESTOQUE (ID,ID_FORNECEDOR,DATA_COMPRA,DATA_ATUALIZACAO,PRECO_COMPRA,OBSERVACAO,QUANTIDADE)
values(1,1,'2018-01-01','2018-01-01',10,'obs',5);

--ITENS DO ESTOQUE
insert into ESTOQUE_ITEM (ID,ID_ESTOQUE,ID_PRODUTO, INVALIDO)
values(1,1,2,0);

insert into ESTOQUE_ITEM (ID,ID_ESTOQUE,ID_PRODUTO, INVALIDO)
values(2,1,2,0);

insert into ESTOQUE_ITEM (ID,ID_ESTOQUE,ID_PRODUTO, INVALIDO)
values(3,1,2,0);

insert into ESTOQUE_ITEM (ID,ID_ESTOQUE,ID_PRODUTO, INVALIDO)
values(4,1,2,0);

insert into ESTOQUE_ITEM (ID,ID_ESTOQUE,ID_PRODUTO, INVALIDO)
values(5,1,2,0);


/*TESTE DE ESTOQUE*/


--items para o teste de listagem de produtos do estoque
insert into ESTOQUE (ID,ID_FORNECEDOR,DATA_COMPRA,DATA_ATUALIZACAO,PRECO_COMPRA,OBSERVACAO,QUANTIDADE)
values(2,1,'2018-01-01','2018-01-01',10,'TESTE_PRODUTO3',5);

insert into ESTOQUE_ITEM (ID,ID_ESTOQUE,ID_PRODUTO, INVALIDO)
values(6,2,3,0);
insert into ESTOQUE_ITEM (ID,ID_ESTOQUE,ID_PRODUTO, INVALIDO)
values(7,2,3,0);
insert into ESTOQUE_ITEM (ID,ID_ESTOQUE,ID_PRODUTO, INVALIDO)
values(8,2,3,0);
insert into ESTOQUE_ITEM (ID,ID_ESTOQUE,ID_PRODUTO, INVALIDO)
values(9,2,3,0);
insert into ESTOQUE_ITEM (ID,ID_ESTOQUE,ID_PRODUTO, INVALIDO)
values(10,2,3,0);


--PEDIDO
INSERT INTO PEDIDO (ID,CODIGO_PEDIDO,DATA,ID_CLIENTE,ID_ENDERECO,ID_CUPOM,VALOR_PRODUTOS,VALOR_FRETE,VALOR_DESCONTO,VALOR_TOTAL)
VALUES (1,123456,'2018-10-23',1,1,null,450,50,10,490);

--ITEMS DO PEDIDO
INSERT INTO ITEM_PEDIDO(ID, ID_PEDIDO,ID_PRODUTO,VALOR)
VALUES (1,1,3,150);
INSERT INTO ITEM_PEDIDO(ID, ID_PEDIDO,ID_PRODUTO,VALOR)
VALUES (2,1,3,150);
INSERT INTO ITEM_PEDIDO(ID, ID_PEDIDO,ID_PRODUTO,VALOR)
VALUES (3,1,3,150);

--BAIXA NO ESTOQUE
insert into BAIXA_ESTOQUE(ID,ID_ESTOQUE_ITEM, ID_ITEM_PEDIDO)
VALUES (NULL,6,1);
insert into BAIXA_ESTOQUE(ID,ID_ESTOQUE_ITEM, ID_ITEM_PEDIDO)
VALUES (NULL,8,2);
insert into BAIXA_ESTOQUE(ID,ID_ESTOQUE_ITEM, ID_ITEM_PEDIDO)
VALUES (NULL,10,3);




--items para o teste de listagem de produtos do estoque
insert into ESTOQUE (ID,ID_FORNECEDOR,DATA_COMPRA,DATA_ATUALIZACAO,PRECO_COMPRA,OBSERVACAO,QUANTIDADE)
values(3,1,'2018-01-01','2018-01-01',10,'TESTE_OUTRO LOTE PRODUTO3',5);

insert into ESTOQUE_ITEM (ID,ID_ESTOQUE,ID_PRODUTO, INVALIDO)
values(11,3,3,0);
insert into ESTOQUE_ITEM (ID,ID_ESTOQUE,ID_PRODUTO, INVALIDO)
values(12,3,3,0);
insert into ESTOQUE_ITEM (ID,ID_ESTOQUE,ID_PRODUTO, INVALIDO)
values(13,3,3,0);
insert into ESTOQUE_ITEM (ID,ID_ESTOQUE,ID_PRODUTO, INVALIDO)
values(14,3,3,0);
insert into ESTOQUE_ITEM (ID,ID_ESTOQUE,ID_PRODUTO, INVALIDO)
values(15,3,3,0);


--PEDIDO
INSERT INTO PEDIDO (ID,CODIGO_PEDIDO,DATA,ID_CLIENTE,ID_ENDERECO,ID_CUPOM,VALOR_PRODUTOS,VALOR_FRETE,VALOR_DESCONTO,VALOR_TOTAL)
VALUES (2,123456,'2018-10-23',1,1,null,450,50,10,490);

--ITEMS DO PEDIDO
INSERT INTO ITEM_PEDIDO(ID, ID_PEDIDO,ID_PRODUTO,VALOR)
VALUES (1,2,3,150);
INSERT INTO ITEM_PEDIDO(ID, ID_PEDIDO,ID_PRODUTO,VALOR)
VALUES (2,2,3,150);

--BAIXA NO ESTOQUE
insert into BAIXA_ESTOQUE(ID,ID_ESTOQUE_ITEM, ID_ITEM_PEDIDO)
VALUES (NULL,11,1);
insert into BAIXA_ESTOQUE(ID,ID_ESTOQUE_ITEM, ID_ITEM_PEDIDO)
VALUES (NULL,12,2);



/**********************************************************************************************************************/

/*TABELAS PARA TESTES DE SISTEMA*/


--TABELA PARA TESTES DE DADOS NO DAO
insert into TABLE_TEST (ID, TEST_VARCHAR, TEST_BOOLEAN, TEST_DOUBLE, TEST_INT, TEST_LONG, TEST_DATE, TEST_DATETIME)
values (null, '123456789012345678901234512345678901234567890123451234567890123456789012345123456789012345678901234512345678901234567890123451234567890123456789012345123456789012345678901234512345678901234567890123451234567890123456789012345123456789012345678901234567890',
 1, 123456.12, 1234567890,12345678901234,'1983-09-30','1983-09-30 19:10:02');

insert into TABLE_TEST (ID, TEST_VARCHAR, TEST_BOOLEAN, TEST_DOUBLE, TEST_INT, TEST_LONG, TEST_DATE, TEST_DATETIME)
values (null, 'aaaaa', 0, 123456.12, 1234567890,12345678901234,'1983-09-30','1983-09-30 19:10:02');

insert into TABLE_TEST (ID, TEST_VARCHAR, TEST_BOOLEAN, TEST_DOUBLE, TEST_INT, TEST_LONG, TEST_DATE, TEST_DATETIME)
values (null, 'REMOVER', 0, 123456.12, 1234567890,12345678901234,'1983-09-30','1983-09-30 19:10:02');

insert into TABLE_TEST (ID, TEST_VARCHAR, TEST_BOOLEAN, TEST_DOUBLE, TEST_INT, TEST_LONG, TEST_DATE, TEST_DATETIME)
values (null, 'TESTE_UPDATE', 0, 123456.12, 99999999,12345678901234,'1983-09-30','1983-09-30 19:10:02');
insert into TABLE_TEST (ID, TEST_VARCHAR, TEST_BOOLEAN, TEST_DOUBLE, TEST_INT, TEST_LONG, TEST_DATE, TEST_DATETIME)
values (null, 'TESTE_UPDATE', 0, 123456.12, 99999999,12345678901234,'1983-09-30','1983-09-30 19:10:02');


--TABELA PARA USUARIO DO SISTEMA (TABELA DE SISTEMA)
CREATE TABLE USERS(
    USERNAME VARCHAR(64) NOT NULL,
    PASSWORD VARCHAR(64) NOT NULL,
    ENABLED boolean NOT NULL DEFAULT true,
    PRIMARY KEY (USERNAME)
);

CREATE TABLE USER_ROLES(
  USER_ROLE_ID bigint generated by default as identity,
  USERNAME VARCHAR(64) NOT NULL,
  ROLE VARCHAR(64) NOT NULL,
  PRIMARY KEY (USER_ROLE_ID),
);

INSERT INTO users(username,password,enabled)
VALUES ('user','$2a$10$8recqP62m2fobIBsNzGRZutoK3t0z7JWDylBunWuHW5ItjmKSWjN.', true);
INSERT INTO user_roles (username, role)
VALUES ('user', 'ROLE_ADMIN');

/**********************************************************************************************************************/