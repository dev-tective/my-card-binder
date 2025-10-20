package org.gatodev.my_card_binder.tcg_data.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.gatodev.my_card_binder.enums.TCG;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "card_sets",
        uniqueConstraints = {
                @UniqueConstraint(name = "uc_cardset_name", columnNames = {"name"}),
                @UniqueConstraint(name = "uc_cardset_code", columnNames = {"code"})
        },
        indexes = {
                // Índice para búsquedas rápidas por TCG
                @Index(name = "idx_cardset_tcg", columnList = "tcg")
        }
)
public class CardSet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TCG tcg;

    @NotBlank(message = "Name cannot be blank")
    @Column(nullable = false)
    private String name;

    @NotBlank(message = "Name cannot be blank")
    @Column(nullable = false)
    private String code;

    private String description;

    @JdbcTypeCode(SqlTypes.ARRAY)
    private List<String> images;

    @Column(name = "release_date")
    private LocalDate releaseDate;
}
