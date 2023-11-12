package pl.edu.pw.mini.zpoif.task3.solution;

import pl.edu.pw.mini.zpoif.task3.generator.GeneratorStatkow;
import pl.edu.pw.mini.zpoif.task3.model.StatekNawodny;
import pl.edu.pw.mini.zpoif.task3.model.jacht.*;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Implementation implements Statki1 {
	List<StatekNawodny> statki;

	public Implementation() {
		statki = GeneratorStatkow.generujStatkiNawodne();
	}

	@Override
	public StatekNawodny getNajciezszyStatek() {
		return statki.stream()
			.max(Comparator.comparing(StatekNawodny::getMasa)).get();
	}

	@Override
	public StatekNawodny getStatekONajdluzszejNazwieProducentaNaR() {
		return statki.stream()
			.filter(statekNawodny -> statekNawodny.getNazwaProducenta().startsWith("R"))
			.max(Comparator.comparing(statekNawodny -> ((StatekNawodny)statekNawodny).getNazwaProducenta().length())).get();
	}

	@Override
	public JachtMotorowy getJachtMotorowyONajwiekszejMocySilnika() {
		return (JachtMotorowy) statki.stream()
				.filter(statekNawodny -> statekNawodny instanceof JachtMotorowy)
				.max(Comparator.comparing(statekNawodny -> ((JachtMotorowy) statekNawodny).getMocSilnika()))
				.get();
	}

	@Override
	public JachtKabinowy getLekkiJachtKabinowyONajmniejszymOzaglowaniu() {
		return (JachtKabinowy) statki.stream()
				.filter(statekNawodny -> statekNawodny instanceof JachtKabinowy & statekNawodny.getMasa() <= 1000)
				.min(Comparator.comparing(statekNawodny -> ((JachtKabinowy) statekNawodny).getPowierzchniaZagla()))
				.get();
	}

	@Override
	public Set<StatekNawodny> getCoNajwyzej11DlugichICiezkichJachtow() {
		return statki.stream()
				.filter(statekNawodny -> statekNawodny.getMasa() >= 1400 & statekNawodny.getDlugosc() <= 700)
				.limit(11)
				.collect(Collectors.toSet());
	}

	@Override
	public List<StatekNawodny> getStatkiPosortowaneWzgledemDlugosciBez2() {
		return statki.stream()
				.skip(2)
				.sorted(Comparator.comparing(StatekNawodny::getDlugosc).reversed())
				.collect(Collectors.toList());
	}

	@Override
	public List<JachtZaglowy> getPierwsze8ZPosortowanychWzgledemOzaglowaniaBezTrzechPierwszych() {
		return statki.stream()
				.filter(statekNawodny -> statekNawodny instanceof JachtZaglowy)
				.map(JachtZaglowy.class::cast)
				.sorted(Comparator.comparing(JachtZaglowy::getPowierzchniaZagla).reversed())
				.skip(3)
				.limit(8)
				.collect(Collectors.toList());
	}

	@Override
	public void oznaczJachtyDobreNaPlycizny() {
		statki.stream()
				.filter(statekNawodny -> statekNawodny instanceof JachtKabinowy)
				.map(JachtKabinowy.class::cast)
				.filter(jachtKabinowy -> jachtKabinowy.getZanurzenie() <= 30 & jachtKabinowy.getMasa() <= 1300)
				.forEach(jachtKabinowy -> System.out.println("Jachtem " + jachtKabinowy.getTyp() + " wplyniesz na kazda plycizne!"));
	}

	@Override
	public String get12UnikalnychNazwTypow() {
		return statki.stream()
				.filter(statekNawodny -> statekNawodny instanceof Jacht & statekNawodny.getMasa() > 2000)
				.skip(3)
				.map(Jacht.class::cast)
				.map(Jacht::getTyp)
				.distinct()
				.limit(12)
				.collect(Collectors.joining("-"));
	}

	@Override
	public Map<String, JachtMotorowy> getMapaJachtowMotorowych() {
		return statki.stream()
				.filter(statekNawodny -> statekNawodny instanceof JachtMotorowy)
				.map(JachtMotorowy.class::cast)
				.collect(Collectors.toMap(
						JachtMotorowy::getTyp,
						jachtMotorowy -> jachtMotorowy,
						(jachtMotorowy1, jachtMotorowy2) ->
								jachtMotorowy1.getNazwaProducenta().length() > jachtMotorowy2.getNazwaProducenta().length() ? jachtMotorowy1 : jachtMotorowy2));
	}

	@Override
	public List<Jacht> getJachtyOdkrytopokladoweIMotoroweJednePoDrugich() {
		List<JachtOdkrytopokladowy> jachtyOdkrytopokladowe = statki.stream()
				.filter(statekNawodny -> statekNawodny instanceof JachtOdkrytopokladowy)
				.map(JachtOdkrytopokladowy.class::cast)
				.limit(10)
				.toList();

		List<JachtMotorowy> jachtyMotorowe = statki.stream()
				.filter(statekNawodny -> statekNawodny instanceof JachtMotorowy)
				.map(JachtMotorowy.class::cast)
				.filter(jachtMotorowy -> jachtMotorowy.getNazwaProducenta().equalsIgnoreCase("regal"))
				.skip(4)
				.limit(4)
				.toList();

		List<Jacht> result = new LinkedList<>();
		result.addAll(jachtyOdkrytopokladowe);
		result.addAll(jachtyMotorowe);
		return result;
	}
}
