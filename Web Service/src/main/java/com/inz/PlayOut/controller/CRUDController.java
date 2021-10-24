package com.inz.PlayOut.controller;

import org.springframework.http.ResponseEntity;

public interface CRUDController <T>{

    ResponseEntity<Object> findAll();

    ResponseEntity<Object> getById(Long id);

     ResponseEntity<Object> add(T object);

    ResponseEntity<Object> delete(Long id);
}
