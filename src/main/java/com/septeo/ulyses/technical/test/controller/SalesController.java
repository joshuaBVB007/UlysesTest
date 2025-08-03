package com.septeo.ulyses.technical.test.controller;

import com.septeo.ulyses.technical.test.entity.BestSellingDTO;
import com.septeo.ulyses.technical.test.entity.Sales;
import com.septeo.ulyses.technical.test.repository.SalesRepository;
import com.septeo.ulyses.technical.test.service.SalesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/sales")
public class SalesController {

	@Autowired
	private SalesService salesService;

//    @GetMapping
//    public ResponseEntity<List<Sales>> getAllSales() {
//        return ResponseEntity.ok(salesService.getAllSales());
//    }

	// REQUERIMIENTO 7
	@GetMapping
	public ResponseEntity<Page<Sales>> getAllSalesPageable(@PageableDefault(page = 0, size = 10) Pageable pageable) {
		return ResponseEntity.ok(salesService.getAllSalesPageable(pageable));
	}

	// REQUERIMIENTO 8 y 9
	@GetMapping("/vehicles/bestSelling")
    public ResponseEntity<List<BestSellingDTO>> getBestSelling(
    		@RequestParam(defaultValue = "2025-01-01") LocalDate startDate,
            @RequestParam(defaultValue = "2025-01-02") LocalDate endDate
    ) {
		/*REQUERIMIENTO 10,FILTRO DE FECHAS
		 * ejemplo de uso: http://localhost:8080/api/sales/vehicles/bestSelling?startDate=2025-01-01&endDate=2025-01-02
		 */
        return ResponseEntity.ok(salesService.getBestSelling(startDate,endDate));
    }

	@GetMapping("/{id}")
	public ResponseEntity<Sales> getSalesById(@PathVariable Long id) {
		return salesService.getSalesById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
	}

	// REQUERIMIENTO 4: YA EXISTIAN LOS ENDPOINTS

	// REQUERIMIENTO 5
	@GetMapping("/brands/{brandId}")
	public ResponseEntity<List<Sales>> getBrandsById(@PathVariable Long brandId) {
		return ResponseEntity.ok(salesService.findAllSalesByBrand(brandId));
	}

	// REQUERIMIENTO 6
	@GetMapping("/vehicles/{vehicleId}")
	public ResponseEntity<List<Sales>> getVehiclesById(@PathVariable Long vehicleId) {
		return ResponseEntity.ok(salesService.findAllSalesByVehicles(vehicleId));
	}
}
