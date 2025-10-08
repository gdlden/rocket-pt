package com.rocketpt.server.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.rocketpt.server.dto.entity.Organization;
import org.apache.ibatis.annotations.Mapper;
/**
 * @author cjbi
 */
@Mapper
public interface OrganizationDao extends BaseMapper<Organization> {

}
