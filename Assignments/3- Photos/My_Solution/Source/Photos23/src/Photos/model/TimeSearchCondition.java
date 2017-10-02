package Photos.model;

import java.io.Serializable;
import java.time.LocalDate;


/**
 * This class represents an album.
 * @author William Chen
 * @author Chijun Sha
 */
public class TimeSearchCondition implements Serializable {

	/**
	 * Serial Version UID
	 */
	private static final long serialVersionUID = 6373163668095808715L;


    /**
     * Start date for range of dates
     */
    private LocalDate startDate = null;

    /**
     * End date for range of dates
     */
    private LocalDate endDate = null;

	
	public TimeSearchCondition() {

	}


    /**
     * @return End date
     */
    public LocalDate getStartDate() {
		return startDate;
	}


    /**
     * @param startDate Start date
     */
    public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}


    /**
     * @return End date
     */
    public LocalDate getEndDate() {
		return endDate;
	}


    /**
     * @param endDate End date
     */
    public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}
}