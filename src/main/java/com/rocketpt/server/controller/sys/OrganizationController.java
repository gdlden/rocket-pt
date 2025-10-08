package com.rocketpt.server.controller.sys;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.rocketpt.server.common.CommonResultStatus;
import com.rocketpt.server.common.base.Result;
import com.rocketpt.server.common.exception.UserException;
import com.rocketpt.server.dto.sys.OrganizationDTO;
import com.rocketpt.server.dto.entity.Organization;
import com.rocketpt.server.dto.sys.OrgTreeDTO;
import com.rocketpt.server.service.sys.OrganizationService;
import com.rocketpt.server.service.sys.UserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * @author cjbi
 */
@SecurityRequirement(name = "bearerAuth")
@RestController
@RequestMapping("/organizations")
public class OrganizationController {

  private final UserService userService;
  private final OrganizationService organizationService;

  public OrganizationController(UserService userService, OrganizationService organizationService) {
    this.userService = userService;
    this.organizationService = organizationService;
  }

  @GetMapping("/tree")
  @SaCheckPermission("user:view")
  public Result<List<OrgTreeDTO>> findOrgTree(Long parentId) {
    return Result.ok(organizationService.findOrgTree(parentId));
  }

  @SaCheckPermission("user:view")
  @GetMapping("/{organizationId}/users")
  public Result findOrgUsers(Pageable pageable, @RequestParam(required = false) String username, @RequestParam(required = false) Integer state, @PathVariable Long organizationId) {
    Organization organization = organizationService.findOrganization(organizationId);
    return userService.findOrgUsers(pageable, username, state, organization);
  }

  @SaCheckPermission("organization:create")
  @PostMapping
  public Result<Organization> createOrganization(@RequestBody @Valid OrganizationDTO request) {
    return Result.ok(organizationService.createOrganization(request.name(), request.type(), request.parentId()));
  }

  @SaCheckPermission("organization:update")
  @PutMapping("/{organizationId}")
  public Result<Organization> updateOrganization(@PathVariable Long organizationId, @RequestBody @Valid OrganizationDTO request) {
    return Result.ok(organizationService.updateOrganization(organizationId, request.name()));
  }

  @SaCheckPermission("organization:delete")
  @DeleteMapping("/{organizationId}")
  public Result<Void> deleteOrganization(@PathVariable Long organizationId) {
    Organization organization = organizationService.findOrganization(organizationId);
    if (userService.existsUsers(organization)) {
      throw new UserException(CommonResultStatus.FAIL, "节点存在用户，不能删除");
    }
    organizationService.deleteOrganization(organizationId);
    return Result.ok();
  }
}
