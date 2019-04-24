/***********************************************
    TABELA DE EMBALAGENS
***********************************************/
insert into EMBALAGEM (ID, DESCRICAO, COMP, LARG, ALT, ATIVO)
values (null,'embalagem1',30,25,5,1);

insert into EMBALAGEM (ID, DESCRICAO, COMP, LARG, ALT, ATIVO)
values (null,'embalagem2',30,25,10,1);

insert into EMBALAGEM (ID, DESCRICAO, COMP, LARG, ALT, ATIVO)
values (null,'embalagem3',30,25,15,1);

insert into EMBALAGEM (ID, DESCRICAO, COMP, LARG, ALT, ATIVO)
values (null,'embalagem4',15,10,5,1);

insert into EMBALAGEM (ID, DESCRICAO, COMP, LARG, ALT, ATIVO)
values (null,'embalagem5',15,10,10,1);

insert into EMBALAGEM (ID, DESCRICAO, COMP, LARG, ALT, ATIVO)
values (null,'embalagem6',15,10,15,1);

insert into EMBALAGEM (ID, DESCRICAO, COMP, LARG, ALT, ATIVO)
values (null,'embalagem7',25,20,5,1);

insert into EMBALAGEM (ID, DESCRICAO, COMP, LARG, ALT, ATIVO)
values (null,'embalagem8',25,20,10,1);

insert into EMBALAGEM (ID, DESCRICAO, COMP, LARG, ALT, ATIVO)
values (null,'embalagem9',25,20,15,1);

insert into EMBALAGEM (ID, DESCRICAO, COMP, LARG, ALT, ATIVO)
values (null,'embalagem10',20,15,5,1);

insert into EMBALAGEM (ID, DESCRICAO, COMP, LARG, ALT, ATIVO)
values (null,'embalagem11',20,15,10,1);

insert into EMBALAGEM (ID, DESCRICAO, COMP, LARG, ALT, ATIVO)
values (null,'embalagem12',20,15,15,1);
/************************************************************/


/******************************************
      TABELA DE STATUS DO PEDIDO
******************************************/
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
/**********************************************************************/




/** CATALOGO DE IMAGENS EXCLUSIVAS **/
insert into catalogo_grupo values(1,'GEEK');
insert into catalogo_grupo values(2,'PLANETAS');

insert into catalogo (ID,ID_CATALOGO_GRUPO,DESCRICAO,IMG,ATIVO) values(1,1,'foto do homem aranha','aranha.jpg',1);
insert into catalogo (ID,ID_CATALOGO_GRUPO,DESCRICAO,IMG,ATIVO) values(2,1,'foto do capitao america','capitao.jpg',1);
insert into catalogo (ID,ID_CATALOGO_GRUPO,DESCRICAO,IMG,ATIVO) values(3,1,'foto do homem de ferro','ferro.jpg',1);
insert into catalogo (ID,ID_CATALOGO_GRUPO,DESCRICAO,IMG,ATIVO) values(4,1,'foto do hulk','hulk.jpg',0);

insert into catalogo (ID,ID_CATALOGO_GRUPO,DESCRICAO,IMG,ATIVO) values(5,2,'foto do terra','terra.jpg',1);
insert into catalogo (ID,ID_CATALOGO_GRUPO,DESCRICAO,IMG,ATIVO) values(6,2,'foto de marte','marte.jpg',1);
insert into catalogo (ID,ID_CATALOGO_GRUPO,DESCRICAO,IMG,ATIVO) values(7,2,'foto de venus','venus.jpg',0);




/*************************************/

/*usando os parameters ja criados para testar os tipos*/
insert into mpr_parameter (ID,CHAVE,VALOR,DATA_ATUALIZACAO)
values(null,'CEP_ORIGEM','07093090','2019-02-25');
insert into mpr_parameter (ID,CHAVE,VALOR,DATA_ATUALIZACAO)
values(null,'MARGEM_PROTECAO','4','2019-02-25');




insert into CUPOM_DESCONTO(ID,DESCRICAO, HASH,DATA_INICIO,DATA_FIM,PROMOCAO, PORCENTAGEM, QUANTIDADE)
values (1,'CUPOM DE TESTE','AABBCCDD','2019-01-01','2050-01-01',0,50, 1);

