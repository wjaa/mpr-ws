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


### SSL
Utilizamos o letsencrypt + certbot para gerar o certificado da api.meuportaretrato.com e stc.meuportaretrato.com

Toda a configuração dela está no docker-compose e confs dos nginx.

IMPORTANTE: Quando houver a migração de servidores, precisamos copiar a pasta **_data_** que está na raiz do projeto no servidor, ou teremos que rodar o script **_init-letsencrypt.sh_**. Ele é responsável por criar um certificado fake até o nginx iniciar e depois faz requisições para letsencrypt gerar e validar um novo certificado.
   