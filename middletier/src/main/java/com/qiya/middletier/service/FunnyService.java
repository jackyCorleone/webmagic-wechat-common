package com.qiya.middletier.service;

import com.qiya.framework.def.StatusEnum;
import com.qiya.framework.middletier.base.IService;
import com.qiya.framework.model.SearchCondition;
import com.qiya.middletier.dao.FunnyDao;
import com.qiya.middletier.model.Article;
import com.qiya.middletier.model.Funny;
import java.util.Date;
import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

/**
 * Created by jacky on 2018/1/19.
 */
@Service
public class FunnyService implements IService<Funny, Funny> {
    @Resource
    private FunnyDao funnyDao;

    public Boolean isExists(String label) {
        if (funnyDao.isExist(label) > 0) {
            return true;
        } else {
            return false;
        }
    }
    public Boolean isExistTiteSiteTime(Long sourcesId, String title) {
        if (funnyDao.isExistTiteSiteTime(sourcesId, title) > 0) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    public Page<Funny> search(SearchCondition sc) {
        Pageable pageable = new PageRequest(sc.getIndex(), sc.getSize(), Sort.Direction.DESC, "createDate");
        Specification<Funny> spec = new Specification<Funny>() {
            @Override
            public Predicate toPredicate(Root<Funny> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
                Predicate bp = builder.equal(root.get("status"), StatusEnum.VALID.getValue());
                return sc.getPredicate(root, builder, bp);
            }
        };
        return   funnyDao.findAll(spec, pageable);
    }

    @Override
    public Funny create(Funny funny) {
        return funnyDao.save(funny);
    }

    @Override
    public Funny read(String s) {
        return funnyDao.findOne(Long.valueOf(s));
    }

    @Override
    public Funny update(Funny funny) {
        return funnyDao.save(funny);
    }

    @Override
    public Funny delete(Funny funny) {
        return funnyDao.save(funny);
    }
}
