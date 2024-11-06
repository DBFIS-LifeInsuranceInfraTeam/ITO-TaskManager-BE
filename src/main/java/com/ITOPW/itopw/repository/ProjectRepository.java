package com.ITOPW.itopw.repository;

import com.ITOPW.itopw.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ProjectRepository extends JpaRepository<Project, String> {

}