package com.jamin.http.model.detail;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by alvin on 17/1/17.
 */

public class CloudBeanHistoryDetail {


    /**
     * reason : success
     * result : [{"e_id":"1","title":"罗马共和国开始使用儒略历","content":"    在2061年前的今天，前45年1月1日 (农历冬月十九)，罗马共和国开始使用儒略历。\r\n    儒略历，是格里历的前身，由罗马共和国独裁官儒略·恺撒采纳埃及亚历山大的希腊数学家兼天文学家索西琴尼(en:Sosigenes of Alexandria)计算的历法，在公元前45年1月1日（距今已2061多年）起执行，取代旧罗马历历法的一种历法。一年设12个月，大小月交替，四年一闰，平年365日，闰年于二月底增加一闰日，年平均长度为365.25日。由于累积误差随着时间越来越大，1582年后被教皇格里高利十三世改善，变为格里历，即沿用至今的西历。现今儒略历只有阿索斯神权共和国和一些北非的柏柏尔人使用。\r\n\u201c月\u201d名由来     一月 Januarius 名字来自古罗马神话的神雅努斯。\r\n    二月 Februarius 名字来自古罗马的节日Februa。\r\n    三月 Martius 名字来自古罗马神话的战神玛尔斯。\r\n    四月 Aprilis 名字来自古罗马的词aperire，意思为\u201c开始\u201d，意味着春天开始。（历史上的今天.com）\r\n    五月 Maius 名字来自古罗马神话的土地女神迈亚，或来自拉丁语词 maiores(意为\u201c较年长者\u201d)。（历史今天LSJT）\r\n    六月 Junius 名字来自古罗马神话的女神朱诺，或来自拉丁语词 iuniores(意为\u201c较年轻者\u201d)。\r\n    七月 原名Quintilis，后改Julius。古罗马历只有10个月，这是第五月，原名是\u201c第五\u201d的意思，因为凯撒是这月出生的，经元老院一致通过，将此月改为凯撒的名字\u201c儒略\u201d。\r\n    八月 原名Sextilis，后改Augustus。原名是\u201c第六\u201d的意思，因为后来独裁者屋大维是死于此月，元老院将此月改为他的称号\u201c奥古斯都\u201d。\r\n    九月 September 拉丁语\u201c第七\u201d的意思。\r\n    十月 October 拉丁语\u201c第八\u201d的意思。\r\n    十一月Novembris 拉丁语\u201c第九\u201d的意思。\r\n    十二月 December 拉丁语\u201c第十\u201d的意思。\r\n罗马失闰     因当时僧侣错误理解\u201c隔三年设置一闰年\u201d，以致每三年设置了一个闰年。奥古斯都为了纠正了以上闰年过多的错误，故取消12年之间三次的闰年，拟补累积误差的天数。此后按儒略历原来的设计，每四年有一次闰年。（历史今天）\r\n    然而，此间究竟何年是平年或者闰年，不同学者之间仍然有异说，尚无定论。\r\n延伸阅读：蔡勒公式\r\n延伸阅读：蔡勒公式     相信学过高数的Geek都知道有个泰勒公式，可是这个世界上还存在一个公式叫蔡勒公式，它是用来计算1582年10月15日之后的任意一天是星期几的。\r\n    为什么有个日期限定呢?因为马教皇格里高利十三世在1582年组织了一批天文学家，根据哥白尼日心说计算出来的数据，对儒略历作了修改。宣布将1582年10月5日到14日之间的10天撤销，即10月4日之后为10月15日。后来人们将这一新的历法称为\u201c格里高利历\u201d，也就是今天世界上所通用的历法，简称格里历或公历。\r\n    儒略历(Julian calendar)是由罗马共和国独裁官儒略·恺撒采纳埃及亚历山大的希腊数学家兼天文学家索西琴尼计算的历法。在公元前46年1月1日（距今已2062多年）起执行，取代旧罗马历法的一种历法。一年设12个月，大小月交替，四年一闰，平年365日，闰年于二月底增加一闰日，年平均长度为365.25日。由于累积误差随着时间越来越大，1582年后被教皇格里高利十三世改善，变为格里历，即沿用至今的公历。\r\n    下面就是蔡勒公式的真身。\r\n    w：星期\r\n    c：世纪数(年份前两位数)\r\n    y：年(后两位数)\r\n    m：月(m的取值范围为3至14，即在蔡勒公式中，某年的1、2月要看作上一年的13、14月来计算，比如2012年1月1日要看作2011年的13月1日来计算)\r\n    d：日\r\n    [　]：称作高斯符号，代表取整，即只要整数部份。\r\n    mod：\u200e\u200e同余\u200e(这里代表括号里的答案除以7后的余数)\r\n    如果某个想挖坟的Geek想算1582年10月15日之前的日期，那么就要用到\u201c变种\u201d蔡勒公式。\r\n\r\n","picNo":"5","picUrl":[{"pic_title":"儒略历","id":1,"url":"http://images.juheapi.com/history/1_1.jpg"},{"pic_title":"","id":2,"url":"http://images.juheapi.com/history/1_2.jpg"},{"pic_title":"","id":3,"url":"http://images.juheapi.com/history/1_3.jpg"},{"pic_title":"公式中的符号含义如下：","id":4,"url":"http://images.juheapi.com/history/1_4.jpg"},{"pic_title":"","id":5,"url":"http://images.juheapi.com/history/1_5.jpg"}]}]
     * error_code : 0
     */

    private String reason;
    @SerializedName("error_code")
    private int errorCode;
    private List<DetailBean> result;

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public List<DetailBean> getResult() {
        return result;
    }

    public void setResult(List<DetailBean> result) {
        this.result = result;
    }


}
