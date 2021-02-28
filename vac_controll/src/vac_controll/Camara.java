package vac_controll;

import vac_controll.Localizacao;
import vac_controll.Vacina;
import java.util.List;

public class Camara implements Cloneable {
	private Localizacao loc;
	private List<Vacina> vacinas;
	private double temperatura;
	
	public Camara(Localizacao loc, List<Vacina> vacinas, double temperatura) {
		super();
		this.loc = loc;
		this.vacinas = vacinas;
		this.temperatura = temperatura;
	}

	public Localizacao getLoc() {
		return loc;
	}

	public void setLoc(Localizacao loc) {
		this.loc = loc;
	}

	public List<Vacina> getVacinas() {
		return vacinas;
	}

	public void setVacinas(List<Vacina> vacinas) {
		this.vacinas = vacinas;
	}

	public double getTemperatura() {
		return temperatura;
	}

	public void setTemperatura(double temperatura) {
		this.temperatura = temperatura;
	}
	
	public Object clone() throws CloneNotSupportedException {		
        return super.clone();
    }
}
