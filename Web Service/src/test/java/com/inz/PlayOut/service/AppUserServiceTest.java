package com.inz.PlayOut.service;

import com.inz.PlayOut.model.entites.AppUser;
import com.inz.PlayOut.model.entites.FootballEvent;
import com.inz.PlayOut.model.repositories.UserRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
class AppUserServiceTest {

    @Mock
    private UserRepo userRepo;

    @InjectMocks
    private AppUserService appUserService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    private List<AppUser> appUserList() {
        List<AppUser> appUser = new ArrayList<>();
        appUser.add(new AppUser("Piotr", "password1", "piotr@mail.com"));
        appUser.add(new AppUser("Paweł", "password2", "pawel@mail.com"));
        return appUser;
    }

    @Test
    void findAll() {
        when(userRepo.findAll()).thenReturn(appUserList());

        List<AppUser> appUser = appUserService.findAll();

        assertEquals(2, appUserList().size());
        verify(userRepo, times(1)).findAll();
    }

    @Test
    void findAll_with_null() {
        when(userRepo.findAll()).thenReturn(null);

        List<AppUser> appUser = appUserService.findAll();

        assertNull(appUser);
        verify(userRepo, times(1)).findAll();
    }

    @Test
    void findById() {
        when(userRepo.findById(1L)).thenReturn(java.util.Optional.ofNullable(appUserList().get(0)));

        Optional<AppUser> appUser = appUserService.findById(1L);

        assertEquals(appUserList().get(0).getEmail(), appUser.get().getEmail());
        assertEquals(appUserList().get(0).getUsername(), appUser.get().getUsername());

        verify(userRepo, times(1)).findById(1L);
    }

    @Test
    void findById_not_found() {
        when(userRepo.findById(anyLong())).thenReturn(null);

        Optional<AppUser> appUser = appUserService.findById(1L);

        assertNull(appUser);
        verify(userRepo, times(1)).findById(1L);
    }

    @Test
    void save() {
        AppUser appUser = new AppUser("Aga", "password3", "aga@mail.com");
        when(userRepo.save(any(AppUser.class))).thenReturn(appUser);

        AppUser appUser1 = appUserService.save(appUser);

        assertEquals(appUser1.getEmail(), appUser.getEmail());
        assertEquals(appUser1.getUsername(), appUser.getUsername());
        verify(userRepo, times(1)).save(appUser1);
    }

    @Test
    void delete() {
        when(userRepo.findById(1L)).thenReturn(Optional.ofNullable(appUserList().get(0)));

        Optional<AppUser> appUser = appUserService.delete(1L);

        assertEquals(appUserList().get(0).getEmail(), appUser.get().getEmail());
        assertEquals(appUserList().get(0).getUsername(), appUser.get().getUsername());

        verify(userRepo, times(1)).findById(1L);
        verify(userRepo, times(1)).deleteById(1L);
    }

    @Test
    void delete_not_found_id() {
        when(userRepo.findById(1L)).thenReturn(isNull());

        Optional<AppUser> appUser = appUserService.delete(1L);

        assertNull(appUser);
        verify(userRepo, times(1)).findById(1L);
        verify(userRepo, times(1)).deleteById(1L);
    }
}