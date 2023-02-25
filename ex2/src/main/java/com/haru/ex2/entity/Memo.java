package com.haru.ex2.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@ToString
@Table(name = "tbl_memo")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Memo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long mno;

    @Column(length = 200, nullable = false)
    private String memoText;
}
