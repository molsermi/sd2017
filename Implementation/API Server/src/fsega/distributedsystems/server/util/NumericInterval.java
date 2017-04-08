package fsega.distributedsystems.server.util;

public class NumericInterval <T extends Number> {
	private T min;
	private T max;
	
	public NumericInterval (T min, T max) {
		this.min = min;
		this.max = max;
	}
	
	public boolean isInRange(T number) {
		double doubleNumber = number.doubleValue();
		return doubleNumber >= min.doubleValue() && doubleNumber <= max.doubleValue();
	}
}
