package telran.employee.dao;

import telran.employee.model.Employee;
import telran.employee.model.SalesManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;

public class CompanyStreamImpl implements Company {
	Map<Integer, Employee> employees;
	int capacity;

	public CompanyStreamImpl(int capacity) {
		employees = new HashMap<Integer, Employee>();
		this.capacity = capacity;
	}

	@Override
	public boolean addEmployee(Employee employee) {
		if (employee == null || employees.size() == capacity) {
			return false;
		}
		return employees.putIfAbsent(employee.getId(), employee) == null;
	}

	// O(1)
	@Override
	public Employee removeEmployee(int id) {
		return employees.remove(id);
	}

	// O(1)
	@Override
	public Employee findEmployee(int id) {
		return employees.get(id);
	}

	// O(n)
	@Override
	public double totalSalary() {
//		return employees.values().stream()
//				.mapToDouble(x -> x.calcSalary())
//				.reduce(0, (a ,b) -> a + b);
		return employees.values().stream()
				.map(x -> x.calcSalary())
				.reduce(0D, (a ,b) -> a + b);

	}

	@Override
	public double totalSales() {

		return employees.values().stream()
				.filter(x -> x instanceof SalesManager)
				.map(x -> ((SalesManager) x).getSalesValue())
				.reduce(0D, (a, b) -> a + b);

	}

	@Override
	public void printEmployees() {
		employees.values().stream()
				.forEach(System.out::println);

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
		employees.values().stream()
				.filter(predicate)
				.forEach(x -> res.add(x));

		return res.toArray(new Employee[res.size()]);


	}

}
