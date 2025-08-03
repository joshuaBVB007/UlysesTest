package com.septeo.ulyses.technical.test.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

import com.septeo.ulyses.technical.test.entity.Brand;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;

/**
 * Implementation of the BrandRepository interface. This class provides the
 * implementation for all brand-related database operations.
 */
@Repository
public class BrandRepositoryImpl implements BrandRepository {

	@PersistenceContext
	private EntityManager entityManager;

	/**
	 * {@inheritDoc}
	 */
	// REQUERIMIENTO 15
	@Override
	@Cacheable(value = "CachingApp", key = "'BrandAll'")
	public List<Brand> findAll() {
		simulateDelay();
		String stringQuery = "SELECT b FROM Brand b";
		Query query = entityManager.createQuery(stringQuery);
		return query.getResultList();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@Cacheable(value = "CachingApp", key = "'BrandOne'")
	public Optional<Brand> findById(Long id) {
		simulateDelay();
		String stringQuery = "SELECT b FROM Brand b WHERE b.id = :id";
		Query query = entityManager.createQuery(stringQuery);
		query.setParameter("id", id);
		try {
			return Optional.of((Brand) query.getSingleResult());
		} catch (NoResultException e) {
			return Optional.empty();
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Brand save(Brand brand) {
		if (brand.getId() == null) {
			entityManager.persist(brand);
			return brand;
		} else {
			return entityManager.merge(brand);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void deleteById(Long id) {
		String stringQuery = "DELETE FROM Brand b WHERE b.id = :id";
		Query query = entityManager.createQuery(stringQuery);
		query.setParameter("id", id);
		query.executeUpdate();
	}

	private void simulateDelay() {
		try {
			long time = 2000L;
			Thread.sleep(time);
		} catch (InterruptedException e) {
			throw new IllegalStateException(e);
		}
	}
}
