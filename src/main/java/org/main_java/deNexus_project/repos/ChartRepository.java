package org.main_java.deNexus_project.repos;

import org.main_java.deNexus_project.domain.Chart;
import org.main_java.deNexus_project.domain.Experiment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChartRepository extends JpaRepository<Chart, Long> {
    List<Chart> findByExperiment(Experiment experiment);
}
