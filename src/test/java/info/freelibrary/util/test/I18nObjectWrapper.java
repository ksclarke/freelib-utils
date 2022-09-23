
package info.freelibrary.util.test;

import info.freelibrary.util.I18nObject;

/**
 * A wrapper to make testing I18nObject easier.
 */
public class I18nObjectWrapper extends I18nObject {

    /**
     * Generic constructor for the I18NObject.
     */
    public I18nObjectWrapper() {
        super("test_freelib-utils_messages");
    }

    /**
     * Generic constructor for the I18nObject.
     *
     * @param aName A resource bundle name
     */
    public I18nObjectWrapper(final String aName) {
        super(aName);
    }

    /**
     * Gets the number of keys in the bundle.
     */
    @Override
    public int countKeys() {
        return super.countKeys();
    }
}
