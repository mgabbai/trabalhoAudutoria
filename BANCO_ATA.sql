/*

	TABELAS

*/

create table usuario(
    id_usuario          number not null,
    nome                varchar2(100 char) not null,
    email               varchar2(200 char),
    usuario             varchar2(30 char) not null,
    senha               varchar2(200 char) not null,
    permissao           integer not null
);

alter table usuario add constraint pk_id_usuario primary key (id_usuario);
alter table usuario add constraint unq_usuario unique (usuario);
create index idx_usuario_nome on usuario(nome);


create table atas(
    id_ata      number not null,
    data        date not null,
    conteudo    clob not null,
    presentes   varchar2(4000 char) not null,
    id_usuario  number not null
);

alter table atas add constraint pk_id_ata primary key (id_ata);
alter table atas add constraint fk_id_usuario FOREIGN key (id_usuario) references usuario(id_usuario) on delete cascade;
create index idx_ata_data on atas(data);

CREATE TABLE log (	
	ID NUMBER(*,0), 
	NOME_TABELA VARCHAR2(100 CHAR), 
	USUARIO VARCHAR2(100 CHAR), 
	EVENTO CHAR(1 CHAR), 
	DATA_EVENTO DATE, 
	COMANDO CLOB, 
	 CONSTRAINT PK_ID_AUDITORIA PRIMARY KEY ("ID"),
	 CONSTRAINT CHK_AUDITORIA_EVENTO CHECK (evento in ('I', 'U', 'D', 'S'))
) ;
   
create table usuario_logado(
    usuario varchar2(30 char),
    status  char(1 char),
    data    date
);

alter table usuario_logado add constraint fk_usuario_logado FOREIGN key (usuario) references usuario(usuario) on delete cascade;
alter table usuario_logado drop constraint fk_usuario_logado;

create table caminho_impressao(
    usuario varchar2(30 char),
    caminho varchar2(4000 char)
);

create table dados(
    nome_resumido   varchar2(100 char),
    nome            varchar2(200 char),
    endereco        varchar2(500 char),
    estado          char(2 char),
    municipio       varchar2(100 char),
    telefone        varchar2(100 char),
    email           varchar2(500 char),
    site            varchar2(300 char),
    cnpj            varchar2(100 char)
);

create table estados(  
 codigo_ibge varchar2(4 char), 
 sigla char(2 char),  
 nome varchar2(30 char)
 );
 
 CREATE TABLE ALGPARAMETERS(
  NAME   VARCHAR2(100 BYTE),
  VALUE  NVARCHAR2(100)
);
 
/*

	CARGA INICIAL

*/

INSERT INTO ALGPARAMETERS
   SELECT 'key' NAME,
          RAWTOHEX ('52AB32;^$!ER94988OP6G321') VALUE
     FROM DUAL
   UNION
   SELECT 'iv' NAME, RAWTOHEX ('TY54AGCX') VALUE FROM DUAL;

 Insert Into estados (codigo_ibge,sigla,nome) Values(12,'AC','Acre');  
 Insert Into estados (codigo_ibge,sigla,nome) Values(27,'AL','Alagoas');  
 Insert Into estados (codigo_ibge,sigla,nome) Values(13,'AM','Amazonas');
 Insert Into estados (codigo_ibge,sigla,nome) Values(16,'AP','Amapá');
 Insert Into estados (codigo_ibge,sigla,nome) Values(29,'BA','Bahia');
 Insert Into estados (codigo_ibge,sigla,nome) Values(23,'CE','Ceará');
 Insert Into estados (codigo_ibge,sigla,nome) Values(53,'DF','Distrito Federal');
 Insert Into estados (codigo_ibge,sigla,nome) Values(32,'ES','Espírito Santo');
 Insert Into estados (codigo_ibge,sigla,nome) Values(52,'GO','Goiás');
 Insert Into estados (codigo_ibge,sigla,nome) Values(21,'MA','Maranhão');
 Insert Into estados (codigo_ibge,sigla,nome) Values(31,'MG','Minas Gerais');
 Insert Into estados (codigo_ibge,sigla,nome) Values(50,'MS','Mato Grosso do Sul');
 Insert Into estados (codigo_ibge,sigla,nome) Values(51,'MT','Mato Grosso');
 Insert Into estados (codigo_ibge,sigla,nome) Values(15,'PA','Pará');
 Insert Into estados (codigo_ibge,sigla,nome) Values(25,'PB','Paraíba');
 Insert Into estados (codigo_ibge,sigla,nome) Values(26,'PE','Pernambuco');
 Insert Into estados (codigo_ibge,sigla,nome) Values(22,'PI','Piauí');
 Insert Into estados (codigo_ibge,sigla,nome) Values(41,'PR','Paraná');
 Insert Into estados (codigo_ibge,sigla,nome) Values(33,'RJ','Rio de Janeiro');
 Insert Into estados (codigo_ibge,sigla,nome) Values(24,'RN','Rio Grande do Norte');
 Insert Into estados (codigo_ibge,sigla,nome) Values(11,'RO','Rondônia');
 Insert Into estados (codigo_ibge,sigla,nome) Values(14,'RR','Roraima');
 Insert Into estados (codigo_ibge,sigla,nome) Values(43,'RS','Rio Grande do Sul');
 Insert Into estados (codigo_ibge,sigla,nome) Values(42,'SC','Santa Catarina');
 Insert Into estados (codigo_ibge,sigla,nome) Values(28,'SE','Sergipe');
 Insert Into estados (codigo_ibge,sigla,nome) Values(35,'SP','São Paulo');
 Insert Into estados (codigo_ibge,sigla,nome) Values(17,'TO','Tocantins');
 
 insert into usuario (id_usuario, nome, email, usuario, senha, permissao) VALUES (SEQ_USUARIO.nextval, 'ADM','', 'ADM', f_encrypt('swadm'), 0); 
 
 insert into caminho_impressao (usuario, caminho)values('ADM', 'C:\temp');


