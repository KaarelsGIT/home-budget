package com.home.home_budget.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "incomes")
@NoArgsConstructor
public class Income  extends Transaction<Income> {
}
