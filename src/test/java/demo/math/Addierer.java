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
}
