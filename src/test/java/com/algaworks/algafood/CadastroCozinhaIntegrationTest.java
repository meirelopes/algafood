package com.algaworks.algafood;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import javax.validation.ConstraintViolationException;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.EmptyResultDataAccessException;

import com.algaworks.algafood.domain.exception.CozinhaNaoEncontradaException;
import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.service.CadastroCozinhaService;

@SpringBootTest
class CadastroCozinhaIntegrationTest {
	
	@Autowired
	private CadastroCozinhaService cadastroCozinha;
	
	@Test
	public void testarCadastroCozinhaComSucesso() {
		//CENÁRIO
		Cozinha novaCozinha = new Cozinha();
		novaCozinha.setNome("Cozinha Chinesa");
		
		//AÇÃO
		novaCozinha = cadastroCozinha.salvar(novaCozinha);
		
		//VALIDAÇÃO
		assertThat(novaCozinha).isNotNull();
		assertThat(novaCozinha.getId()).isNotNull();

	}
	
	@Test
	public void deveFalhar_QuandoCadastrarCozinhaSemNome() {
		
		  assertThrows(ConstraintViolationException.class, () -> {
		         Cozinha novaCozinha = new Cozinha();
			 novaCozinha.setNome(null);
		         novaCozinha = cadastroCozinha.salvar(novaCozinha);
		     });	
	}
	
	@Test
	public void deveFalhar_QuandoExcluirCozinhaEmUso() {
		
		  EntidadeEmUsoException exception =
				  assertThrows(EntidadeEmUsoException.class, () -> {
		         cadastroCozinha.excluir(1L);
		     });	

		assertThat(exception).isNotNull();
	}

	

	@Test
	public void deveFalhar_QuandoExcluirCozinhaInexistente() {
		
		 CozinhaNaoEncontradaException exception =
				 assertThrows(CozinhaNaoEncontradaException.class, () -> {
		         cadastroCozinha.excluir(100L);
		     });	
		 assertThat(exception).isNotNull();
	}

}
