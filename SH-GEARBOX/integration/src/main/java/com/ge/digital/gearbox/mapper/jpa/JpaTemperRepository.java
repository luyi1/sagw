package com.ge.digital.gearbox.mapper.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.ge.digital.gearbox.entity.archive.JpaTemper;

@Repository
public interface JpaTemperRepository extends JpaRepository<JpaTemper, Long> {

}
