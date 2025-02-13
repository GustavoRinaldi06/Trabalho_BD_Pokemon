import psycopg2
import pokebase as pb

ataquesAdicionar = set()
habilidadesAdicionar = set()
pokemonAdicionados = dict()
formatosDosPokemon = []
numpokemon = 151
adicionouAtaque = False


cores = [["red", 15073331], ["blue",38361], ["yellow",16767232],
         ["green",4109168], ["black",2829099], ["brown",9850946],
         ["purple",8931480], ["gray",8224125], ["white",16777215],
         ["pink",14911128]]

with open("info.txt") as info:
    username = info.readline().strip()
    senha = info.readline().strip()
#informações de conexão aqui 
#                       V
conn = psycopg2.connect(user=username, password=senha, dbname="pokemon")
cursor = conn.cursor()


formatos = pb.APIResourceList("pokemon-shape")
print("formas")
for formato in formatos:
    print(formato['name'])
    with open("shape-sprites/"+formato['name']+".png", "rb") as sprite:
        cursor.execute(f"INSERT INTO shape(nome,sprite) VALUES ('{formato['name']}',{psycopg2.Binary(sprite.read())});")
    formatosDosPokemon.append([formato['name'],formato])
conn.commit()
print(cursor.statusmessage)

#TODO: selecionar itens à serem adicionados ao banco de dados. uns 20 deve dar

print("cores")
for cor in cores:
    cursor.execute("INSERT INTO cor(nome,hex_code) VALUES ('"+str(cor[0])+"',"+str(cor[1])+");")
conn.commit()
print(cursor.statusmessage)

for i in range(18):
    tipo = pb.APIResource("type",i+1)
    cursor.execute("INSERT INTO tipos(nome) VALUES ('"+tipo.name+"');")
    print("Inserindo tipo "+tipo.name+" na tabela. "+str(i)+"/18")
cursor.execute("INSERT INTO tipos(nome) VALUES ('none');")
conn.commit()
print(cursor.statusmessage)


for i in range (18):
    atacante = pb.APIResource("type",i+1)
    print("Inserindo relações do tipo "+atacante.name+" na tabela. "+str(i)+"/18")
    nomeATK = atacante.name
    for defensor in atacante.damage_relations.double_damage_to:
        nomeDEF = defensor.name
        cursor.execute("INSERT INTO relacao_tipos (atacante,defensor,multiplicador) VALUES('"+nomeATK+"', '"+nomeDEF+"', 2);")
    for defensor in atacante.damage_relations.half_damage_to:
        nomeDEF = defensor.name
        cursor.execute("INSERT INTO relacao_tipos (atacante,defensor,multiplicador) VALUES('"+nomeATK+"', '"+nomeDEF+"', 0.5);")
    for defensor in atacante.damage_relations.no_damage_to:
        nomeDEF = defensor.name
        cursor.execute("INSERT INTO relacao_tipos (atacante,defensor,multiplicador) VALUES('"+nomeATK+"', '"+nomeDEF+"', 0);")
conn.commit()
print(cursor.statusmessage)


for i in range(numpokemon):
    specie = pb.APIResource("pokemon-species",i+1)
    f = 0
    for versao in specie.varieties:
        stats = dict()
        forma = pb.APIResource('pokemon-form',versao.pokemon.name)
        
        if forma.sprites.front_default != None:

            print("Pokemon: "+versao.pokemon.name+" "+str(i+1)+"/151")
            pokemonAdicionados[versao.pokemon.name] = (i+1,f)

            for ataque in versao.pokemon.moves:
                ataquesAdicionar.add(ataque.move.name)

            for habilidade in versao.pokemon.abilities:
                habilidadesAdicionar.add(habilidade.ability.name)

        
            selec = not forma.is_battle_only

            for stat in versao.pokemon.stats:
                stats[stat.stat.name] = stat.base_stat
        
            cursor.execute(f"""INSERT INTO pokemons(nome,numero,forma,hp,speed,atk,spatk,def,spdef,color,body_shape,sprite,selecionavel)
                           VALUES('{versao.pokemon.name}',{i+1},{f},
                           {stats['hp']},{stats['speed']},
                           {stats['attack']},{stats['special-attack']},
                           {stats['defense']},{stats['special-defense']},
                           '{specie.color.name}','{specie.shape.name}',{psycopg2.Binary(pb.SpriteResource("pokemon",versao.pokemon.id).img_data)},
                           {str(selec)});""")
        
            if len(versao.pokemon.types) == 1:
                cursor.execute(f"INSERT INTO do_tipo(num_poke,num_forma,nome_tipo,pos) VALUES({i+1},{f},'{versao.pokemon.types[0].type.name}',1);")
                cursor.execute(f"INSERT INTO do_tipo(num_poke,num_forma,nome_tipo,pos) VALUES({i+1},{f},'none',2)")
            else:
                cursor.execute(f"INSERT INTO do_tipo(num_poke,num_forma,nome_tipo,pos) VALUES({i+1},{f},'{versao.pokemon.types[0].type.name}',1);")
                cursor.execute(f"INSERT INTO do_tipo(num_poke,num_forma,nome_tipo,pos) VALUES({i+1},{f},'{versao.pokemon.types[1].type.name}',2);")

            f +=1

