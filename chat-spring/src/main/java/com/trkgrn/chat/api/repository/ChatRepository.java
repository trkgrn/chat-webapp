package com.trkgrn.chat.api.repository;

import com.trkgrn.chat.api.model.concretes.Chat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatRepository extends JpaRepository<Chat,Long> {
   public Chat findByName(String name);
}
