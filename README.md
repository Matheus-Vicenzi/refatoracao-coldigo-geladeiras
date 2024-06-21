# REFATORAÇÃO COLDIGO

## Tópicos relevantes

- OT16: Alterar a pesquisa sobre ZEROFILL e UNSIGNED para INDEX
- Classe UtilRest: Reestruturar a classe para que os erros e mensagens sejam parametrizáveis e que possam ser utilizados os status code corretos em determinadas circunstancias
- Rotas REST: Padronizar as rotas para seguir o padrão rest correto, EX: Alterar de PUT“/produtos/alterar” para PUT”/produtos”
- Rota PATCH: Alterar o verbo da rota “/inativar” para patch, pois está sendo realizada a edição parcial do conteúdo
