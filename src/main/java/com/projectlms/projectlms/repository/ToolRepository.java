package com.projectlms.projectlms.repository;

import org.springframework.stereotype.Repository;

import com.projectlms.projectlms.domain.dao.Tool;
import com.projectlms.projectlms.repository.softdeletes.SoftDeletesRepository;

@Repository
public interface ToolRepository extends SoftDeletesRepository<Tool, Long> {
    
}