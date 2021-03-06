package com.supergo.common.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Table(name = "tb_content")
@ApiModel(value = "content对象", description = "内容实体类")
public class Content implements Serializable {
    @Id
    @Column(name = "id")
    @ApiModelProperty(value = "id", name = "Id", required = true, dataType = "Long")
    private Long id;

    /**
     * 内容类目ID
     */
    @Column(name = "category_id")
    @ApiModelProperty(value = "内容类目ID", name = "categoryId", required = true, dataType = "Long")
    private Long categoryId;

    /**
     * 内容标题
     */
    @Column(name = "title")
    @ApiModelProperty(value = "内容标题", name = "title", dataType = "String")
    private String title;

    /**
     * 链接
     */
    @Column(name = "url")
    @ApiModelProperty(value = "链接", name = "url", dataType = "String")
    private String url;

    /**
     * 图片绝对路径
     */
    @Column(name = "pic")
    @ApiModelProperty(value = "图片绝对路径", name = "pic", dataType = "String")
    private String pic;

    /**
     * 状态
     */
    @Column(name = "status")
    @ApiModelProperty(value = "状态", name = "status", dataType = "String")
    private String status;

    /**
     * 排序
     */
    @Column(name = "sort_order")
    @ApiModelProperty(value = "排序", name = "sortOrder", dataType = "Integer")
    private Integer sortOrder;

    private static final long serialVersionUID = 1L;

    /**
     * @return id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 获取内容类目ID
     *
     * @return category_id - 内容类目ID
     */
    public Long getCategoryId() {
        return categoryId;
    }

    /**
     * 设置内容类目ID
     *
     * @param categoryId 内容类目ID
     */
    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    /**
     * 获取内容标题
     *
     * @return title - 内容标题
     */
    public String getTitle() {
        return title;
    }

    /**
     * 设置内容标题
     *
     * @param title 内容标题
     */
    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    /**
     * 获取链接
     *
     * @return url - 链接
     */
    public String getUrl() {
        return url;
    }

    /**
     * 设置链接
     *
     * @param url 链接
     */
    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }

    /**
     * 获取图片绝对路径
     *
     * @return pic - 图片绝对路径
     */
    public String getPic() {
        return pic;
    }

    /**
     * 设置图片绝对路径
     *
     * @param pic 图片绝对路径
     */
    public void setPic(String pic) {
        this.pic = pic == null ? null : pic.trim();
    }

    /**
     * 获取状态
     *
     * @return status - 状态
     */
    public String getStatus() {
        return status;
    }

    /**
     * 设置状态
     *
     * @param status 状态
     */
    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    /**
     * 获取排序
     *
     * @return sort_order - 排序
     */
    public Integer getSortOrder() {
        return sortOrder;
    }

    /**
     * 设置排序
     *
     * @param sortOrder 排序
     */
    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }
}