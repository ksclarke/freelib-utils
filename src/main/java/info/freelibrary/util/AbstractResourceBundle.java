
package info.freelibrary.util;

import java.io.File;
import java.util.Enumeration;
import java.util.Properties;
import java.util.ResourceBundle;

/**
 * A base class for resource bundles.
 */
abstract class AbstractResourceBundle extends ResourceBundle implements I18nResourceBundle {

    /**
     * The bundle's properties.
     */
    protected final Properties myProperties;

    /**
     * Creates a new abstract resource bundle.
     *
     * @param aProperties Contents of the resource bundle
     */
    protected AbstractResourceBundle(final Properties aProperties) {
        super();
        myProperties = aProperties;
    }

    @Override
    protected Object handleGetObject(final String aKey) {
        return myProperties.getProperty(aKey);
    }

    @Override
    public int countKeys() {
        return myProperties.size();
    }

    @Override
    public Enumeration<String> getKeys() {
        final Enumeration<?> enumeration = myProperties.elements();

        return new Enumeration<>() {

            /**
             * Returns whether the {@link Enumeration} has more elements.
             *
             * @return True if the {@link Enumeration} has more elements; else, false.
             */
            @Override
            public boolean hasMoreElements() {
                return enumeration.hasMoreElements();
            }

            /**
             * Returns the next element in the {@link Enumeration}.
             *
             * @return The next element in the {@link Enumeration}
             */
            @Override
            public String nextElement() {
                return (String) enumeration.nextElement();
            }
        };
    }

    @Override
    public String get(final String aMessage, final String... aArray) {
        return StringUtils.format(super.getString(aMessage), aArray);
    }

    @Override
    public String get(final String aMessage, final String aDetail) {
        return StringUtils.format(super.getString(aMessage), aDetail);
    }

    @Override
    public String get(final String aMessage) {
        return getString(aMessage);
    }

    @Override
    public String get(final String aMessage, final File... aFileArray) {
        final String[] details = new String[aFileArray.length];

        for (int index = 0; index < details.length; index++) {
            details[index] = aFileArray[index].getAbsolutePath();
        }

        return StringUtils.format(super.getString(aMessage), details);
    }

    @Override
    public String get(final String aMessage, final File aFile) {
        return StringUtils.format(super.getString(aMessage), aFile.getAbsolutePath());
    }

    @Override
    public String get(final File aFile) {
        return getString(aFile.getAbsolutePath());
    }

    @Override
    public String get(final String aMessage, final Object... aDetailsArray) {
        final String[] details = new String[aDetailsArray.length];

        for (int index = 0; index < details.length; index++) {
            details[index] = aDetailsArray[index].toString();
        }

        return StringUtils.format(super.getString(aMessage), details);
    }

    @Override
    public String get(final String aMessage, final Object aDetail) {
        return StringUtils.format(super.getString(aMessage), aDetail.toString());
    }

    @Override
    public String get(final Object aObject) {
        return getString(aObject.toString());
    }

    @Override
    public boolean containsKey(final String aKey) {
        return myProperties.containsKey(aKey);
    }

}
