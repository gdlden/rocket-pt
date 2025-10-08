package com.rocketpt.server.dto.sys;


import com.rocketpt.server.dto.entity.Organization;

import java.util.List;

/**
 * @author cjbi
 */
public class OrgTreeDTO {
  private final Integer id;
  private final String name;
  private final Organization.Type type;
  private final List<OrgTreeDTO> children;

  public OrgTreeDTO(Organization organization) {
    this.id = organization.getId();
    this.name = organization.getName();
    this.type = organization.getType();
    this.children = organization.getChildren().stream().map(OrgTreeDTO::new).toList();
  }

  public Integer getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public Organization.Type getType() {
    return type;
  }

  public List<OrgTreeDTO> getChildren() {
    return children;
  }

  public boolean getIsLeaf() {
    return getChildren().isEmpty();
  }

}
