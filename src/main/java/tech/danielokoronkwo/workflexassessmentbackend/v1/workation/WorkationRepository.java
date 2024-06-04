package tech.danielokoronkwo.workflexassessmentbackend.v1.workation;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WorkationRepository extends JpaRepository<WorkationEntity, Long> {
    Optional<WorkationEntity> findOneByWorkationId(String workationId);
}
