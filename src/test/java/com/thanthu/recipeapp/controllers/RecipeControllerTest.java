package com.thanthu.recipeapp.controllers;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.thanthu.recipeapp.domain.Recipe;
import com.thanthu.recipeapp.services.RecipeService;

@ExtendWith(MockitoExtension.class)
class RecipeControllerTest {
	
	@Mock
	RecipeService recipeService;
	
	@InjectMocks
	RecipeController recipeController;
	
	MockMvc mockMvc;
	final Long RECIPE_ID = 1L;
	Recipe recipe;
	

	@BeforeEach
	void setUp() throws Exception {
		mockMvc = MockMvcBuilders.standaloneSetup(recipeController).build();
		recipe = new Recipe();
		recipe.setId(RECIPE_ID);
	}

	@Test
	void testGetRecipe() throws Exception {
		
		when(recipeService.getRecipeById(RECIPE_ID)).thenReturn(recipe);
		
		mockMvc.perform(get("/recipe/show/" + RECIPE_ID))
		.andExpect(status().isOk())
		.andExpect(view().name("recipe/show"));
	}

}
