package com.thanthu.recipeapp.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
	
	@RequestMapping("/{id}/show")
	public String getRecipe(@PathVariable Long id, Model model) {
		log.debug("GET /recipe/" + id + "show");
		model.addAttribute("recipe", recipeService.findById(id));
		return "recipe/show";
	}
	
	@RequestMapping("/new")
	public String newRecipe(Model model) {
		log.debug("GET /recipe/new");
		model.addAttribute("recipe", new RecipeCommand());
		return "recipe/recipeform";
	}
	
	@PostMapping("")
	public String saveOrUpdate(@ModelAttribute RecipeCommand command) {
		log.debug("POST /recipe");
		RecipeCommand savedCommand = recipeService.saveRecipeCommand(command);
		return "redirect:/recipe/" + savedCommand.getId() + "/show";
	}
	
	@RequestMapping("/{id}/update")
	public String newRecipe(@PathVariable Long id, Model model) {
		log.debug("GET /recipe/" + id + "/update");
		model.addAttribute("recipe", recipeService.findCommandById(id));
		return "recipe/recipeform";
	}

}
