package edu.du.sb1014_2.entity;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.sql.Date;

@ToString
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "t_board")
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer boardIdx;

    private String title;

    private String contents;

    private Integer hitCnt;

    private String creatorId;

    private Date createdDatetime;

    private String updaterId;

    private Date updatedDatetime;

    private String deletedYn;
}
