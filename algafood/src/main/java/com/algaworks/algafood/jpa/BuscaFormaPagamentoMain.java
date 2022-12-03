package com.algaworks.algafood.jpa;

import java.util.List;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

import com.algaworks.algafood.AlgafoodApplication;
import com.algaworks.algafood.domain.model.FormaPagamento;
import com.algaworks.algafood.domain.repository.FormaPagamentoRepository;

public class BuscaFormaPagamentoMain {
	public static void main(String[] args) {

	
	ApplicationContext  applicationContext = new SpringApplicationBuilder(AlgafoodApplication.class)
			.web(WebApplicationType.NONE)
			.run(args);
	
	FormaPagamentoRepository formaPagamentoRepository = applicationContext.getBean(FormaPagamentoRepository.class);
	
	List<FormaPagamento> formaPagamentos = formaPagamentoRepository.listar();
	
	for (FormaPagamento formaPagamento : formaPagamentos) {
		System.out.printf("%s\n", formaPagamento.getDescricao());
	}
	
}
}
