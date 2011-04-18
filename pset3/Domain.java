public class Domain {
	private int[] ints;
	private boolean[] booleans;
	private String[] strings;

	public Domain() {
		// set fields to default values; "set" methods can modify them
		ints = new int[] { -1, 0, 1 };
		booleans = new boolean[] { false, true };
		strings = new String[] { null, "\"\"", "\"3\"", "\"Hello\"" };
	}

	public void setInts(int[] is) {
		ints = is;
	}

	public void setBooleans(boolean[] bs) {
		booleans = bs;
	}

	public void setStrings(String[] ss) {
		strings = ss;
	}

	public int[] getInts() {
		return ints;
	}

	public boolean[] getBooleans() {
		return booleans;
	}

	public String[] getStrings() {
		return strings;
	}
}