package com.example.lab3.repository;

import com.example.lab3.entity.Host;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HostRepository extends JpaRepository<Host, Long> {}