package com.ingleside.popeh.admin.repository;

import com.ingleside.popeh.admin.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Long> {


    //syntax to query creation from methods name approach : findby to retrieve from database
    boolean existsByUsername(String username);

    boolean existsByPhoneNumber(String phoneNumber);

    //define custom query with jpql based on classes because we are using orm
    @Query("select a from Admin a where a.firstName = :firstName and a.lastName = :lastName ")
    Admin findByJpql(@Param("firstName") String firstName, @Param("lastName") String lastName);


    //native sql based on sql statement
    @Query(value = "select * from admins e where e.first_name = ?1 and e.last_name = ?2 " ,nativeQuery = true)
    Admin findBySQLNative(String firstName , String lastName);

    @Query(value = "select * from admins e where e.username = :username and e.password = :password " ,nativeQuery = true)
    Admin findBySQLNativeNamedParam(@Param("username") String username , @Param("password") String password);

}
