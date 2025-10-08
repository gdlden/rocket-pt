package com.rocketpt.server.dto.event;


import com.rocketpt.server.common.DomainEvent;
import com.rocketpt.server.dto.entity.Organization;

/**
 * @author cjbi
 */
public record OrganizationCreated(Organization organization) implements DomainEvent {
}
