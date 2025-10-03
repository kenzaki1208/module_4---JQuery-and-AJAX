package org.example.demo_jquery_and_ajax.practice.p2.repository;

import org.example.demo_jquery_and_ajax.practice.p2.model.Smartphone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ISmartphoneRepository extends JpaRepository<Smartphone, Long> {
}
