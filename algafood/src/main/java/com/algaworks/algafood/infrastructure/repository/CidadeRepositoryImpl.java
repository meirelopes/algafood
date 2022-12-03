package com.algaworks.algafood.infrastructure.repository;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Repository;

import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.repository.CidadeRepository;

@Repository
public class CidadeRepositoryImpl implements CidadeRepository{

	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	public List<Cidade> listar(){
		 TypedQuery<Cidade> query = entityManager.createQuery("from Cidade", Cidade.class);
		 return query.getResultList();
	}
	
	@Override
	@Transactional
	public Cidade salvar(Cidade cidade) {
		 return entityManager.merge(cidade);
	}
	
	@Override
	public Cidade buscar(Long id) {
		return entityManager.find(Cidade.class, id);
	}
	
	@Override
	@Transactional
	public void remover(Long id) {
		Cidade cidade = buscar(id);
		if(cidade==null) {
			throw new EmptyResultDataAccessException(1);
		}
		entityManager.remove(cidade);
	}
}
