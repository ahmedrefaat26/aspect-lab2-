package com.example.lab3.controller;

import com.example.lab3.entity.Host;
import com.example.lab3.service.HostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/hosts")
public class HostController {
    @Autowired
    private HostService service;

    @GetMapping
    public List<Host> getAll() { return service.getAll(); }

    @GetMapping("/{id}") public Host get(@PathVariable Long id) { return service.get(id); }

    @PostMapping
    public Host create(@RequestBody Host host) { return service.create(host); }

    @DeleteMapping("/{id}") public void delete(@PathVariable Long id) { service.delete(id); }
}
