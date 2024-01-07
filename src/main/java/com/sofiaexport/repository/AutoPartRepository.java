package com.sofiaexport.repository;

import com.sofiaexport.model.AutoPart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AutoPartRepository extends JpaRepository<AutoPart, Long> {
}
