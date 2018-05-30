package com.ge.digital.spo.chain.infrastructure.user.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;

import com.ge.digital.spo.chain.infrastructure.user.model.RoleResource;

public interface RoleResourceMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table public.rel_role_resource
     *
     * @mbg.generated Fri Sep 22 15:02:02 CST 2017
     */
    int deleteByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table public.rel_role_resource
     *
     * @mbg.generated Fri Sep 22 15:02:02 CST 2017
     */
    int insert(RoleResource record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table public.rel_role_resource
     *
     * @mbg.generated Fri Sep 22 15:02:02 CST 2017
     */
    int insertSelective(RoleResource record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table public.rel_role_resource
     *
     * @mbg.generated Fri Sep 22 15:02:02 CST 2017
     */
    RoleResource selectByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table public.rel_role_resource
     *
     * @mbg.generated Fri Sep 22 15:02:02 CST 2017
     */
    int updateByPrimaryKeySelective(RoleResource record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table public.rel_role_resource
     *
     * @mbg.generated Fri Sep 22 15:02:02 CST 2017
     */
    int updateByPrimaryKey(RoleResource record);
    
    //--------------------------------------------------------//
    List<RoleResource> selectByRoleId(String roleId);
    
    int deleteAll();

    @Delete("delete from rel_role_resource where role_id=#{id}")
    int deleteByRole(@Param("id") String id);
}