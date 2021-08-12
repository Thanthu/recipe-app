package com.thanthu.recipeapp.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.thanthu.recipeapp.commands.RecipeCommand;
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
	
	@RequestMapping({"/new"})
	public String newRecipe(Model model) {
		log.debug("GET /recipe/new");
		model.addAttribute("recipe", new RecipeCommand());
		return "recipe/recipeform";
	}
	
	@PostMapping("")
	public String saveOrUpdate(@ModelAttribute RecipeCommand command) {
		log.debug("Inside RecipeController.saveOrUpdate()");
		RecipeCommand savedCommand = recipeService.saveRecipeCommand(command);
		return "redirect:/recipe/show/" + savedCommand.getId();
	}

}
