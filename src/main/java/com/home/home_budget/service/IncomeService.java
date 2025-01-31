package com.home.home_budget.service;

import com.home.home_budget.Model.Income;
import com.home.home_budget.repository.IncomeRepository;
import org.springframework.stereotype.Service;

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

//    public List<Income> getIncomeByMonth(String month) {
//        return incomeRepository.findByMonth(month);
//    }
//
//    public List<Income> getIncomesByCategory(String category) {
//        return incomeRepository.findByCategoryName(category);
//    }
//
//    public List<Income> getIncomesByYearAndMonth(int year, String month) {
//        return incomeRepository.findByYearAndMonth(year, month);
//    }
//
//    public List<Income> getIncomeByYearAndCategory(int year, String category) {
//        return incomeRepository.findByYearAndCategory(year, category);
//    }
//
//    public  List<Income> getIncomesByMonthAndCategory(String monthName, String category) {
//        return incomeRepository.findByMonthAndCategory(monthName, category);
//    }
//
//    public List<Income> getIncomesByYearMonthAndCategory (int year, String month, String category) {
//        return incomeRepository.findByYearMonthAndCategory(year, month, category);
//    }

    public Income updateIncome(Income income) {
        return incomeRepository.save(income);
    }

    public void deleteIncome(Long id) {
        incomeRepository.deleteById(id);
    }
}
