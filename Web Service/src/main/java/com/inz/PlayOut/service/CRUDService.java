package com.inz.PlayOut.service;

import java.util.List;
import java.util.Optional;

public interface CRUDService <T>{

    List<T> findAll();

    Optional<T> findById(Long id);

    T save(T object);

    Optional<T> delete(Long id);
}