insert into CUPOM_DESCONTO(ID,DESCRICAO, HASH,DATA_INICIO,DATA_FIM,PROMOCAO, PORCENTAGEM, QUANTIDADE)
values (2,'CUPOM EXPIRADO','EEFFGGHH','2019-01-01','2019-01-01',0,50, 1);

/*TABELAS PARA TESTES DE NEGOCIOS*/

--LOGIN PARA O CLIENTE 1
insert into LOGIN (ID, DATA_CRIACAO, DATA_ULTIMO_ACESSO, SENHA, SOCIAL_KEY, LOGIN_TYPE, URL_FOTO, KEY_DEVICE_GCM)
values (null, '2018-12-06', '2018-12-06', null, 'SOCIAL_KEY_GPLUS', 2, null, 'ZAZAZAZA' );
insert into LOGIN (ID, DATA_CRIACAO, DATA_ULTIMO_ACESSO, SENHA, SOCIAL_KEY, LOGIN_TYPE, URL_FOTO, KEY_DEVICE_GCM)
values (null, '2018-12-06', '2018-12-06', null, 'SOCIAL_KEY_FACEBOOK', 1, null, 'ZAZAZAZA' );
insert into LOGIN (ID, DATA_CRIACAO, DATA_ULTIMO_ACESSO, SENHA, SOCIAL_KEY, LOGIN_TYPE, URL_FOTO, KEY_DEVICE_GCM)
values (null, '2018-12-06', '2018-12-06', 'fcea920f7412b5da7be0cf42b8c93759', null, 0, null, 'ZAZAZAZA' );
insert into LOGIN (ID, DATA_CRIACAO, DATA_ULTIMO_ACESSO, SENHA, SOCIAL_KEY, LOGIN_TYPE, URL_FOTO, KEY_DEVICE_GCM)
values (null, '2018-12-06', '2018-12-06', 'SOCIAL_KEY_HAHSHDHA', null, 0, null, 'XXCCVVBB' );

--CLIENTES
insert into CLIENTE (ID, ANIVERSARIO, CELULAR, CPF, EMAIL, NOME, ATIVO, GENERO, ID_LOGIN)
values (null, '1983-09-30', '11999999999', '11111111111', 'wag182@gmail.com', 'Wagner', 1, 'M', 1);
insert into CLIENTE (ID, ANIVERSARIO, CELULAR, CPF, EMAIL, NOME, ATIVO, GENERO, ID_LOGIN)
values (null, '1983-09-30', '11999999999', '22222222222', 'wag183@gmail.com', 'Wagner', 1, 'M', 2);
insert into CLIENTE (ID, ANIVERSARIO, CELULAR, CPF, EMAIL, NOME, ATIVO, GENERO, ID_LOGIN)
values (null, '1983-09-30', '11999999999', '33333333333', 'wag184@gmail.com', 'Wagner', 1, 'M', 3);
insert into CLIENTE (ID, ANIVERSARIO, CELULAR, CPF, EMAIL, NOME, ATIVO, GENERO)
values (null, '1983-09-30', '11999999999', '44444444444', 'wag185@gmail.com', 'CLIENTE REMOVER', 1, 'M');
insert into CLIENTE (ID, ANIVERSARIO, CELULAR, CPF, EMAIL, NOME, ATIVO, GENERO, ID_LOGIN)
values (null, '1983-09-30', '11999999999', '44444444444', 'wagner@gmail.com', 'Wagner', 1, 'M', 4);

--ENDERECO DO CLIENTE 1
insert into ENDERECO (ID, CEP, LOGRADOURO, NUMERO, COMPLEMENTO, BAIRRO, CIDADE, UF, ID_CLIENTE, ATIVO, DESCRICAO, OBSERVACAO, PRINCIPAL)
values (1,'01034030', 'Rua das porpetas malucas', '132a', 'sem complemento', 'bairro da pizza', 'guarulhos', 'sp', 1, 1, 'principal', 'perto da rua das coxinhas', 1);

insert into ENDERECO (ID, CEP, LOGRADOURO, NUMERO, COMPLEMENTO, BAIRRO, CIDADE, UF, ID_CLIENTE, ATIVO, DESCRICAO, OBSERVACAO, PRINCIPAL)
values (2,'01034030', 'Rua das porpetas malucas', '132a', 'sem complemento', 'bairro da pizza', 'guarulhos', 'sp', 5, 1, 'principal', 'perto da rua das coxinhas', 1);

