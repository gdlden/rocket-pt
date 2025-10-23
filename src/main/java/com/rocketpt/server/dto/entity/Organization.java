package com.rocketpt.server.dto.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * @author cjbi
 */
@Data
@Schema(description = "组织架构")
@TableName("organization")
public class Organization extends EntityBase {

  @TableId(type = IdType.AUTO)
  private Integer id;
  @Schema(description = "名称")
  private String name;

  @Schema(description = "类型")
  private Type type;


  @TableField(exist = false)
  private Organization parent;

  private String parentId;

  private String parentIds;


  @TableField(exist = false)
  private Set<Organization> children = new LinkedHashSet<>();

  public String makeSelfAsParentIds() {
    return getParentIds() + getId() + "/";
  }

  public String getFullName() {
    return concatOrgName(this);
  }

  private String concatOrgName(Organization org) {
    if (org.getParent() != null) {
      return concatOrgName(org.getParent()).concat("-").concat(org.getName());
    }
    return org.getName();
  }


  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Type getType() {
    return type;
  }

  public void setType(Type type) {
    this.type = type;
  }

  public Organization getParent() {
    return parent;
  }

  public void setParent(Organization parent) {
    this.parent = parent;
  }

  public String getParentIds() {
    return parentIds;
  }

  public void setParentIds(String parentIds) {
    this.parentIds = parentIds;
  }

  public Set<Organization> getChildren() {
    return children;
  }

  public void setChildren(Set<Organization> children) {
    this.children = children;
  }

  @RequiredArgsConstructor
  public enum Type {
    /**
     * 部门
     */
    DEPARTMENT(0),
    /**
     * 岗位
     */
    JOB(1);
    @Getter
    @EnumValue
    private final int code;
  }
}
