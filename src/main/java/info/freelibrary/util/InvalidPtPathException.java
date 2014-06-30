/**
 * Licensed under the GNU LGPL v.2.1 or later.
 */

package info.freelibrary.util;

/**
 * An exception thrown for an invalid Pairtree path.
 * 
 * @author <a href="mailto:ksclarke@gmail.com">Kevin S. Clarke</a>
 */
public class InvalidPtPathException extends Exception {

    /**
     * The <code>serialVersionUID</code> for <code>InvalidPpathException</code>.
     */
    private static final long serialVersionUID = -3816513744343123355L;

    /**
     * Constructor for an invalid Pairtree path.
     * 
     * @param aMessage
     */
    public InvalidPtPathException(final String aMessage) {
        super(aMessage);
    }

    /**
     * Constructor for an invalid Pairtree path that takes an exception message and additional details to be input into
     * the {}s in the message.
     * 
     * @param aMessage An exception message
     * @param aDetailsVarargs Additional details to insert into the message
     */
    public InvalidPtPathException(final String aMessage, final String... aDetailsVarargs) {
        super(StringUtils.format(aMessage, aDetailsVarargs));
    }

    /**
     * Constructor for an invalid Pairtree path that takes an exception message and a related exception.
     * 
     * @param aMessage An exception message
     * @param aCause An upstream exception
     */
    public InvalidPtPathException(final String aMessage, final Exception aCause) {
        super(aMessage, aCause);
    }

    /**
     * Constructor for an invalid Pairtree path that takes an exception message, a related exception, and additional
     * details to be input into the {}s in the message.
     * 
     * @param aMessage An exception message
     * @param aCause An upstream exception
     * @param aDetailsVarargs Additional details to insert into the message
     */
    public InvalidPtPathException(final String aMessage, final Exception aCause, final String... aDetailsVarargs) {
        super(StringUtils.format(aMessage, aDetailsVarargs), aCause);
    }
}
