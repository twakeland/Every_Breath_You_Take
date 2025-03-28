package edu.ycp.cs320.TBAG.model;

// model class for AddNumbers and MultiplyNumbers
// only the controller should be allowed to call the set methods
// the JSP will call the "get" methods implicitly
// when the JSP specifies number.first, that gets converted to
//    a call to model.getFirst()

public class Numbers {
	private double first, second, third, result;
	
	public Numbers(double first, double second, double third) {
		this.first = first;
		this.second = second;
		this.third = third;
	}
	
	public Numbers(double first, double second) {
		this.first = first;
		this.second = second;
	}
	
	public void setFirst(double first) {
		this.first = first;
	}
	
	public double getFirst() {
		return first;
	}
	
	public void setSecond(double second) {
		this.second = second;
	}
	
	public double getSecond() {
		return second;
	}
	
	public void setThird(double third) {
		this.third = third;
	}
	
	public double getThird() {
		return third;
	}
	
	public void setResult(double result) {
		this.result = result;
	}
	
	public double getResult() {
		return result;
	}
}
