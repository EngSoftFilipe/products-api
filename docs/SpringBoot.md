# Spring Boot

> ## Importante
- O Application é o inicializador do servidor, ele deve ficar na raiz do pacote, porque o Spring procura as outras classes (controllers, repositories, configurações) a partir do pacote dele para baixo. Classes fora desse pacote não são encontradas
- No repository, o Spring lê o nome do método e monta a consulta sozinho (ex: findByNameContainingIgnoreCase), dê preferência por padrões de mercado
> ## Anotações

### @RestController
- Diz para o Spring que essa classe vai receber requisições REST
- Usado nas classes controller
- O retorno dos métodos vira JSON automaticamente

### @RequestMapping
- Diz qual é a URL base do controller
- Usado em cima da classe controller

### @RequestBody
- Diz **o quê** a requisição está mandando (os dados)
- Só pega dados do corpo da requisição (o JSON)
- Exemplo prático: quero cadastrar um mouse de 150 reais. Na requisição, mando os dados no corpo em JSON, e o `@RequestBody` transforma esse JSON num objeto `Product` já preenchido
```java
@PostMapping
public Product saveProduct(@RequestBody Product product){}
 
// POST /products
// { "name": "Mouse", "price": 150.0 }
// product.getName() = "Mouse" | product.getPrice() = 150.0
```
### @PathVariable
- Diz **qual** item a requisição quer (ex: qual id)
- Só pega dados da URL
- Exemplo prático: tenho 10 produtos cadastrados e quero ver só o de id 5. Na requisição, coloco o 5 na URL (`/products/5`), e o `@PathVariable` entrega esse 5 para a variável `id`
```java
@GetMapping("/{id}")
public ResponseEntity<Product> getById(@PathVariable Long id){}
 
// GET /products/5 → id = 5
```

- Só lê a URL: se o valor vier no JSON do corpo, quem pega é o `@RequestBody`
### @RequestParam
- Diz **como** a requisição quer os dados (ex: filtrar pelo nome)
- Só pega dados que vêm depois do `?` na URL
- Exemplo prático: tenho 10 produtos cadastrados e quero só os que têm "mouse" no nome. Na requisição, coloco `?name=mouse` no final da URL, e o `@RequestParam` entrega esse "mouse" para a variável `name`
```java
@GetMapping("/search")
public List<Product> getByName(@RequestParam String name){}
 
// GET /products/search?name=mouse → name = "mouse"
```

- Se o parâmetro puder não vir, usa `@RequestParam(required = false)`. Assim, sem o `?name=`, a variável chega `null` em vez de dar erro

### @Autowired
- Injeção de dependência (DI)
- Avisa ao Spring que ele deve **entregar** naquele campo um objeto que ele já criou (ex: o repository)
- Usada quando a classe **não recebe** a dependência pelo construtor (ex: nas classes de teste, criadas pelo JUnit)
- Exemplo prático: no teste, quero usar o repository para salvar um produto. Coloco o `@Autowired` em cima do campo, e o Spring entrega o repository pronto. Sem ele, o campo fica `null` e dá `NullPointerException`

```java
@Autowired
private ProductRepository productRepository;
```
### @Configuration
- Diz para o Spring que essa classe **fabrica objetos** para ele guardar
- Usado em cima de uma classe de configuração, que contém métodos `@Bean`

### @Bean
- Diz para o Spring **guardar o objeto que o método devolve**, para depois entregar onde for pedido
- Usado em cima de um **método**, dentro de uma classe `@Configuration`
- Exemplo prático: a montadora (`@Configuration`) fabrica o motor (`@Bean`) com cavalos, cilindros e modelo já definidos. Depois, quem precisar do motor só pede no construtor, e o Spring entrega o motor pronto

```java
// Classe comum, sem anotação: é só o "molde" do motor
public class Motor {
    private String modelo;
    private Integer cavalos;
    private Integer cilindros;
    private Double litragem;

    // getters e setters
}

// A montadora: fabrica o motor e entrega para o Spring guardar
@Configuration
public class MontadoraConfiguration {

    @Bean
    public Motor motor() {
        Motor motor = new Motor();
        motor.setModelo("XPTO-0");
        motor.setCavalos(120);
        motor.setCilindros(4);
        motor.setLitragem(2.0);
        return motor;
    }
}
```

#### Onde entra a injeção de dependência
- **Dependência** = algo que a classe precisa para funcionar (o carro **depende** do motor)
- **Injetar** = o Spring **entrega** essa dependência pronta, em vez de a classe criar com `new`
- Ou seja: a classe só diz **o que precisa**, e o Spring decide **de onde vem**

**Não recomendado:** sem injeção, o carro fabrica o próprio motor

```java
public class CarroController {
    private Motor motor = new Motor(); // o carro fica preso a esse motor, vazio e sem configuração
}
```

**Recomendado:** com injeção pelo construtor, o carro só pede o motor, e o Spring entrega o da montadora

