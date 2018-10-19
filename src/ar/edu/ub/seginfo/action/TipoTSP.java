package ar.edu.ub.seginfo.action;

import java.util.Arrays;
import java.util.Optional;

public enum TipoTSP {
	SYSTEM_TIME(0),
	SYSTEM_TIME_IN_FILE(1),
	TSA_SERVICE(2);
	
    private final int value;    

	TipoTSP(final int newValue) {
        value = newValue;
    }

    public int getValue() { return value; }	

    
    @Override
    public String toString() {    	
    	return String.valueOf( this.getValue() );
    }
    
    public static TipoTSP valueOf(int value) {
        return Arrays.stream(values())
            .filter(tipoTSP -> tipoTSP.value == value)
            .findFirst().get();
    }
}
