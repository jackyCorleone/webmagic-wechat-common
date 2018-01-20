package com.qiya.middletier.dao;

import com.qiya.middletier.model.Article;
import com.qiya.middletier.model.Funny;
import java.util.Date;
import java.util.List;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by jacky on 2018/1/19.
 */
@Repository
@RepositoryRestResource(exported = false)
@Transactional
public interface FunnyDao extends CrudRepository<Funny, Long>, JpaSpecificationExecutor<Funny> {

    @Query("select count(1) FROM Funny WHERE label=:label ")
    public Integer isExist(@Param("label") String label);
    @Query("select count(1) FROM Funny WHERE sourcesId=:sourcesId and title= :title")
    public Integer isExistTiteSiteTime( @Param("sourcesId") Long sourcesId, @Param("title") String title);

    @Query(" FROM Funny WHERE sourcesId=:sourcesId and title= :title and author= :author")
    public Article getArticlebyTiteSiteAuthor( @Param("sourcesId") Long sourcesId, @Param("title") String title,
                                               @Param("author") String author);
}
