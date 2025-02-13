CREATE TABLE tipos(
    nome VARCHAR NOT NULL,
    PRIMARY KEY(nome)
);
CREATE TABLE ataques(
    nome VARCHAR NOT NULL,
    power SMALLINT,
    accuracy SMALLINT,
    texto VARCHAR,
    tipo_de_ataque VARCHAR,
    nome_tipo VARCHAR,
    PRIMARY KEY(nome),
    CONSTRAINT chave_tipo
        FOREIGN KEY(nome_tipo)
            REFERENCES tipos(nome)
        ON DELETE CASCADE
);
CREATE TABLE habilidades(
    nome VARCHAR NOT NULL,
    texto VARCHAR,
    PRIMARY KEY(nome)
);
CREATE TABLE cor(
    nome VARCHAR NOT NULL,
    hex_code INTEGER,
    PRIMARY KEY(nome)
);
CREATE TABLE shape(
    nome VARCHAR NOT NULL,
    sprite BYTEA,
    PRIMARY KEY(nome)
);
CREATE TABLE itens(
    nome VARCHAR NOT NULL,
    descricao VARCHAR,
    PRIMARY KEY(nome)
);
CREATE TABLE usuarios(
    username VARCHAR NOT NULL,
    PRIMARY KEY(username)
);
CREATE TABLE pokemons(
    nome VARCHAR NOT NULL,
    numero SMALLINT,
    forma SMALLINT,
    hp SMALLINT,
    speed SMALLINT,
    atk SMALLINT,
    spatk  SMALLINT,
    def SMALLINT,
    spdef SMALLINT,
    color VARCHAR NOT NULL,
    body_shape VARCHAR NOT NULL,
    sprite BYTEA,
    selecionavel BOOLEAN,
    PRIMARY KEY(numero,forma),
    CONSTRAINT chave_shape
        FOREIGN KEY(body_shape)
            REFERENCES shape(nome)
        ON DELETE CASCADE,
    CONSTRAINT chave_cor
        FOREIGN KEY(color)
            REFERENCES cor(nome)
        ON DELETE CASCADE
);
CREATE TABLE equipes(
    nome VARCHAR NOT NULL,
    nome_dono VARCHAR NOT NULL,
    privado BOOLEAN NOT NULL,
    PRIMARY KEY(nome, nome_dono),
    CONSTRAINT chave_usuario
        FOREIGN KEY(nome_dono)
            REFERENCES usuarios(username)
        ON DELETE CASCADE
);
CREATE TABLE membros_de_time(
    num_poke SMALLINT NOT NULL,
    num_forma SMALLINT NOT NULL,
    nome_equipe VARCHAR NOT NULL,
    nome_dono VARCHAR NOT NULL,
    pos_equipe SMALLINT NOT NULL,
    apelido VARCHAR,
    nome_item VARCHAR,
    nome_habilidade VARCHAR,
    PRIMARY KEY(nome_equipe,nome_dono,pos_equipe),
    CONSTRAINT chave_pokemon
        FOREIGN KEY(num_poke,num_forma)
            REFERENCES pokemons(numero,forma)
        ON DELETE CASCADE,
    CONSTRAINT chave_habilidade
        FOREIGN KEY(nome_habilidade)
            REFERENCES habilidades(nome)
        ON DELETE CASCADE,
    CONSTRAINT chave_equipe
        FOREIGN KEY(nome_equipe,nome_dono)
            REFERENCES equipes(nome,nome_dono)
        ON DELETE CASCADE,
    CONSTRAINT chave_item
        FOREIGN KEY(nome_item)
            REFERENCES itens(nome)
        ON DELETE CASCADE
);
--Abaixo daqui são somente tabelas relacionais.
CREATE TABLE aprende_ataque(
    num_poke SMALLINT NOT NULL,
    num_forma SMALLINT NOT NULL,
    nome_ataque VARCHAR NOT NULL,
    CONSTRAINT chave_pokemon
        FOREIGN KEY(num_poke,num_forma)
            REFERENCES pokemons(numero,forma)
        ON DELETE CASCADE,
    CONSTRAINT chave_ataque
        FOREIGN KEY(nome_ataque)
            REFERENCES ataques(nome)
        ON DELETE CASCADE
);
CREATE TABLE do_tipo(
    num_poke SMALLINT NOT NULL,
    num_forma SMALLINT NOT NULL,
    nome_tipo VARCHAR NOT NULL,
    pos SMALLINT,
    CONSTRAINT chave_pokemon
        FOREIGN KEY(num_poke,num_forma)
            REFERENCES pokemons(numero,forma)
        ON DELETE CASCADE,
    CONSTRAINT chave_tipo
        FOREIGN KEY(nome_tipo)
            REFERENCES tipos(nome)
        ON DELETE CASCADE
);
CREATE TABLE pode_ter_habilidade(
    num_poke SMALLINT NOT NULL,
    num_forma SMALLINT NOT NULL,
    nome_habilidade VARCHAR NOT NULL,
    CONSTRAINT chave_pokemon
        FOREIGN KEY(num_poke,num_forma)
            REFERENCES pokemons(numero,forma)
        ON DELETE CASCADE,
    CONSTRAINT chave_habilidade
        FOREIGN KEY(nome_habilidade)
            REFERENCES habilidades(nome)
        ON DELETE CASCADE
);
CREATE TABLE sabe_ataque(
    nome_equipe VARCHAR NOT NULL,
    nome_dono VARCHAR NOT NULL,
    pos_equipe SMALLINT NOT NULL,
    pos_ataque SMALLINT NOT NULL,
    nome_ataque VARCHAR,
    PRIMARY KEY(nome_equipe,nome_dono,pos_equipe,pos_ataque),
    CONSTRAINT chave_ataque
        FOREIGN KEY(nome_ataque)
            REFERENCES ataques(nome)
        ON DELETE CASCADE,
    CONSTRAINT chave_membro
        FOREIGN KEY(nome_equipe,nome_dono,pos_equipe)
            REFERENCES membros_de_time(nome_equipe,nome_dono,pos_equipe)
        ON DELETE CASCADE
        ON UPDATE CASCADE
);
CREATE TABLE relacao_tipos(
    atacante VARCHAR NOT NULL,
    defensor VARCHAR NOT NULL,
    multiplicador FLOAT NOT NULL,
    CONSTRAINT chave_tipo1
        FOREIGN KEY(atacante)
            REFERENCES tipos(nome)
        ON DELETE CASCADE,
    CONSTRAINT chave_tipo2
        FOREIGN KEY(defensor)
            REFERENCES tipos(nome)
        ON DELETE CASCADE
);
CREATE TABLE evolui_para(
    pre_evo_num SMALLINT NOT NULL,
    pre_evo_forma SMALLINT NOT NULL,
    pos_evo_num SMALLINT NOT NULL,
    pos_evo_forma SMALLINT NOT NULL,
    CONSTRAINT chave_pokemon_pre
        FOREIGN KEY(pre_evo_num,pre_evo_forma)
            REFERENCES pokemons(numero,forma)
        ON DELETE CASCADE,
    CONSTRAINT chave_pokemon_pos
        FOREIGN KEY(pos_evo_num,pos_evo_forma)
            REFERENCES pokemons(numero,forma)
        ON DELETE CASCADE
);

