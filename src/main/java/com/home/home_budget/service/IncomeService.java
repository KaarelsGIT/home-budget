package com.home.home_budget.service;

import com.home.home_budget.Model.Category;
import com.home.home_budget.Model.Income;
import com.home.home_budget.Model.User;
import com.home.home_budget.repository.IncomeRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

    public Page<Income> getPagedAndFilteredAndSortedIncomes(User user,
                                                            LocalDate date,
                                                            Category category,
                                                            String sortBy,
                                                            String sortOrder,
                                                            Integer year,
                                                            Pageable pageable
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
        pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), sort);

        if (year != null) {
            return getPagedAndFilteredAndSortedIncomesByYear(user, category, year, pageable);
        } else {
            return getFilteredAndSortedIncomesWithoutYear(user, date, category, pageable);
        }
    }

    private Page<Income> getPagedAndFilteredAndSortedIncomesByYear(User user, Category category, Integer year, Pageable pageable) {
        if (user != null && category != null) {
            return incomeRepository.findByUserAndYearAndCategory(user, year, category, pageable);
        } else if (user != null) {
            return incomeRepository.findByUserAndYear(user, year, pageable);
        } else if (category != null) {
            return incomeRepository.findByCategoryAndYear(category, year, pageable);
        } else {
            return incomeRepository.findByYear(year, pageable);
        }
    }

    private Page<Income> getFilteredAndSortedIncomesWithoutYear(User user, LocalDate date, Category category, Pageable pageable) {
        if (user != null && date != null && category != null) {
            return incomeRepository.findByUserAndDateAndCategoryId(user, date, category, pageable);
        } else if (user != null && date != null) {
            return incomeRepository.findByUserAndDate(user, date, pageable);
        } else if (user != null && category != null) {
            return incomeRepository.findByUserAndCategory(user, category, pageable);
        } else if (date != null && category != null) {
            return incomeRepository.findByDateAndCategory(date, category, pageable);
        } else if (user != null) {
            return incomeRepository.findByUser(user, pageable);
        } else if (date != null) {
            return incomeRepository.findByDate(date, pageable);
        } else if (category != null) {
            return incomeRepository.findByCategory(category, pageable);
        } else {
            return incomeRepository.findAll(pageable);
        }
    }

    public BigDecimal getTotalFilteredIncome(User user, LocalDate date, Category category, Integer year) {
        return incomeRepository.getTotalFilteredIncome(user, category, date, year);
    }
}
