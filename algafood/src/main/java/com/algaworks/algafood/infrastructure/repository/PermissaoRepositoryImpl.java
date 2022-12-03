package com.algaworks.algafood.infrastructure.repository;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.algaworks.algafood.domain.model.Permissao;
import com.algaworks.algafood.domain.repository.PermissaoRepository;

@Repository
public class PermissaoRepositoryImpl implements PermissaoRepository{

	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	public List<Permissao> listar(){
		 TypedQuery<Permissao> query = entityManager.createQuery("from Permissao", Permissao.class);
		 return query.getResultList();
	}
	
	@Override
	@Transactional
	public Permissao salvar(Permissao permissao) {
		 return entityManager.merge(permissao);
	}
	
	@Override
	public Permissao buscar(Long id) {
		return entityManager.find(Permissao.class, id);
	}
	
	@Override
	@Transactional
	public void remover(Permissao permissao) {
		permissao = buscar(permissao.getId());
		entityManager.remove(permissao);
	}
}
