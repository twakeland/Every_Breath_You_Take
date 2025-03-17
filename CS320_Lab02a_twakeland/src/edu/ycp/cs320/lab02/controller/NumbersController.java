package edu.ycp.cs320.lab02.controller;

import edu.ycp.cs320.lab02.model.Numbers;

public class NumbersController {
	private Numbers model;
	
	public void setModel(Numbers model) {
		this.model = model;
	}
	
	public void add() {
		model.setResult(model.getFirst() + model.getSecond() + model.getThird());
	}
	
	public void multiply() {
		model.setResult(model.getFirst() * model.getSecond());
	}
}
