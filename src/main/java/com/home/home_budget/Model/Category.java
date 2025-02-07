package com.home.home_budget.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "categories")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "category_name", nullable = false)
    private String name;

    @Column(name = "type", nullable = false)
    @Enumerated(EnumType.STRING)
    private CategoryType type;

    @Column(name = "description")
    private String description;

    @Column(name = "recurring_payment")
    private boolean recurringPayment = false;

}
