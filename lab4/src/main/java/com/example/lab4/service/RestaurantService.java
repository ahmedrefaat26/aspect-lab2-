package com.example.lab4.service;

import com.example.lab4.Repository.RestaurantRepository;
import com.example.lab4.model.Restaurant;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RestaurantService {

    private final RestaurantRepository repository;

    public RestaurantService(RestaurantRepository repository) {
        this.repository = repository;
    }

    @Cacheable(value = "restaurants")
    public List<Restaurant> getAllRestaurants() {
        simulateLongProcessing();
        return repository.findAll();
    }

    public Optional<Restaurant> getRestaurantById(Long id) {
        return repository.findById(id);
    }

    @CacheEvict(value = "restaurants", allEntries = true)
    public Restaurant createRestaurant(Restaurant restaurant) {
        return repository.save(restaurant);
    }

    @CacheEvict(value = "restaurants", allEntries = true)
    public Optional<Restaurant> updateRestaurant(Long id, Restaurant updatedRestaurant) {
        return repository.findById(id).map(existing -> {
            existing.setName(updatedRestaurant.getName());
            existing.setAddress(updatedRestaurant.getAddress());
            return repository.save(existing);
        });
    }

    @CacheEvict(value = "restaurants", allEntries = true)
    public boolean deleteRestaurant(Long id) {
        return repository.findById(id).map(existing -> {
            repository.delete(existing);
            return true;
        }).orElse(false);
    }

    private void simulateLongProcessing() {
        try {
            Thread.sleep(10000); // Simulate 10s delay
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