--Views

CREATE VIEW ver_times
AS 
SELECT * 
FROM equipes
WHERE privado = false
OR nome_dono = session_user;

CREATE VIEW ver_membros
AS
SELECT membros_de_time.*
FROM membros_de_time, equipes
WHERE (equipes.privado = false OR equipes.nome_dono = session_user)
AND equipes.nome_dono = membros_de_time.nome_dono
AND membros_de_time.nome_equipe = equipes.nome;

CREATE VIEW pokedex_tabela
AS 
SELECT pokemons.numero as numero, pokemons.nome as nome, tipo1.nome_tipo as tipo_1, tipo2.nome_tipo as tipo_2, pokemons.color as cor, pokemons.body_shape as formato
FROM pokemons
LEFT JOIN do_tipo as tipo1 ON (pokemons.numero = tipo1.num_poke AND pokemons.forma = tipo1.num_forma AND tipo1.pos = 1)
LEFT JOIN do_tipo as tipo2 ON (pokemons.numero = tipo2.num_poke AND pokemons.forma = tipo2.num_forma AND tipo2.pos = 2);

-- meu monstrinho <3
CREATE VIEW membros_tabela
AS
SELECT pokemons.nome as nome, ver_membros.apelido as apelido, ver_membros.nome_item as item, ver_membros.nome_habilidade as habilidade, atk1.nome_ataque as ataque1, atk2.nome_ataque as ataque2, atk3.nome_ataque as ataque3, atk4.nome_ataque as ataque4, ver_membros.nome_equipe as equipe, ver_membros.nome_dono as dono, ver_membros.pos_equipe as posicao
FROM ver_membros --pokemons, sabe_ataque atk1, sabe_ataque atk2, sabe_ataque atk3, sabe_ataque atk4
JOIN pokemons ON (ver_membros.num_poke = pokemons.numero AND ver_membros.num_forma = pokemons.forma)
LEFT JOIN sabe_ataque atk1 ON (ver_membros.nome_equipe = atk1.nome_equipe AND ver_membros.nome_dono = atk1.nome_dono AND ver_membros.pos_equipe = atk1.pos_equipe AND atk1.pos_ataque = 1)
LEFT JOIN sabe_ataque atk2 ON (ver_membros.nome_equipe = atk2.nome_equipe AND ver_membros.nome_dono = atk2.nome_dono AND ver_membros.pos_equipe = atk2.pos_equipe AND atk2.pos_ataque = 2)
LEFT JOIN sabe_ataque atk3 ON (ver_membros.nome_equipe = atk3.nome_equipe AND ver_membros.nome_dono = atk3.nome_dono AND ver_membros.pos_equipe = atk3.pos_equipe AND atk3.pos_ataque = 3)
LEFT JOIN sabe_ataque atk4 ON (ver_membros.nome_equipe = atk4.nome_equipe AND ver_membros.nome_dono = atk4.nome_dono AND ver_membros.pos_equipe = atk4.pos_equipe AND atk4.pos_ataque = 4)
ORDER BY ver_membros.pos_equipe;

