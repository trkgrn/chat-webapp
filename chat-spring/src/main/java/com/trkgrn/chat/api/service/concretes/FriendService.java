package com.trkgrn.chat.api.service.concretes;

import com.trkgrn.chat.api.model.concretes.Friend;
import com.trkgrn.chat.api.repository.FriendRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FriendService {
    private final FriendRepository friendRepository;

    public FriendService(FriendRepository friendRepository) {
        this.friendRepository = friendRepository;
    }

    public Friend addFriend(Friend friend){
        return this.friendRepository.save(friend);
    }

}
