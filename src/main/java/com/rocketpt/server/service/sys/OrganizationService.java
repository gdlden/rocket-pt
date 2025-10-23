package com.rocketpt.server.service.sys;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.rocketpt.server.common.CommonResultStatus;
import com.rocketpt.server.common.DomainEventPublisher;
import com.rocketpt.server.common.exception.UserException;
import com.rocketpt.server.dao.OrganizationDao;
import com.rocketpt.server.dto.entity.Organization;
import com.rocketpt.server.dto.event.OrganizationCreated;
import com.rocketpt.server.dto.event.OrganizationDeleted;
import com.rocketpt.server.dto.event.OrganizationUpdated;
import com.rocketpt.server.dto.sys.OrgTreeDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author cjbi
 */
@Service
@RequiredArgsConstructor
public class OrganizationService {

  final OrganizationDao organizationDao;


  public Organization findOrganization(Long id) {
    Organization organization = organizationDao.selectById(id);
    if (organization == null) {
      throw new UserException(CommonResultStatus.RECORD_NOT_EXIST);
    }
    return organization;
  }

  @Transactional
  public Organization createOrganization(String name, Organization.Type type, Long parentId) {
    Organization organization = new Organization();
    organization.setName(name);
    organization.setType(type);
    Organization parent = findOrganization(parentId);
    organization.setParent(findOrganization(parentId));
    organization.setParentIds(parent.makeSelfAsParentIds());
    int inserted = organizationDao.insert(organization);
    DomainEventPublisher.instance().publish(new OrganizationCreated(organization));
    return organization;
  }

  @Transactional
  public Organization updateOrganization(Long id, String name) {
    Organization organization = findOrganization(id);
    organization.setName(name);
    int inserted = organizationDao.insert(organization);
    DomainEventPublisher.instance().publish(new OrganizationUpdated(organization));
    return organization;
  }

  @Transactional
  public void deleteOrganization(Long id) {
    Organization organization = findOrganization(id);
    organizationDao.deleteById(organization);
    DomainEventPublisher.instance().publish(new OrganizationDeleted(organization));
  }

  public List<OrgTreeDTO> findOrgTree(Long parentId) {
    return organizationDao.selectList(Wrappers.lambdaQuery(Organization.class).eq(Organization::getParentIds, parentId)).stream()
      .map(OrgTreeDTO::new)
      .collect(Collectors.toList());
  }
}
