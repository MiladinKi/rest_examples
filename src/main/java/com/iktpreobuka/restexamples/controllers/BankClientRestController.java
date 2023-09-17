package com.iktpreobuka.restexamples.controllers;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.iktpreobuka.restexamples.entities.BankClientBean;
import com.iktpreobuka.restexamples.entities.BankClientNames;

@RestController
@RequestMapping("/bankclients")
public class BankClientRestController {

	private List<BankClientBean> clients = new ArrayList<>();

	@RequestMapping(method = RequestMethod.GET)
	public List<BankClientBean> getDB() {
		List<BankClientBean> clients = new ArrayList<BankClientBean>();
		clients.add(new BankClientBean(1, "Miladin", "Kovacevic", "miladinkovacevic@hotmail.com",
				LocalDate.of(1981, 8, 2), "Kikinda"));
		clients.add(
				new BankClientBean(2, "Tanja", "Krstin", "tanjakrstin@yahoo.com", LocalDate.of(1984, 6, 28), "Mokrin"));
		return clients;
	}

	@RequestMapping(method = RequestMethod.GET, value = ("/{clientId}"))
	public BankClientBean getByID(@PathVariable String clientId) {
		for (BankClientBean bc : getDB()) {
			if (bc.getId().equals(Integer.parseInt(clientId))) {
				return bc;
			}

		}
		return new BankClientBean();
	}

	@RequestMapping(method = RequestMethod.POST)
	public String add(@RequestBody BankClientBean client) {
		System.out.println(client.getName() + " " + client.getSurname());
		return "New client added";
	}

	@RequestMapping(method = RequestMethod.PUT, value = ("/{clientId}"))
	public BankClientBean modify(@RequestBody BankClientBean client, @PathVariable Integer clientId) {
		for (BankClientBean bc : getDB()) {
			if (bc.getId() == clientId) {
				bc.setName(client.getName());
				bc.setSurname(client.getSurname());
				bc.setEmail(client.getEmail());
				return bc;
			}
		}
		return null;
	}

	@RequestMapping(method = RequestMethod.DELETE, value = ("/{clientId}"))
	public BankClientBean delete(@PathVariable Integer clientId) {
		for (BankClientBean bc : getDB()) {
			if (bc.getId() == clientId) {
				getDB().remove(clientId);
				return bc;
			}
		}
		return new BankClientBean();
	}

	@RequestMapping(method = RequestMethod.GET, value = "/{name}/{surname}")
	public BankClientBean getByNameAndSurname(@PathVariable String name, @PathVariable String surname) {
		for (BankClientBean bc : getDB()) {
			if ((bc.getName().equals(name)) && bc.getSurname().equals(surname)) {
				return bc;
			}
		}
		return new BankClientBean();
	}

	@RequestMapping(method = RequestMethod.GET, value = ("/email"))
	public List<String> getEmails() {
		List<String> emails = new ArrayList<String>();
		for (BankClientBean clients : getDB()) {
			emails.add(clients.getEmail());
		}
		return emails;
	}

	@RequestMapping(method = RequestMethod.GET, value = ("/clients/{firstLetter}"))
	public List<String> getLetter(@PathVariable String firstLetter) {
		List<String> letterName = new ArrayList<String>();
		for (BankClientBean clients : getDB()) {
			if (clients.getName().toLowerCase().startsWith(firstLetter)) {
				letterName.add(clients.getName());
				return letterName;
			}
		}
		return null;
	}

	@RequestMapping(method = RequestMethod.GET, value = ("/clients/firstLetters"))
	public List<BankClientNames> getFirstLetter(@RequestParam String firstLetterName,
			@RequestParam String firstLetterSurname) {
		List<BankClientNames> clients = new ArrayList<BankClientNames>();
		for (BankClientBean client : getDB()) {
			if ((client.getName().toLowerCase().startsWith(firstLetterName))
					&& (client.getSurname().toLowerCase().startsWith(firstLetterSurname))) {
				clients.add(new BankClientNames(client.getName(), client.getSurname()));
				return clients;
			}
		}
		return null;
	}

