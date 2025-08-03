package com.septeo.ulyses.technical.test.service;

import com.septeo.ulyses.technical.test.entity.BestSellingDTO;
import com.septeo.ulyses.technical.test.entity.Brand;
import com.septeo.ulyses.technical.test.entity.Sales;
import com.septeo.ulyses.technical.test.entity.Vehicle;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service interface for Sales operations.
 */
public interface SalesService {

	/**
	 * Get all sales.
	 *
	 * @return a list of all sales
	 */
	List<Sales> getAllSales();

	List<Sales> findAllSalesByBrand(Long id);

	List<Sales> findAllSalesByVehicles(Long id);

	Page<Sales> getAllSalesPageable(Pageable pageable);

	List<BestSellingDTO> getBestSelling(LocalDate start, LocalDate end);

	/**
	 * Get a sales by its ID.
	 *
	 * @param id the ID of the sales to find
	 * @return an Optional containing the sales if found, or empty if not found
	 */
	Optional<Sales> getSalesById(Long id);

}