```java
@RestController
@RequestMapping("/carros")
public class CarroController {

    private final Motor motor;

    public CarroController(Motor motor) { // "preciso de um motor"
        this.motor = motor;               // o Spring entrega o motor do @Bean
    }

    @GetMapping("/motor")
    public Motor getMotor() {
        return motor;
    }
}

// GET /carros/motor
// { "modelo": "XPTO-0", "cavalos": 120, "cilindros": 4, "litragem": 2.0 }
```

**Funciona, mas não é o ideal:** com `@Autowired` no atributo. Não é recomendado fora dos testes, porque a classe pode ser criada sem a dependência (o motor fica `null` e gera `NullPointerException`), o atributo não pode ser `final` e as dependências ficam escondidas, em vez de aparecerem no construtor

```java
@Autowired
private Motor motor;
```

- Nos testes, o `@Autowired` no atributo é aceito porque quem cria a classe de teste é o JUnit, então não dá para passar nada pelo construtor

#### Por que isso é bom
- Para trocar o motor (ex: de 120 para 200 cavalos), mexo **só na montadora**, sem tocar no carro
- O carro não precisa saber **como** o motor é fabricado, só que ele existe
- Nos testes, dá para entregar um motor diferente para o carro sem alterar o código dele
- O Spring cria o motor **uma vez só** e entrega o mesmo objeto para todo mundo que pedir (singleton)

#### Quando usar cada um
- Classe minha --> `@Component`, `@Service` ou `@Repository` (o Spring cria sozinho)
- Classe de biblioteca, que não posso alterar, ou que precisa de configuração --> `@Bean` (eu crio e o Spring guarda)
- Nos dois casos, quem usa **pede pelo construtor**, e isso é a injeção de dependência

### @Qualifier
- Diz para o Spring **qual bean entregar** quando existe mais de um do mesmo tipo
- Usado na hora de **pedir** o bean (no parâmetro do construtor), junto com o nome do bean
- Exemplo prático: a montadora agora fabrica dois motores, um aspirado e um turbo. Se o carro só pedir "um motor", o Spring não sabe qual entregar e dá erro. Com o `@Qualifier`, o carro diz qual motor quer pelo nome

```java
@Configuration
public class MontadoraConfiguration {

    @Bean(name = "motorAspirado") // dá o nome na hora de criar
    public Motor motorAspirado() {
        Motor motor = new Motor();
        motor.setCavalos(120);
        return motor;
    }

    @Bean(name = "motorTurbo")
    public Motor motorTurbo() {
        Motor motor = new Motor();
        motor.setCavalos(180);
        return motor;
    }
}
```

**Sem o `@Qualifier`:** o Spring encontra dois motores e não sabe qual entregar

```java
public CarroController(Motor motor) { }

// Erro ao iniciar: NoUniqueBeanDefinitionException
```

**Com o `@Qualifier`:** o carro escolhe o motor pelo nome

```java
public CarroController(@Qualifier("motorTurbo") Motor motor) {
    this.motor = motor;
}

// motor.getCavalos() = 180
```

- `@Bean(name = "...")` dá o nome na hora de **criar**
- `@Qualifier("...")` escolhe pelo nome na hora de **pedir**

### @Primary
- Diz para o Spring **qual bean é o padrão** quando existe mais de um do mesmo tipo
- Usado em cima do método `@Bean` (ou da classe `@Component`) que deve ser entregue quando ninguém diz qual quer
- Exemplo prático: a maioria dos carros usa o motor aspirado e só alguns usam o turbo. Marco o aspirado como `@Primary`, e só quem quer o turbo precisa usar o `@Qualifier`

```java
@Bean(name = "motorAspirado")
@Primary // se ninguém disser qual, entrega este
public Motor motorAspirado() {
    Motor motor = new Motor();
    motor.setCavalos(120);
    return motor;
}
```

```java
public CarroController(Motor motor) { }
// recebe o motorAspirado (o padrão)

public CarroEsportivoController(@Qualifier("motorTurbo") Motor motor) { }
// recebe o motorTurbo (escolhido pelo nome)
```

#### Quando usar cada um
- **`@Primary`:** quando um dos beans é o padrão e o outro é usado só de vez em quando
- **`@Qualifier`:** quando cada lugar precisa escolher um bean específico
- **Os dois juntos:** funciona, e o `@Qualifier` ganha, porque é a escolha mais específica

> ## Classes Spring

### ResponseEntity
- É uma classe do Spring que representa a resposta HTTP inteira
- Possui métodos que abstraem os status HTTP
#### Métodos mais usados
- `ok()`: Deu certo e devolve algo (**200**)
- `notFound()`: Não achou o que estava procurando (**404**)
- `noContent()`: Deu certo, mas não tem nada para devolver (**204**)
- `build()`: Finaliza a resposta sem corpo
- `body()`: Finaliza a resposta com corpo

### JpaRepository
- Interface do Spring Data que já traz os métodos prontos de acesso ao banco

#### Métodos mais usados
- `existsById()`: Confere se o ID existe
- `findById()`: Procura pelo ID