package com.supergo.common.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Table(name = "tb_item_cat")
@ApiModel(value = "address对象", description = "地址实体类")
public class TbItemCat implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @ApiModelProperty(value = "id", name = "Id", required = true, dataType = "Long")
    private Long id;

    @Column(name = "parent_id")
    @ApiModelProperty(value = "parentId", name = "parentId",  dataType = "Long")
    private Long parentId;

    @Column(name = "name")
    @ApiModelProperty(value = "name", name = "name",  dataType = "String")
    private String name;

    @Column(name = "type_id")
    @ApiModelProperty(value = "typeId", name = "typeId", dataType = "Long")
    private Long typeId;

    @Transient
    private List<TbItemCat> chrildren;

    public List<TbItemCat> getChrildren() {
        return chrildren;
    }

    public void setChrildren(List<TbItemCat> chrildren) {
        this.chrildren = chrildren;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Long getTypeId() {
        return typeId;
    }

    public void setTypeId(Long typeId) {
        this.typeId = typeId;
    }
}