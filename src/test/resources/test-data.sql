insert into CLIENTE (ID, ANIVERSARIO, CELULAR, CPF, EMAIL, NOME, ATIVO, GENERO)
values (null, '1983-09-30', '11999999999', '11111111111', 'wag182@gmail.com', 'Wagner', 1, 'M');
insert into CLIENTE (ID, ANIVERSARIO, CELULAR, CPF, EMAIL, NOME, ATIVO, GENERO)
values (null, '1983-09-30', '11999999999', '22222222222', 'wag183@gmail.com', 'Wagner', 1, 'M');
insert into CLIENTE (ID, ANIVERSARIO, CELULAR, CPF, EMAIL, NOME, ATIVO, GENERO)
values (null, '1983-09-30', '11999999999', '33333333333', 'wag184@gmail.com', 'Wagner', 1, 'M');
insert into CLIENTE (ID, ANIVERSARIO, CELULAR, CPF, EMAIL, NOME, ATIVO, GENERO)
values (null, '1983-09-30', '11999999999', '44444444444', 'wag185@gmail.com', 'CLIENTE REMOVER', 1, 'M');


insert into FORNECEDOR (ID, NOME, EMAIL, CNPJ, ENDERECO, TELEFONE_PRINCIPAL, TELEFONE_SECUNDARIO, ATIVO)
values (null, 'CASA CASTRO', 'casa@castro.com.br', '11111111111111', 'rua do riacho verde, 4444,
centro sao paulo - sp', '111111111111', null, 1);

insert into FORNECEDOR (ID, NOME, EMAIL, CNPJ, ENDERECO, TELEFONE_PRINCIPAL, TELEFONE_SECUNDARIO, ATIVO)
values (null, 'REMOVER', 'casa@castro.com.br', '11111111111111', 'rua do riacho verde, 4444,
centro sao paulo - sp', '111111111111', null, 1);

insert into TIPO_PRODUTO (ID, DESCRICAO)
VALUES (1,'PORTA RETRATO');

insert into PRODUTO (ID, ID_TIPO_PRODUTO, DESCRICAO,REFERENCIA,PESO,IMG_DESTAQUE,IMG_PREVIEW)
values (1, 1,'PORTA RETRATO','xxxpp',10.10,'IMG.JPG','IMG2.JPG');

insert into TABELA_PRECO (ID, ID_PRODUTO, DATA_VIGENCIA, PRECO, DESCRICAO)
values (null, 1, '2018-06-01', '80.50', 'VALOR INICIAL DE PRECO DO PRODUTO 10');
insert into TABELA_PRECO (ID, ID_PRODUTO, DATA_VIGENCIA, PRECO, DESCRICAO)
values (null, 1, '2018-06-15', '85.50', 'VALOR INICIAL DE PRECO DO PRODUTO 20');
insert into TABELA_PRECO (ID, ID_PRODUTO, DATA_VIGENCIA, PRECO, DESCRICAO)
values (null, 1, '2018-06-28', '100.50', 'VALOR INICIAL DE PRECO DO PRODUTO 20');


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

insert into ESTOQUE (ID,ID_FORNECEDOR,DATA_COMPRA,DATA_ATUALIZACAO,PRECO_COMPRA,OBSERVACAO)
values(1,1,'2018-01-01','2018-01-01',10,'obs');

insert into ESTOQUE_PRODUTO (ID,ID_ESTOQUE,ID_PRODUDO,INVALIDO)
values(105,1,1,0);

insert into ESTOQUE_PRODUTO (ID,ID_ESTOQUE,ID_PRODUTO, INVALIDO)
values(106,1,1,0);

insert into ESTOQUE_PRODUTO (ID,ID_ESTOQUE,ID_PRODUTO, INVALIDO)
values(107,1,1,0);

/*SYSTEM TABLES*/
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
