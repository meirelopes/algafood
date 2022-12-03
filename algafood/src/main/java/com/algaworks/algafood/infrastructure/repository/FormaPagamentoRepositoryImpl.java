package com.algaworks.algafood.infrastructure.repository;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.algaworks.algafood.domain.model.FormaPagamento;
import com.algaworks.algafood.domain.repository.FormaPagamentoRepository;

@Repository
public class FormaPagamentoRepositoryImpl implements FormaPagamentoRepository{

	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	public List<FormaPagamento> listar(){
		 TypedQuery<FormaPagamento> query = entityManager.createQuery("from FormaPagamento", FormaPagamento.class);
		 return query.getResultList();
	}
	
	@Override
	@Transactional
	public FormaPagamento salvar(FormaPagamento formaPagamento) {
		 return entityManager.merge(formaPagamento);
	}
	
	@Override
	public FormaPagamento buscar(Long id) {
		return entityManager.find(FormaPagamento.class, id);
	}
	
	@Override
	@Transactional
	public void remover(FormaPagamento formaPagamento) {
		formaPagamento = buscar(formaPagamento.getId());
		entityManager.remove(formaPagamento);
	}
}
