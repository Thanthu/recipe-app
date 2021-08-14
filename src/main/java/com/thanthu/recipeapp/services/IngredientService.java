package com.thanthu.recipeapp.services;

import com.thanthu.recipeapp.commands.IngredientCommand;

public interface IngredientService {

	IngredientCommand findByRecipeIdAndIngredientId(Long recipeId, Long ingredientId);

}
