### Api-MPR


### Instalaçao
Pre requisitos do servidor
docker
git
criar pasta
/var/www/static/mpr

```sh
sudo ./install.sh
```

### OAuth2

Existem 3 camadas da API do MPR.
   - APP
   - USER
   - ADMIN

Para cada camada existem um tipo de usuário especifico para acessa-las.
   - APP - São usuários de aplicação (WEB, MOBILE e terceiros).
   - USER - São usuários de clientes para áreas mais sensiveis que exigem autenticação deles.
   - ADMIN - Usuário com alto poder dentro da API, utilizado apenas no backoffice.

Veja como obter o token de acesso para cada tipo de usuário.
https://api.meuportaretrato.com/oauth/token
   - APP - É um Basic Authentication passando usuário e senha 'encodado' no header.

```sh
    curl -v -H "Authorization: Basic Y2xpZW50ZTpjbGllbnRl" -H "Content-Type: application/x-www-form-urlencoded"
    -d 'grant_type=client_credentials' -X POST https://api.meuportaretrato.com/oauth/token
```
### Continuar documentacao do OAuth2....



### Pastas de imagem
As pastas das imagens estão dentro da estrutura de estáticos.
`Raiz: /var/www/static/mpr/images`
   - Catalogo: ${raiz}/catalogo
   - Preview: ${raiz}/p
   - Destaque: ${raiz}/d
   - Cliente: ${raiz}/cliente
   - Preview Cliente: ${raiz}/preview_cliente
   
A classe **ImageService** é responsavel por centralizar a escrita e montagem do path das imagens.   