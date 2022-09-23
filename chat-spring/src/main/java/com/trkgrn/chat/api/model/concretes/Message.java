package com.trkgrn.chat.api.model.concretes;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Table(schema = "public",name = "message")
public class Message {
    @Id
    @Column(name = "message_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "sender")
    private String sender;

    @Column(name = "t_stamp")
    private String t_stamp;

    @Column(name = "content")
    private String content;

    @ManyToOne()
    @JoinColumn(name = "chat_id")
    private Chat chat;
}
