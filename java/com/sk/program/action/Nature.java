package com.sk.program.nature;

public class Nature {

    /* 	The aspects of Human personality captured in 4 dimensions
     * 	which can be extended to Groups as well
     * 	Used to derive Philosophy
     *	private enum dimension1 {MORALITY (1), IMMORALITY (0)}
     *	private enum dimension2 {INTEGRITY (1), FRAGILITY (0)}
     *	private enum dimension3 {COEXISTENCE (1), SURVIVALISM (0)}
     *	private enum dimension4 {RATIONALITY (1), IRRATIONALITY (0)}
     */

	public Nature(float d1, float d2, float d3, float d4) {
        this.nature = d1+"-"+d2+"-"+d3+"-"+d4;
    }
	
	private String nature;
	public static final String BENEVOLENT = "Benevolence";
	public static final String MALEVOLENT = "Malevolence";
	public static final String INDIFFERENT = "Indifference";

    public void setNature(float d1, float d2, float d3, float d4) {
        this.nature = d1+"-"+d2+"-"+d3+"-"+d4;
    }
    public String getNature() {
        return this.nature;
    }

    public String getNatureType() {
    	String[] dimensions = this.nature.split("-");
    	float AH = Float.parseFloat(dimensions[0]);
		float BH = Float.parseFloat(dimensions[1]);
		float CH = Float.parseFloat(dimensions[2]);
		float DH = Float.parseFloat(dimensions[3]);
		int d1 = AH>0.5?1:0; 
		int d2 = BH>0.5?1:0;
		int d3 = CH>0.5?1:0;
		int d4 = DH>0.5?1:0;
		String n = d1+"."+d2+"."+d3+"."+d4;
		String type="";
		switch(n) {
			case "1.1.1.1":
			case "1.1.1.0":
			case "1.0.1.1": 
				type = BENEVOLENT;
				break;
			case "1.1.0.1": 
			case "1.1.0.0":
			case "1.0.1.0":
			case "1.0.0.1": 
			case "1.0.0.0":
			case "0.1.1.1": 
			case "0.0.1.1": 
			case "0.0.1.0":
				type = INDIFFERENT;
				break;
			case "0.1.1.0":
			case "0.1.0.1":
			case "0.1.0.0":
			case "0.0.0.1":
			case "0.0.0.0":
				type = MALEVOLENT;
				break;
		}
		return type;
    }
}
