package com.algaworks.algafood;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.hasSize;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.TestPropertySource;

import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.CozinhaRepository;
import com.algaworks.algafood.domain.repository.RestauranteRepository;
import com.algaworks.algafood.util.DatabaseCleaner;
import com.algaworks.algafood.util.ResourceUtils;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("/application-test.properties")
public class CadastroRestauranteIT {

	@LocalServerPort
	private int port;

	@Autowired
	private DatabaseCleaner databaseCleaner;

	@Autowired
	private RestauranteRepository restauranteRepository;

	@Autowired
	private CozinhaRepository cozinhaRepository;

	private String jsonCorretoRestauranteNewYorkBarbecue;

	private String restauranteNewYorkBarbecueComCozinhaInexistente;

	private String restauranteNewYorkBarbecueSemCozinha;

	private String restauranteNewYorkBarbecueSemFrete;

	private Restaurante burgerTopRestaurante;

	private static final int RESTAURANTE_ID_INEXISTENTE = 100;

	private int quantidadeRestaurantes;

	private static final String DADOS_INVALIDOS_PROBLEM_TITLE = "Dados inválidos";

	private static final String VIOLACAO_DE_REGRA_DE_NEGOCIO_PROBLEM_TITLE = "Violação de regra de negócio";

	@BeforeEach
	public void setUp() {
		RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
		RestAssured.port = port;
		RestAssured.basePath = "/restaurantes";
		databaseCleaner.clearTables();
		prepararDados();
		jsonCorretoRestauranteNewYorkBarbecue = ResourceUtils
				.getContentFromResource("/json/correto/restaurante-new-york-barbecue.json");

		restauranteNewYorkBarbecueComCozinhaInexistente = ResourceUtils
				.getContentFromResource("/json/incorreto/restaurante-new-york-barbecue-com-cozinha-inexistente.json");

		restauranteNewYorkBarbecueSemCozinha = ResourceUtils
				.getContentFromResource("/json/incorreto/restaurante-new-york-barbecue-sem-cozinha.json");

		restauranteNewYorkBarbecueSemFrete = ResourceUtils
				.getContentFromResource("/json/incorreto/restaurante-new-york-barbecue-sem-frete.json");
	}

	@Test
	public void deveRetornarStatus200_QuandoConsultarRestaurantes() {

		given().accept(ContentType.JSON).when().get().then().statusCode(HttpStatus.OK.value());
	}

	@Test
	public void deveRetornarStatus201_QuandoCadastrarRestaurante() {

		given().body(jsonCorretoRestauranteNewYorkBarbecue).contentType(ContentType.JSON).accept(ContentType.JSON)
				.when().post().then().statusCode(HttpStatus.CREATED.value());
	}

	@Test
	public void deveRetornarStatus400_QuandoTentarCadastrarRestauranteSemFrete() {

		given().body(restauranteNewYorkBarbecueSemFrete).contentType(ContentType.JSON).accept(ContentType.JSON).when()
				.post().then().statusCode(HttpStatus.BAD_REQUEST.value())
				.body("title", equalTo(DADOS_INVALIDOS_PROBLEM_TITLE));

	}

	@Test
	public void deveRetornarStatus400_QuandoTentarCadastrarRestauranteSemCozinha() {

		given().body(restauranteNewYorkBarbecueSemCozinha).contentType(ContentType.JSON).accept(ContentType.JSON).when()
				.post().then().statusCode(HttpStatus.BAD_REQUEST.value())
				.body("title", equalTo(DADOS_INVALIDOS_PROBLEM_TITLE));

	}

	@Test
	public void deveRetornarStatus400_QuandoTentarCadastrarRestauranteComCozinhaInexistente() {

		given().body(restauranteNewYorkBarbecueComCozinhaInexistente).contentType(ContentType.JSON)
				.accept(ContentType.JSON).when().post().then().statusCode(HttpStatus.BAD_REQUEST.value())
				.body("title", equalTo(VIOLACAO_DE_REGRA_DE_NEGOCIO_PROBLEM_TITLE));

	}

	@Test
	public void deveRetornarRespostaEStatusCorretos_QuandoConsultarRestauranteExistente() {

		given().pathParam("restauranteId", burgerTopRestaurante.getId()).accept(ContentType.JSON).when()
				.get("/{restauranteId}").then().statusCode(HttpStatus.OK.value())
				.body("nome", equalTo(burgerTopRestaurante.getNome()));
	}

	@Test
	public void deveRetornarStatus404_QuandoConsultarRestauranteInexistente() {

		given().pathParam("restauranteId", RESTAURANTE_ID_INEXISTENTE).accept(ContentType.JSON).when()
				.get("/{restauranteId}").then().statusCode(HttpStatus.NOT_FOUND.value());
	}

	@Test
	public void deveConterQuantidadeRestaurantes_QuandoConsultarRestaurantes() {

		given().accept(ContentType.JSON).when().get().then().body("", hasSize(quantidadeRestaurantes)).body("nome",
				hasItems("Burger Top", "Comida Mineira"));
	}

	private void prepararDados() {
		Cozinha cozinhaBrasileira = new Cozinha();
		cozinhaBrasileira.setNome("Brasileira");
		cozinhaRepository.save(cozinhaBrasileira);

		Cozinha cozinhaAmericana = new Cozinha();
		cozinhaAmericana.setNome("Americana");
		cozinhaRepository.save(cozinhaAmericana);

		burgerTopRestaurante = new Restaurante();
		burgerTopRestaurante.setNome("Burger Top");
		burgerTopRestaurante.setTaxaFrete(new BigDecimal(10));
		burgerTopRestaurante.setCozinha(cozinhaAmericana);
		restauranteRepository.save(burgerTopRestaurante);

		Restaurante comidaMineiraRestaurante = new Restaurante();
		comidaMineiraRestaurante.setNome("Comida Mineira");
		comidaMineiraRestaurante.setTaxaFrete(new BigDecimal(10));
		comidaMineiraRestaurante.setCozinha(cozinhaBrasileira);
		restauranteRepository.save(comidaMineiraRestaurante);

		quantidadeRestaurantes = (int) restauranteRepository.count();
	}

}
