package com.thanthu.recipeapp.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.thanthu.recipeapp.services.IngredientService;
import com.thanthu.recipeapp.services.RecipeService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class IngredientController {

	private final RecipeService recipeService;
	private final IngredientService ingredientService;

	public IngredientController(RecipeService recipeService, IngredientService ingredientService) {
		this.recipeService = recipeService;
		this.ingredientService = ingredientService;
	}

	@GetMapping("/recipe/{recipeId}/ingredients")
	public String listIngredients(@PathVariable Long recipeId, Model model) {
		log.debug("/recipe/" + recipeId + "/ingredients");

		model.addAttribute("recipe", recipeService.findCommandById(recipeId));

		return "recipe/ingredient/list";
	}

	@GetMapping("recipe/{recipeId}/ingredient/{id}/show")
	public String showRecipeIngredient(@PathVariable Long recipeId, @PathVariable Long id, Model model) {
		model.addAttribute("ingredient", ingredientService.findByRecipeIdAndIngredientId(recipeId, id));
		return "recipe/ingredient/show";
	}

}
