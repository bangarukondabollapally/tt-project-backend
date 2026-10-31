package com.ttproject.repository;

import com.ttproject.entity.Project;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository extends JpaRepository<Project, Long> {

    List<Project> findAllByOrderByDeadlineAscIdAsc();
}
