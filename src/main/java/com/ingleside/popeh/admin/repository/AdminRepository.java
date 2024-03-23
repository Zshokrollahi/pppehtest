package com.ingleside.popeh.admin.repository;

import com.ingleside.popeh.admin.entity.Admin;
import org.hibernate.boot.archive.scan.internal.ScanResultImpl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRepository extends JpaRepository< Admin, Long> {

    //syntax to query creation from methods name approach : findby to retrieve from database
    boolean existsByUsername(String username);

    boolean existsByPhoneNumber(String phoneNumber);

    //define cutom query with jpql
    @Query("select a from Admin a where a.firstName = :firstName and a.lastName = :lastName ")
    Admin findByJpql (@Param("firstName") String firstName , @Param("lastName") String lastName);

}
