package com.qiya.middletier.webmagic.pipe;

import com.qiya.framework.def.StatusEnum;
import com.qiya.middletier.bizenum.SiteType;
import com.qiya.middletier.bizenum.TaskPropressEnum;
import com.qiya.middletier.bizenum.TaskRunStateEnum;
import com.qiya.middletier.model.Article;
import com.qiya.middletier.model.ArticleDetail;
import com.qiya.middletier.model.Funny;
import com.qiya.middletier.model.Site;
import com.qiya.middletier.service.ArticleService;
import com.qiya.middletier.service.FunnyService;
import com.qiya.middletier.service.TaskService;
import com.qiya.middletier.webmagic.monitor.MySpiderMonitor;
import com.qiya.middletier.webmagic.monitor.MySpiderStatus;
import java.util.Date;
import java.util.Map;
import javax.annotation.Resource;
import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

/**
 * Created by jacky on 2018/1/19.
 */
@Service
@Scope("prototype")
public class MySqlFunnyPipeline implements Pipeline {

    private static Logger log = LoggerFactory.getLogger(MySqlFunnyPipeline.class);

    @Resource
    private FunnyService funnyService;
    @Resource
    private TaskService taskService;

    @Override
    public void process(ResultItems resultItems, Task task) {

        if (resultItems.isSkip() == false) {
            try {

                Map<String, Object> data2 = resultItems.getAll();

                for (Map.Entry<String, Object> entry : resultItems.getAll().entrySet()) {
                    if (entry.getKey().contains("funny")) {
                        Map<String, Object> data = (Map<String, Object>) entry.getValue();

                        if (data.size() == 0) {
                            resultItems.setSkip(true);
                            return;
                        }

                        // 判断时间是否在开始时间和结束时间内
                        if (data.get("taskStatus") != null) {
                            int taskStatus = Integer.parseInt(data.get("taskStatus").toString());

                            if (taskStatus == TaskPropressEnum.TASKSTOP.getValue()) {
                                // 结束爬取任务
                                log.info("爬取历史文章日期已超出开始时间范围,爬取任务结束.");

                                MySpiderMonitor spiderMonitor = MySpiderMonitor.instance();
                                Map<String, MySpiderStatus> spiderStatuses = spiderMonitor.getSpiderStatuses();
                                MySpiderStatus spiderStatus = spiderStatuses.get(task.getUUID());

                                spiderStatus.stop();
                                spiderStatus.getSpider().close();

                                // 修改任务状态
                                com.qiya.middletier.model.Task task1 = taskService.getByUUID(task.getUUID());
                                task1.setEndTime(new Date());
                                task1.setRunState(TaskRunStateEnum.TASK_STOP.getCode());
                                taskService.update(task1);
                                return;

                            } else if (taskStatus == TaskPropressEnum.CONTINUE.getValue()) {
                                log.info("爬取历史文章日期未达到指定时间范围,不保存数据库,爬取任务继续.");
                                // 跳过 继续爬
                                resultItems.setSkip(true);
                                return;
                            }
                            log.info("爬取历史文章日期是指定时间范围,保存数据库,爬取任务继续.");
                        }

				/*
				 * 添加文章信息
				 */
                        Funny funny = new Funny();
                        BeanUtils.populate(funny, data);
                        Long siteId = (Long) data2.get("siteId");
                        funny.setSourcesId(siteId.intValue());
                        funny.setSort(1);
                        funny.setHistory(false);
                        funny.setRecommend(false);
                        funny.setPlacedTop(false);
                        funny.setCreateDate(new Date());
                        funny.setStatus(StatusEnum.VALID.getValue());
                        if(StringUtils.isEmpty(funny.getContent())){
                            funny.setContentType(1);
                        }else {
                            funny.setContentType(2);
                        }
                        if (StringUtils.isEmpty(funny.getTitle())) {
                            resultItems.setSkip(true);
                            return;
                            //article.setTitle("");
                        }


                        if (StringUtils.isEmpty(funny.getAuthor()))
                            funny.setAuthor("");

                        if (!funnyService.isExists(funny.getLabel())) {

                            funnyService.create(funny);
                            log.info("爬取任务文章保存数据库:" + funny.getLabel() + "!");


                        } else {
                            resultItems.setSkip(true);
                            return;
                        }
                    }
                }

            } catch (Exception ex) {
                log.error("保存到mysql出错:" + ex.getLocalizedMessage());
                ex.printStackTrace();
            }
        }
    }
}
