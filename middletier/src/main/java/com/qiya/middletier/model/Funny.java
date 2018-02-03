package com.qiya.middletier.model;

import com.google.gson.JsonObject;
import com.qiya.framework.baselib.util.base.DateUtils;
import com.qiya.framework.middletier.base.IModel;
import java.util.Date;
import javax.persistence.*;

/**
 * Created by jacky on 2018/1/19.
 */
@Entity
@Table(name = "baozoupictx")
public class Funny implements IModel{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "type_id",columnDefinition = "int COMMENT '类别'")
    private Integer typeId;
    @Column(name = "title",columnDefinition = "NVARCHAR(4096) COMMENT '标题'")
    private String title;
    @Column(name = "content",columnDefinition = "NVARCHAR(200) COMMENT '内容'")
    private String content;
    @Column(name = "sort",columnDefinition = "int COMMENT '排序'")
    private Integer sort;
    @Column(name = "author",columnDefinition = "NVARCHAR(200) COMMENT '作者'")
    private String author;
    @Column(name = "author_avatar",columnDefinition = "NVARCHAR(200) COMMENT '作者头像'")
    private String authorAvatar;
    @Column(name = "content_type",columnDefinition = "int COMMENT '内容类型'")
    private Integer contentType;
    @Column(name = "sources_id",columnDefinition = "int COMMENT '来源 站点 id'")
    private Integer sourcesId;
    @Column(name = "label",columnDefinition = "NVARCHAR(2000) COMMENT '原文链接'")
    private String label;
    @Column(name = "thumb_up",columnDefinition = "bigint COMMENT '点赞'")
    private Integer thumbUp;
    @Column(name = "placed_top",columnDefinition = "int COMMENT '置顶'")
    private Boolean placedTop;
    @Column(name = "history",columnDefinition = "int COMMENT '是否置顶过'")
    private Boolean history;
    @Column(name = "recommend",columnDefinition = "int COMMENT '推荐'")
    private Boolean recommend;
    @Column(name = "status",columnDefinition = "int COMMENT '状态'")
    private Integer status;
    @Column(name = "create_date",columnDefinition = "datetime COMMENT '创建时间'")
    private Date createDate;
    @Column(name = "modify_date",columnDefinition = "datetime COMMENT '修改时间'")
    private Date modifyDate;

    @Override
    public void update(JsonObject jo) {
        if (jo.has("typeId"))
            this.setTypeId(jo.get("typeId").getAsInt());
        if (jo.has("title"))
            this.setTitle(jo.get("title").toString());
        if (jo.has("content"))
            this.setContent(jo.get("content").toString());
        if (jo.has("sort"))
            this.setSort(jo.get("sort").getAsInt());

        if (jo.has("author"))
            this.setAuthor(jo.get("author").toString());
        if (jo.has("authorAvatar"))
            this.setAuthorAvatar(jo.get("authorAvatar").toString());
        if (jo.has("contentType"))
            this.setContentType(jo.get("contentType").getAsInt());
        if (jo.has("sourcesId"))
            this.setSourcesId(jo.get("sourcesId").getAsInt());
        if (jo.has("label"))
            this.setLabel(jo.get("label").toString());
        if (jo.has("thumbUp"))
            this.setThumbUp(jo.get("thumbUp").getAsInt());


        if (jo.has("placedTop"))
            this.setPlacedTop(jo.get("placedTop").getAsBoolean());
        if (jo.has("history"))
            this.setHistory(jo.get("history").getAsBoolean());

        if (jo.has("createDate"))
            this.setCreateDate(DateUtils.toDateTime(jo.get("createDate").toString()));
        if (jo.has("modifyDate"))
            this.setModifyDate(DateUtils.toDateTime(jo.get("modifyDate").toString()));
        if (jo.has("status"))
            this.setStatus(jo.get("status").getAsInt());
        if (jo.has("recommend"))
            this.setRecommend(jo.get("recommend").getAsBoolean());

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getAuthorAvatar() {
        return authorAvatar;
    }

    public void setAuthorAvatar(String authorAvatar) {
        this.authorAvatar = authorAvatar;
    }

    public Integer getContentType() {
        return contentType;
    }

    public void setContentType(Integer contentType) {
        this.contentType = contentType;
    }

    public Integer getSourcesId() {
        return sourcesId;
    }

    public void setSourcesId(Integer sourcesId) {
        this.sourcesId = sourcesId;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Integer getThumbUp() {
        return thumbUp;
    }

    public void setThumbUp(Integer thumbUp) {
        this.thumbUp = thumbUp;
    }

    public Boolean getPlacedTop() {
        return placedTop;
    }

    public void setPlacedTop(Boolean placedTop) {
        this.placedTop = placedTop;
    }

    public Boolean getHistory() {
        return history;
    }

    public void setHistory(Boolean history) {
        this.history = history;
    }

    public Boolean getRecommend() {
        return recommend;
    }

    public void setRecommend(Boolean recommend) {
        this.recommend = recommend;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getModifyDate() {
        return modifyDate;
    }

    public void setModifyDate(Date modifyDate) {
        this.modifyDate = modifyDate;
    }
}
