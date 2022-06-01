package us.codecraft.webmagic.processor.example;

import us.codecraft.webmagic.*;
import us.codecraft.webmagic.processor.ChangedPageProcessor;
import us.codecraft.webmagic.processor.PageProcessor;

import java.util.ArrayList;
import java.util.List;

public class ChangedBaikePageProcessor implements ChangedPageProcessor {
    private Site site = Site.me()//.setHttpProxy(new HttpHost("127.0.0.1",8888))
            .setRetryTimes(3).setSleepTime(1000).setUseGzip(true);

    @Override
    public ResultItems process(Page page, ResultItems resultItems) {
        resultItems.put("name", page.getHtml().css("dl.lemmaWgt-lemmaTitle h1","text").toString());
        resultItems.put("description", page.getHtml().xpath("//div[@class='lemma-summary']/allText()"));
//        page.putField("name", page.getHtml().css("dl.lemmaWgt-lemmaTitle h1","text").toString());
//        page.putField("description", page.getHtml().xpath("//div[@class='lemma-summary']/allText()"));
        return resultItems;
    }

    @Override
    public Site getSite() {
        return site;
    }

    public static void main(String[] args) {
        //single download
        Spider2 spider = Spider2.create(new ChangedBaikePageProcessor()).thread(2);
        String urlTemplate = "http://baike.baidu.com/search/word?word=%s&pic=1&sug=1&enc=utf8";
        ResultItems resultItems = spider.<ResultItems>get(String.format(urlTemplate, "水力发电"));
        System.out.println(resultItems);

        //multidownload
//        List<String> list = new ArrayList<String>();
//        list.add(String.format(urlTemplate,"春晓"));
//        list.add(String.format(urlTemplate,"太阳能"));
//        list.add(String.format(urlTemplate,"地热发电"));
//        List<ResultItems> resultItemses = spider.<ResultItems>getAll(list);
//        for (ResultItems resultItemse : resultItemses) {
//            System.out.println(resultItemse.getAll());
//        }
        spider.close();
    }
}
