package com.thanthu.recipeapp.services;

import java.util.HashSet;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.thanthu.recipeapp.domain.Recipe;
import com.thanthu.recipeapp.repositories.RecipeRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class RecipeServiceImpl implements RecipeService {

	private final RecipeRepository recipeRepository;

	public RecipeServiceImpl(RecipeRepository recipeRepository) {
		this.recipeRepository = recipeRepository;
	}

	@Override
	public Set<Recipe> getRecipes() {
		log.debug("I'm in getRecipes() in RecipeServiceImpl class");
		
		Set<Recipe> recipes = new HashSet<Recipe>();
		recipeRepository.findAll().forEach(recipes::add);
		return recipes;
	}
	
	@Override
	public Recipe getRecipeById(Long id) {
		return recipeRepository.findById(id).orElse(null);
	}

}
