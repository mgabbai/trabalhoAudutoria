create table usuario(
    id_usuario          number not null,
    nome                varchar2(100 char) not null,
    email               varchar2(200 char),
    usuario             varchar2(30 char) not null,
    senha               varchar2(200 char) not null,
    permissao           integer
);

alter table usuario add constraint pk_id_usuario primary key (id_usuario);
create index idx_usuario_nome on usuario(nome);

create table atas(
    id_ata      number not null,
    data        date not null,
    conteudo    long not null,
    presentes   varchar2(4000 char) not null
);

alter table atas add constraint pk_id_ata primary key (id_ata);
create index idx_ata_data on atas(data);

create table usuario_ata(
    id_usuario  number,
    id_ata      number
);

alter table usuario_ata add constraint pk_id_usuario_ata primary key (id_usuario,id_ata);
alter table usuario_ata add constraint fk_id_usuario FOREIGN key (id_usuario) references usuario(id_usuario) on delete cascade;
alter table usuario_ata add constraint fk_id_ata FOREIGN key (id_ata) references atas(id_ata) on delete cascade;

CREATE TABLE log 
   (	ID NUMBER(*,0), 
	NOME_TABELA VARCHAR2(100 CHAR), 
	USUARIO VARCHAR2(100 CHAR), 
	EVENTO CHAR(1 CHAR), 
	DATA_EVENTO DATE, 
	COMANDO CLOB, 
	 CONSTRAINT PK_ID_AUDITORIA PRIMARY KEY ("ID"),
	 CONSTRAINT CHK_AUDITORIA_EVENTO CHECK (evento in ('I', 'U', 'D'))
   ) ;
   
