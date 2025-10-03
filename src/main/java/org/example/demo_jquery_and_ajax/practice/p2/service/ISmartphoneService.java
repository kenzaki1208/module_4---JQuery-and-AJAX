package org.example.demo_jquery_and_ajax.practice.p2.service;

import org.example.demo_jquery_and_ajax.practice.p2.model.Smartphone;

import java.util.Optional;

public interface ISmartphoneService {
    Iterable<Smartphone> findAll();
    Optional<Smartphone> findById(Long id);
    Smartphone save(Smartphone smartphone);
    void remove(Long id);
}
