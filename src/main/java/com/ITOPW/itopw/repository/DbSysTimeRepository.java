package com.ITOPW.itopw.repository;

import com.ITOPW.itopw.domain.DbSysTime;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import java.time.Instant;
public interface DbSysTimeRepository extends CrudRepository<DbSysTime, Long> {

    @Query(value = "SELECT NOW()", nativeQuery = true)
    Instant getDbSysTime();
}
