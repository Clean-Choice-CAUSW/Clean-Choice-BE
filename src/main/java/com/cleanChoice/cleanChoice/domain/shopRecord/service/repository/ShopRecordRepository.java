package com.cleanChoice.cleanChoice.domain.shopRecord.service.repository;

import com.cleanChoice.cleanChoice.domain.shopRecord.domain.ShopRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShopRecordRepository extends JpaRepository<ShopRecord, Long> {
}
