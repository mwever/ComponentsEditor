package Entity;

import java.util.List;

public class Cat implements ParamType {
	private List<Kitten> kittens;

	public List<Kitten> getKittens() {
		return kittens;
	}

	public void setKittens(List<Kitten> kittens) {
		this.kittens = kittens;
	}

	public Cat(List<Kitten> kittens) {
		super();
		this.kittens = kittens;
	}
	public Cat() {
		
	}
}
