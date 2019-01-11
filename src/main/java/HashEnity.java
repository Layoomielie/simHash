import org.bson.Document;

import java.util.*;

/**
 * @author zhangHongJian
 * @create 2019/1/8
 * @descript                二次判断
 * @since 1.0.0
 */
public class HashEnity {
    private Document document;
    private String maintext;
    private String Rep;

    public Document getDocument() {
        return document;
    }

    public void setDocument(Document document) {
        this.document = document;
    }

    public String getMaintext() {
        return maintext;
    }

    public void setMaintext(String maintext) {
        this.maintext = maintext;
    }

    public String getRep() {
        return Rep;
    }

    public void setRep(String rep) {
        Rep = rep;
    }
}