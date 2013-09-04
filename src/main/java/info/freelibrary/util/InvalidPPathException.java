/**
 * Licensed under the GNU LGPL v.2.1 or later.
 */

package info.freelibrary.util;

/**
 * An exception thrown for an invalid Pairtree path.
 * 
 * @author <a href="mailto:ksclarke@gmail.com">Kevin S. Clarke</a>
 */
public class InvalidPPathException extends Exception {

    /**
     * The <code>serialVersionUID</code> for <code>InvalidPpathException</code>.
     */
    private static final long serialVersionUID = -3816513744343123355L;

    /**
     * Constructor for an invalid Pairtree path.
     * 
     * @param aMessage
     */
    public InvalidPPathException(String aMessage) {
        super(aMessage);
    }

}
