package uk.co.bbc.team12;

public class Term {
	
	private String value;
	private int frequency;
	
	public Term() {
		
	}
	
	public Term(String string, int i) {
		this.value = string;
		this.frequency = i;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public int getFrequency() {
		return frequency;
	}
	public void setFrequency(int frequency) {
		this.frequency = frequency;
	}
	
	public String toString() {
		return "Value: " + value + " Freq: " + frequency;
	}

}
