package com.ge.digital.spo.chain.infrastructure.user.dao;

import com.ge.digital.spo.chain.infrastructure.user.model.Group;

public interface GroupMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table public.spo_group
     *
     * @mbg.generated Thu Sep 21 10:46:10 CST 2017
     */
    int deleteByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table public.spo_group
     *
     * @mbg.generated Thu Sep 21 10:46:10 CST 2017
     */
    int insert(Group record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table public.spo_group
     *
     * @mbg.generated Thu Sep 21 10:46:10 CST 2017
     */
    int insertSelective(Group record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table public.spo_group
     *
     * @mbg.generated Thu Sep 21 10:46:10 CST 2017
     */
    Group selectByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table public.spo_group
     *
     * @mbg.generated Thu Sep 21 10:46:10 CST 2017
     */
    int updateByPrimaryKeySelective(Group record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table public.spo_group
     *
     * @mbg.generated Thu Sep 21 10:46:10 CST 2017
     */
    int updateByPrimaryKey(Group record);
}