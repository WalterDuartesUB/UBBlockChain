package ar.edu.ub.seginfo.timestamping;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import ar.edu.ub.seginfo.cipher.hashgenerator.IHashedData;

public class TimestampingProviderLocalFile implements ITimestampingProvider {
	private String pathTSD;
	private ITimestampingProvider tsprovider;
	
	public TimestampingProviderLocalFile() {
		this.setPathTSD("timespampedhash.txt");
		this.setTsprovider( new TimestampingProviderSystem() );
	}
	
	@Override
	public long getTimeStamp(String hashToStamp) {
		return this.getTsprovider().getTimeStamp(hashToStamp);
	}

	@Override
	public IStampedHashedData stamp(IHashedData data) {
		IStampedHashedData tsdata = this.getTsprovider().stamp(data);
		
		//Grabo en un file el par hash, time stamp en formato CSV para poder verlo en excel
		String linea = String.format("%s,%d\r\n", tsdata.getHash(), tsdata.getTimestamp() );
				
		try {
			Files.write( Paths.get( this.getPathTSD() ), linea.getBytes(), StandardOpenOption.CREATE, StandardOpenOption.APPEND );
		} catch (IOException e) {
			
		}

		// devuelvo el registro marcado 		
		return tsdata;
	}

	private String getPathTSD() {
		return pathTSD;
	}

	private void setPathTSD(String pathTSD) {
		this.pathTSD = pathTSD;
	}

	private ITimestampingProvider getTsprovider() {
		return tsprovider;
	}

	private void setTsprovider(ITimestampingProvider tsprovider) {
		this.tsprovider = tsprovider;
	}

}
