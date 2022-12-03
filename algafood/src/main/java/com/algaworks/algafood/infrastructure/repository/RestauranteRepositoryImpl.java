package com.algaworks.algafood.infrastructure.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Repository;

import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.RestauranteRepository;

@Repository
public class RestauranteRepositoryImpl implements RestauranteRepository {
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	public List<Restaurante> listar(){
		TypedQuery<Restaurante> query = entityManager.createQuery("from Restaurante", Restaurante.class);
		return query.getResultList();
	}
	
	
	@Override
	@Transactional
	public Restaurante salvar(Restaurante restaurante) {
		return entityManager.merge(restaurante);
	}
	
	
	@Override
	public Restaurante buscar(Long id) {
		return entityManager.find(Restaurante.class, id);
	}
	
	
	@Override
	@Transactional
	public void remover(Long id) {
		Restaurante restaurante = buscar(id);
		if(restaurante==null) {
			throw new EmptyResultDataAccessException(1);
		}
		entityManager.remove(restaurante);
	}

}
