package com.ITOPW.itopw.repository;

import com.ITOPW.itopw.entity.Statistics;
import com.ITOPW.itopw.entity.User;
import jakarta.persistence.QueryHint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {

    // 이메일로 사용자 조회
    Optional<User> findByEmail(String email);

    // ID로 사용자 조회
    Optional<User> findByUserId(String userId);

    // 프로젝트 ID로 사용자 목록 조회
    //List<User> findByProjectId(String projectId);
//    @Query(value = "SELECT * FROM \"user\" WHERE :projectId = ANY (project_id)", nativeQuery = true)
//    List<User> findByProjectId(@Param("projectId") String projectId);

    // 프로젝트 ID 리스트를 기준으로 사용자 조회
    @Query(value = "SELECT * FROM \"user\" WHERE project_id  && CAST(:projectIds AS text[])", nativeQuery = true)
    List<User> findByProjectId(@Param("projectIds") String projectIds);
    //List<User> findByProjectId(@Param("projectIds") List<String> projectIds);

    @Query(value = "SELECT * FROM \"user\" WHERE project_id  && CAST(:projectIds AS text[]) AND unit = :unit", nativeQuery = true)
    List<User> findByProjectIdAndUnit(@Param("projectIds") String projectIds, @Param("unit") String unit);

    // 여러 userId 값을 기반으로 사용자 목록 조회
    List<User> findAllByUserIdIn(List<String> userIds);


}