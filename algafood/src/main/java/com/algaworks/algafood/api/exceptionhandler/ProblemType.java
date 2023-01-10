package com.algaworks.algafood.api.exceptionhandler;

import lombok.Getter;

@Getter
public enum ProblemType {
	ENTIDADE_NAO_ENCONTRADA("Entidade não encontrada", "/entidade-nao-encontrada"),
	ENTIDADE_EM_USO("Entidade em uso", "/entidade-em-uso"),
	NEGOCIO_EXCEPTION("Violação de regra de negócio", "/erro-negocio");
	
	private String title;
	private String uri;
	
	ProblemType(String title, String path){
		this.title = title;
		this.uri = "https://algafood.com.br" + path;
	}
	

}
