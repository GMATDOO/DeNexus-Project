package org.main_java.deNexus_project.repos;

import org.main_java.deNexus_project.domain.Experiment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ExperimentRepository extends JpaRepository<Experiment, Long> {

    @Query("SELECT e FROM Experiment e LEFT JOIN FETCH e.samples")
    List<Experiment> findAllExperiments();
}
