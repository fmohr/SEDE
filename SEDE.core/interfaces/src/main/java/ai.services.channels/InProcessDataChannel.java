package ai.services.channels;

import ai.services.core.SEDEObject;

public interface InProcessDataChannel extends DataChannel {

    SEDEObject getObject(String fieldname);

    void setObject(String fieldname, SEDEObject object);

}
