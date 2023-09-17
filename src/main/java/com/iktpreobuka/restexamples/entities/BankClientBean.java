package com.iktpreobuka.restexamples.entities;

import java.time.LocalDate;

public class BankClientBean {
	private Integer id;
	private String name;
	private String surname;
	private String email;
	private LocalDate datumR;
	private String bonitet;
	private String grad;

	public BankClientBean() {
	}

	public BankClientBean(Integer id, String name, String surname, String email, LocalDate datumR,
			String grad) {
		super();
		this.id = id;
		this.name = name;
		this.surname = surname;
		this.email = email;
		this.datumR = datumR;
//		this.bonitet = bonitet;
		this.grad = grad;
	}

	public LocalDate getDatumR() {
		return datumR;
	}

	public void setDatumR(LocalDate datumR) {
		this.datumR = datumR;
	}

	public String getBonitet() {
		return bonitet;
	}

	public void setBonitet(String bonitet) {
		this.bonitet = bonitet;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getGrad() {
		return grad;
	}

	public void setGrad(String grad) {
		this.grad = grad;
	}

}
