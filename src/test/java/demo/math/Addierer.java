package demo.math;

import java.io.Serializable;

import demo.types.NummerList;


public class Addierer implements Serializable {
	private static final long serialVersionUID = 1L;
	final double basisZahl;

	public Addierer(double basisZahl) {
		this.basisZahl = basisZahl;
	}

	public double addier(double summand) {
		return basisZahl + summand;
	}

	public NummerList addierListe(NummerList nummerListe) {
		NummerList sumList = new NummerList();
		for (double summand : nummerListe) {
			sumList.add(addier(summand));
		}
		return sumList;
	}

	public NummerList summierListe(NummerList nl1, NummerList nl2) {
		NummerList sumList = new NummerList();
		for (int i = 0, size = Math.min(nl1.size(), nl2.size()); i < size; i++) {
			sumList.add(nl1.get(i) + nl2.get(i));
		}
		return sumList;
	}
}
