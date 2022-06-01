package us.codecraft.webmagic.processor.example;

import us.codecraft.webmagic.*;
import us.codecraft.webmagic.processor.PageProcessor;

import java.util.ArrayList;
import java.util.List;

/**
 * @author code4crafter@gmail.com <br>
 * @since 0.4.0
 */
public class Baidu3 implements PageProcessor {

    private Site site = Site.me()//.setHttpProxy(new HttpHost("127.0.0.1",8888))
            .setRetryTimes(3).setSleepTime(1000).setUseGzip(true);

    @Override
    public void process(Page page) {
        page.putField("name", page.getHtml().css("dl.lemmaWgt-lemmaTitle h1","text").toString());
        page.putField("description", page.getHtml().xpath("//div[@class='lemma-summary']/allText()"));
    }

    @Override
    public Site getSite() {
        return site;
    }

    public static void main(String[] args) {
        //generate a user
        System.out.println("create user 'me' with username me and password 123");
        Credential me = new Credential("me","123");
        //generate a new spider by passing in page processor named Baidu3
        System.out.println("generate a new spider and pass user 'me' as a user who will be verified by spider for rights to manipulate the user");
        Spider3 spider = Spider3.create(new Baidu3(),me).thread(2);
        // add the user wth username me and password 123 as a valid user to created spider
        System.out.println("add the user with username me and password 123 as a valid user to created spider");
        spider.addCredential(me);
        // the template of accessing different entries in BaiduBaike website
        String urlTemplate = "http://baike.baidu.com/search/word?word=%s&pic=1&sug=1&enc=utf8";
        //download content of one webpage of a specif entry "登黄鹤楼" in BaiduBaike webpage
        System.out.println("download content of one webpage of a specif entry 登黄鹤楼 in BaiduBaike website: ");
        ResultItems resultItems = spider.<ResultItems>get(String.format(urlTemplate, "登黄鹤楼"));
        System.out.println(resultItems);
        System.out.println();
        System.out.println();
        /*multi-download download the description of multiple entries
        *"水调歌头", "春晓", "长恨歌", "渔舟唱晚"
        in BaiduBaidke website */
//        System.out.println("multi-download, download the description of multiple entries, 水调歌头, 春晓, 长恨歌, 渔舟唱晚 in BaiduBaidke website: ");
//        List<String> list = new ArrayList<String>();
//        list.add(String.format(urlTemplate,"水调歌头"));
//        list.add(String.format(urlTemplate,"春晓"));
//        list.add(String.format(urlTemplate,"长恨歌"));
//        list.add(String.format(urlTemplate,"渔舟唱晚"));
//        List<ResultItems> resultItemses = spider.<ResultItems>getAll(list);
//        for (ResultItems resultItemse : resultItemses) {
//            System.out.println(resultItemse.getAll());
//        }
        System.out.println("delete the user, whose user name is 'me' and has been added into spider before from spider");
        spider.deleteCredential(me);
        System.out.println("check if deleted user named 'me' will lose the rights to use spider or not:");
        ResultItems resultItems3 = spider.<ResultItems>get(String.format(urlTemplate, "登黄鹤楼"));
        System.out.println(resultItems3);
        //spider.close();
//        System.out.println();
//        System.out.println();
        System.out.println("create user 'he' with username he and password 456");
        System.out.println("create user 'us' with username us and password 789");
        Credential he = new Credential("he","456");
        Credential us = new Credential("us", "789");
        System.out.println("create another new spider for crawling BaiduBaike webpages");
        //Spider3 spiderT = Spider3.create(new Baidu3(),he).thread(2);
        System.out.println("create a user list as valid user list to be added to a new spider and saved user 'me', 'he', and 'us' in the user list ");
        List<Credential> userList = new ArrayList<Credential>();
        userList.add(me);
        userList.add(he);
        userList.add(us);
        System.out.println("add the created valid user list to spider");
        spider.addCredentialList(userList);
        System.out.println("check if added user named 'me' will gain the rights to use spider or not:");
        ResultItems resultItems2 = spider.<ResultItems>get(String.format(urlTemplate, "渔舟唱晚"));
        System.out.println(resultItems2);
        System.out.println("create a user list as valid user list to be removed to the new spider and saved user 'me', and 'us' in the user list ");
        List<Credential> removeUserList = new ArrayList<Credential>();
        removeUserList.add(me);
        removeUserList.add(us);
        System.out.println("remove a list of valid users from spider");
        spider.removeCredentialList(removeUserList);
        System.out.println("check if removed user named 'me' will lose the rights to use spider or not:");
        ResultItems resultItems4 = spider.<ResultItems>get(String.format(urlTemplate, "登黄鹤楼"));
        System.out.println(resultItems4);
        spider.close();

    }
}
