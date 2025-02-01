package com.home.home_budget.service;

import com.home.home_budget.Model.Category;
import com.home.home_budget.Model.Income;
import com.home.home_budget.Model.User;
import com.home.home_budget.repository.IncomeRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
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

    public Income updateIncome(Income income) {
        return incomeRepository.save(income);
    }

    public void deleteIncome(Long id) {
        incomeRepository.deleteById(id);
    }

    public List<Income> getFilteredAndSortedIncomes(User user,
                                                    LocalDate date,
                                                    Category category,
                                                    String sortBy,
                                                    String sortOrder,
                                                    Integer year
    ) {
        if (sortBy == null)
            sortBy = "date";

        if (sortOrder == null)
            sortOrder = "desc";

        Sort.Direction direction;
        if (sortOrder.equalsIgnoreCase("desc")) {
            direction = Sort.Direction.DESC;
        } else {
            direction = Sort.Direction.ASC;
        }

        Sort sort = Sort.by(direction, sortBy);

        if (year != null) {
            return getFilteredAndSortedIncomesByYear(user, category, year, sort);
        } else {
            return getFilteredAndSortedIncomesWithoutYear(user, date, category, sort);
        }
    }

    private List<Income> getFilteredAndSortedIncomesByYear(User user, Category category, Integer year, Sort sort) {
        if (user != null && category != null) {
            return incomeRepository.findByUserAndYearAndCategory(user, year, category, sort);
        } else if (user != null) {
            return incomeRepository.findByUserAndYear(user, year, sort);
        } else if (category != null) {
            return incomeRepository.findByCategoryAndYear(category, year, sort);
        } else {
            return incomeRepository.findByYear(year, sort);
        }
    }

    private List<Income> getFilteredAndSortedIncomesWithoutYear(User user, LocalDate date, Category category, Sort sort) {
        if (user != null && date != null && category != null) {
            return incomeRepository.findByUserAndDateAndCategoryId(user, date, category, sort);
        } else if (user != null && date != null) {
            return incomeRepository.findByUserAndDate(user, date, sort);
        } else if (user != null && category != null) {
            return incomeRepository.findByUserAndCategory(user, category, sort);
        } else if (date != null && category != null) {
            return incomeRepository.findByDateAndCategory(date, category, sort);
        } else if (user != null) {
            return incomeRepository.findByUser(user, sort);
        } else if (date != null) {
            return incomeRepository.findByDate(date, sort);
        } else if (category != null) {
            return incomeRepository.findByCategory(category, sort);
        } else {
            return incomeRepository.findAll(sort);
        }
    }

    public BigDecimal getTotalIncomeAmount(List<Income> incomes) {
        return incomes.stream()
                .map(Income::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
