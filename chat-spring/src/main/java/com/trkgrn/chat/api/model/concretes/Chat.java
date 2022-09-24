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
@Table(schema = "public",name = "chat")
public class Chat {
    @Id
    @Column(name = "chat_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long chatId;

    @Column(name = "chat_name")
    private String name;

    @OneToOne
    @JoinColumn(name = "origin_id",referencedColumnName = "user_id")
    private User origin;

    @OneToOne
    @JoinColumn(name = "destination_id",referencedColumnName = "user_id")
    private User destination;
}
