package com.trkgrn.chat.api.repository;

import com.trkgrn.chat.api.model.concretes.Friend;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FriendRepository extends JpaRepository<Friend,Long> {
}
