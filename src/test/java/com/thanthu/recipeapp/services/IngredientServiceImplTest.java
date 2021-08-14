package com.thanthu.recipeapp.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.thanthu.recipeapp.commands.IngredientCommand;
import com.thanthu.recipeapp.converters.IngredientToIngredientCommand;
import com.thanthu.recipeapp.converters.UnitOfMeasureToUnitOfMeasureCommand;
import com.thanthu.recipeapp.domain.Ingredient;
import com.thanthu.recipeapp.domain.Recipe;
import com.thanthu.recipeapp.repositories.RecipeRepository;

@ExtendWith(MockitoExtension.class)
class IngredientServiceImplTest {

	IngredientToIngredientCommand ingredientToIngredientCommand;

	@Mock
	RecipeRepository recipeRepository;

	IngredientServiceImpl ingredientService;
	
	final Long RECIPE_ID = 1L;
	final Long INGREDIENT_ID = 3L;
	
	@BeforeEach
	public void setUp() {
		ingredientToIngredientCommand = new IngredientToIngredientCommand(new UnitOfMeasureToUnitOfMeasureCommand());
		ingredientService = new IngredientServiceImpl(recipeRepository, ingredientToIngredientCommand);
	}

	@Test
	public void findByRecipeIdAndIngredientIdHappyPath() throws Exception {
		// given
		Recipe recipe = new Recipe();
		recipe.setId(RECIPE_ID);

		Ingredient ingredient1 = new Ingredient();
		ingredient1.setId(1L);

		Ingredient ingredient2 = new Ingredient();
		ingredient2.setId(1L);

		Ingredient ingredient3 = new Ingredient();
		ingredient3.setId(INGREDIENT_ID);

		recipe.addIngredient(ingredient1);
		recipe.addIngredient(ingredient2);
		recipe.addIngredient(ingredient3);
		Optional<Recipe> recipeOptional = Optional.of(recipe);
		
		when(recipeRepository.findById(anyLong())).thenReturn(recipeOptional);

		// then
		IngredientCommand ingredientCommand = ingredientService.findByRecipeIdAndIngredientId(RECIPE_ID, INGREDIENT_ID);

		// when
		assertEquals(INGREDIENT_ID, ingredientCommand.getId());
		assertEquals(RECIPE_ID, ingredientCommand.getRecipeId());
		verify(recipeRepository, times(1)).findById(anyLong());
	}

}
