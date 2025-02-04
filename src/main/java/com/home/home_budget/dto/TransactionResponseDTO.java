package com.home.home_budget.dto;

import com.home.home_budget.Model.Transaction;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.domain.Page;

import java.math.BigDecimal;

@AllArgsConstructor
@Data
public class TransactionResponseDTO<T extends Transaction<T>> {

    private Page<T> transactionPage;
    private BigDecimal totalAmount;

}
