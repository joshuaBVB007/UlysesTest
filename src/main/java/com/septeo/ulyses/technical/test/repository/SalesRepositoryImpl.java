package com.septeo.ulyses.technical.test.repository;

import com.septeo.ulyses.technical.test.entity.BestSellingDTO;
import com.septeo.ulyses.technical.test.entity.Brand;
import com.septeo.ulyses.technical.test.entity.Sales;
import com.septeo.ulyses.technical.test.entity.Vehicle;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Implementation of the SalesRepository interface. This class provides the
 * implementation for all sales-related operations.
 */
@Repository
public class SalesRepositoryImpl implements SalesRepository {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public List<Sales> findAllSalesByBrand(Long id) {
		String stringQuery = "SELECT s FROM Sales s WHERE s.brand.id = :id";
		Query query = entityManager.createQuery(stringQuery);
		query.setParameter("id", id);
		return query.getResultList();
	}

	@Override
	public List<Sales> findAllSalesByVehicles(Long id) {
		String stringQuery = "SELECT s FROM Sales s WHERE s.vehicle.id = :id";
		Query query = entityManager.createQuery(stringQuery);
		query.setParameter("id", id);
		return query.getResultList();
	}

	@Override
	public List<Sales> findAll() {
		String stringQuery = "SELECT s FROM Sales s";
		Query query = entityManager.createQuery(stringQuery);
		return query.getResultList();
	}

	public List<BestSellingDTO> getBestSelling(LocalDate startDate, LocalDate endDate) {
//    	StringBuilder contentQueryString = "SELECT NEW com.septeo.ulyses.technical.test.entity.BestSellingDTO(v,sum(s.price))"
//    			+ " FROM Sales s INNER JOIN Vehicle v  ON s.vehicle.id=v.id";
//    			+ " GROUP BY v.model"
//    			+ " ORDER BY SUM(s.price) DESC";
		StringBuilder contentQueryBuilder = new StringBuilder();
		contentQueryBuilder.append("SELECT NEW com.septeo.ulyses.technical.test.entity.BestSellingDTO(v,SUM(s.price))");
		contentQueryBuilder.append(" FROM Sales s INNER JOIN Vehicle v ON s.vehicle.id=v.id");
		contentQueryBuilder.append(" WHERE s.saleDate BETWEEN :startDate AND :endDate");
		contentQueryBuilder.append(" GROUP BY v.model");
		contentQueryBuilder.append(" ORDER BY SUM(s.price) DESC");

		TypedQuery<BestSellingDTO> contentQuery = entityManager.createQuery(contentQueryBuilder.toString(),
				BestSellingDTO.class);

		contentQuery.setParameter("startDate", startDate);
		contentQuery.setParameter("endDate", endDate);

		contentQuery.setFirstResult(0);
		contentQuery.setMaxResults(5);
		List<BestSellingDTO> listOfBestSellingVehicles = contentQuery.getResultList();
		return listOfBestSellingVehicles;
	}

	@Override
	public Page<Sales> findAllPageable(Pageable pageable) {
		String countQueryString = "SELECT COUNT(s) FROM Sales s";
		Query countQuery = entityManager.createQuery(countQueryString);
		Long totalCount = (Long) countQuery.getSingleResult();

		String contentQueryString = "SELECT s FROM Sales s";
		Query contentQuery = entityManager.createQuery(contentQueryString);

		contentQuery.setFirstResult(pageable.getPageNumber() * pageable.getPageSize()); // Starting record for the
																						// current page
		contentQuery.setMaxResults(pageable.getPageSize()); // Number of records per page

		List<Sales> salesList = contentQuery.getResultList();

		return new PageImpl<>(salesList, pageable, totalCount);
	}

	@Override
	public Optional<Sales> findById(Long id) {
		String stringQuery = "SELECT s FROM Sales s WHERE s.id = :id";
		Query query = entityManager.createQuery(stringQuery);
		query.setParameter("id", id);

		try {
			return Optional.of((Sales) query.getSingleResult());
		} catch (NoResultException e) {
			return Optional.empty();
		}
	}

	@Override
	public Iterable<Sales> findAll(Sort sort) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<Sales> findAll(Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}
}
