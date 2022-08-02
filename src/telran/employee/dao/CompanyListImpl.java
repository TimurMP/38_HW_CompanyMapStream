package telran.employee.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import telran.employee.model.Employee;
import telran.employee.model.SalesManager;

public class CompanyListImpl implements Company {
	List<Employee> employees;
	int capacity;

	public CompanyListImpl(int capacity) {
		employees = new ArrayList<Employee>();
		this.capacity = capacity;
	}

	@Override
	public boolean addEmployee(Employee employee) {
		if (employee == null || employees.size() == capacity || findEmployee(employee.getId()) != null) {
			return false;
		}
		employees.add(employee);
		return true;
	}

	@Override
	public Employee removeEmployee(int id) {
		Employee removed = findEmployee(id);
		if (removed != null) {
			employees.remove(removed);
		}
		return removed;
	}

	@Override
	public Employee findEmployee(int id) {
		for (Employee employee : employees) {
			if (employee.getId() == id) {
				return employee;
			}
		}
		return null;
	}

	@Override
	public double totalSalary() {
		double sum = 0;
		for (Employee employee : employees) {
			sum += employee.calcSalary();
		}
		return sum;
	}

	@Override
	public double totalSales() {
		double totalSales = 0;
		for (Employee employee : employees) {
			if (employee instanceof SalesManager) {
				SalesManager sm = (SalesManager) employee;
				totalSales += sm.getSalesValue();
			}
		}
		return totalSales;
	}

	@Override
	public void printEmployees() {
		for (Employee employee : employees) {
			System.out.println(employee);
		}
	}

	@Override
	public int getSize() {
		return employees.size();
	}

	@Override
	public Employee[] findEmployeesHoursGreaterThan(int hours) {
		return findEmployeesByPredicate(e -> e.getHours() >= hours);
	}

	@Override
	public Employee[] findEmployeesSalaryBetween(double min, double max) {
		Predicate<Employee> predicate = new Predicate<>() {

			@Override
			public boolean test(Employee t) {
				return t.calcSalary() >= min && t.calcSalary() < max;
			}
		};
		return findEmployeesByPredicate(predicate);
	}
	
	private Employee[] findEmployeesByPredicate(Predicate<Employee> predicate) {
		ArrayList<Employee> res = new ArrayList<Employee>();
		for (Employee employee : employees) {
			if(predicate.test(employee)) {
				res.add(employee);
			}
		}
		return res.toArray(new Employee[res.size()]);
	}
}
