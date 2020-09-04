package ai.services.channels;

public interface ExecutionDataChannel {

    UploadLink getUploadLink(String fieldname, String semantictype);

//    DownloadLink getDownloadLink(String fieldname);
//    SEDEObject inspectField(String fieldname);

}
