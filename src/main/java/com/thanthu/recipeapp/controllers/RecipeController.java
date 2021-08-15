package com.thanthu.recipeapp.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import com.thanthu.recipeapp.commands.RecipeCommand;
import com.thanthu.recipeapp.exceptions.NotFoundException;
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

	@GetMapping("/{id}/show")
	public String getRecipe(@PathVariable Long id, Model model) {
		log.debug("GET /recipe/" + id + "/show");
		model.addAttribute("recipe", recipeService.findById(id));
		return "recipe/show";
	}

	@GetMapping("/new")
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

	@GetMapping("/{id}/update")
	public String updateRecipe(@PathVariable Long id, Model model) {
		log.debug("GET /recipe/" + id + "/update");
		model.addAttribute("recipe", recipeService.findCommandById(id));
		return "recipe/recipeform";
	}

	@GetMapping("/{id}/delete")
	public String deleteRecipe(@PathVariable Long id) {
		log.debug("GET /recipe/" + id + "/delete");
		recipeService.deleteById(id);
		return "redirect:/";
	}

	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ExceptionHandler(NotFoundException.class)
	public ModelAndView handleNotFound(Exception e) {

		log.error("Handling not found exception");
		log.error(e.getMessage());

		ModelAndView modelAndView = new ModelAndView();

		modelAndView.setViewName("404error");
		modelAndView.addObject("exception", e);

		return modelAndView;
	}
	
	@ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(NumberFormatException.class)
    public ModelAndView handleNumberFormat(Exception exception){

        log.error("Handling Number Format Exception");
        log.error(exception.getMessage());

        ModelAndView modelAndView = new ModelAndView();

        modelAndView.setViewName("400error");
        modelAndView.addObject("exception", exception);

        return modelAndView;
    }

}
