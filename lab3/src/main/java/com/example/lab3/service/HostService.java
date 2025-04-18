package com.example.lab3.service;

import com.example.lab3.entity.Host;
import com.example.lab3.repository.HostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HostService {
    @Autowired
    private HostRepository hostRepo;

    public List<Host> getAll() { return hostRepo.findAll(); }

    public Host get(Long id) {
        return hostRepo.findById(id).orElseThrow(() -> new RuntimeException("Host not found"));
    }

    public Host create(Host host) { return hostRepo.save(host); }

    public void delete(Long id) { hostRepo.deleteById(id); }
}
