package org.example.demo_jquery_and_ajax.practice.p2.service.impl;

import org.example.demo_jquery_and_ajax.practice.p2.model.Smartphone;
import org.example.demo_jquery_and_ajax.practice.p2.repository.ISmartphoneRepository;
import org.example.demo_jquery_and_ajax.practice.p2.service.ISmartphoneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SmartphoneService implements ISmartphoneService {
    @Autowired
    private ISmartphoneRepository smartphoneRepository;


    @Override
    public Iterable<Smartphone> findAll() {
        return smartphoneRepository.findAll();
    }

    @Override
    public Optional<Smartphone> findById(Long id) {
        return smartphoneRepository.findById(id);
    }

    @Override
    public Smartphone save(Smartphone smartphone) {
        return smartphoneRepository.save(smartphone);
    }

    @Override
    public void remove(Long id) {
        smartphoneRepository.deleteById(id);
    }
}
