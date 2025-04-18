package com.example.lab3.controller;

import com.example.lab3.dto.GuestWithHostDTO;
import com.example.lab3.entity.Guest;
import com.example.lab3.service.GuestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/guests")
public class GuestController {
    @Autowired
    private GuestService service;

    @GetMapping
    public List<Guest> getAll() { return service.getAll(); }

    @GetMapping("/{id}") public Guest get(@PathVariable Long id) { return service.get(id); }

    @PostMapping
    public Guest create(@RequestBody Guest guest) { return service.create(guest); }

    @DeleteMapping("/{id}") public void delete(@PathVariable Long id) { service.delete(id); }
    // GET specific guest and host
    @GetMapping("/{id}/with-host")
    public ResponseEntity<GuestWithHostDTO> getGuestWithHost(@PathVariable Long id) {
        return ResponseEntity.ok(service.getGuestWithHost(id));
    }

    // POST new guest with host assignment
    @PostMapping("/host/{hostId}")
    public ResponseEntity<Guest> createGuest(
            @PathVariable Long hostId,
            @RequestBody Guest guest) {
        return new ResponseEntity<>(service.createGuest(guest, hostId), HttpStatus.CREATED);
    }

    // PUT update guest info
    @PutMapping("/{id}")
    public ResponseEntity<Guest> updateGuest(
            @PathVariable Long id,
            @RequestBody Guest guest) {
        return ResponseEntity.ok(service.updateGuest(id, guest));
    }
}
