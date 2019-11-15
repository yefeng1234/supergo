package com.supergo.common.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Table(name = "tb_freight_template")
@ApiModel(value = "freightTemplate对象", description = "模板实体类")
public class FreightTemplate implements Serializable {
    @Id
    @Column(name = "id")
    @ApiModelProperty(value = "id", name = "Id", required = true, dataType = "Long")
    private Long id;

    /**
     * 商家ID
     */
    @Column(name = "seller_id")
    @ApiModelProperty(value = "商家ID", name = "sellerId", dataType = "String")
    private String sellerId;

    /**
     * 是否默认   （‘Y’是   'N'否）
     */
    @Column(name = "is_default")
    @ApiModelProperty(value = "是否默认", name = "isDefault", dataType = "String")
    private String isDefault;

    /**
     * 模版名称
     */
    @Column(name = "name")
    @ApiModelProperty(value = "模版名称", name = "area", dataType = "String")
    private String name;

    /**
     * 发货时间（1:12h  2:24h  3:48h  4:72h  5:7d 6:15d ）
     */
    @Column(name = "send_time_type")
    @ApiModelProperty(value = "发货时间", name = "sendTimeType", dataType = "String")
    private String sendTimeType;

    /**
     * 统一价格
     */
    @Column(name = "price")
    @ApiModelProperty(value = "统一价格", name = "price", dataType = "Long")
    private Long price;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    @ApiModelProperty(value = "创建时间", name = "createTime", dataType = "Date")
    private Date createTime;

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
     * 获取商家ID
     *
     * @return seller_id - 商家ID
     */
    public String getSellerId() {
        return sellerId;
    }

    /**
     * 设置商家ID
     *
     * @param sellerId 商家ID
     */
    public void setSellerId(String sellerId) {
        this.sellerId = sellerId == null ? null : sellerId.trim();
    }

    /**
     * 获取是否默认   （‘Y’是   'N'否）
     *
     * @return is_default - 是否默认   （‘Y’是   'N'否）
     */
    public String getIsDefault() {
        return isDefault;
    }

    /**
     * 设置是否默认   （‘Y’是   'N'否）
     *
     * @param isDefault 是否默认   （‘Y’是   'N'否）
     */
    public void setIsDefault(String isDefault) {
        this.isDefault = isDefault == null ? null : isDefault.trim();
    }

    /**
     * 获取模版名称
     *
     * @return name - 模版名称
     */
    public String getName() {
        return name;
    }

    /**
     * 设置模版名称
     *
     * @param name 模版名称
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * 获取发货时间（1:12h  2:24h  3:48h  4:72h  5:7d 6:15d ）
     *
     * @return send_time_type - 发货时间（1:12h  2:24h  3:48h  4:72h  5:7d 6:15d ）
     */
    public String getSendTimeType() {
        return sendTimeType;
    }

    /**
     * 设置发货时间（1:12h  2:24h  3:48h  4:72h  5:7d 6:15d ）
     *
     * @param sendTimeType 发货时间（1:12h  2:24h  3:48h  4:72h  5:7d 6:15d ）
     */
    public void setSendTimeType(String sendTimeType) {
        this.sendTimeType = sendTimeType == null ? null : sendTimeType.trim();
    }

    /**
     * 获取统一价格
     *
     * @return price - 统一价格
     */
    public Long getPrice() {
        return price;
    }

    /**
     * 设置统一价格
     *
     * @param price 统一价格
     */
    public void setPrice(Long price) {
        this.price = price;
    }

    /**
     * 获取创建时间
     *
     * @return create_time - 创建时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 设置创建时间
     *
     * @param createTime 创建时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}