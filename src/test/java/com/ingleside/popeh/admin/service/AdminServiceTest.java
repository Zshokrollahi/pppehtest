package com.ingleside.popeh.admin.service;

import com.ingleside.popeh.admin.dto.AdminCreationsRequest;
import com.ingleside.popeh.admin.dto.AdminInformationResponse;
import com.ingleside.popeh.admin.entity.Admin;
import com.ingleside.popeh.admin.exception.AdminUsenameAlreadyExistsException;
import com.ingleside.popeh.admin.mapper.AdminMapper;
import com.ingleside.popeh.admin.repository.AdminRepository;
import java.time.LocalDateTime;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

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

    Assertions.assertThat(actual).usingRecursiveAssertion().isEqualTo(expect);
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
    Assertions.assertThatThrownBy(() -> underTest.create(adminCreationsRequest))
        // then
        .isInstanceOf(AdminUsenameAlreadyExistsException.class)
        .hasMessage("this user name already taken");

    BDDMockito.verify(adminRepository, Mockito.never()).save(Mockito.any());
  }
}
