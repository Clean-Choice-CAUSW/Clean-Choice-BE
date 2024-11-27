package com.cleanChoice.cleanChoice.domain.viewRecord.domain.repository;

import com.cleanChoice.cleanChoice.domain.viewRecord.domain.ViewRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ViewRecordRepository extends JpaRepository<ViewRecord, Long> {
}
