package com.septeo.ulyses.technical.test.entity;

import java.math.BigDecimal;

import jakarta.persistence.Column;

public class BestSellingDTO {
	private BigDecimal totalGanancias;
	private Vehicle vehicle;

	public BestSellingDTO(Vehicle nameVehicle, BigDecimal totalGanancias) {
		this.vehicle = nameVehicle;
		this.totalGanancias = totalGanancias;
	}

	public Vehicle getVehicle() {
		return vehicle;
	}

	public void setVehicle(Vehicle vehicle) {
		this.vehicle = vehicle;
	}

	public BigDecimal getTotalGanancias() {
		return totalGanancias;
	}

	public void setTotalGanancias(BigDecimal totalGanancias) {
		this.totalGanancias = totalGanancias;
	}
}
