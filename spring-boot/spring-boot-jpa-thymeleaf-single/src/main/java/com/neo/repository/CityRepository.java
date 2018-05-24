package com.neo.repository;

import com.neo.entity.City;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CityRepository extends JpaRepository<City, Long> {

    @Query("select u from City u")
    Page<City> findList(Pageable pageable);

    City findById(long id);

    City findByName(String name);

    Long deleteById(Long id);
}