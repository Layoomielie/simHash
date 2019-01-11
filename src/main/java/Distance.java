import com.mongodb.Cursor;
import com.mongodb.DBObject;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCursor;
import org.bson.Document;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author zhangHongJian
 * @create 2019/1/8
 * @descript   算出文本距离       一次算相似度用process   二次统计用getRepFlag   两者不要一起用
 * @since 1.0.0
 */
public class Distance {

    static int simflag = 0;
    static Integer simNum = 0;
 //   static Integer ReqNum=1;
    MongoSave mongoSave = new MongoSave();


    public void process() {
        Distance distance = new Distance();
        HashMap<String, Map<String, String>> hashMap = new HashMap<>();
        FindIterable<Document> iterable = mongoSave.getAllData();
        for (Document document : iterable) {
            simflag = 0;
            String hash = document.getString("hash");
            String hash1 = document.getString("hash1");
            String hash2 = document.getString("hash2");
            String hash3 = document.getString("hash3");
            String hash4 = document.getString("hash4");

            Boolean flag1 = distance.QueryKey(hashMap, hash1);
            Boolean flag2 = distance.QueryKey(hashMap, hash2);
            Boolean flag3 = distance.QueryKey(hashMap, hash3);
            Boolean flag4 = distance.QueryKey(hashMap, hash4);
            if (flag1) {
                hashMap = distance.queryHash(hashMap, hash1, hash, document);
            } else {
                hashMap = distance.setKey(hashMap, hash1, hash);
            }
            if (flag2) {
                hashMap = distance.queryHash(hashMap, hash2, hash, document);
            } else {
                hashMap = distance.setKey(hashMap, hash2, hash);
            }
            if (flag3) {
                hashMap = distance.queryHash(hashMap, hash3, hash, document);
            } else {
                hashMap = distance.setKey(hashMap, hash3, hash);
            }
            if (flag4) {
                hashMap = distance.queryHash(hashMap, hash4, hash, document);
            } else {
                hashMap = distance.setKey(hashMap, hash4, hash);
            }
            if (simflag == 0) {
                mongoSave.setFlag(document, simNum.toString());
            }
            simNum++;
        }

    }

    public HashMap setKey(HashMap hashMap, String keyHash, String hash) {
        LinkedHashMap<String, String> map = new LinkedHashMap<>();
        map.put(hash, simNum.toString());
        hashMap.put(keyHash, map);
        return hashMap;
    }

    /**
     * 功能描述:
     *
     * @descript: 如果当前hash存在则算Key值  若不存在则添加进去
     */
    public Boolean QueryKey(Map<String, Map<String, String>> hashMap, String hash) {
        for (String key : hashMap.keySet()) {
            if (key.equals(hash)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 功能描述: <br>
     *
     * @descript: 算出hash之间的距离
     */
    public HashMap<String, Map<String, String>> queryHash(HashMap KeyMap, String hashKey, String hash, Document document) {
        SimHash simHash = new SimHash();
        Object object = KeyMap.get(hashKey);

        Map<String, String> map = (Map) object;
        for (String key : map.keySet()) {
            int dis = simHash.getDistance(key, hash);
            if (dis < 3) {
                String flag = map.get(key);//如果存在距离小于3的  则取其flag 作为hash新的flag
                simflag = 1;
                mongoSave.setFlag(document, flag);
                map.put(hash, flag);
                KeyMap.put(hashKey, map);
                return KeyMap;
            }
        }
        //距离没有想相似的
        map.put(hash, simNum.toString());
        KeyMap.put(hashKey, map);
        return KeyMap;
    }

    /**
     * 功能描述: <br>
     *
     * @descript: 去除所有字符
     */
    public String removeAllSymbol(String text) {
        String regEx = "[\n`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。， 、？]";
        String blank = "";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(text);
        return m.replaceAll(blank).trim();

    }

    /**
     * 功能描述: <br>
     *
     * @descript: 获取相似文本
     */
    public void getMainText(String flag) {
        FindIterable iterable = mongoSave.queryMainText(flag);
        MongoCursor<Document> cursor = iterable.iterator();
        int updateFlag = 0;
     //   ReqNum=0;
        List<HashEnity> list = new ArrayList<>();
        while (cursor.hasNext()) {
            updateFlag=0;
            Document document = cursor.next();
            HashEnity enity = new HashEnity();
            String mainText = (String) document.get("mainText");
            mainText = removeAllSymbol(mainText);
            for (HashEnity hashEnity : list) {
                String text = hashEnity.getMaintext();
                if (text.contains(mainText) || mainText.contains(text)) {
                    updateFlag = 1;
                    enity.setDocument(document);
                    enity.setMaintext(mainText);
                    enity.setRep("1");
                    list.add(enity);
                    mongoSave.setRep(document, "1");
                  //  continue;
                    break;
                }
            }
            if (updateFlag == 0) {
                enity.setDocument(document);
                enity.setMaintext(mainText);
                enity.setRep("0");
                list.add(enity);
                mongoSave.setRep(document, "0");
            }
        }
    }

    /**
     * 功能描述: <br>
     *
     * @descript: 获取 重复的flag
     */
    public void getRepFlag() {
        try {
            Cursor cursor = mongoSave.queryFlag();
            while (cursor.hasNext()) {
                DBObject db = cursor.next();
                String flag = (String) db.get("_id");
                getMainText(flag);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static void main(String[] args) {
        long startTime = new Date().getTime();
        Distance distance = new Distance();
//        distance.process();
//        Distance distance = new Distance();
//        String text="fhioas9423$$(%) ?p    .   das;:gd  dqaw+-+f '^*(";
//        System.out.println(distance.removeAllSymbol(text));
        distance.getRepFlag();
        long endTime = new Date().getTime();
        System.out.println((endTime - startTime) / 1000.0);

    }

}