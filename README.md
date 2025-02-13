Introdução:

Este trabalho consiste em um sistema de criação de times Pokémon, com uma ferramenta de gerenciamento no estilo CRUD, além da busca por times públicos criados por outros usuários e acesso à Pokédex completa da primeira geração. Na Pokédex, é possível buscar os Pokémon e acessá-los para obter mais informações e visualizar suas imagens. Na tela de busca de times só serão vistos os times do tipo público, os quais não poderão ser modificados pelos demais usuários. Espero que se divirta ao criar e analizar times Pokémon!!

Instruções para instalação:

- Dentro da pasta "Pokemon" têm o código fonte do software.
- Dentro da pasta "Gerar" tem o código de criação do banco de dados com os documentos necessários.
- O código de python depende de 2 bibliotecas, a psycopg2, que conecta ao banco de dados, e o pokebase,
  que é um wrapper para a api de onde saem as informações dos Pokémon para popular o banco de dados.
- Também depende de informações de login de superusuário.
- O código também irá criar um superusuário novo em seu banco de dados. A senha é bem previsível, então
  tome cuidado.
- O executável .jar compilado está em "Pokemon/dist".
- Use java 17 ou mais recente.

Este projeto foi realizado com muito carinho pelos alunos da UnB:

- Marina Silva Lyra
- Gustavo Rinaldi Braga de Albuquerque
- Rebeca de Souza Coutinho 
