package com.thanthu.recipeapp.services;

import org.springframework.stereotype.Service;

import com.thanthu.recipeapp.commands.IngredientCommand;
import com.thanthu.recipeapp.converters.IngredientToIngredientCommand;
import com.thanthu.recipeapp.domain.Recipe;
import com.thanthu.recipeapp.repositories.RecipeRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class IngredientServiceImpl implements IngredientService {

	private final RecipeRepository recipeRepository;

	private final IngredientToIngredientCommand ingredientToIngredientCommand;

	public IngredientServiceImpl(RecipeRepository recipeRepository,
			IngredientToIngredientCommand ingredientToIngredientCommand) {
		this.recipeRepository = recipeRepository;
		this.ingredientToIngredientCommand = ingredientToIngredientCommand;
	}

	@Override
	public IngredientCommand findByRecipeIdAndIngredientId(Long recipeId, Long ingredientId) {
		Recipe recipe = recipeRepository.findById(recipeId)
				.orElseThrow(() -> new RuntimeException("Recipe not found for ID: " + recipeId));

		log.debug(ingredientToIngredientCommand.toString());
		
		IngredientCommand ingredientCommand = recipe.getIngredients().stream()
				.filter(ingredient -> ingredient.getId().equals(ingredientId))
				.map(ingredient -> ingredientToIngredientCommand.convert(ingredient)).findFirst()
				.orElseThrow(() -> new RuntimeException("Ingredient not found for ID: " + ingredientId));

		return ingredientCommand;
	}

}