insert into ENDERECO (ID, CEP, LOGRADOURO, NUMERO, COMPLEMENTO, BAIRRO, CIDADE, UF, ID_CLIENTE, ATIVO, DESCRICAO, OBSERVACAO, PRINCIPAL)
values (3,'02323000', 'Rua da casa da mae da feeh', '132a', 'sem complemento', 'bairro dos morros', 'sao paulo', 'sp', 1, 1, 'secundario', 'perto da rua das enxentes', 1);



--FORNECEDORES
insert into FORNECEDOR (ID, NOME, EMAIL, CNPJ, ENDERECO, TELEFONE_PRINCIPAL, TELEFONE_SECUNDARIO, ATIVO)
values (null, 'CASA CASTRO', 'casa@castro.com.br', '11111111111111', 'rua do riacho verde, 4444,centro sao paulo - sp', '111111111111', null, 1);
insert into FORNECEDOR (ID, NOME, EMAIL, CNPJ, ENDERECO, TELEFONE_PRINCIPAL, TELEFONE_SECUNDARIO, ATIVO)
values (null, 'REMOVER', 'casa@castro.com.br', '11111111111111', 'rua do riacho verde, 4444,centro sao paulo - sp', '111111111111', null, 1);

--TIPO DE PRODUTO
insert into TIPO_PRODUTO (ID, DESCRICAO,ACESSORIO)
VALUES (1,'PORTA RETRATO',0);

--PRODUTOS
insert into PRODUTO (ID, ID_TIPO_PRODUTO, DESCRICAO,REFERENCIA,PESO,IMG_PREVIEW, ESTOQUE_MINIMO, DESCRICAO_DETALHADA, IMG_DESTAQUE, ATIVO, HEXA_COR, NOME_COR, COMP, LARG, ALT, LANCAMENTO,POPULAR)
values (1, 1,'PORTA RETRATO','xrp2',1,'IMG2.JPG', 10, 'TESTES TESTE TESTE TESTE','IMG2.JPG',1,'FFF', 'BRANCO', 20, 15, 2,1,1);
insert into PRODUTO (ID, ID_TIPO_PRODUTO, DESCRICAO,REFERENCIA,PESO,IMG_PREVIEW, ESTOQUE_MINIMO, DESCRICAO_DETALHADA,IMG_DESTAQUE, ATIVO, HEXA_COR, NOME_COR, COMP, LARG, ALT, LANCAMENTO,POPULAR)
values (2, 1,'PORTA RETRATO 2','btc1',1,'IMG2.JPG',10, 'TESTES TESTE TESTE TESTE','IMG2.JPG',1,'DDD', 'CINZA', 20, 15, 2,1,1);
insert into PRODUTO (ID, ID_TIPO_PRODUTO, DESCRICAO,REFERENCIA,PESO,IMG_PREVIEW, ESTOQUE_MINIMO, DESCRICAO_DETALHADA,IMG_DESTAQUE, ATIVO, HEXA_COR, NOME_COR, COMP, LARG, ALT, LANCAMENTO,POPULAR)
values (3, 1,'PRODUTO TESTE ESTOQUE','TESTE',1,'IMG2.JPG',10, 'TESTES TESTE TESTE TESTE','IMG2.JPG',1,'000', 'PRETO', 20, 15, 2,0,1);

insert into PRODUTO_IMAGEM_DESTAQUE (ID, ID_PRODUTO, IMG)
values (1,1,'IMGDESTAQUE1.JPG');
insert into PRODUTO_IMAGEM_DESTAQUE (ID, ID_PRODUTO, IMG)
values (2,1,'IMGDESTAQUE2.JPG');
insert into PRODUTO_IMAGEM_DESTAQUE (ID, ID_PRODUTO, IMG)
values (3,2,'IMGDESTAQUE.JPG');
insert into PRODUTO_IMAGEM_DESTAQUE (ID, ID_PRODUTO, IMG)
values (4,3,'IMGDESTAQUE.JPG');

--TABELA DE PRECO
insert into TABELA_PRECO (ID, ID_PRODUTO, DATA_VIGENCIA, PRECO, DESCRICAO)
values (null, 1, '2018-06-01', '80.50', 'VALOR INICIAL DE PRECO DO PRODUTO 10');
insert into TABELA_PRECO (ID, ID_PRODUTO, DATA_VIGENCIA, PRECO, DESCRICAO)
values (null, 1, '2018-06-15', '85.50', 'VALOR INICIAL DE PRECO DO PRODUTO 20');
insert into TABELA_PRECO (ID, ID_PRODUTO, DATA_VIGENCIA, PRECO, DESCRICAO)
values (null, 1, '2018-06-28', '100.50', 'VALOR INICIAL DE PRECO DO PRODUTO 20');

