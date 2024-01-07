package com.sofiaexport.repository;

import com.sofiaexport.commands.FindAutoPartsCommand;
import com.sofiaexport.model.AutoPart;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class AutoPartRepositoryCustomImpl implements AutoPartRepositoryCustom {
    @PersistenceContext
    private final EntityManager entityManager;

    @Override
    public List<AutoPart> findAutoParts(FindAutoPartsCommand command) {
        CriteriaBuilder cb = this.entityManager.getCriteriaBuilder();
        CriteriaQuery<AutoPart> query = cb.createQuery(AutoPart.class);
        Root<AutoPart> root = query.from(AutoPart.class);
        Predicate predicate = cb.conjunction();
        if (command.getBrand() != null) {
            predicate = cb.and(predicate, cb.equal(root.get("brand"), command.getBrand()));
        }

        if (command.getPrice() != null) {
            predicate = cb.and(predicate, cb.lessThan(root.get("price"), command.getPrice()));
        }

        if (command.getPartType() != null) {
            predicate = cb.and(predicate, cb.equal(root.get("partType"), command.getPartType()));
        }

        query.select(root).where(predicate);
        return this.entityManager.createQuery(query).getResultList();
    }
}