conn.commit()
print(cursor.statusmessage)

cont = 0
for ataque in ataquesAdicionar:
    print("ataque "+ataque+" "+str(cont)+"/"+str(len(ataquesAdicionar)))
    atk = pb.APIResource("move",ataque)
    poder = atk.power
    precisao = atk.accuracy
    tipo = atk.type.name
    if poder == None:
        poder = 0
    if precisao == None:
        precisao = 0
    if tipo == None:
        tipo = "none"
    if len(atk.effect_entries) >=1:
        for descricao in atk.effect_entries:
            if descricao.language.name == 'en':
                texto = descricao.short_effect.replace("'","''")

        cursor.execute(f"""INSERT INTO ataques(nome,power,accuracy,texto,tipo_de_ataque,nome_tipo) VALUES(
                        '{ataque}',{poder},{precisao},'{texto}','{atk.damage_class.name}',
                        '{tipo}');""")
    
        for poke in atk.learned_by_pokemon:
            if poke.name in pokemonAdicionados.keys():
                cursor.execute(f"""INSERT INTO aprende_ataque(num_poke,num_forma,nome_ataque)
                               VALUES({pokemonAdicionados[poke.name][0]},{pokemonAdicionados[poke.name][1]},'{ataque}');""")
    cont+=1

conn.commit()
print(cursor.statusmessage)

cont = 0
for habilidade in habilidadesAdicionar:
    print(f"habilidade: {habilidade} ({cont}/{len(habilidadesAdicionar)})")
    ability = pb.APIResource('ability',habilidade)
    for descricao in ability.effect_entries:
        if descricao.language.name == 'en':
            texto = descricao.effect.replace("'","''")
    cursor.execute(f"INSERT INTO habilidades(nome,texto) VALUES ('{habilidade}','{texto}')")

    for poke in ability.pokemon:
        if poke.pokemon.name in pokemonAdicionados.keys():
            cursor.execute(f"""INSERT INTO pode_ter_habilidade(num_poke,num_forma,nome_habilidade)
                           VALUES({pokemonAdicionados[poke.pokemon.name][0]},
                           {pokemonAdicionados[poke.pokemon.name][1]},
                           '{habilidade}');""")
    cont +=1

conn.commit()
print(cursor.statusmessage)

with open("evolutions.txt","r") as documento:
    linha = documento.readline().strip()
    while linha != "":
        pokes = linha.split()
        print(linha)
        if (pokes[0] in pokemonAdicionados.keys()) and (pokes[1] in pokemonAdicionados.keys()):
            cursor.execute(f"""INSERT INTO evolui_para(pre_evo_num,pre_evo_forma,pos_evo_num,pos_evo_forma)
                           VALUES({pokemonAdicionados[pokes[0]][0]},{pokemonAdicionados[pokes[0]][1]},
                           {pokemonAdicionados[pokes[1]][0]},{pokemonAdicionados[pokes[1]][1]});""")
        linha = documento.readline().strip()

conn.commit()
print(cursor.statusmessage)

items = pb.APIResource("item-category","held-items")

for item in items.items:
    print(item.name)
    if len(item.effect_entries) > 0:
        for entry in item.effect_entries:
            if entry.language.name == "en":
                texto = entry.effect.replace("'","''")
                
    cursor.execute(f"INSERT INTO itens(nome,descricao) VALUES ('{item.name}','{texto}')")

conn.commit()
print(cursor.statusmessage)

conn.close()


print("cabou")
