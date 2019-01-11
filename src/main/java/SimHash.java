

import org.apdplat.word.WordSegmenter;
import org.apdplat.word.segmentation.Word;

import java.math.BigInteger;
import java.util.*;
/**
 * @author zhangHongJian
 * @create 2018/12/29
 * @descript             计算hash
 * @since 1.0.0
 */
public class SimHash {

    public String tokens;

    public BigInteger intSimHash;

    public String strSimHash;

    private int hashbits = 64;

    public SimHash() {
    }

    public SimHash(String tokens) {
        this.tokens = tokens;
        this.intSimHash = this.simHash();
    }

    public SimHash(String tokens, int hashbits) {
        this.tokens = tokens;
        this.hashbits = hashbits;
        this.intSimHash = this.simHash();
    }

    public BigInteger simHash() {
        int[] v = new int[this.hashbits];
        StringTokenizer stringTokens = new StringTokenizer(this.tokens);
        while (stringTokens.hasMoreTokens()) {
            String temp = stringTokens.nextToken();
            BigInteger t = this.hash(temp);
            System.out.println(t);
            for (int i = 0; i < this.hashbits; i++) {
                BigInteger bitmask = new BigInteger("1").shiftLeft(i);
                if (t.and(bitmask).signum() != 0) {
                    v[i] += 1;
                } else {
                    v[i] -= 1;
                }
            }
        }
        BigInteger fingerprint = new BigInteger("0");
        StringBuffer simHashBuffer = new StringBuffer();
        for (int i = 0; i < this.hashbits; i++) {
            if (v[i] >= 0) {
                fingerprint = fingerprint.add(new BigInteger("1").shiftLeft(i));
                simHashBuffer.append("1");
            }else{
                simHashBuffer.append("0");
            }
        }
        this.strSimHash = simHashBuffer.toString();
        System.out.println(this.strSimHash + " length " + this.strSimHash.length());

        return fingerprint;
    }

    private BigInteger hash(String source) {
        if (source == null || source.length() == 0) {
            return new BigInteger("0");
        } else {
            char[] sourceArray = source.toCharArray();
            BigInteger x = BigInteger.valueOf(((long) sourceArray[0]) << 7);
            BigInteger m = new BigInteger("1000003");
            BigInteger mask = new BigInteger("2").pow(this.hashbits).subtract(
                    new BigInteger("1"));
            for (char item : sourceArray) {
                BigInteger temp = BigInteger.valueOf((long) item);
                x = x.multiply(m).xor(temp).and(mask);
            }
            x = x.xor(new BigInteger(String.valueOf(source.length())));
            if (x.equals(new BigInteger("-1"))) {
                x = new BigInteger("-2");
            }
            return x;
        }
    }



    public int hammingDistance(SimHash other) {

        BigInteger x = this.intSimHash.xor(other.intSimHash);
        int tot = 0;

        //统计x中二进制位数为1的个数
        //我们想想，一个二进制数减去1，那么，从最后那个1（包括那个1）后面的数字全都反了，对吧，然后，n&(n-1)就相当于把后面的数字清0，
        //我们看n能做多少次这样的操作就OK了。

        while (x.signum() != 0) {
            tot += 1;
            x = x.and(x.subtract(new BigInteger("1")));
        }
        return tot;
    }


    public int getDistance(String str1, String str2) {
        int distance;
        if (str1.length() != str2.length()) {
            distance = -1;
        } else {
            distance = 0;
            for (int i = 0; i < str1.length(); i++) {
                if (str1.charAt(i) != str2.charAt(i)) {
                    distance++;
                }
            }
        }
        return distance;
    }


    public List subByDistance(SimHash simHash, int distance){
        int numEach = this.hashbits/(distance+1);
        List characters = new ArrayList();

        StringBuffer buffer = new StringBuffer();

        int k = 0;
        for( int i = 0; i < this.intSimHash.bitLength(); i++){
            boolean sr = simHash.intSimHash.testBit(i);

            if(sr){
                buffer.append("1");
            }
            else{
                buffer.append("0");
            }

            if( (i+1)%numEach == 0 ){
                BigInteger eachValue = new BigInteger(buffer.toString(),2);
                System.out.println("----" +eachValue );
                buffer.delete(0, buffer.length());
                characters.add(eachValue);
            }

        }

        return characters;
    }