	@RequestMapping(method = RequestMethod.GET, value = ("/clients/sort/{order}"))
	public List<String> sortNameClients(@PathVariable String order) {
		List<String> clients = new ArrayList<>();
		for (BankClientBean client : getDB()) {
			clients.add(client.getName());
			if (order.equals("asc")) {
				clients.sort((o1, o2) -> o1.compareTo(o2));
			} else if (order.equals("desc")) {
				clients.sort((o1, o2) -> o2.compareTo(o1));
			}
		}
		return clients;
	}

	@RequestMapping(method = RequestMethod.GET, value = ("/clients/bonitet"))
	public List<BankClientBean> clientBonitet() {
		List<BankClientBean> clientsList = getDB();
		LocalDate danas = LocalDate.now();
		for (BankClientBean clients : clientsList) {
			if (ChronoUnit.YEARS.between(clients.getDatumR(), danas) > 65) {
				clients.setBonitet("N");
			} else {
				clients.setBonitet("P");
			}
		}
		return clientsList;

	}

	@RequestMapping(method = RequestMethod.DELETE, value = ("/clients/delete"))
	public List<BankClientBean> deleteClient() {
		List<BankClientBean> clientsDelete = getDB();
		for (BankClientBean client : clientsDelete) {
			if ((client.getName() == null) && (client.getSurname() == null) && (client.getEmail() == null)) {
				clientsDelete.remove(client);
			}
		}
		return clientsDelete;
	}

	@RequestMapping(method = RequestMethod.GET, value = ("/clients/countLess/{years}"))
//	public String brojKlijenata(@PathVariable Integer years) {
	public Integer brojKlijenata(@PathVariable Integer years) {
		int razlika;
		int brojac = 0;
		LocalDate datum = LocalDate.now();
		for (BankClientBean clients : getDB()) {
			razlika = (int) ChronoUnit.YEARS.between(clients.getDatumR(), datum);
			if (razlika < years) {
				brojac++;
			}
		}
		return brojac;
//		return "Ukupan broj klijenata koji imaju manje od " + years + " godina je: " + brojac;
	}

	@RequestMapping(method = RequestMethod.GET, value = ("/clients/averageYears"))
	public Double prosecanBrojGodina() {
		int suma = 0;
		int godina;
		int brojKlijenata = getDB().size();
		double prosecnaStarost;
		LocalDate danas = LocalDate.now();
		for (BankClientBean clients : getDB()) {
			godina = (int) ChronoUnit.YEARS.between(clients.getDatumR(), danas);
			suma += godina;
		}
		prosecnaStarost = suma / brojKlijenata;
		return prosecnaStarost;
	}

	@RequestMapping(method = RequestMethod.PUT, value = ("/clients/changeLocation/{clientId}"))
	public BankClientBean noviGrad(@PathVariable Integer clientId, @RequestParam String newLocation) {
		for (BankClientBean clients : getDB()) {
			if (clients.getId() == clientId) {
				clients.setGrad(newLocation);
				return clients;
			}
		}
		return new BankClientBean();
	}

	@RequestMapping(method = RequestMethod.GET, value = ("/clients/from/{city}"))
	public List<BankClientBean> fromCity(@PathVariable String city) {
		List<BankClientBean> clientsCity = new ArrayList<>();
		for (BankClientBean client : getDB()) {
			if (client.getGrad().equalsIgnoreCase(city)) {
				clientsCity.add(client);
				return clientsCity;
			}
		}
		return null;
	}

	@RequestMapping(method = RequestMethod.GET, value = ("/clients/findByCityAndAge"))
	public List<BankClientBean> findByCityAndAge(@RequestParam String city, @RequestParam Integer brojGodina) {
		LocalDate danas = LocalDate.now();
		List<BankClientBean> clients = new ArrayList<>();
		for (BankClientBean client : getDB()) {
			if ((client.getGrad().equalsIgnoreCase(city))
					&& (brojGodina < (int) ChronoUnit.YEARS.between(client.getDatumR(), danas))) {
				clients.add(client);
			}
		}
		return clients;
	}

}
