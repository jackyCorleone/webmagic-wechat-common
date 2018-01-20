package com.qiya.middletier.webmagic.process;

import com.qiya.middletier.webmagic.comm.configmodel.RuleMatchConfig;
import com.qiya.middletier.webmagic.comm.configmodel.WebmagicConfig;
import java.util.List;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.selector.Selectable;

/**
 * Created by jacky on 2018/1/19.
 */
@Service
@Scope("prototype")
public class XPathWebPageListProcess extends AbstractCommPageProcess{

    private RuleMatchConfig ruleMatchConfig;

    @Override
    public void init(WebmagicConfig config) {
        super.setConfig(config);
    }

    @Override
    public void process(Page page) {
        this.ruleMatchConfig = super.getConfig().getRule();

        // 列表页
        if (!page.getUrl().regex(ruleMatchConfig.getDetailregex()).match()) {
            page.addTargetRequests(page.getHtml().xpath(ruleMatchConfig.getListxpath()).links().all());
            this.setPutFile2(page,ruleMatchConfig);
        } else {
            page.getResultItems().setSkip(true);
        }
        setIsCircle(page);
    }
}