--Procedures

CREATE PROCEDURE insere_time(nome_i VARCHAR, privado_i BOOLEAN)
LANGUAGE sql
BEGIN ATOMIC
    INSERT INTO equipes(nome,nome_dono,privado)
    VALUES (nome_i, session_user,privado_i);
END;

CREATE PROCEDURE delete_time (nome_equipe_i VARCHAR)
LANGUAGE sql
BEGIN ATOMIC
    DELETE FROM equipes
    WHERE nome_dono = session_user AND nome_dono = nome_equipe_i;
END;

CREATE PROCEDURE insere_pokemon_time(nome_equipe_i VARCHAR, num_forma_i SMALLINT, num_poke_i SMALLINT)
LANGUAGE plpgsql
as $$
DECLARE
    num_pokemons SMALLINT := (SELECT count(pos_equipe) FROM membros_de_time WHERE nome_dono = session_user AND nome_equipe = nome_equipe_i);
BEGIN
    IF (num_pokemons) < 6 THEN
        INSERT INTO membros_de_time(nome_equipe,nome_dono,num_poke,num_forma,pos_equipe)
        VALUES (nome_equipe_i, session_user, num_poke_i, num_forma_i, num_pokemons+1);
        INSERT INTO sabe_ataque(nome_equipe,nome_dono,pos_equipe,pos_ataque,nome_ataque)
        VALUES (nome_equipe_i, session_user, num_pokemons+1, 1, null),
               (nome_equipe_i, session_user, num_pokemons+1, 2, null),
               (nome_equipe_i, session_user, num_pokemons+1, 3, null),
               (nome_equipe_i, session_user, num_pokemons+1, 4, null);
    END IF;
END $$;

CREATE PROCEDURE edit_pokemon_time(nome_equipe_i VARCHAR, pos_equipe_i SMALLINT, atributo VARCHAR, mudanca VARCHAR)
LANGUAGE plpgsql
as $$
BEGIN
    IF (atributo = 'apelido') THEN
        UPDATE membros_de_time
        SET apelido = mudanca
        WHERE nome_dono = session_user AND nome_equipe = nome_equipe_i AND pos_equipe = pos_equipe_i;
    ELSIF (atributo = 'item') THEN
        UPDATE membros_de_time
        SET nome_item = mudanca
        WHERE nome_dono = session_user AND nome_equipe = nome_equipe_i AND pos_equipe = pos_equipe_i;
    ELSIF (atributo = 'habilidade') THEN
        UPDATE membros_de_time
        SET nome_habilidade = mudanca
        WHERE nome_dono = session_user AND nome_equipe = nome_equipe_i AND pos_equipe = pos_equipe_i;
    END IF;
END $$;

CREATE PROCEDURE edit_ataque_pokemon(nome_equipe_i VARCHAR, pos_equipe_i SMALLINT, pos_ataque_i SMALLINT, nome_ataque_i VARCHAR)
LANGUAGE sql
BEGIN ATOMIC
    UPDATE sabe_ataque
    SET nome_ataque = nome_ataque_i
    WHERE nome_dono = session_user AND nome_equipe = nome_equipe_i AND pos_equipe = pos_equipe_i AND pos_ataque = pos_ataque_i;
END;

CREATE PROCEDURE delete_pokemon_time(nome_equipe_i VARCHAR, pos_equipe_i SMALLINT)
LANGUAGE sql
BEGIN ATOMIC
    DELETE FROM membros_de_time
    WHERE nome_dono = session_user AND nome_equipe = nome_equipe_i AND pos_equipe = pos_equipe_i;

    UPDATE membros_de_time
    SET pos_equipe = pos_equipe-1
    WHERE nome_dono = session_user AND nome_equipe = nome_equipe_i AND pos_equipe > pos_equipe_i;
END;

CREATE PROCEDURE delete_usuario(usuario VARCHAR)
language plpgsql
as $$
BEGIN
    EXECUTE FORMAT('DELETE FROM usuarios where username = ''%I''',usuario);
    EXECUTE FORMAT('DROP OWNED BY %I',usuario);
    EXECUTE FORMAT('DROP USER %I',usuario);
END $$;


CREATE PROCEDURE novo_user(username_i VARCHAR, senha_i VARCHAR)
LANGUAGE plpgsql
as $$
BEGIN
    EXECUTE FORMAT('CREATE ROLE %I PASSWORD %L LOGIN SUPERUSER', username_i, senha_i);
    EXECUTE FORMAT('INSERT INTO usuarios(username) VALUES (''%I'')',username_i);
END $$;

CREATE USER anon LOGIN PASSWORD '123' SUPERUSER;