insert into TABELA_PRECO (ID, ID_PRODUTO, DATA_VIGENCIA, PRECO, DESCRICAO)
values (null, 2, '2018-06-28', '28.50', 'VALOR INICIAL DE PRECO DO PRODUTO 2');
insert into TABELA_PRECO (ID, ID_PRODUTO, DATA_VIGENCIA, PRECO, DESCRICAO)
values (null, 3, '2018-06-28', '100.50', 'VALOR INICIAL DE PRECO DO PRODUTO 3');

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
values(7,2,3,0);
insert into ESTOQUE_ITEM (ID,ID_ESTOQUE,ID_PRODUTO, INVALIDO)
values(9,2,3,0);

--PEDIDO
INSERT INTO PEDIDO (ID,CODIGO_PEDIDO,DATA,ID_CLIENTE,ID_ENDERECO,ID_CUPOM,VALOR_PRODUTOS,VALOR_FRETE,VALOR_DESCONTO,VALOR_TOTAL,TIPO_PAGAMENTO,DATA_ENTREGA)
VALUES (1,'123456AA','2018-10-23',1,1,null,450,50,10,490,0,'2019-01-01');

--ITEMS DO PEDIDO
INSERT INTO ITEM_PEDIDO(ID, ID_PEDIDO,ID_PRODUTO,VALOR, ID_ESTOQUE)
VALUES (1,1,3,150,2);
INSERT INTO ITEM_PEDIDO(ID, ID_PEDIDO,ID_PRODUTO,VALOR, ID_ESTOQUE)
VALUES (2,1,3,150,2);
INSERT INTO ITEM_PEDIDO(ID, ID_PEDIDO,ID_PRODUTO,VALOR, ID_ESTOQUE)
VALUES (3,1,3,150,2);

INSERT INTO HISTORICO_PEDIDO(ID, ID_PEDIDO, ID_STATUS_PEDIDO, DATA)
VALUES (null,1,1,'2019-01-01');


--items para o teste de listagem de produtos do estoque
insert into ESTOQUE (ID,ID_FORNECEDOR,DATA_COMPRA,DATA_ATUALIZACAO,PRECO_COMPRA,OBSERVACAO,QUANTIDADE)
values(3,1,'2018-01-01','2018-01-01',10,'TESTE_OUTRO LOTE PRODUTO3',5);

insert into ESTOQUE_ITEM (ID,ID_ESTOQUE,ID_PRODUTO, INVALIDO)
values(13,3,3,0);
insert into ESTOQUE_ITEM (ID,ID_ESTOQUE,ID_PRODUTO, INVALIDO)
values(14,3,3,0);
insert into ESTOQUE_ITEM (ID,ID_ESTOQUE,ID_PRODUTO, INVALIDO)
values(15,3,3,0);


--PEDIDO
INSERT INTO PEDIDO (ID,CODIGO_PEDIDO,DATA,ID_CLIENTE,ID_ENDERECO,ID_CUPOM,VALOR_PRODUTOS,VALOR_FRETE,VALOR_DESCONTO,VALOR_TOTAL,TIPO_PAGAMENTO,DATA_ENTREGA)
VALUES (2,'789234BB','2018-10-23',1,1,null,450,50,10,490,0,'2019-01-01');

--ITEMS DO PEDIDO
INSERT INTO ITEM_PEDIDO(ID, ID_PEDIDO,ID_PRODUTO,VALOR, ID_ESTOQUE)
VALUES (4,2,3,150,3);
INSERT INTO ITEM_PEDIDO(ID, ID_PEDIDO,ID_PRODUTO,VALOR, ID_ESTOQUE)
VALUES (5,2,3,150,3);

INSERT INTO HISTORICO_PEDIDO(ID, ID_PEDIDO, ID_STATUS_PEDIDO, DATA)
VALUES (null,2,2,'2019-01-01');

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
CREATE TABLE IF NOT EXISTS USERS (
    USERNAME VARCHAR(64) NOT NULL,
    PASSWORD VARCHAR(64) NOT NULL,
    ENABLED boolean NOT NULL DEFAULT true,
    PRIMARY KEY (USERNAME)
);

