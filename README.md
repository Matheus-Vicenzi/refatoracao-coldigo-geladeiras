# REFATORAÇÃO COLDIGO

## Tópicos relevantes

- OT16: Alterar a pesquisa sobre ZEROFILL e UNSIGNED para INDEX
- Classe UtilRest: Reestruturar a classe para que os erros e mensagens sejam parametrizáveis e que possam ser utilizados os status code corretos em determinadas circunstancias
- Rotas REST: Padronizar as rotas para seguir o padrão rest correto, EX: Alterar de PUT“/produtos/alterar” para PUT”/produtos”
- Rota PATCH: Alterar o verbo da rota “/inativar” para patch, pois está sendo realizada a edição parcial do conteúdo

## Como rodar o projeto (DESENVOLVIMENTO)

- Baixar no vscode as extensões:
  - Extension Pack for Java
  - Spring Boot Extension Pack
- No vscode, abra o arquivo src/main/java/com/coldigo/br/coldigo/ColdigoApplication.java, em seguida, clique no icone de "play" no canto superior direito do vscode, ou, no botão "run" que aparecerá acima do método main

- Banco de dados docker (opcional)
  - Executar no diretorio raiz do projeto o comando "docker compose up"