    public static void main(String[] args) {
/*      String s = "This is a test string for testing";

        SimHash hash1 = new SimHash(s, 64);
        System.out.println(hash1.intSimHash + "  " + hash1.intSimHash.bitLength());

        hash1.subByDistance(hash1, 3);

        System.out.println("\n");
        s = "This is a test string for testing, This is a test string for testing abcdef";
        SimHash hash2 = new SimHash(s, 64);
        System.out.println(hash2.intSimHash+ "  " + hash2.intSimHash.bitCount());
        hash1.subByDistance(hash2, 3);

        s = "This is a test string for testing als";
        SimHash hash3 = new SimHash(s, 64);
        System.out.println(hash3.intSimHash+ "  " + hash3.intSimHash.bitCount());
        hash1.subByDistance(hash3, 3);
        System.out.println("============================");

        int dis = hash1.getDistance(hash1.strSimHash,hash2.strSimHash);

        System.out.println(hash1.hammingDistance(hash2) + " "+ dis);

        int dis2 = hash1.getDistance(hash1.strSimHash,hash3.strSimHash);

        System.out.println(hash1.hammingDistance(hash3) + " " + dis2);*/
        try {

            /*File file = new File("D:/simWord/a.txt");
            File file2 = new File("D:/simWord/c.txt");
            InputStreamReader read = new InputStreamReader(new FileInputStream(file), "utf-8");
            BufferedReader reader = new BufferedReader(read);
            String str = reader.readLine();*/
            String str1="\t项目及标段名称 理县孟屯河谷旅游基础设施建设项目施工、监理(施工标段) 项目业主 理县旅游发展局 项目业主联系电话 0837-6822888 招标人 理县旅游发展局 招标人联系电话 0837-6822888 招标代理机构 河北筑城工程招标咨询有限公司 招标代理机构 联系电话 028-65018150 开标地点 开标室3 开标时间 2018-12-14 10:00:00 公示期 2018 年 12 月 19 日 至 2018 年 12 月 21 日 投标最高限价（元） 33751823.010000 流标 流标 流标原因 中标候选人项目管理机构主要人员 其他投标人（除中标候选人之外）的评审情况 序号 投标人名称 投标报价（元） 或 否决投标依据条款（投标文件被 认定为不合格所依据的招标文件 评标办法中的评审因素和评审标 准的条款） 经评审的投标价（元或%） 或 否决投标理由（投标文件被认定为不 合格的具体事实，不得简单地表述为未 响应招标文件实质性内容、某处有问 题等） 综合评审得分 或 备注 其它需公示的内容 评标委员会成员名单 姓名：无 单位：无 监督部门名称及监督电话 项目审批部门： 联系电话： 行业主管部门： 联系电话： *异议投诉注意事项 \n" +
                    "\t1.投标人或者其他利害关系人对依法必须进行招标的项目的评标结果有异议的，应当在中标候选人公示期间提出。招标人应当自收到异议之日起3日内作出答复；作出答复前，应当暂停招标投标活动。\n" +
                    "\t2.投标人或者其他利害关系人认为评标结果不符合法律、行政法规规定的，可以自知道或者应当知道之日起10日内向有关行政监督部门投诉。投诉前应当先向招标人提出异议，异议答复期间不计算在前款规定的期限内。投诉书应当符合《工程建设项目招标投标活动投诉处理办法》规定。\n" +
                    "\t3.对评标结果的投诉，涉及投标人弄虚作假骗取中标的由行业主管部门负责受理，涉及评标错误或评标无效的由项目审批部门负责受理。\n" +
                    "\t4.投诉人就同一事项向两个以上有权受理的行政监督部门投诉的，由最先收到投诉的行政监督部门负责处理。\n" +
                    "\t5.应先提出异议没有提出异议，超过投诉时效等不符合受理条件的投诉，有关行政监督部门不予受理；投诉人故意捏造事实、伪造证明材料或者以非法手段取得证明材料进行投诉，给他人造成损失的，依法承担赔偿责任。 序号 附件名称 上传时间";
            String str2="一、招标项目简介 1、项目名称：精整厂、三炼钢厂编码器等电器备件招标 2、项目位置：江苏省南京市南京钢铁股份有限公司。 3、送货地点：南钢。 4、交货期：见下表。 5、投标单位要求提供原产地证明。 二、招标范围 见明细资料 三、投标单位资质要求 1、非合格供应商必须具备下列条件 1）、投标人在法律上和财务上独立并能合法运作，具有法人地位和独立订立合同的能力。 2）、注册资金不少于 100 万元。 3）、投标人具有良好的银行资信和商业信誉，没有处于被责令停业或破产状态，且资产未被重组、接管和冻结。 4）、投标人营业执照经营范围应包含本次招标标的物范围。 2、投标范围其他电器设备C级以上合格供应商可直接参与投标，等级达不到要求但符合上述条款要求的合格供应商也可报名参加投标。 四、本标采用资格后审，非合格供应商投标必须提交以下资料（在系统上上传） 1、公司简介、营业执照、组织机构代码、税务登记证、资质证书、管理体系认证等（均要在有效期内）； 2、报名联系业务人员必须为企业正式在职员工，提供有效证明文件（社保证明、名片、身份证复印件加盖公司公章、授权委托书等）； 3、近3年主要相类似业绩及相关证明文件，以备考察； 4、近3年企业财务状况审计报告、获奖和荣誉证书、专利证书、产品技术介绍等资料可根据自身情况提供。 5、各种资质文件均需加盖企业公章； 五、报名 1、报名方式：点击南京鑫智链科技信息有限公司网站（http://mq.nisco.cn）招标公告进行报名和投标。 2、报名截止日期：2018年12月23日 13时30分 3、报名联系人及地址： 联系部门： 南京鑫智链科技信息有限公司 联系人：邵玉和 联系电话：13813987628 邮箱地址： 六、定标原则 技术标合格，合理低价中标（恶意报价除外）。 投标方请谨慎投标、均衡报价，招标方保留按分项授标的权利。 七、付款方式 1）无预付款、进度款； 2）货到验收合格后开具16%的增值税发票； 3）凭入库单及发票到采购人员处办理报账手续； 4）账龄90天，6个月银票（单笔合同金额低于 2 万元（不含 2 万元）的以现款方式结算）。账龄计算：以ERP系统正式报账并形成应付账款时点为起点。 5）超过账龄第8个工作日起至实际支付日，由南钢向收款单位支付对应的资金占用利息。 超账龄付款利率：为同期人民银行一年期贷款基准利率。 利息由ERP系统自动计算并及时计入相应账户；利息费用单独开票、单独核算；利息收取方向利息支付方出具合法的利息发票。 南钢利息付款方式：按客商统计，每年6、12月累计集中付款，客商提供发票报账后付现款，利息付款时如有延期或提前不再额外计息。 6）对支付银票的，如付款日至到期日短于规定期限，则按实际短于天数向供应商收取贴现利息（小于等于7个工作日的免收贴息）。贴现利率为同期人民银行一年期贷款基准利率；贴现天数：规定银票期限–（银票到期日–银票支付日）。 利息计算方式同上。 南钢利息收款方式：按合同逐笔收款，利息收款时如有延期或提前不再额外计息。 八、其他说明 1、非合格供应商在投标截止时间前必须提交投标保证金1000.00元，拒绝提交投标保证金的投标为无效投标。 本平台为每位供应商建立保证金账户，供应商预先交纳一定金额的费用作为保证金，投标时系统冻结相应金额作为投标保证金。定标后，未中标的供应商被冻结的资金自动解冻；中标人在交纳中标服务费、交纳履约保证金、签署合同后，保证金解冻。 如投标截止时间后撤回投标或中标后拒签合同，投标保证金将不予退还，并视情况停止其一定期限的投标权限。 合格供应商投标前无须提交投标保证金（要求所有供应商提交投标保证金的项目除外，具体根据系统提醒进行操作），如投标截止时间后撤回投标或中标后拒签合同，我方有权在所欠其货款中扣除本项目对应的投标保证金（若无货款，则必须补交投标保证金），并视情况停止其一定期限的投标权限。 供应商如需撤回保证金，请致电025-57074937（王女士）。 2、本项目向中标单位（包括合格供应商）收取履约保证金，标准为中标额的5%，最高不超过50万元。已缴纳投标保证金小于履约保证金的，中标人必须补齐后才能签署合同；已缴纳投标保证金大于履约保证金的，多出部分将于合同签署后30日内退还中标人。 有货款的中标单位可以用货款抵作履约保证金。 中标单位收到中标通知后与采购人员联系办理履约保证金缴费事宜。 合同执行完毕后与采购人员联系办理退还履约保证金事宜。执行期间如发生履约异议，按扣罚履约保证金--扣罚违约金--合同法律争议等流程顺序执行，不同时合并处罚，扣罚后中标单位仍需按合同金额开全额发票，扣罚金额开具收据。 3、中标服务费按照国家标准收取。 4、标书费、投标保证金、履约保证金、中标服务费采用电汇方式缴纳，账号如下：（请注明费用用途） 人民币账户： 单位名称：南京鑫智链科技信息有限公司 开 户 行：工商银行江苏省南京市柳州东路支行 账 号：4301020809100067852 欧元账户（EUR）： 受款银行地区 CHINA - 南京 受款银行名称 BANK OF CHINA LTD 受款银行地址 JIANGSU BRANCH 148 ZHONG SHAN NAN LU NANJING 210005 CHINA 受款银行 SWIFT 地址/BIC BKCHCNBJ 940 受款人户口号码/IBAN 498871973291 受款人名称 NanJing Xin Intelligent Chain Technology Infromation CO.,LTD 受款人地址 No1 Xinfu Rd Xie jia dian Yan Jiang Industrial Development District Nanjing City 美元账户（USD）： 受款银行地区 CHINA - 南京 受款银行名称 BANK OF CHINA LTD 受款银行地址 JIANGSU BRANCH 148 ZHONG SHAN NAN LU NANJING 210005 CHINA 受款银行 SWIFT 地址/BIC BKCHCNBJ 940 受款人户口号码/IBAN 501471968686 受款人名称 NanJing Xin Intelligent Chain Technology Infromation CO.,LTD 受款人地址 No1 Xinfu Rd Xie jia dian Yan Jiang Industrial Development District Nanjing City 5、供应商一旦投标，则视为对上述条款的认可。 九、投诉方式 复星集团廉政监督部： 联系人：佘女士 电 话：021-23156625 电子邮箱：lianzhengdc＠fosun.com 南钢纪检监察室： 联系人：马先生 电 话：025-57074396 感谢您的支持与配合！";
            String str3="一、招标项目简介 1、项目名称：高线厂工业垂直提升门修理 2、项目位置：江苏省南京市南京钢铁股份有限公司。 3、送货地点：南钢。 4、交货期：见下表。 二、招标范围 见明细资料 三、投标单位资质要求 1、非合格供应商必须具备下列条件 1）、投标人在法律上和财务上独立并能合法运作，具有法人地位和独立订立合同的能力。 2）、注册资金不少于 万元。 3）、投标人具有良好的银行资信和商业信誉，没有处于被责令停业或破产状态，且资产未被重组、接管和冻结。 4）、投标人营业执照经营范围应包含本次招标标的物范围。 2、投标范围电动门、卷帘门等C级以上合格供应商可直接参与投标，等级达不到要求但符合上述条款要求的合格供应商也可报名参加投标。 四、本标采用资格后审，非合格供应商投标必须提交以下资料（在系统上上传） 1、公司简介、营业执照、组织机构代码、税务登记证、资质证书、管理体系认证等（均要在有效期内）； 2、报名联系业务人员必须为企业正式在职员工，提供有效证明文件（社保证明、名片、身份证复印件加盖公司公章、授权委托书等）； 3、近3年主要相类似业绩及相关证明文件，以备考察； 4、近3年企业财务状况审计报告、获奖和荣誉证书、专利证书、产品技术介绍等资料可根据自身情况提供。 5、各种资质文件均需加盖企业公章； 五、报名 1、报名方式：点击南京鑫智链科技信息有限公司网站（http://mq.nisco.cn）招标公告进行报名和投标。 2、报名截止日期：2019年01月01日 09时00分 3、报名联系人及地址： 联系部门： 南京鑫智链科技信息有限公司 联系人：李云龙 联系电话：15895818700 邮箱地址： 六、定标原则 技术标合格，合理低价中标（恶意报价除外）。 投标方请谨慎投标、均衡报价，招标方保留按分项授标的权利。 七、付款方式 1）无预付款、进度款； 2）货到验收合格后开具16%的增值税发票； 3）凭入库单及发票到采购人员处办理报账手续； 4）账龄90天，6个月银票（单笔合同金额低于 2 万元（不含 2 万元）的以现款方式结算）。账龄计算：以ERP系统正式报账并形成应付账款时点为起点。 5）超过账龄第8个工作日起至实际支付日，由南钢向收款单位支付对应的资金占用利息。 超账龄付款利率：为同期人民银行一年期贷款基准利率。 利息由ERP系统自动计算并及时计入相应账户；利息费用单独开票、单独核算；利息收取方向利息支付方出具合法的利息发票。 南钢利息付款方式：按客商统计，每年6、12月累计集中付款，客商提供发票报账后付现款，利息付款时如有延期或提前不再额外计息。 6）对支付银票的，如付款日至到期日短于规定期限，则按实际短于天数向供应商收取贴现利息（小于等于7个工作日的免收贴息）。贴现利率为同期人民银行一年期贷款基准利率；贴现天数：规定银票期限–（银票到期日–银票支付日）。 利息计算方式同上。 南钢利息收款方式：按合同逐笔收款，利息收款时如有延期或提前不再额外计息。 八、其他说明 1、非合格供应商在投标截止时间前必须提交投标保证金560.00元，拒绝提交投标保证金的投标为无效投标。 本平台为每位供应商建立保证金账户，供应商预先交纳一定金额的费用作为保证金，投标时系统冻结相应金额作为投标保证金。定标后，未中标的供应商被冻结的资金自动解冻；中标人在交纳中标服务费、交纳履约保证金、签署合同后，保证金解冻。 如投标截止时间后撤回投标或中标后拒签合同，投标保证金将不予退还，并视情况停止其一定期限的投标权限。 合格供应商投标前无须提交投标保证金（要求所有供应商提交投标保证金的项目除外，具体根据系统提醒进行操作），如投标截止时间后撤回投标或中标后拒签合同，我方有权在所欠其货款中扣除本项目对应的投标保证金（若无货款，则必须补交投标保证金），并视情况停止其一定期限的投标权限。 供应商如需撤回保证金，请致电025-57074937（王女士）。 2、本项目向中标单位（包括合格供应商）收取履约保证金，标准为中标额的5%，最高不超过50万元。已缴纳投标保证金小于履约保证金的，中标人必须补齐后才能签署合同；已缴纳投标保证金大于履约保证金的，多出部分将于合同签署后30日内退还中标人。 有货款的中标单位可以用货款抵作履约保证金。 中标单位收到中标通知后与采购人员联系办理履约保证金缴费事宜。 合同执行完毕后与采购人员联系办理退还履约保证金事宜。执行期间如发生履约异议，按扣罚履约保证金--扣罚违约金--合同法律争议等流程顺序执行，不同时合并处罚，扣罚后中标单位仍需按合同金额开全额发票，扣罚金额开具收据。 3、中标服务费按照国家标准收取。 4、标书费、投标保证金、履约保证金、中标服务费采用电汇方式缴纳，账号如下：（请注明费用用途） 人民币账户： 单位名称：南京鑫智链科技信息有限公司 开 户 行：工商银行江苏省南京市柳州东路支行 账 号：4301020809100067852 欧元账户（EUR）： 受款银行地区 CHINA - 南京 受款银行名称 BANK OF CHINA LTD 受款银行地址 JIANGSU BRANCH 148 ZHONG SHAN NAN LU NANJING 210005 CHINA 受款银行 SWIFT 地址/BIC BKCHCNBJ 940 受款人户口号码/IBAN 498871973291 受款人名称 NanJing Xin Intelligent Chain Technology Infromation CO.,LTD 受款人地址 No1 Xinfu Rd Xie jia dian Yan Jiang Industrial Development District Nanjing City 美元账户（USD）： 受款银行地区 CHINA - 南京 受款银行名称 BANK OF CHINA LTD 受款银行地址 JIANGSU BRANCH 148 ZHONG SHAN NAN LU NANJING 210005 CHINA 受款银行 SWIFT 地址/BIC BKCHCNBJ 940 受款人户口号码/IBAN 501471968686 受款人名称 NanJing Xin Intelligent Chain Technology Infromation CO.,LTD 受款人地址 No1 Xinfu Rd Xie jia dian Yan Jiang Industrial Development District Nanjing City 5、供应商一旦投标，则视为对上述条款的认可。 九、投诉方式 复星集团廉政监督部： 联系人：佘女士 电 话：021-23156625 电子邮箱：lianzhengdc＠fosun.com 南钢纪检监察室： 联系人：马先生 电 话：025-57074396 感谢您的支持与配合！";
            List<Word> list1 = WordSegmenter.segWithStopWords(str1);
            SimHash sim1 = new SimHash(list1.toString(), 64);
            /*read.close();
            System.out.println("    --------------------");
            InputStreamReader read2 = new InputStreamReader(new FileInputStream(file2), "utf-8");
            BufferedReader reader2 = new BufferedReader(read2);
            String str2 = reader2.readLine();
            read2.close();*/
            List<Word> list2 = WordSegmenter.segWithStopWords(str2);
            SimHash sim2 = new SimHash(list2.toString(), 64);

            List<Word> list3 = WordSegmenter.segWithStopWords(str3);
            SimHash sim3 = new SimHash(list3.toString(), 64);
/*
            System.out.println("list1       "+list1.toString());
            System.out.println("list2       "+list2.toString());
            System.out.println("list3       "+list3.toString());
            System.out.println("sim1       "+sim1.intSimHash);
            System.out.println("sim2       "+sim2.intSimHash);
            System.out.println("sim3       "+sim3.intSimHash);
            System.out.println("     --------------------     ");
            System.out.println("比较二进制 1,2 "+sim1.getDistance(sim1.strSimHash, sim2.strSimHash));
            System.out.println("比较二进制 1,3 "+sim1.getDistance(sim1.strSimHash, sim3.strSimHash));
            System.out.println("比较二进制 2,3 "+sim2.getDistance(sim2.strSimHash,sim3.strSimHash));
            System.out.println("比较海明 1,2"+sim1.hammingDistance(sim2));
            System.out.println("比较海明 1,3"+sim1.hammingDistance(sim3));
            System.out.println("比较海明 2,3"+sim2.hammingDistance(sim3));*/
//
//            String a="1000010101111110011011101000011111011110010000000000000000000100";   //1
//            String b="1011010101111110011101111010011010011010010000000000000000000100";   // 1
//            String c="0001010100001100011001100010011001010000110000000000000000000100";   // 2
            System.out.println(list1.toString());
            System.out.println(list2.toString());
            System.out.println(list3.toString());
            System.out.println("-----------------------------------------");
            System.out.println("sim1    "+sim1.strSimHash);
            System.out.println("sim2    "+sim2.strSimHash);
            System.out.println("sim3    "+sim3.strSimHash);

//            LinkedHashMap<String, Object> map = new LinkedHashMap<String, Object>();
//            map.put("url", "http://www.cgdcbidding.com:80/ggjg/26796.jhtml");
//            map.put("hash", sim3.strSimHash);
//            new MongoSave().setHash(map);
//            System.out.println(sim1.getDistance(a, b));
//            System.out.println(sim1.getDistance(a, c));
        }catch (Exception e){
            System.out.println(e);
        }finally {

        }


    }
}

