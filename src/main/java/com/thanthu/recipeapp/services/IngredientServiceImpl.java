package com.thanthu.recipeapp.services;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thanthu.recipeapp.commands.IngredientCommand;
import com.thanthu.recipeapp.converters.IngredientCommandToIngredient;
import com.thanthu.recipeapp.converters.IngredientToIngredientCommand;
import com.thanthu.recipeapp.domain.Ingredient;
import com.thanthu.recipeapp.domain.Recipe;
import com.thanthu.recipeapp.repositories.RecipeRepository;
import com.thanthu.recipeapp.repositories.UnitOfMeasureRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class IngredientServiceImpl implements IngredientService {

	private final RecipeRepository recipeRepository;

	private final IngredientToIngredientCommand ingredientToIngredientCommand;

	private final UnitOfMeasureRepository unitOfMeasureRepository;

	private final IngredientCommandToIngredient ingredientCommandToIngredient;

	public IngredientServiceImpl(RecipeRepository recipeRepository,
			IngredientToIngredientCommand ingredientToIngredientCommand,
			UnitOfMeasureRepository unitOfMeasureRepository,
			IngredientCommandToIngredient ingredientCommandToIngredient) {
		this.recipeRepository = recipeRepository;
		this.ingredientToIngredientCommand = ingredientToIngredientCommand;
		this.unitOfMeasureRepository = unitOfMeasureRepository;
		this.ingredientCommandToIngredient = ingredientCommandToIngredient;
	}

	@Override
	public IngredientCommand findByRecipeIdAndIngredientId(Long recipeId, Long ingredientId) {
		Recipe recipe = recipeRepository.findById(recipeId)
				.orElseThrow(() -> new RuntimeException("Recipe not found for ID: " + recipeId));

		IngredientCommand ingredientCommand = recipe.getIngredients().stream()
				.filter(ingredient -> ingredient.getId().equals(ingredientId))
				.map(ingredient -> ingredientToIngredientCommand.convert(ingredient)).findFirst()
				.orElseThrow(() -> new RuntimeException("Ingredient not found for ID: " + ingredientId));

		return ingredientCommand;
	}

	@Override
	@Transactional
	public IngredientCommand saveIngredientCommand(IngredientCommand command) {
		Recipe recipe = recipeRepository.findById(command.getRecipeId())
				.orElseThrow(() -> new RuntimeException("Recipe not found for ID: " + command.getRecipeId()));

		Optional<Ingredient> ingredientOptional = recipe.getIngredients().stream()
				.filter(ingredient -> ingredient.getId().equals(command.getId())).findFirst();

		if (ingredientOptional.isPresent()) {
			Ingredient ingredientFound = ingredientOptional.get();
			ingredientFound.setDescription(command.getDescription());
			ingredientFound.setAmount(command.getAmount());
			ingredientFound.setUnitOfMeasure(unitOfMeasureRepository.findById(command.getUnitOfMeasure().getId())
					.orElseThrow(() -> new RuntimeException(
							"Unit Of Measure not found for ID: " + command.getUnitOfMeasure().getId())));
		} else {
			// add new Ingredient
			recipe.addIngredient(ingredientCommandToIngredient.convert(command));
		}

		Recipe savedRecipe = recipeRepository.save(recipe);

		Optional<Ingredient> savedIngredientOptional = savedRecipe.getIngredients().stream()
				.filter(recipeIngredients -> recipeIngredients.getId().equals(command.getId())).findFirst();

		// check by description
		if (!savedIngredientOptional.isPresent()) {
			// not totally safe... But best guess
			savedIngredientOptional = savedRecipe.getIngredients().stream()
					.filter(recipeIngredients -> recipeIngredients.getDescription().equals(command.getDescription()))
					.filter(recipeIngredients -> recipeIngredients.getAmount().equals(command.getAmount()))
					.filter(recipeIngredients -> recipeIngredients.getUnitOfMeasure().getId()
							.equals(command.getUnitOfMeasure().getId()))
					.findFirst();
		}

		// to do check for fail
		return ingredientToIngredientCommand.convert(savedIngredientOptional.get());

	}

}
