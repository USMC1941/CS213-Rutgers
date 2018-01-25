package Photos.model;

import java.io.Serializable;

/**
 * This class represents a tag.
 * @author William Chen
 * @author Chijun Sha
 */
public class Tag implements Comparable<Tag>, Serializable {

    /**
	 * Serial Version UID
	 */
	private static final long serialVersionUID = -9161460696154678034L;

    /**
     * Tag name
     */
    private String tagName;

    /**
     * Tag value
     */
    private String tagValue;

    /**
     * @param _tagName Tag name
     * @param _tagValue Tag value
     */
    public Tag(String _tagName, String _tagValue) {
        tagName = _tagName;
        tagValue = _tagValue;
    }


    /**
     * @param t Input tag
     */
    public Tag(Tag t) {
        tagName = t.tagName;
        tagValue = t.tagValue;
    }


    /**
     * @return Output for tags
     */
    public String toString() {
    	return tagName + "=" + tagValue;
    }

    /**
     * @param tag Input tag
     * @return Checks whether tags are equal to each other
     */
    @Override
    public int compareTo(Tag tag) {
    	int iRet = tagName.compareToIgnoreCase(tag.tagName);
    	return (iRet!=0) ? iRet : tagValue.compareToIgnoreCase(tag.tagValue);
    }
}