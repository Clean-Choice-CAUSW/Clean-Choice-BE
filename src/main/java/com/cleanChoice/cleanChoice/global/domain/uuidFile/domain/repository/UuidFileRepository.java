package com.cleanChoice.cleanChoice.global.domain.uuidFile.domain.repository;


import com.cleanChoice.cleanChoice.global.domain.uuidFile.domain.UuidFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UuidFileRepository extends JpaRepository<UuidFile, Long> {
}
