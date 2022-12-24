package com.algaworks.algafood.api.controller;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.CozinhaRepository;
import com.algaworks.algafood.domain.repository.RestauranteRepository;
import com.algaworks.algafood.infrastucture.repository.spec.RestauranteSpec;

@RestController
@RequestMapping(value = "/teste")
public class TesteController {

	@Autowired
	private CozinhaRepository cozinhaRepository;

	@Autowired
	private RestauranteRepository restauranteRepository;

	@GetMapping("/cozinhas/por-nome")
	public List<Cozinha> cozinhasPorNome(@RequestParam("nome") String nome) {
		return cozinhaRepository.findTodasByNome(nome);
	}

	@GetMapping("/cozinhas/unica-por-nome")
	public Optional<Cozinha> cozinhaPorNome(@RequestParam("nome") String nome) {
		return cozinhaRepository.findByNome(nome);
	}

	@GetMapping("/cozinhas/por-nome-containing")
	public List<Cozinha> cozinhasPorNomeContaining(@RequestParam("nome") String nome) {
		return cozinhaRepository.findTodasByNomeContaining(nome);
	}

	@GetMapping("/restaurantes/por-taxa-frete")
	public List<Restaurante> restaurantePorTaxaFrete(BigDecimal taxaInicial, BigDecimal taxaFinal) {
		return restauranteRepository.findByTaxaFreteBetween(taxaInicial, taxaFinal);
	}

	@GetMapping("/restaurantes/por-nome-cozinha")
	public List<Restaurante> restaurantePorNomeECozinha(String nome, Long cozinhaId) {
		return restauranteRepository.consultarPorNome(nome, cozinhaId);
	}

	@GetMapping("/restaurantes/primeiro-por-nome")
	public Optional<Restaurante> restaurantePrimeiroPorNome(String nome) {
		return restauranteRepository.findFirstRestauranteByNomeContaining(nome);
	}

	@GetMapping("/restaurantes/top2-por-nome")
	public List<Restaurante> restaurantesTop2PorNome(String nome) {
		return restauranteRepository.findTop2ByNomeContaining(nome);
	}

	@GetMapping("/cozinhas/exists")
	public boolean cozinhaExists(String nome) {
		return cozinhaRepository.existsByNome(nome);
	}

	@GetMapping("/restaurantes/count-por-cozinha")
	public int restaurantesCountPorCozinha(Long cozinhaId) {
		return restauranteRepository.countByCozinhaId(cozinhaId);
	}

	// Método customizado:
	@GetMapping("/restaurantes/por-nome-e-taxa-frete")
	public List<Restaurante> restaurantePorNomeFrete(String nome, BigDecimal taxaFreteInicial,
			BigDecimal taxaFreteFinal) {
		return restauranteRepository.find(nome, taxaFreteInicial, taxaFreteFinal);
	}

	// Método customizado:
	@GetMapping("/restaurantes/por-nome-e-taxa-frete-2")
	public List<Restaurante> restaurantePorNomeFrete2(String nome, BigDecimal taxaFreteInicial,
			BigDecimal taxaFreteFinal) {
		return restauranteRepository.find2(nome, taxaFreteInicial, taxaFreteFinal);
	}

	// Método com Criteria Api:
	@GetMapping("/restaurantes/por-nome-criteria")
	public List<Restaurante> restaurantePorNomeCriteria(String nome, BigDecimal taxaFreteInicial,
			BigDecimal taxaFreteFinal) {
		return restauranteRepository.find3(nome, taxaFreteInicial, taxaFreteFinal);
	}

	// Método com Criteria Api adicionando restrições na cláusula where:
	@GetMapping("/restaurantes/por-nome-criteria2")
	public List<Restaurante> restaurantePorNomeCriteria2(String nome, BigDecimal taxaFreteInicial,
			BigDecimal taxaFreteFinal) {
		return restauranteRepository.find4(nome, taxaFreteInicial, taxaFreteFinal);
	}

	// Método com Criteria Api adicionando restrições na cláusula where e filtros
	// dinâmicos:
	@GetMapping("/restaurantes/por-nome-criteria3")
	public List<Restaurante> restaurantePorNomeCriteria3(String nome, BigDecimal taxaFreteInicial,
			BigDecimal taxaFreteFinal) {
		return restauranteRepository.find5(nome, taxaFreteInicial, taxaFreteFinal);
	}

	@GetMapping("/restaurantes/com-frete-gratis")
	public List<Restaurante> restauranteComFreteGratis(String nome) {
		return restauranteRepository
				.findAll(RestauranteSpec.comFreteGratis().and(RestauranteSpec.comNomeSemelhante(nome)));

	}

	@GetMapping("/restaurantes/com-frete-gratis-2")
	public List<Restaurante> restauranteComFreteGratis2(String nome) {
		return restauranteRepository.findComFreteGratis(nome);

	}
	
	@GetMapping("/restaurantes/primeiro-aula-5.20")
	public Optional<Restaurante> restaurantePrimeiroAula5_20() {
		return restauranteRepository.buscarPrimeiro();
	}

	@GetMapping("/cozinhas/primeiro-aula-5.20")
	public Optional<Cozinha> cozinhaPrimeiroAula5_20() {
		return cozinhaRepository.buscarPrimeiro();
	}

}
