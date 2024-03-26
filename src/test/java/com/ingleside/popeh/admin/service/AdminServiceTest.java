package com.ingleside.popeh.admin.service;

import com.ingleside.popeh.admin.dto.AdminCreationsRequest;
import com.ingleside.popeh.admin.dto.AdminInformationResponse;
import com.ingleside.popeh.admin.entity.Admin;
import com.ingleside.popeh.admin.exception.AdminUsenameAlreadyExistsException;
import com.ingleside.popeh.admin.mapper.AdminMapper;
import com.ingleside.popeh.admin.repository.AdminRepository;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import static org.assertj.core.api.Assertions.*;

class AdminServiceTest {
  @Mock private AdminRepository adminRepository;
  @Mock private AdminMapper adminMapper;
  private AdminService underTest;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
    underTest = new AdminService(adminRepository, adminMapper);
  }

  @Test
  void itShouldCreateNewAdmin() {
    // given
    String firstName = "firstName";
    String lastName = "lastName";
    String username = "username";
    String password = "password";
    String number = "09131911893";
    LocalDateTime createdAt = LocalDateTime.now();
    long id = 1L;

    var adminCreationsRequest =
        new AdminCreationsRequest(firstName, lastName, username, password, number);

    var savableAdmin =
        Admin.builder()
            .firstName(firstName)
            .lastName(lastName)
            .username(username)
            .password(password)
            .phoneNumber(number)
            .createdAt(createdAt)
            .build();

    var savedAdmin =
        Admin.builder()
            .id(id)
            .firstName(firstName)
            .lastName(lastName)
            .username(username)
            .password(password)
            .phoneNumber(number)
            .createdAt(createdAt)
            .build();

    BDDMockito.when(adminMapper.mapToAdmin(adminCreationsRequest)).thenReturn(savableAdmin);

    BDDMockito.when(adminRepository.existsByUsername(username)).thenReturn(false);

    BDDMockito.when(adminRepository.save(Mockito.any())).thenReturn(savedAdmin);

    var expect =
        new AdminInformationResponse(
            id, firstName, lastName, username, password, number, createdAt);

    BDDMockito.when(adminMapper.mapToAdminInformationResponse(savedAdmin)).thenReturn(expect);

    // when
    var actual = underTest.create(adminCreationsRequest);
    // then

    assertThat(actual).usingRecursiveAssertion().isEqualTo(expect);
  }

  @Test
  void itShouldCreateNewAdminThrowException() {

    // given
    String firstName = "firstName";
    String lastName = "lastName";
    String username = "username";
    String password = "password";
    String number = "09131911893";

    var adminCreationsRequest =
        new AdminCreationsRequest(firstName, lastName, username, password, number);

    BDDMockito.when(adminRepository.existsByUsername(username)).thenReturn(true);

    // when
    assertThatThrownBy(() -> underTest.create(adminCreationsRequest))
        // then
        .isInstanceOf(AdminUsenameAlreadyExistsException.class)
        .hasMessage("this user name already taken");

    BDDMockito.verify(adminRepository, Mockito.never()).save(Mockito.any());
  }

  @Test
  void getAllAdminsThenReturnList() {

    // given -precondition or setup

    String firstName = "firstName";
    String lastName = "lastName";
    String username = "username";
    String password = "password";
    String number = "09131911893";
    LocalDateTime createdAt = LocalDateTime.now();
    long id = 1L;
    var savedAdmin =
        Admin.builder()
            .id(id)
            .firstName(firstName)
            .lastName(lastName)
            .username(username)
            .password(password)
            .phoneNumber(number)
            .createdAt(createdAt)
            .build();
    BDDMockito.given(adminRepository.findAll()).willReturn(List.of(savedAdmin));

    // when -action or behaviour that we are going to test
    List<Admin> adminList = underTest.getAllAdmins();

    // verify the output
    assertThat(adminList).isNotNull();
    assertThat(adminList.size()).isEqualTo(1);
  }

  @DisplayName("negative scenario")
  @Test
  void givenEmptyListOfAdminsThenReturnEmptyList() {

    // given -precondition or setup

    String firstName = "firstName";
    String lastName = "lastName";
    String username = "username";
    String password = "password";
    String number = "09131911893";
    LocalDateTime createdAt = LocalDateTime.now();
    long id = 1L;
    var savedAdmin =
        Admin.builder()
            .id(id)
            .firstName(firstName)
            .lastName(lastName)
            .username(username)
            .password(password)
            .phoneNumber(number)
            .createdAt(createdAt)
            .build();
    BDDMockito.given(adminRepository.findAll()).willReturn(Collections.emptyList());

    // when -action or behaviour that we are going to test
    List<Admin> adminList = underTest.getAllAdmins();

    // verify the output
    assertThat(adminList).isEmpty();
    assertThat(adminList.size()).isEqualTo(0);
  }

  @Test
  void givenAdminIdwhengetAdminByidthenReturnAdminObject() {

    // given -precondition or setup
    String firstName = "firstName";
    String lastName = "lastName";
    String username = "username";
    String password = "password";
    String number = "09131911893";
    LocalDateTime createdAt = LocalDateTime.now();
    long id = 1L;
    var savedAdmin =
        Admin.builder()
            .id(id)
            .firstName(firstName)
            .lastName(lastName)
            .username(username)
            .password(password)
            .phoneNumber(number)
            .createdAt(createdAt)
            .build();
    BDDMockito.when(adminRepository.findById(id)).thenReturn(Optional.of(savedAdmin));
    // when -action or behaviour that we are going to test
    Optional<Admin> adminOptional = underTest.getAdminById(id);
    // verify the output
    assertThat(adminOptional).isNotNull();
  }

  @Test
  void givenAdminObjectWhenUpdatedAdminThenReturnUpdatedAdmin() {

    // given -precondition or setup
    String firstName = "firstName";
    String lastName = "lastName";
    String username = "username";
    String password = "password";
    String number = "09131911893";
    LocalDateTime createdAt = LocalDateTime.now();
    long id = 1L;
    var savedAdmin =
        Admin.builder()
            .id(id)
            .firstName(firstName)
            .lastName(lastName)
            .username(username)
            .password(password)
            .phoneNumber(number)
            .createdAt(createdAt)
            .build();
    BDDMockito.given(adminRepository.save(savedAdmin)).willReturn(savedAdmin);
    savedAdmin.setFirstName("jahan");
    savedAdmin.setLastName("hajar");
    savedAdmin.setUsername("servbaset");
    savedAdmin.setPassword("973hhghdgf");
    // when -action or behaviour that we are going to test

    Admin updatedAdmin = underTest.updateAdmin(savedAdmin);
    // verify the output
    assertThat(updatedAdmin.getUsername()).isEqualTo("servbaset");
    assertThat(updatedAdmin.getLastName()).isEqualTo("hajar");
    assertThat(updatedAdmin.getFirstName()).isEqualTo("jahan");
    assertThat(updatedAdmin.getPassword()).isEqualTo("973hhghdgf");
  }
}
