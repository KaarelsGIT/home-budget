package com.home.home_budget.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "expenses")
@NoArgsConstructor
public class Expense extends Transaction<Expense> {
}
