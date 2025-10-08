package com.rocketpt.server.dao;

import com.rocketpt.server.dto.entity.Organization;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

public record OrganizationRequest(@NotBlank String name, @NotNull Organization.Type type, @NotNull Long parentId) {}