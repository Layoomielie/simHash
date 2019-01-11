import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCursor;
import org.apdplat.word.WordSegmenter;
import org.apdplat.word.segmentation.SegmentationAlgorithm;
import org.apdplat.word.segmentation.Word;
import org.bson.Document;

import java.util.*;

/**
 * @author zhangHongJian
 * @create 2018/12/29
 * @descript                       此类用于生成五位hash值
 * @since 1.0.0
 */
public class test {
    public static void main(String[] args) {
        MongoSave mongoSave = new MongoSave();
        FindIterable iterable = mongoSave.getAllData();
        MongoCursor mongoCursor = iterable.iterator();

        long start = new Date().getTime();

        int i =0;
        while (mongoCursor.hasNext()) {
            Object obj = mongoCursor.next();
            if (obj == null) continue;
            Document document = (Document) obj;
            Object mainText = document.get("mainText");
          //  String  mainText2="第一章 招标公告 项目名称： 国电内蒙古电力有限公司信息系统测评项目招标（以下简称“项目”） 招标编号： GDCX03-FJZB18-0788( 01- 01) 日 期： 2018年11月27日 1.1 国电诚信招标有限公司（以下亦称“招标代理机构”），受 国电内蒙古电力有限公司（以下简称“招标人”）的委托，在中国招标投标公共服务平台(网址：http://www.cebpubservice.com)和国电招投标网(http://www.cgdcbidding.com)上采用国内 公开招标方式( 一步制、 网上开标)邀请符合招标文件要求的投标人，就本次招标项目的供货和服务提交密封的有竞争性的投标文件。 1.2 合格投标人的基本要求如下： 1.2.1 投标人在电子购标前应进行企业CA（certificate authority）注册，注册时须提供企业营业执照、组织机构代码证、税务登记证和银行基本账户开户许可证的彩色扫描件。 1.2.2 投标人必须是在中华人民共和国依法注册的独立企业法人，具备在其合法的营业范围内履行民事责任能力的企业。 1.2.3 投标人不得直接地或间接地与招标人或其雇佣或曾经雇佣过的，为本次招标编制技术规范和其他文件或提供咨询服务的公司包括其附属机构或电子招投标系统平台有任何关联。 1.2.4 投标人单位负责人为同一人或者存在控股、管理关系的不同单位，不得同时参加同一标的物的投标。 1.2.5投标人在专业技术、加工设备、人员组织、业绩经验、财力等方面具有设计、制造、质量控制、经营管理的资格和能力。 1.2.6 投标人须具有完善的质量保证体系及其相应的有效的认证证书。 1.2.7 投标人必须满足招标文件中规定的性能和质量的要求，必须提供标的物的性能保证值和/或带星号（“☆”）的技术参数(若有)的技术证明文件（包括但不限于标的物的型式试验报告和/或鉴定报告和/或性能验收试验报告等），达到技术先进、成熟、安全可靠、环保和成套供货的要求。 1.2.8 投标人须实质性地响应招标文件规定的合同条款。 1.2.9 标的物的主体、关键部分，未经招标人书面同意不得分包、转让。 1.2.10投标人没有处于被责令停业，财产被接管、冻结、破产和重组状态。 1.2.11 投标人及其分包商在近5年内必须不曾在任何合同中有违约或属投标人及其分包商的原因而被终止合同、必须不在处罚期内、必须没有与拟投标标的物类似的重大质量、安全事故。投标人没有被中国国家有关部门所界定的腐败或欺诈行为或近三年中没有在投标中违背1.2.10条的规定和履约中的不良商业信誉行为。 1.2.12财务及业绩必须满足下表的要求（若与招标文件其他部分业绩要求不一致，以本条为准）。 序号 分包名称 投标人财务、资质及业绩要求 1 信息系统测评 4、须提供近3年不少于1项电力企业信息系统安全等级保护检测业绩（合同+验收证明） 1、投标人必须具有中华人民共和国独立企业法人资格 2、须具备由省级及以上部门推荐的信息安全等级保护测评机构推荐证书 3、须具有中国信息安全认证中心颁发的风险评估资质证书 如1.2.12条只需提供合同，投标人须提供合同复印件（须满足1.2.12关于合同业绩的要求），业绩起止时间的认定为合同签订日期至投标文件截止日。 如1.2.12条须提供合同复印件和用户证明，则原则如下： 投标人须同时提供合同复印件（须满足1.2.12关于合同业绩的要求）和由用户签署的用户证明（需含供货名称、投运时间及供货单位名称）；投标人可参考附录中提供的模板填写。若投标人未按此要求提供业绩，其业绩为无效业绩。关于投运时间的认定是以用户证明中写明的本设备的投运时间为准，投标人业绩起止时间的认定为投运日期至投标文件截止日， 关于近xx年业绩中的“近xx年”的定义：自投标文件递交截止之日倒推xx年（只精确到月份）。 1.2.13 投标人在项目评标期间不存在被列为失信被执行人的情形，具体认定以全国法院失信被执行人名单信息公布与查询网(shixin.court.gov.cn)和国家发展改革委信用中国(www.creditchina.gov.cn）网站检索结果为准。 1.2.14 招标文件规定的其他条件。 1.3 本次招标不接受联合体投标。 1.4 招标范围为本次招标项目的供货和服务（以下简称标的物），主要包括： 国电内蒙古电力有限公司及所属单位二级、三级信息系统测评，按照《内蒙古自治区计算机信息系统安全保护办法》(自治区人民政府令第183号)和公安部、国家保密局、国家密码管理局、国务院信息化工作办公室联合制定的《信息安全等级保护管理办法》工作要求，开展等保测评工作。 同时也包括所有必要的材料、备品备件、专用工具、消耗品以及设计、培训、工厂检验、调试以及安装和试运行的指导等技术服务（以下简称“服务”）等。具体内容详见招标文件第二卷的有关规定。 1.5 潜在投标人必须以自己的名义有偿从招标代理机构获得招标文件后，才有权参加竞争性投标。招标人及招标代理机构拒绝接受未购买招标文件的投标人的投标。投标人获得的招标文件的版权属招标人和招标代理机构，仅供本次投标之用。 1.6 出售招标文件时间： 2018年11月27日 09:00整到 2018年12月03日 16:00整（北京时间)。 1.7 欲购买招标文件的潜在投标人，可在国电招投标网(网址：http://www.cgdcbidding.com)上查看招标公告、免费注册。缴纳平台使用费后，可进行招标文件购买、澄清、投标等事项。 1.8 出售招标文件方式： 招标文件购买采用网上支付的模式（不接受电汇、网银转账、现场现金支付等其他购买方式），系统同时支持企业网银支付和个人网银支付。 1.8.1 若通过公司账户购买，公司账户必须具备网上银行功能。网上支付时，由本网站跳转（链接）至购买人选择的银行网站进行。 1.8.2 若通过个人账户购买，将被认为购买人已经获得了公司的授权，等同于公司购买。购买前核实您的银行卡的网上支付单笔限额不少于招标文件售价，以免影响招标文件的购买。 1.8.3 购买招标文件人必须是投标人。潜在投标人在平台填写的投标人名称、通信地址、联系人、联系方式等基本信息必须准确无误，招投标全流程信息发布和联络以此为准。 1.8.4 支付成功后，投标人直接从网上下载招标文件电子版。招标人/招标代理机构不提供任何纸质招标文件。支付成功，即视为招标文件己经售出。除招标人或招标代理机构的原因外，招标文件一经售出，恕不退款。 1.8.5 招标代理机构将尽快按照购买招标文件时填写的邮寄地址将购买招标文件的商业发票邮寄给潜在投标人。 1.8.6 招标文件购买失败或其他问题，请与客服中心联系。 1.9 招标设备清单及招标文件售价： 序号 设备名称和规格 数量 招标文件售价 (元) 交货期 招标编号 投标保证金(元或比例) 1 信息系统测评 37个系统（二级系统29个，三级系统8个） 1200元 服务期限：2019年1月--2019年12月 GDCX03-FJZB18-0788/01 20000元 1.10 投标文件截止时间：投标人递交开标的电子投标文件截止时间为 2018年12月18日 15:00整（北京时间），逾期或不符合规定的投标文件恕不接受。 1.11 投标文件递交地点：投标人递交开标的电子投标文件地点为 国电招投标网(网址:http://www.cgdcbidding.com)。（联系电话： 400-010-4000）。 1.12 开标时间和地点：开标时间为 2018年12月18日 15:00，地点为 国电招投标网(网址:http://www.cgdcbidding.com)。 1.13 投标人投标时必须向国电诚信招标有限公司提供本招标文件规定的投标保证金。不得以个人名义电汇投标保证金。平台上填写的保证金账户名称、银行账号、开户银行名称等相关信息必须准确无误，保证金移交、退还以此为准。 投标保证金受益人的抬头、开户行、账号如下： 开户名：国电诚信招标有限公司 开户行：交通银行（如需填写支行请填写：北京西单支行） 帐 号：“投标人提交保证金信息后生成的保证金虚拟子账号” 1.14 有意向的潜在的投标人可从招标人或招标代理机构得到进一步的信息。若对招标公告内容有疑义，请与1.15条联系人联系。 1.15 联系方式。 招 标 人： 国电内蒙古电力有限公司 地 址： 内蒙古自治区呼和浩特市新城区海拉尔东街曙光培训大厦 邮 编： 010050 传 真： 0471-2267669 联 系 人： 陈亚红 电 话： 0476-8831312 招标代理机构：国电诚信招标有限公司 地 址： 北京市昌平区北七家镇未来科技城北2街 邮 编： 102209 电 话： 4000104000-1-8145 邮 箱： ylg8145@163.com 联 系 人： 耿永玲 客户服务：中国国电集团电子招投标平台客户服务中心 地 址：北京市昌平区北七家镇未来科技城新能源研究院101号楼5层 邮 编：102209 邮 箱：4000184000@b.qq.com 工作时间：9:00-12:00，13:00-17:00 购买CA:021-962600、021-36393171; 客户服务热线：4000104000转1";
            String mainText2=mainText.toString();
            String hash = "";
//            Integer sub1 =0;
//            Integer sub2 =0;
//            Integer sub3 =0;
//            Integer sub4 =0;
//            Integer sub5 =0;
//            Integer sub6 =0;
//            Integer sub7 =0;
//            Integer sub8 =0;
            String hash1="";
            String hash2="";
            String hash3="";
            String hash4="";
//            Set<String> hashSet = new HashSet<String>();
//            Map<String, Set> hashMap = new LinkedHashMap<String, Set>();
            Map<String, Object> map = new LinkedHashMap<String, Object>();
            try {
                if (mainText2 != null && !mainText2.equals("")) {

                    String listStr = document.getString("words");

                    if(listStr==null){
                        continue;
                    }
//                    List<Word> list = WordSegmenter.segWithStopWords(mainText2, SegmentationAlgorithm.MinimumMatching);

//                    System.out.println(list.toString());
               //     String text = list.toString().replace(",", "").replace("[", "").replace("]", "");
                    SimHash simHash = new SimHash(listStr, 64);
                    hash = simHash.strSimHash;
                    /*sub1 = Integer.parseInt(hash.substring(0, 8),2);
                    sub2 = Integer.parseInt(hash.substring(8, 16),2);
                    sub3 = Integer.parseInt(hash.substring(16, 24),2);
                    sub4 = Integer.parseInt(hash.substring(24, 32),2);
                    sub5 = Integer.parseInt(hash.substring(32, 40),2);
                    sub6 = Integer.parseInt(hash.substring(40, 48),2);
                    sub7 = Integer.parseInt(hash.substring(48, 56),2);
                    sub8 = Integer.parseInt(hash.substring(56, 64),2);*/
                    hash1=hash.substring(0,16 );
                    hash2=hash.substring(16, 32);
                    hash3=hash.substring(32, 48);
                    hash4=hash.substring(48, 64);
                }
            } catch (Exception e) {
                System.err.println(document.get("url"));
            }
            map.put("hash1", hash1);
            map.put("hash2", hash2);
            map.put("hash3", hash3);
            map.put("hash4", hash4);
            map.put("id",document.get("_id"));
//            map.put("url", document.get("url").toString());
           // map.put("url", "http://www.cgdcbidding.com:80/ggjg/26492.jhtml");
            map.put("hash", hash);
            mongoSave.setHash(map);
            System.out.println("  "+hash);


        }

        long end = new Date().getTime();
        System.out.println((end-start)/1000.0);
    }
}