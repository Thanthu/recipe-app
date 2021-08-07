package com.thanthu.recipeapp.repositories;

import org.springframework.data.repository.CrudRepository;

import com.thanthu.recipeapp.domain.Category;

public interface CategoryRepository extends CrudRepository<Category, Long> {

}
