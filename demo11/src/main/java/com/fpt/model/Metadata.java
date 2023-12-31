package com.fpt.model;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "metadata")
public class Metadata {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    // mã icon
    private String icon;
    @Column(columnDefinition = "VARCHAR(1000)")
    private String title;
    @Column(columnDefinition = "VARCHAR(1000)")
    private String content;
    @ManyToOne()
    @JoinColumn(name = "laptop_id", nullable = false)
    private Laptop laptop;
    @ManyToOne
    @JoinColumn(name = "group_id", nullable = false)
    private MetadataGroup metadataGroup;
}
