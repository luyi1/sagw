package com.ge.digital.gearbox.mapper.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.ge.digital.gearbox.entity.archive.JpaTunnel;

@Repository
public interface JpaTunnelRepository extends JpaRepository<JpaTunnel, Long> {

}
