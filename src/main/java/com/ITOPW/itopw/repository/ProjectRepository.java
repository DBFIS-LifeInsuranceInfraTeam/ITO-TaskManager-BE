package com.ITOPW.itopw.repository;

import com.ITOPW.itopw.entity.Project;
import com.ITOPW.itopw.entity.Statistics;
import jakarta.persistence.QueryHint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ProjectRepository extends JpaRepository<Project, String> {

    @Query("SELECT p FROM Project p WHERE p.projectId IN :projectIds")
    List<Project> findProjectsByProjectId(List<String> projectIds);

}