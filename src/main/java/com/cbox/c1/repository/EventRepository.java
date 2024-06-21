package com.cbox.c1.repository;

import com.cbox.c1.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<Event,Integer> {

    
}
