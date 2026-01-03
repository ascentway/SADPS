package com.sadps.respository;

import com.sadps.entity.AuditLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuditRepository extends JpaRepository<AuditLog, Long> {

public interface AuditRepository extends JpaRepository<AuditLog, Long> {

}
