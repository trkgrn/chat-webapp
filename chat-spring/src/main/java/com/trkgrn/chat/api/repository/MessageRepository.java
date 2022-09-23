package com.trkgrn.chat.api.repository;

import com.trkgrn.chat.api.model.concretes.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message,Long> {

    public List<Message> findAllByChat_Name(String name);
}
