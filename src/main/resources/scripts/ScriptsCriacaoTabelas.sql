create table autor(
                      id uuid not null primary key,
                      nome varchar(100) not null,
                      data_nascimento date not null,
                      nacionalidade varchar(50) not null,
                      data_cadastro timestamp,
                      data_atualizacao timestamp,
                      id_usuario uuid
);

create table livro (
                       id uuid not null primary key,
                       isbn varchar(20) not null unique,
                       titulo varchar(150) not null,
                       data_publicacao date not null,
                       genero varchar(30) not null,
                       preco numeric(18,2),
                       data_cadastro timestamp,
                       data_atualizacao timestamp,
                       id_usuario uuid,
                       id_autor uuid not null references autor(id),
                       constraint chk_genero check (genero in ('FICCAO', 'FANTASIA', 'MISTERIO','ROMANCE', 'BIOGRAFIA', 'CIENCIA') )
);

-- deleta tabela usuario
drop table usuario;

-- edita a tabela adicionando uma coluna
alter table usuario add column email varchar(150) not null;

create table usuario(
                        id uuid not null primary key,
                        login varchar(20) not null unique,
                        senha varchar(300) not null,
                        email varchar(150) not null,
                        roles varchar[]
);

CREATE TABLE client(
                       id uuid not null primary key,
                       client_id varchar(150) not null,
                       client_secret varchar(400) not null,
                       redirect_uri varchar(200) not null,
                       scope varchar(50)
);


CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

insert into usuario
(id, login, senha, email, roles)
values
    (uuid_generate_v4(), 'gerente', '$2a$12$5QVew7CWJ1vCXfUwlppNWerhElz/KyZNlFqDOseutR2Rv8wjItfXy', 'gerente@email.com', '{GERENTE}')


insert into client
(id, client_id, client_secret, redirect_uri, scope)
values
    (uuid_generate_v4(), 'client-production', '$2a$12$ORM7eItYlqmCWNPCq2r5oO1F5vJ.ZLmBTu.SAtSO2lZ/qazwz/aXC', 'http://localhost:8080/authorized', 'GERENTE')