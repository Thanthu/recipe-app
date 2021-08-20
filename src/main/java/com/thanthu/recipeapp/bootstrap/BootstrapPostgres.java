package com.thanthu.recipeapp.bootstrap;

import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import com.thanthu.recipeapp.domain.Category;
import com.thanthu.recipeapp.domain.UnitOfMeasure;
import com.thanthu.recipeapp.repositories.CategoryRepository;
import com.thanthu.recipeapp.repositories.UnitOfMeasureRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@Profile({ "dev", "prod" })
public class BootstrapPostgres implements ApplicationListener<ContextRefreshedEvent> {

	private final CategoryRepository categoryRepository;
	private final UnitOfMeasureRepository unitOfMeasureRepository;

	public BootstrapPostgres(CategoryRepository categoryRepository, UnitOfMeasureRepository unitOfMeasureRepository) {
		this.categoryRepository = categoryRepository;
		this.unitOfMeasureRepository = unitOfMeasureRepository;
	}

	@Override
	public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {

		if (categoryRepository.count() == 0L) {
			log.debug("Loading Categories");
			loadCategories();
		}

		if (unitOfMeasureRepository.count() == 0L) {
			log.debug("Loading UOMs");
			loadUom();
		}
	}

	private void loadCategories() {
		Category cat1 = new Category();
		cat1.setDescription("American");
		categoryRepository.save(cat1);

		Category cat2 = new Category();
		cat2.setDescription("Italian");
		categoryRepository.save(cat2);

		Category cat3 = new Category();
		cat3.setDescription("Mexican");
		categoryRepository.save(cat3);

		Category cat4 = new Category();
		cat4.setDescription("Fast Food");
		categoryRepository.save(cat4);
	}

	private void loadUom() {
		UnitOfMeasure uom1 = new UnitOfMeasure();
		uom1.setUnit("Teaspoon");
		unitOfMeasureRepository.save(uom1);

		UnitOfMeasure uom2 = new UnitOfMeasure();
		uom2.setUnit("Tablespoon");
		unitOfMeasureRepository.save(uom2);

		UnitOfMeasure uom3 = new UnitOfMeasure();
		uom3.setUnit("Cup");
		unitOfMeasureRepository.save(uom3);

		UnitOfMeasure uom4 = new UnitOfMeasure();
		uom4.setUnit("Pinch");
		unitOfMeasureRepository.save(uom4);

		UnitOfMeasure uom5 = new UnitOfMeasure();
		uom5.setUnit("Ounce");
		unitOfMeasureRepository.save(uom5);

		UnitOfMeasure uom6 = new UnitOfMeasure();
		uom6.setUnit("Each");
		unitOfMeasureRepository.save(uom6);

		UnitOfMeasure uom7 = new UnitOfMeasure();
		uom7.setUnit("Pint");
		unitOfMeasureRepository.save(uom7);

		UnitOfMeasure uom8 = new UnitOfMeasure();
		uom8.setUnit("Dash");
		unitOfMeasureRepository.save(uom8);
	}
}
