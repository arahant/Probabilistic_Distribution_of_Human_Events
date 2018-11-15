package com.sk.program;

public class Nature {

    /*
    private enum dimension1 {MORALITY, IMMORALITY} (Red)
    private enum dimension2 {INTEGRITY, FRAGILITY} (Green)
    private enum dimension3 {COXISTENCE, SURVIVALISM} (Blue)
    The Base Natures are Benevolence (Blue), Indifference (Green) and Malevolence (Red)
    */

	public Nature(float d1, float d2, float d3) {
        this.nature = d1+"-"+d2+"-"+d3;
    }
	
	private String nature;
	public static final String BENEVOLENT = "Benevolence";
	public static final String MALEVOLENT = "Malevolence";
	public static final String INDIFFERENT = "Indifference";

    public void setNature(float d1, float d2, float d3) {
        this.nature = d1+"-"+d2+"-"+d3;
    }
    public String getNature() {
        return this.nature;
    }

    public String getNatureType() {
    	String[] dimensions = this.nature.split("-");
    	float RH = Float.parseFloat(dimensions[0]);
		float GH = Float.parseFloat(dimensions[1]);
		float BH = Float.parseFloat(dimensions[2]);
		int d1 = RH>0.5?1:0; 
		int d2 = GH>0.5?1:0;
		int d3 = BH>0.5?1:0;
		String n = d1+"."+d2+"."+d3;
		String type="";
		switch(n) {
			case "1.1.1": 
				type = BENEVOLENT;
				break;
			case "1.1.0": 
				type = INDIFFERENT;
				break;
			case "1.0.1": 
				type = BENEVOLENT;
				break;
			case "1.0.0": 
				type = INDIFFERENT;
				break;
			case "0.1.1": 
				type = INDIFFERENT;
				break;
			case "0.1.0": 
				type = MALEVOLENT;
				break;
			case "0.0.1": 
				type = INDIFFERENT;
				break;
			case "0.0.0": 
				type = MALEVOLENT;
				break;
		}
		return type;
    }
}
