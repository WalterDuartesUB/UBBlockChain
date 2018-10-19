package ar.edu.ub.seginfo.action;

public enum TipoTSP {
	SYSTEM_TIME(0),
	SYSTEM_TIME_IN_FILE(1),
	TSA_SERVICE(2);
	
    private final int value;

	TipoTSP(final int newValue) {
        value = newValue;
    }

    public int getValue() { return value; }	

}
