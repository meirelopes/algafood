package com.algaworks.algafood.jpa;

import java.util.List;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

import com.algaworks.algafood.AlgafoodApplication;
import com.algaworks.algafood.domain.model.Estado;
import com.algaworks.algafood.domain.repository.EstadoRepository;

public class BuscaEstadoMain {
	public static void main(String[] args) {

	
	ApplicationContext  applicationContext = new SpringApplicationBuilder(AlgafoodApplication.class)
			.web(WebApplicationType.NONE)
			.run(args);
	
	EstadoRepository estadoRepository = applicationContext.getBean(EstadoRepository.class);
	
	List<Estado> estados = estadoRepository.listar();
	
	for (Estado estado : estados) {
		System.out.printf("%s\n", estado.getNome());
	}
	

}
}
