package com.rocketpt.server.dto.sys;

import com.rocketpt.server.dto.entity.Organization;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotBlank;

public record OrganizationDTO(@NotBlank String name, @NotNull Organization.Type type, @NotNull Long parentId) {}