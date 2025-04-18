package com.example.lab3.service;
import com.example.lab3.dto.GuestWithHostDTO;
import com.example.lab3.entity.Guest;
import com.example.lab3.entity.Host;
import com.example.lab3.repository.GuestRepository;
import com.example.lab3.repository.HostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GuestService {
    @Autowired
    private GuestRepository guestRepo;
    @Autowired private HostRepository hostRepo;

    public List<Guest> getAll() { return guestRepo.findAll(); }

    public Guest get(Long id) {
        return guestRepo.findById(id).orElseThrow(() -> new RuntimeException("Guest not found"));
    }

    public Guest create(Guest guest) { return guestRepo.save(guest); }

    public void delete(Long id) { guestRepo.deleteById(id); }

    // GET guest + host info
    public GuestWithHostDTO getGuestWithHost(Long guestId) {
        Guest guest = guestRepo.findById(guestId)
                .orElseThrow(() -> new RuntimeException("Guest not found"));

        Host host = guest.getHost();
        if (host == null) throw new RuntimeException("No host assigned to this guest");

        GuestWithHostDTO dto = new GuestWithHostDTO();
        dto.setGuestId(guest.getId());
        dto.setGuestName(guest.getName());
        dto.setGuestEmail(guest.getEmail());

        dto.setHostId(host.getId());
        dto.setHostName(host.getName());
        dto.setHostEmail(host.getEmail());

        return dto;
    }

    // POST new guest linked to a host
    public Guest createGuest(Guest guest, Long hostId) {
        Host host = hostRepo.findById(hostId)
                .orElseThrow(() -> new RuntimeException("Host not found"));
        guest.setHost(host);
        return guestRepo.save(guest);
    }

    // PUT update guest info
    public Guest updateGuest(Long guestId, Guest updatedGuest) {
        Guest existing = guestRepo.findById(guestId)
                .orElseThrow(() -> new RuntimeException("Guest not found"));

        existing.setName(updatedGuest.getName());
        existing.setEmail(updatedGuest.getEmail());
        existing.setPassword(updatedGuest.getPassword());
        existing.setRating(updatedGuest.getRating());

        return guestRepo.save(existing);
    }
}
