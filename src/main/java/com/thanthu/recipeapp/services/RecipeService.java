package com.thanthu.recipeapp.services;

import java.util.Set;

import com.thanthu.recipeapp.commands.RecipeCommand;
import com.thanthu.recipeapp.domain.Recipe;

public interface RecipeService {

	Set<Recipe> getRecipes();

	Recipe findById(Long id);

	RecipeCommand saveRecipeCommand(RecipeCommand command);

	RecipeCommand findCommandById(Long id);

}
