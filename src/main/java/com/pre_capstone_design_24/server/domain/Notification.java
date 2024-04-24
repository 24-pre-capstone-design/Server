package com.pre_capstone_design_24.server.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String title;

    @NotNull
    private String content;

    @CreatedDate
    private LocalDateTime createdAt;

    @Column(name = "`read`", nullable = false)
    private boolean read = false;

}
