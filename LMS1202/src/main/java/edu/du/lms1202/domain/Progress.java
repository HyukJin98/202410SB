package edu.du.lms1202.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Progress {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "enrollment_id")
    private Enrollment enrollment;
    
    @ManyToOne
    @JoinColumn(name = "content_id")
    private Content content;
    
    private boolean completed;
    private LocalDateTime completedAt;
    private double progressRate;
} 