CREATE TABLE IF NOT EXISTS USER_ROLES(
  USER_ROLE_ID bigint generated by default as identity,
  USERNAME VARCHAR(64) NOT NULL,
  ROLE VARCHAR(64) NOT NULL,
  PRIMARY KEY (USER_ROLE_ID),
);

INSERT INTO users(username,password,enabled)
VALUES ('user','$2a$10$8recqP62m2fobIBsNzGRZutoK3t0z7JWDylBunWuHW5ItjmKSWjN.', true);
INSERT INTO user_roles (username, role)
VALUES ('user', 'ROLE_ADMIN');
INSERT INTO user_roles (username, role)
VALUES ('user', 'ROLE_USER');

/**********************************************************************************************************************/


/**************************
  MASSA DE TESTES PARA O CARRINHO
***************************/
insert into PRODUTO (ID, ID_TIPO_PRODUTO, DESCRICAO,REFERENCIA,PESO,IMG_PREVIEW, ESTOQUE_MINIMO, DESCRICAO_DETALHADA,IMG_DESTAQUE, ATIVO, HEXA_COR, NOME_COR, COMP, LARG, ALT, LANCAMENTO,POPULAR)
values (4, 1,'PRODUTO TESTE CARRINHO','TESTE',1,'IMG2.JPG',10, 'TESTES TESTE TESTE TESTE','IMG2.JPG',1,'000', 'PRETO', 20, 15, 2,0,1);
insert into PRODUTO_IMAGEM_DESTAQUE (ID, ID_PRODUTO, IMG)
values (5,4,'IMGDESTAQUE.JPG');

insert into TABELA_PRECO (ID, ID_PRODUTO, DATA_VIGENCIA, PRECO, DESCRICAO)
values (null, 4, '2018-06-01', '55.50', 'VALOR INICIAL DE PRECO DO PRODUTO 4');

insert into ESTOQUE (ID,ID_FORNECEDOR,DATA_COMPRA,DATA_ATUALIZACAO,PRECO_COMPRA,OBSERVACAO,QUANTIDADE)
values(4,1,'2018-01-01','2018-01-01',10,'TESTE_OUTRO LOTE PRODUTO4',13);
--13 itens no estoque
insert into ESTOQUE_ITEM (ID,ID_ESTOQUE,ID_PRODUTO, INVALIDO)
values(null,4,4,0);
insert into ESTOQUE_ITEM (ID,ID_ESTOQUE,ID_PRODUTO, INVALIDO)
values(null,4,4,0);
insert into ESTOQUE_ITEM (ID,ID_ESTOQUE,ID_PRODUTO, INVALIDO)
values(null,4,4,0);
insert into ESTOQUE_ITEM (ID,ID_ESTOQUE,ID_PRODUTO, INVALIDO)
values(null,4,4,0);
insert into ESTOQUE_ITEM (ID,ID_ESTOQUE,ID_PRODUTO, INVALIDO)
values(null,4,4,0);
insert into ESTOQUE_ITEM (ID,ID_ESTOQUE,ID_PRODUTO, INVALIDO)
values(null,4,4,0);
insert into ESTOQUE_ITEM (ID,ID_ESTOQUE,ID_PRODUTO, INVALIDO)
values(null,4,4,0);
insert into ESTOQUE_ITEM (ID,ID_ESTOQUE,ID_PRODUTO, INVALIDO)
values(null,4,4,0);
insert into ESTOQUE_ITEM (ID,ID_ESTOQUE,ID_PRODUTO, INVALIDO)
values(null,4,4,0);
insert into ESTOQUE_ITEM (ID,ID_ESTOQUE,ID_PRODUTO, INVALIDO)
values(null,4,4,0);
insert into ESTOQUE_ITEM (ID,ID_ESTOQUE,ID_PRODUTO, INVALIDO)
values(null,4,4,0);
insert into ESTOQUE_ITEM (ID,ID_ESTOQUE,ID_PRODUTO, INVALIDO)
values(null,4,4,0);
insert into ESTOQUE_ITEM (ID,ID_ESTOQUE,ID_PRODUTO, INVALIDO)
values(null,4,4,0);

