package com.septeo.ulyses.technical.test.repository;

import com.septeo.ulyses.technical.test.entity.BestSellingDTO;
import com.septeo.ulyses.technical.test.entity.Brand;
import com.septeo.ulyses.technical.test.entity.Sales;
import com.septeo.ulyses.technical.test.entity.Vehicle;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Repository interface for Sales entity.
 */
@Repository
public interface SalesRepository extends PagingAndSortingRepository<Sales, Integer> {
	/**
	 * Find all sales.
	 *
	 * @return a list of all sales
	 */
	List<Sales> findAll();

	List<Sales> findAllSalesByBrand(Long id);

	List<Sales> findAllSalesByVehicles(Long id);

	Page<Sales> findAllPageable(Pageable pageable);

	List<BestSellingDTO> getBestSelling(LocalDate start, LocalDate end);

	/**
	 * Find a sale by its ID.
	 *
	 * @param id the ID of the sale to find
	 * @return an Optional containing the sale if found, or empty if not found
	 */
	Optional<Sales> findById(Long id);

}
