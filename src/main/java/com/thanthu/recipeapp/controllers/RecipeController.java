package com.thanthu.recipeapp.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.thanthu.recipeapp.services.RecipeService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/recipe")
public class RecipeController {
	
	private final RecipeService recipeService;

	public RecipeController(RecipeService recipeService) {
		this.recipeService = recipeService;
	}
	
	@RequestMapping({"/show/{id}"})
	public String getRecipe(@PathVariable Long id, Model model) {
		log.debug("GET /recipe/show/" + id);
		model.addAttribute("recipe", recipeService.getRecipeById(id));
		return "recipe/show";
	}

}
