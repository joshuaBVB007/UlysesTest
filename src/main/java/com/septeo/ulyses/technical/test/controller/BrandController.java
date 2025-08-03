package com.septeo.ulyses.technical.test.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.septeo.ulyses.technical.test.entity.Brand;
import com.septeo.ulyses.technical.test.service.BrandService;

/**
 * REST controller for Brand operations.
 */
@RestController
@RequestMapping("/api/brands")
public class BrandController {
	@Autowired
	private BrandService brandService;

	@Autowired
	private CacheManager cacheManager;

	@GetMapping
	public ResponseEntity<List<Brand>> getAllBrands() {
		Cache cache = cacheManager.getCache("CachingApp");
		Cache.ValueWrapper cachedValue = cache.get("BrandAll"); // la key depende de la configuración

		// 16.Check if the data exists in the cache and has not expired.
		if (cachedValue != null) {
			List<Brand> cachedBrands = (List<Brand>) cachedValue.get();
			return ResponseEntity.ok(cachedBrands);
		}

		// 17.If it does not exist or has expired, call the supplier.
		// 18.Update the cache with the new value.
		List<Brand> brands = brandService.getAllBrands();
		return ResponseEntity.ok(brands);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Brand> getBrandById(@PathVariable Long id) {
		Cache cache = cacheManager.getCache("CachingApp");
		Cache.ValueWrapper cachedValue = cache.get("BrandOne");

		// 16.Check if the data exists in the cache and has not expired.
		if (cachedValue != null) {
			return brandService.getBrandById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
		}
		return brandService.getBrandById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
	}

	@PostMapping
	public ResponseEntity<Brand> createBrand(@RequestBody Brand brand) {
		Brand savedBrand = brandService.saveBrand(brand);
		return ResponseEntity.status(HttpStatus.CREATED).body(savedBrand);
	}

	@PutMapping("/{id}")
	public ResponseEntity<Brand> updateBrand(@PathVariable Long id, @RequestBody Brand brand) {
		return brandService.getBrandById(id).map(existingBrand -> {
			brand.setId(id);
			return ResponseEntity.ok(brandService.saveBrand(brand));
		}).orElse(ResponseEntity.notFound().build());
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteBrand(@PathVariable Long id) {
		if (!brandService.getBrandById(id).isPresent()) {
			return ResponseEntity.notFound().build();
		}
		brandService.deleteBrand(id);
		return ResponseEntity.noContent().build();
	}
}
