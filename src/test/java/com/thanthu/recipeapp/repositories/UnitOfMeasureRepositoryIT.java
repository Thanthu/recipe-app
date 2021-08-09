package com.thanthu.recipeapp.repositories;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.thanthu.recipeapp.domain.UnitOfMeasure;

@DataJpaTest
class UnitOfMeasureRepositoryIT {

	@Autowired
	UnitOfMeasureRepository unitOfMeasureRepository;

	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void testFindByUnit() {
		String expectedUnit = "Teaspoon";
		Optional<UnitOfMeasure> unitOfMeasure = unitOfMeasureRepository.findByUnit(expectedUnit);
		assertEquals(expectedUnit, unitOfMeasure.get().getUnit());
	}

}
