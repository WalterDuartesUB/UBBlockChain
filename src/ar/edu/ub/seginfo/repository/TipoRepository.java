package ar.edu.ub.seginfo.repository;

import java.util.Arrays;

public enum TipoRepository {
	RAM(0), MS_ACCESS(1);

	private final int value;

	TipoRepository(final int newValue) {
		value = newValue;
	}

	public int getValue() {
		return value;
	}

	@Override
	public String toString() {
		return String.valueOf(this.getValue());
	}

	public static TipoRepository valueOf(int value) {
		return Arrays.stream(values()).filter(tipoRepo -> tipoRepo.value == value).findFirst().get();
	}

}
