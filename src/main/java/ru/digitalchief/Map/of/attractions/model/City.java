package ru.digitalchief.Map.of.attractions.model;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "cities")
public class City {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", unique = true)
    private String name;

    @Column(name = "population")
    private Long population;

    @Column(name = "area")
    private int area;

    @Column(name = "website")
    private String website;

    @OneToMany(mappedBy = "city", cascade = CascadeType.ALL)
    private Set<Attraction> attractions;



}
