package us.codecraft.webmagic.processor;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Task;

import javax.xml.transform.Result;

public interface ChangedPageProcessor {
    ResultItems process(Page page, ResultItems resultItems);
    default Site getSite() {
        return Site.me();
    }
}
