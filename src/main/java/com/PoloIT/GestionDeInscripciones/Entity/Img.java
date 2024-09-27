package com.PoloIT.GestionDeInscripciones.Entity;


import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Getter
@Entity
public class Img {

    @Id
    private Long id;

    @OneToOne
    @MapsId
    @JoinColumn(name = "event_id")
    private Event event;
    private String imageFileName;
    
    @Column(columnDefinition = "BINARY(16)")
    private UUID uuid;

}
