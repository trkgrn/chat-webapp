package com.trkgrn.chat.api.repository;

import com.trkgrn.chat.api.model.concretes.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageRepository extends JpaRepository<Image,Long> {
    Image findByName(String name);
    Image findByImageId(Long id);
}
