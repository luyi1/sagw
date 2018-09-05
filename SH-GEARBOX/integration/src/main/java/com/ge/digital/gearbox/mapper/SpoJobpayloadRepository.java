package com.ge.digital.gearbox.mapper;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ge.digital.gearbox.entity.SpoJobpayload;

@Repository
public interface SpoJobpayloadRepository extends JpaRepository<SpoJobpayload,Long> {


}
