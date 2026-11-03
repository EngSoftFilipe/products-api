# Spring Boot

## Anotações

### @RestController
- Diz para o Spring que essa classe vai receber requisições REST
- Usado nas classes controller
- O retorno dos métodos vira JSON automaticamente

### @RequestMapping
- Diz qual é a URL base do controller
- Usado em cima da classe controller

### @RequestBody
- Pega o JSON da requisição e transforma num objeto Java
- Usado no parâmetro do método: saveProduct(@RequestBody Product product)