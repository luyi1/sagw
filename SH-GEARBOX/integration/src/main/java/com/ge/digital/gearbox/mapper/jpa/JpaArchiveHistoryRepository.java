package com.ge.digital.gearbox.mapper.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ge.digital.gearbox.entity.archive.ArchiveHistory;

@Repository
public interface JpaArchiveHistoryRepository extends JpaRepository<ArchiveHistory, Long> {

}