insert into PRODUTO (ID, ID_TIPO_PRODUTO, DESCRICAO,REFERENCIA,PESO,IMG_PREVIEW, ESTOQUE_MINIMO, DESCRICAO_DETALHADA,IMG_DESTAQUE, ATIVO, HEXA_COR, NOME_COR, COMP, LARG, ALT)
values (5, 1,'PRODUTO TESTE CARRINHO','TESTE',1,'IMG2.JPG',10, 'TESTES TESTE TESTE TESTE','IMG2.JPG',1,'000', 'PRETO', 20, 15, 2);
insert into TABELA_PRECO (ID, ID_PRODUTO, DATA_VIGENCIA, PRECO, DESCRICAO)
values (null, 5, '2018-06-28', '100.50', 'VALOR INICIAL DE PRECO DO PRODUTO 5');
insert into PRODUTO_IMAGEM_DESTAQUE (ID, ID_PRODUTO, IMG)
values (6,5,'IMGDESTAQUE.JPG');

insert into ESTOQUE (ID,ID_FORNECEDOR,DATA_COMPRA,DATA_ATUALIZACAO,PRECO_COMPRA,OBSERVACAO,QUANTIDADE)
values(5,1,'2018-01-01','2018-01-01',10,'TESTE_OUTRO LOTE PRODUTO5',6);

insert into ESTOQUE_ITEM (ID,ID_ESTOQUE,ID_PRODUTO, INVALIDO)
values(null,5,5,0);
insert into ESTOQUE_ITEM (ID,ID_ESTOQUE,ID_PRODUTO, INVALIDO)
values(null,5,5,0);
insert into ESTOQUE_ITEM (ID,ID_ESTOQUE,ID_PRODUTO, INVALIDO)
values(null,5,5,0);
insert into ESTOQUE_ITEM (ID,ID_ESTOQUE,ID_PRODUTO, INVALIDO)
values(null,5,5,0);
insert into ESTOQUE_ITEM (ID,ID_ESTOQUE,ID_PRODUTO, INVALIDO)
values(null,5,5,0);
insert into ESTOQUE_ITEM (ID,ID_ESTOQUE,ID_PRODUTO, INVALIDO)
values(null,5,5,0);
insert into ESTOQUE_ITEM (ID,ID_ESTOQUE,ID_PRODUTO, INVALIDO)
values(null,5,5,0);
insert into ESTOQUE_ITEM (ID,ID_ESTOQUE,ID_PRODUTO, INVALIDO)
values(null,5,5,0);
insert into ESTOQUE_ITEM (ID,ID_ESTOQUE,ID_PRODUTO, INVALIDO)
values(null,5,5,0);

insert into PRODUTO (ID, ID_TIPO_PRODUTO, DESCRICAO,REFERENCIA,PESO,IMG_PREVIEW, ESTOQUE_MINIMO, DESCRICAO_DETALHADA,IMG_DESTAQUE, ATIVO, HEXA_COR, NOME_COR, COMP, LARG, ALT)
values (6, 1,'PRODUTO TESTE CARRINHO','TESTE',1,'IMG2.JPG',10, 'TESTES TESTE TESTE TESTE','IMG2.JPG',1,'000', 'PRETO', 20, 15, 2);
insert into TABELA_PRECO (ID, ID_PRODUTO, DATA_VIGENCIA, PRECO, DESCRICAO)
values (null, 6, '2018-06-28', '100.50', 'VALOR INICIAL DE PRECO DO PRODUTO 5');
insert into PRODUTO_IMAGEM_DESTAQUE (ID, ID_PRODUTO, IMG)
values (7,6,'IMGDESTAQUE.JPG');

insert into ESTOQUE (ID,ID_FORNECEDOR,DATA_COMPRA,DATA_ATUALIZACAO,PRECO_COMPRA,OBSERVACAO,QUANTIDADE)
values(6,1,'2018-01-01','2018-01-01',10,'TESTE_OUTRO LOTE PRODUTO5',1);

insert into ESTOQUE_ITEM (ID,ID_ESTOQUE,ID_PRODUTO, INVALIDO)
values(null,6,6,0);
/********************************************************************************/

/**************************
  MASSA DE TESTES PARA O CHECKOUT
***************************/
insert  into CARRINHO ( ID, ID_CLIENTE, KEY_DEVICE, DATA_CRIACAO)
values (1,5,null,'2019-01-06');

insert into item_carrinho (ID, ID_ESTOQUE_ITEM, ID_CARRINHO)
values (null,1,1);

insert into item_carrinho_anexo (ID, ID_ITEM_CARRINHO, FOTO, ID_CATALOGO)
values (null,1,'FOTO CLIENTE', null);



