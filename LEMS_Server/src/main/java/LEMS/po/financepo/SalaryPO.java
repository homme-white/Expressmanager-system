package LEMS.po.financepo;

import java.io.Serializable;

@SuppressWarnings("serial")
public class SalaryPO implements Serializable {

	private String id;
	private String institution;
	private String name;
	private double salary;

	public SalaryPO(String id, String institution, String name, double salary) {
		this.id = id;
		this.institution = institution;
		this.name = name;
		this.salary = salary;
	}

	public String getId() {
		return id;
	}

	public String getInstitution() {
		return institution;
	}

	public String getName() {
		return name;
	}

	public double getSalary() {
		return salary;
	}
}
