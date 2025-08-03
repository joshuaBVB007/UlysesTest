package com.septeo.ulyses.technical.test.service;

import com.septeo.ulyses.technical.test.entity.BestSellingDTO;
import com.septeo.ulyses.technical.test.entity.Sales;
import com.septeo.ulyses.technical.test.repository.SalesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Implementation of the SalesService interface. This class provides the
 * implementation for all sales-related operations.
 */
@Service
@Transactional(readOnly = false)
public class SalesServiceImpl implements SalesService {

	@Autowired
	private SalesRepository salesRepository;

	/**
	 * {@inheritDoc}
	 */

	public List<Sales> getAllSales() {
		return salesRepository.findAll();
	}

	public List<Sales> findAllSalesByBrand(Long id) {
		return salesRepository.findAllSalesByBrand(id);
	}

	public List<Sales> findAllSalesByVehicles(Long id) {
		return salesRepository.findAllSalesByVehicles(id);
	}

	@Override
	public Page<Sales> getAllSalesPageable(Pageable pageable) {
		return salesRepository.findAllPageable(pageable);
	}

	public List<BestSellingDTO> getBestSelling(LocalDate start, LocalDate end) {
		return salesRepository.getBestSelling(start, end);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Optional<Sales> getSalesById(Long id) {
		return salesRepository.findById(id);
	}

}
