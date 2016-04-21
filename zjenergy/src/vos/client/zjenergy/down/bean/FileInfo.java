package vos.client.zjenergy.down.bean;

import java.io.Serializable;

/**
 * Created by ws_cjlu on 2016/4/15.
 *
 * 文件信息
 */
public class FileInfo implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public int id;
    public String fileName;
    public String url;
    public int finished;
    public int length;

    public FileInfo(int id, String fileName, String url, int finished, int length) {
        this.id = id;
        this.fileName = fileName;
        this.url = url;
        this.finished = finished;
        this.length = length;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int getFinished() {
        return finished;
    }

    public void setFinished(int finished) {
        this.finished = finished;
    }

    @Override
    public String toString() {
        return "FileInfo{" +
                "id=" + id +
                ", fileName='" + fileName + '\'' +
                ", url='" + url + '\'' +
                ", finished=" + finished +
                ", length=" + length +
                '}';
    }
}
