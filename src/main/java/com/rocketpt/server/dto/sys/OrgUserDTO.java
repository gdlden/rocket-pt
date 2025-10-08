package com.rocketpt.server.dto.sys;


import java.time.LocalDateTime;

/**
 * @author cjbi
 */
public record OrgUserDTO(Long id,
                         String username,
                         String avatar,
                         Integer gender,
                         Integer state,
                         String orgFullName,
                         LocalDateTime createdTime) {
}
