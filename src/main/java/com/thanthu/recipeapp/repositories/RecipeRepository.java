package com.thanthu.recipeapp.repositories;

import org.springframework.data.repository.CrudRepository;

import com.thanthu.recipeapp.domain.Recipe;

public interface RecipeRepository extends CrudRepository<Recipe, Long> {

}
