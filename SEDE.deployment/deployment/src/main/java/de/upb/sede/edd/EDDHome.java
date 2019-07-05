package de.upb.sede.edd;

import java.io.File;

public class EDDHome {


    public static final String DEFAULT_EDD_USER_HOME = System.getProperty("user.home") + "/.edd";
    public static final String EDD_USER_HOME_PROPERTY_KEY = "edd.user.home";
    public static final String EDD_USER_HOME_ENV_KEY = "EDD_USER_HOME";
    private static final SystemSettingLookup EDD_HOME = new SystemSettingLookup(
        DEFAULT_EDD_USER_HOME,
        EDD_USER_HOME_PROPERTY_KEY,
        EDD_USER_HOME_ENV_KEY);

    private final LockableDir homeDir;

    private final SEDECodeBase codeBase;

    public EDDHome() {
        this(lookupHome());
    }

    EDDHome(File dir) {
        homeDir = new UnlockableHome(dir);
        codeBase = new SEDECodeBase(getHomeDir());
    }

    public LockableDir getHomeDir() {
        return homeDir;
    }

    public SEDECodeBase getSEDECodeBase(){
        return codeBase;
    }

    public LockableDir getPortDir() {
        return homeDir.getChild("ports");
    }

    public LockableDir getGroupsDir() {
        return homeDir.getChild("groups");
    }

    public LockableDir getGroupDir(String groupname) {
        return homeDir.getChild("groups").getChild(groupname);
    }

    public LockableDir getMarketDir(String marketName) {
        return homeDir.getChild("markets").getChild(marketName);
    }

    public LockableDir getAppDir(String appName) {
        return homeDir.getChild("apps").getChild(appName);
    }

    public LockableDir getServiceDir() {
        return homeDir.getChild("serviceinstances");
    }

    public static File lookupHome() {
        return EDD_HOME.lookupFile();
    }

    public File getRegistry() {
        return new File(homeDir.toFile(), "deployconf.json");
    }

    public LockableDir getLocalDeplDir() {
        return homeDir.getChild("localdepl");
    }


    private static class UnlockableHome extends DefaultLockableDir {

        public UnlockableHome(String path) {
            super(path);
        }

        public UnlockableHome(File file) {
            super(file);
        }

        @Override
        public AutoCloseable lockDir(boolean tryLock) throws DirLockAlreadyAcquiredException {
            throw new UnsupportedOperationException("Edd home shouldn't be locked..");
        }
    }
}
