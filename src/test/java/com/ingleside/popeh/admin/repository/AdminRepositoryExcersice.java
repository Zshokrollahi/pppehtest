package com.ingleside.popeh.admin.repository;

import com.ingleside.popeh.admin.dto.AdminCreationsRequest;
import com.ingleside.popeh.admin.dto.AdminInformationResponse;
import com.ingleside.popeh.admin.entity.Admin;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.testcontainers.shaded.com.google.common.annotations.VisibleForTesting;
import org.testcontainers.shaded.org.bouncycastle.jcajce.provider.asymmetric.dsa.DSASigner;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;

import static org.assertj.core.api.Assertions.*;

@DataJpaTest
class AdminRepositoryExcersice {

  @Autowired private AdminRepository adminRepository;
  private Admin admin;

  // this method will execute before every unit test that we created
  // common code in it
  @BeforeEach
  void setUp() {
    admin =
        Admin.builder()
            .firstName("zahra")
            .lastName("shokrollahi")
            .username("ingleside")
            .password("9743")
            .phoneNumber("094248432")
            .createdAt(LocalDateTime.now())
            .build();
  }

  //    @Test
  //    @DisplayName("name for showing")
  //    void itshouldexistsByUsername() {
  //        //given
  //        Admin saved = adminRepository.save(admin);
  //
  //        //when
  //        AdminInformationResponse existsByUsername =
  // adminRepository.existsByUsername(adminCreationsRequest.username());
  //
  //        //verify
  //        assertThat(existsByUsername).isNotNull();
  //
  //
  //    }

  @Test
  void itShouldSaveNewAdmin() {

    // given
    Admin admin =
        Admin.builder()
            .firstName("zahra")
            .lastName("shokrollahi")
            .username("ingleside")
            .password("9743")
            .phoneNumber("094248432")
            .createdAt(LocalDateTime.now())
            .build();

    // when
    Admin savedAdmin = adminRepository.save(admin);

    // then -verify the output
    assertThat(savedAdmin).isNotNull();
    assertThat(savedAdmin.getPhoneNumber()).isEqualTo("094248432").hasSizeGreaterThan(8);
  }

  @Test
  void itShouldFindAllAdmins() {

    // given -precondition or setup
    Admin admin =
        Admin.builder()
            .firstName("ali")
            .lastName("abdoli")
            .username("sideside")
            .password("9743")
            .phoneNumber("094248432")
            .createdAt(LocalDateTime.now())
            .build();

    Admin admin1 =
        Admin.builder()
            .firstName("zahra")
            .lastName("shoki")
            .username("ingllleside")
            .password("973443")
            .phoneNumber("043448432")
            .createdAt(LocalDateTime.now())
            .build();

    adminRepository.save(admin);
    adminRepository.save(admin1);

    // when -action or behaviour that we are going to test
    List<Admin> adminList = adminRepository.findAll();

    // verify the output
    assertThat(adminList).isNotNull();
    assertThat(adminList.size()).isEqualTo(2);
  }

  @Test
  void itShouldGetAdminById() {

    // given -precondition or setup
    Admin admin =
        Admin.builder()
            .firstName("alia")
            .lastName("abdodli")
            .username("sidesfide")
            .password("97433")
            .phoneNumber("094238432")
            .createdAt(LocalDateTime.now())
            .build();

    adminRepository.save(admin);

    // when -action or behaviour that we are going to test
    Admin adminInDB = adminRepository.findById(admin.getId()).get();

    // verify the output
    assertThat(adminInDB).isNotNull();
  }

  @Test
  void itShouldUpdateAdmin() {

    // given -precondition or setup
    Admin admin =
        Admin.builder()
            .firstName("alia")
            .lastName("abdodli")
            .username("sidesfide")
            .password("97433")
            .phoneNumber("094238432")
            .createdAt(LocalDateTime.now())
            .build();

    adminRepository.save(admin);

    // when -action or behaviour that we are going to test
    // retrieve object first to database

    Admin adminRetrieve = adminRepository.findById(admin.getId()).get();

    // we need to change some fields and then save again
    adminRetrieve.setFirstName("mahmood");
    adminRetrieve.setLastName("alimandi");
    Admin updatedAdmin = adminRepository.save(adminRetrieve);

    // verify the output
    assertThat(updatedAdmin.getFirstName()).isEqualTo("mahmood");
    assertThat(updatedAdmin.getLastName()).isEqualTo("alimandi");
  }

  @Test
  void itSholdDeleteAdminWeSaved() {
    // given -precondition or setup
    Admin admin =
        Admin.builder()
            .firstName("alia")
            .lastName("abdodli")
            .username("sidesfide")
            .password("97433")
            .phoneNumber("094238432")
            .createdAt(LocalDateTime.now())
            .build();

    adminRepository.save(admin);

    // when -action or behaviour that we are going to test
    // delete saved object
    // we should call deleted object with its id that can checked it is null or not
    adminRepository.delete(admin);
    Optional<Admin> optionalAdmin = adminRepository.findById(admin.getId());

    // verify the output
    assertThat(optionalAdmin).isEmpty();
  }

  @Test
  void itShouldReturnAdminUsingJPQl() {
    // given -precondition or setup
    Admin admin =
        Admin.builder()
            .firstName("alia")
            .lastName("abdodli")
            .username("sidesfide")
            .password("97433")
            .phoneNumber("094238432")
            .createdAt(LocalDateTime.now())
            .build();

    adminRepository.save(admin);
    String firstName = "alia";
    String lastName = "abdodli";

    // when -action or behaviour that we are going to test
    Admin savedAdmin = adminRepository.findByJpql(firstName, lastName);

    adminRepository.findAll().stream()
        .filter(s -> s.getFirstName().startsWith("ali"))
        .map(Admin::getFirstName)
        .forEach(System.out::println);

    // verify the output
    assertThat(savedAdmin).isNotNull();
  }

  @Test
  void itShouldReturnAdminWithNativeQuery() {

    // given -precondition or setup
    Admin admin =
        Admin.builder()
            .firstName("alia")
            .lastName("abdodli")
            .username("sidesfide")
            .password("97433")
            .phoneNumber("094238432")
            .createdAt(LocalDateTime.now())
            .build();

    adminRepository.save(admin);

    // when -action or behaviour that we are going to test
    Admin savedAdmin = adminRepository.findBySQLNative(admin.getFirstName(), admin.getLastName());

    // verify the output
    assertThat(savedAdmin).isNotNull();
  }

  @Test
  void itShouldReturnAdminUserAndPasswordWithNativeQuery() {

    // given -precondition or setup
    Admin admin =
        Admin.builder()
            .firstName("alia")
            .lastName("abdodli")
            .username("sidesfide")
            .password("97433")
            .phoneNumber("094238432")
            .createdAt(LocalDateTime.now())
            .build();

    adminRepository.save(admin);

    // when -action or behaviour that we are going to test
    Admin adminSaved =
        adminRepository.findBySQLNativeNamedParam(admin.getUsername(), admin.getPassword());

    // verify the output
    assertThat(adminSaved).isNotNull();
  }

  @Test
  void itShouldReturnTrueWhenUsernameIsExist() {

    // given -precondition or setup
    adminRepository.save(admin);

    // when -action or behaviour that we are going to test
    boolean savedAdmin = adminRepository.existsByUsername(admin.getUsername());


    // verify the output
    assertThat(savedAdmin).isTrue();
  }
}
