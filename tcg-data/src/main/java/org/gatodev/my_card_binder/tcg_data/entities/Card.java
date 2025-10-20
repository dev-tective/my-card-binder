package org.gatodev.my_card_binder.tcg_data.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.gatodev.my_card_binder.enums.TCG;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "cards", indexes = {
        // 1. Índice para búsquedas por nombre (crucial)
        @Index(name = "idx_card_name", columnList = "name"),
        // 2. Índice para ordenar por popularidad/deseo
        @Index(name = "idx_card_wanted", columnList = "wanted"),
        // 3. Índice compuesto para búsquedas comunes por set y código
        @Index(name = "idx_card_set_code", columnList = "card_set_id, code")
})
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @NotBlank(message = "Name cannot be blank")
    private String name;

    @Column(nullable = false)
    @NotBlank(message = "Code cannot be blank")
    private String code;

    @Column(nullable = false)
    private String rarity;

    @Column(name = "rarity_code")
    private String rarityCode;

    @Column(nullable = false, updatable = false)
    @Enumerated(EnumType.STRING)
    private TCG tcg;

    @Column(nullable = false)
    @NotBlank(message = "Type cannot be blank")
    private String type;

    @JdbcTypeCode(SqlTypes.ARRAY)
    private List<String> subtypes;

    private String archetype;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(columnDefinition = "TEXT", name = "details_url")
    private String detailsUrl;

    @Column(columnDefinition = "TEXT")
    private String image;

    private long wanted;

    @ManyToOne
    @JoinColumn(name = "card_set_id")
    private CardSet cardSet;
}