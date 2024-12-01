package com.cleanChoice.cleanChoice.domain.viewRecord.domain.repository;

import com.cleanChoice.cleanChoice.domain.member.domain.Member;
import com.cleanChoice.cleanChoice.domain.viewRecord.domain.ViewRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ViewRecordRepository extends JpaRepository<ViewRecord, Long> {

    List<ViewRecord> findByMemberOrderByCreatedAtDesc(Member member);

}
