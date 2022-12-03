package com.algaworks.algafood.infrastructure.repository;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Repository;

import com.algaworks.algafood.domain.model.Estado;
import com.algaworks.algafood.domain.repository.EstadoRepository;

@Repository
public class EstadoRepositoryImpl implements EstadoRepository{

	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	public List<Estado> listar(){
		 TypedQuery<Estado> query = entityManager.createQuery("from Estado", Estado.class);
		 return query.getResultList();
	}
	
	@Override
	@Transactional
	public Estado salvar(Estado estado) {
		 return entityManager.merge(estado);
	}
	
	@Override
	public Estado buscar(Long id) {
		return entityManager.find(Estado.class, id);
	}
	
	@Override
	@Transactional
	public void remover(Long id) {
		Estado estado = buscar(id);
		if(estado==null) {
			throw new EmptyResultDataAccessException(1);
		}
		entityManager.remove(estado);
	}
}