/*

	OBJETOS

*/

-- TRIGGERS

create or replace trigger trg_usuarios before insert or update or delete on usuario
referencing new as NEW old as OLD for each row
	declare
		v_DML_TYPE char(1 char);
        v_DML clob;
begin
			IF INSERTING THEN 
			 v_DML_TYPE := 'I';
             v_DML := 'insert into usuario (id_usuario,nome,email,usuario,senha,permissao) values ('||to_char(:NEW.id_usuario)||','||:NEW.nome||','||nvl(:NEW.email,'''''')||','||:NEW.usuario||','||:NEW.senha||','||to_char(:NEW.permissao)||');';
			ELSIF DELETING THEN
			 v_DML_TYPE := 'D';
             v_DML := 'delete from usuario where id_usuario = '''||to_char(:OLD.id_usuario)||''';';
			ELSIF UPDATING THEN
			 v_DML_TYPE := 'U';
             v_DML := 'update usuario set id_usuario = '''||to_char(nvl(:NEW.id_usuario, :OLD.id_usuario))||''', nome = '''||nvl(:NEW.nome, :OLD.nome)||''',email = '''||nvl(:NEW.email, :OLD.email)||''',usuario = '''||nvl(:NEW.usuario,:OLD.usuario)||''',senha = '''||nvl(:NEW.senha,:OLD.senha)||''',permissao = '''||to_char(nvl(:NEW.permissao,:OLD.permissao))||''' where id_usuario = '''||:OLD.id_usuario||''';';
			END IF;
			insert into log(id,nome_tabela, usuario, evento, data_evento, comando)
				 values (seq_logs.nextval, 'USUARIO', (select usuario from usuario_logado where status = 'S'), to_char(v_DML_TYPE),sysdate,to_char(v_DML));
end;
/

create or replace trigger trg_atas before insert on atas
referencing new as NEW old as OLD for each row
	declare
		v_DML_TYPE char(1 char);
        v_DML clob;
        v_id_usuario integer;
begin
    
    select id_usuario 
      into v_id_usuario
      from usuario
     where usuario = (select usuario
                        from usuario_logado
                       where status = 'S');
        
			IF INSERTING THEN 
			 v_DML_TYPE := 'I';
             v_DML := 'insert into atas (id_ata, data, conteudo, presentes, id_usuario) values ('''||:new.id_ata||''','''||:new.data||''','''||:new.conteudo||''','''||:new.presentes||''','''||to_char(v_id_usuario)||''');';
            
            end if;
            
			insert into log(id,nome_tabela, usuario, evento, data_evento, comando)
				 values (seq_logs.nextval, 'ATAS', (select usuario from usuario_logado where status = 'S'), to_char(v_DML_TYPE),sysdate,to_char(v_DML));
end;
/

create or replace trigger trg_dados before insert or update on dados
referencing new as NEW old as OLD for each row
	declare
		v_DML_TYPE char(1 char);
        v_DML clob;
begin
			IF INSERTING THEN 
			 v_DML_TYPE := 'I';
             v_DML := 'insert into dados (nome,nome_resumido,endereco,estado,municipio,telefone, email, site, cnpj) values ('||nvl(:NEW.nome, '''''')||','||nvl(:NEW.nome_resumido, '''''')||','||nvl(:NEW.endereco,'''''')||','||nvl(:NEW.estado, '''''')||','||nvl(:NEW.municipio, '''''')||','||nvl(:NEW.telefone,'''''')||','||nvl(:NEW.email, '''''')||','||nvl(:NEW.site, '''''')||','||nvl(:NEW.cnpj, '''''')||');';
			ELSIF UPDATING THEN
			 v_DML_TYPE := 'U';
             v_DML := 'update dados set nome = '||nvl(:NEW.nome,nvl(:OLD.nome, ''''''))||' ,nome_resumido = '||nvl(:NEW.nome_resumido ,nvl(:OLD.nome_resumido , ''''''))||',endereco = '||nvl(:NEW.endereco ,nvl(:OLD.endereco , ''''''))||',estado = '||nvl(:NEW.estado ,nvl(:OLD.estado , ''''''))||',municipio = ' ||nvl(:NEW.municipio,nvl(:OLD.municipio, ''''''))||',telefone = '|| nvl(:NEW.telefone,nvl(:OLD.telefone, ''''''))||', email = ' ||nvl(:NEW.email,nvl(:OLD.email, ''''''))||', site = '|| nvl(:NEW.site,nvl(:OLD.site, ''''''))||', cnpj = '||nvl(:NEW.cnpj,nvl(:OLD.cnpj, ''''''))||' ;';
			END IF;
			insert into log(id,nome_tabela, usuario, evento, data_evento, comando)
				 values (seq_logs.nextval, 'DADOS', (select usuario from usuario_logado where status = 'S'), to_char(v_DML_TYPE),sysdate,to_char(v_DML));
end;
/
create or replace trigger trg_usuario_logado before insert on usuario_logado
referencing new as NEW old as OLD for each row
	declare
		v_DML_TYPE char(1 char);
        v_DML clob;
begin
			IF INSERTING THEN 
			 v_DML_TYPE := 'S';
             v_DML := 'Acesso no sistema pelo usuario: '||:NEW.usuario||' - Data: '|| sysdate;
			END IF;
			insert into log(id,nome_tabela, usuario, evento, data_evento, comando)
				 values (seq_logs.nextval, 'USUARIO_LOGADO', (select usuario from usuario_logado where status = 'S'), to_char(v_DML_TYPE),sysdate,to_char(v_DML));
end;
/

create or replace trigger trg_caminho_impressao before insert or update on caminho_impressao
referencing new as NEW old as OLD for each row
	declare
		v_DML_TYPE char(1 char);
        v_DML clob;
begin
			IF INSERTING THEN 
			 v_DML_TYPE := 'I';
             v_DML := 'Usuario ' || nvl(:NEW.usuario, '') || ' inseriu novo caminho para gerar documentos ' ||  nvl(:NEW.caminho, '');
			ELSIF UPDATING THEN
			 v_DML_TYPE := 'U';
             v_DML := 'Usuario ' || nvl(:OLD.usuario, '') || ' alterou o caminho para gerar documentos ' ||  nvl(:NEW.caminho, '');
			END IF;
			insert into log(id,nome_tabela, usuario, evento, data_evento, comando)
				 values (seq_logs.nextval, 'CAMINHO_IMPRESSAO', (select usuario from usuario_logado where status = 'S'), to_char(v_DML_TYPE),sysdate,to_char(v_DML));
end;
/

-- SEQUENCE

create sequence seq_usuario
minvalue 1
maxvalue 9999999999
start with 1
increment by 1
nocache
cycle;

create sequence seq_logs
minvalue 1
maxvalue 9999999999
start with 1
increment by 1
nocache
cycle;

-- FUNCTION

CREATE OR REPLACE FUNCTION F_ENCRYPT (p_input VARCHAR2)
   RETURN VARCHAR2
AS
   v_encrypted_raw     RAW (2000);
   v_key               RAW (320);
   v_encryption_type   PLS_INTEGER
      :=   DBMS_CRYPTO.DES_CBC_PKCS5;
   v_iv                RAW (320);
BEGIN
   SELECT VALUE
     INTO v_key
     FROM algparameters
    WHERE name = 'key';
   SELECT VALUE
     INTO v_iv
     FROM algparameters
    WHERE name = 'iv';
   v_encrypted_raw :=
      DBMS_CRYPTO.encrypt (src   => UTL_I18N.STRING_TO_RAW (p_input, 'AL32UTF8'),
                           typ   => v_encryption_type,
                           key   => v_key,
                           iv    => v_iv);
   RETURN UTL_RAW.CAST_TO_VARCHAR2 (UTL_ENCODE.base64_encode (v_encrypted_raw));
END;
/

CREATE OR REPLACE FUNCTION F_DECRYPT (p_input VARCHAR2)
   RETURN VARCHAR2
AS
   v_decrypted_raw     RAW (2000);
   v_key               RAW (320);
   v_encryption_type   PLS_INTEGER := DBMS_CRYPTO.DES_CBC_PKCS5;
   v_iv                RAW (320);
BEGIN
   SELECT VALUE
     INTO v_key
     FROM algparameters
    WHERE name = 'key';
   SELECT VALUE
     INTO v_iv
     FROM algparameters
    WHERE name = 'iv';
   v_decrypted_raw :=
      DBMS_CRYPTO.DECRYPT (
         src   => UTL_ENCODE.base64_decode (UTL_RAW.CAST_TO_RAW (p_input)),
         typ   => v_encryption_type,
         key   => v_key,
         iv    => v_iv);
   RETURN UTL_I18N.RAW_TO_CHAR (v_decrypted_raw, 'AL32UTF8');
END;
/

create or replace function md5 (valor varchar) return varchar2 is
    v_input varchar2(2000) := valor;  
    hexkey varchar2(32) := null;   
begin
    hexkey := rawtohex(dbms_obfuscation_toolkit.md5(input => utl_raw.cast_to_raw(v_input)));
    return hexkey;
end;
/