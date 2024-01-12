package com.sofiaexport.repository;

import com.sofiaexport.model.AutoPart;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AutoPartRepository extends JpaRepository<AutoPart, String> {
    @Lock(value = LockModeType.OPTIMISTIC_FORCE_INCREMENT)
    @Override
    <S extends AutoPart> List<S> saveAll(Iterable<S> entities);
}
