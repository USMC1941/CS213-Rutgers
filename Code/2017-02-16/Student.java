package functional;

public class Student {
	public static final int FRESHMAN=1;
	public static final int SOPHOMORE=2;
	public static final int JUNIOR=3;
	public static final int SENIOR=4;
	
	public static final String[] years = {"fr","so","jr","sr"};
	
	private int year;
	private boolean commuter;
	private String major;
	
	public Student(int year, boolean commuter, String major) {
		this.year = year;
		this.commuter = commuter;
		this.major = major;
	}
	
	public Student(int year) {
		this(year, false, "Unknown");
	}
	
	public Student() {
		this(FRESHMAN, false, "Unknown");
	}
	
	public Student(int year, String major) {
		this(year, false, major);
	}
	
	public int getYear() {
		return year;
	}
	
	public boolean getCommuter() {
		return commuter;
	}
	
	public String getMajor() {
		return major;
	}
	
	public String toString() {
		return "(" + years[year-1] + "," + (commuter ? "commuter" : "resident") + "," + major + ")";
		
	}
	
	public boolean isFreshman() {
		return year == FRESHMAN;
	}
	
	public boolean isSophomore() {
		return year == SOPHOMORE;
	}
	
	public boolean isJunior() {
		return year == JUNIOR;
	}
	
	public boolean isSenior() {
		return year == SENIOR;
	}
}
