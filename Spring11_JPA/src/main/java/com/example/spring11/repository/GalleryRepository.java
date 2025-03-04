package com.example.spring11.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.spring11.entity.Gallery;

public interface GalleryRepository extends JpaRepository<Gallery, Long> {

}
