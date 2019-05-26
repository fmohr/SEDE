package de.upb.sede.edd;

import java.io.File;

public class EDDHome extends LockableDirImpl implements LockableDir {


    public static final String DEFAULT_EDD_USER_HOME = System.getProperty("user.home") + "/.edd";
    public static final String EDD_USER_HOME_PROPERTY_KEY = "edd.user.home";
    public static final String EDD_USER_HOME_ENV_KEY = "EDD_USER_HOME";
    private static final SystemSettingLookup EDD_HOME = new SystemSettingLookup(
        DEFAULT_EDD_USER_HOME,
        EDD_USER_HOME_PROPERTY_KEY,
        EDD_USER_HOME_ENV_KEY);

    public EDDHome() {
        super(lookupHome());
    }

    public static File lookupHome() {
        return EDD_HOME.lookupFile();
    }

    @Override
    public AutoCloseable lockDir(boolean tryLock) throws DirLockAlreadyAcquiredException {
        throw new UnsupportedOperationException("Edd home shouldn't be locked..");
    }
}
