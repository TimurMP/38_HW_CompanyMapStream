package telran.employee.dao;

import java.util.function.Predicate;

import telran.employee.model.Employee;
import telran.employee.model.SalesManager;

public class CompanyImpl implements Company {
	// Fields
	Employee[] employees;
	int size;

	public CompanyImpl(int capacity) {
		employees = new Employee[capacity];
	}

	@Override
	public boolean addEmployee(Employee employee) {
		if (size >= employees.length) {
			return false;
		}
		for (int i = 0; i < size; i++) {
			if (employees[i].equals(employee)) {
				return false;
			}
		}
		employees[size] = employee;
		size++;
		return true;
	}

	@Override
	public Employee removeEmployee(int id) {
		Employee temp;
		for (int i = 0; i < size; i++) {
			if (employees[i].getId() == id) {
				temp = employees[i];
				employees[i] = employees[size - 1];
				employees[size - 1] = null; // TODO (???)
				size--;
				return temp;
			}
		}
		return null;
	}

	@Override
	public Employee findEmployee(int id) {
		for (int i = 0; i < size; i++) {
			if (employees[i].getId() == id) {
				return employees[i];
			}
		}
		return null;
	}

	@Override
	public double totalSalary() {
		double total = 0;
		for (int i = 0; i < size; i++) {
			total += employees[i].calcSalary();
		}
		return total;
	}

	@Override
	public double totalSales() {
		double total = 0;
		for (int i = 0; i < size; i++) {
			if (employees[i] instanceof SalesManager) {
				total += ((SalesManager) employees[i]).getSalesValue();
			}
		}
		return total;
	}

	@Override
	public int getSize() {
		return size;
	}

	@Override
	public void printEmployees() {
		for (int i = 0; i < size; i++) {
			System.out.println(employees[i]);
		}
	}

//	public Employee[] findEmployeesSalaryBetween(double min, double max) {
//		int count = 0;
//		for (int i = 0; i < size; i++) {
//			if (min <= employees[i].calcSalary() && employees[i].calcSalary() < max) {
//				count++;
//			}
//		}
//		Employee[] emp = new Employee[count];
//		for (int i = 0, j = 0; j < emp.length; i++) {
//			if (min <= employees[i].calcSalary() && employees[i].calcSalary() < max) {
//				emp[j++] = employees[i];
//			}
//		}
//		return emp;
//	}

//	public Employee[] findEmployeesHoursGreaterThan(int hours) {
//		int count = 0;
//		for (int i = 0; i < size; i++) {
//			if (employees[i].getHours() >= hours) {
//				count++;
//			}
//		}
//		Employee[] emp = new Employee[count];
//		for (int i = 0, j = 0; j < emp.length; i++) {
//			if (employees[i].getHours() >= hours) {
//				emp[j++] = employees[i];
//			}
//		}
//		return emp;
//	}

	private Employee[] findEmployeesByPredicate(Predicate<Employee> predicate) {
		int count = 0;
		for (int i = 0; i < size; i++) {
			if (predicate.test(employees[i])) {
				count++;
			}
		}
		Employee[] emp = new Employee[count];
		for (int i = 0, j = 0; j < emp.length; i++) {
			if (predicate.test(employees[i])) {
				emp[j++] = employees[i];
			}
		}
		return emp;
	}

	public Employee[] findEmployeesHoursGreaterThan(int hours) {
		return findEmployeesByPredicate(e -> e.getHours() >= hours);
	}

//	public Employee[] findEmployeesSalaryBetween(double min, double max) {
//		return findEmployeesByPredicate(e -> min <= e.calcSalary() && e.calcSalary() < max);
//	}

	public Employee[] findEmployeesSalaryBetween(double min, double max) {
		Predicate<Employee> predicate = new Predicate<>() {

			@Override
			public boolean test(Employee t) {
				return t.calcSalary() >= min && t.calcSalary() < max;
			}
		};
		return findEmployeesByPredicate(predicate);
	}
}
