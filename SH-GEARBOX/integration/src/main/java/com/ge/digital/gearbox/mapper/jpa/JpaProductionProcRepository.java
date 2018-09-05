package com.ge.digital.gearbox.mapper.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ge.digital.gearbox.entity.archive.JpaProductionProc;

@Repository
public interface JpaProductionProcRepository extends JpaRepository<JpaProductionProc, Long> {

}
