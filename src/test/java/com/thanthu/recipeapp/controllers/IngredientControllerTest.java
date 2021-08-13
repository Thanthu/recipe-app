package com.thanthu.recipeapp.controllers;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
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

import com.thanthu.recipeapp.commands.RecipeCommand;
import com.thanthu.recipeapp.services.RecipeService;

@ExtendWith(MockitoExtension.class)
public class IngredientControllerTest {

	@Mock
	RecipeService recipeService;

	@InjectMocks
	IngredientController controller;

	MockMvc mockMvc;
	final Long RECIPE_ID = 1L;

	@BeforeEach
	public void setUp() throws Exception {
		mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
	}

	@Test
	public void testListIngredients() throws Exception {
		// given
		RecipeCommand recipeCommand = new RecipeCommand();
		when(recipeService.findCommandById(RECIPE_ID)).thenReturn(recipeCommand);

		// when
		mockMvc.perform(get("/recipe/" + RECIPE_ID + "/ingredients")).andExpect(status().isOk())
				.andExpect(view().name("recipe/ingredient/list")).andExpect(model().attributeExists("recipe"));

		// then
		verify(recipeService, times(1)).findCommandById(anyLong());
	}

}
