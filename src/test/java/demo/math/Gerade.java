package demo.math;

import demo.types.Punkt;

public class Gerade {
	double achsenabschnitt, steigung;

	public Gerade(double achsenabschnitt, double steigung) {
		this.achsenabschnitt = achsenabschnitt;
		this.steigung = steigung;
	}

	public boolean liegtAufGerade(Punkt p) {
		return calc(p.x).y == p.y;
	}
	
	public Punkt calc(double stelle) {
		return new Punkt(stelle, steigung * stelle + achsenabschnitt);
	}

	public Punkt achsenabschnitt() {
		return new Punkt(0, achsenabschnitt);
	}
	
	public Punkt nullstelle() {
		return new Punkt(-achsenabschnitt/steigung, 0);
	}
}
