package com.thanthu.recipeapp.services;

import java.util.HashSet;
import java.util.Set;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thanthu.recipeapp.commands.RecipeCommand;
import com.thanthu.recipeapp.converters.RecipeCommandToRecipe;
import com.thanthu.recipeapp.converters.RecipeToRecipeCommand;
import com.thanthu.recipeapp.domain.Recipe;
import com.thanthu.recipeapp.exceptions.NotFoundException;
import com.thanthu.recipeapp.repositories.RecipeRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class RecipeServiceImpl implements RecipeService {

	private final RecipeRepository recipeRepository;
	private final RecipeCommandToRecipe recipeCommandToRecipe;
	private final RecipeToRecipeCommand recipeToRecipeCommand;

	public RecipeServiceImpl(RecipeRepository recipeRepository, RecipeToRecipeCommand recipeToRecipeCommand,
			RecipeCommandToRecipe recipeCommandToRecipe) {
		this.recipeRepository = recipeRepository;
		this.recipeCommandToRecipe = recipeCommandToRecipe;
		this.recipeToRecipeCommand = recipeToRecipeCommand;
	}

	@Override
	public Set<Recipe> getRecipes() {
		log.debug("I'm in getRecipes() in RecipeServiceImpl class");

		Set<Recipe> recipes = new HashSet<Recipe>();
		recipeRepository.findAll().forEach(recipes::add);
		return recipes;
	}

	@Override
	public Recipe findById(Long id) {
		return recipeRepository.findById(id).orElseThrow(() -> new NotFoundException("Recipe Not Found"));
	}

	@Override
	@Transactional
	public RecipeCommand saveRecipeCommand(RecipeCommand command) {
		Recipe detachedRecipe = recipeCommandToRecipe.convert(command);

		Recipe savedRecipe = recipeRepository.save(detachedRecipe);
		log.debug("Saved RecipeId:" + savedRecipe.getId());
		return recipeToRecipeCommand.convert(savedRecipe);
	}

	@Override
	public RecipeCommand findCommandById(Long id) {
		return recipeToRecipeCommand.convert(findById(id));
	}

	@Override
	public void deleteById(Long id) {
		recipeRepository.deleteById(id);
	}

}
