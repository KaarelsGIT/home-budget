package com.home.home_budget.service;

import com.home.home_budget.Model.Income;
import com.home.home_budget.repository.IncomeRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class IncomeService {

    private final IncomeRepository incomeRepository;

    public IncomeService(IncomeRepository incomeRepository) {
        this.incomeRepository = incomeRepository;
    }

    public Income addIncome(Income income) {
        return incomeRepository.save(income);
    }

    public Optional<Income> getIncomeById(Long id) {
        return incomeRepository.findById(id);
    }

    public List<Income> getAllIncomes() {
        return incomeRepository.findAll();
    }

    public List<Income> getIncomesByYear(int year) {
        return incomeRepository.findByYear(year);
    }

    public Income updateIncome(Income income) {
        return incomeRepository.save(income);
    }

    public void deleteIncome(Long id) {
        incomeRepository.deleteById(id);
    }

    public List<Income> getSortedIncomes(String setOrder, String filterBy) {
        // sortOrder = "asc" / "desc"
        // sortBy = "year" / "date" / "category" / "user"
        return incomeRepository.findAll(Sort.by(setOrder, filterBy));

    }

    public BigDecimal getTotalIncome() {
        return incomeRepository.findAll().stream()
                .map(Income::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
