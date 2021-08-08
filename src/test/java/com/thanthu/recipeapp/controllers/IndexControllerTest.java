package com.thanthu.recipeapp.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anySet;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;

import com.thanthu.recipeapp.domain.Recipe;
import com.thanthu.recipeapp.services.RecipeService;

class IndexControllerTest {

	IndexController indexController;

	@Mock
	RecipeService recipeService;

	@Mock
	Model model;

	AutoCloseable openMocks;

	@BeforeEach
	void setUp() throws Exception {
		openMocks = MockitoAnnotations.openMocks(this);
		indexController = new IndexController(recipeService);
	}

	@AfterEach
	void tearDown() throws Exception {
		openMocks.close();
	}

	@Test
	void getIndexPage() {
		String expectedView = "index";
		
		Set<Recipe> recipes = new HashSet<Recipe>();
		Recipe recipe1 = new Recipe();
		recipe1.setId(1L);
		recipes.add(recipe1);
		Recipe recipe2 = new Recipe();
		recipe2.setId(2L);
		recipes.add(recipe2);
		
		when(recipeService.getRecipes()).thenReturn(recipes);
		
		ArgumentCaptor<Set<Recipe>> argumentCaptor = ArgumentCaptor.forClass(Set.class);

		String actualView = indexController.getIndexPage(model);

		assertEquals(expectedView, actualView);
		verify(recipeService, times(1)).getRecipes();
		verify(model, times(1)).addAttribute(eq("recipes"), argumentCaptor.capture());
		assertEquals(recipes, argumentCaptor.getValue());
	}

}
