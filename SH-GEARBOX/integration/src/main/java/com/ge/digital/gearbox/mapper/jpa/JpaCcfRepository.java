package com.ge.digital.gearbox.mapper.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.ge.digital.gearbox.entity.archive.JpaCcf;

@Repository
public interface JpaCcfRepository extends JpaRepository<JpaCcf, Long> {

}
