package de.upb.sede.edd;

import de.upb.sede.util.FileCache;

public class LocalEDDInfo extends EDDInfo {

    public LocalEDDInfo(EDDHome home) {
        super(new FileCache<>(home.getHomeDir().subFile("edd.info.json"),
            EDDInfoModel::decode,
            EDDInfoModel::encode));
        setIdentifier(getIdentifier());
    }

}
