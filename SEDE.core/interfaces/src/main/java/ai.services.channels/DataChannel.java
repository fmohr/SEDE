package ai.services.channels;

public interface DataChannel {

    UploadLink getUploadLink(String fieldname, String semantictype);

    DownloadLink getDownloadLink(String fieldname);

}
