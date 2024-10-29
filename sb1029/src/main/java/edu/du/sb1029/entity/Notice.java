package edu.du.sb1029.entity;

import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;

@Entity
@Table(name = "t_notice")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Notice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String title;

    private String content;

    @ColumnDefault("0") //default 0
    private Integer hitCnt;

    private String creatorId;

    private String createdDatetime;

    private String updaterId;

    private String updatedDatetime;

    @Column(columnDefinition = "varchar(2) default 'N'")
    private String deletedYn;
}
