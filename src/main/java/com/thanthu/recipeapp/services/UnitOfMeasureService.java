package com.thanthu.recipeapp.services;

import java.util.Set;

import com.thanthu.recipeapp.commands.UnitOfMeasureCommand;

public interface UnitOfMeasureService {
	
	Set<UnitOfMeasureCommand> listAllUoms();

}
