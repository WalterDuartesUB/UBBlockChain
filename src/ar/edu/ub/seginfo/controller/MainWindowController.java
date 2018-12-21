package ar.edu.ub.seginfo.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;

import ar.edu.ub.seginfo.action.FindFileAction;
import ar.edu.ub.seginfo.cipher.hashgenerator.IHashGenerator;
import ar.edu.ub.seginfo.cipher.hashgenerator.IHashedData;
import ar.edu.ub.seginfo.exception.HashGeneratorException;
import ar.edu.ub.seginfo.model.BlockChain;
import ar.edu.ub.seginfo.view.MainWindowView;

public class MainWindowController {
	private MainWindowView view;
	private BlockChain blockChain;
	private IHashGenerator hashGenerator;
	private List<IModelListener> modelListeners;

	public MainWindowController(BlockChain blockChain, IHashGenerator hashGenerator) {
		this.setBlockChain(blockChain);
		this.setHashGenerator(hashGenerator);

		this.setModelListeners(new LinkedList<IModelListener>());
	}

	public MainWindowView getView() {
		return view;
	}

	public void setView(MainWindowView view) {
		this.view = view;
	}

	public void addModelListener(IModelListener ml) {
		this.getModelListeners().add(ml);
	}

	public void uploadFile(String filePath) {

		try {

			IHashedData hashedData = this.getHashedData(filePath);
			// Lo agrego a la blockchain
			this.getBlockChain().addBlock( hashedData );

			this.getView().showSuccessBlock( hashedData.getHashAsString() );
			
			// informo que se agrego un block en la blockchain a los observadores
			this.dispatchModelUpdate();

		} catch (Exception e) {
			// Si algo salio mal, muestro el mensaje
			this.getView().showError(e.getMessage());
		}
	}

	private IHashedData getHashedData(String filePath) throws HashGeneratorException {
		try {
			return this.getHashGenerator().hash(Files.readAllBytes(Paths.get(filePath)));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	private void dispatchModelUpdate() {
		for (IModelListener ml : this.getModelListeners())
			ml.update();
	}

	private BlockChain getBlockChain() {
		return blockChain;
	}

	private void setBlockChain(BlockChain blockChain) {
		this.blockChain = blockChain;
	}

	private IHashGenerator getHashGenerator() {
		return hashGenerator;
	}

	private void setHashGenerator(IHashGenerator hashGenerator) {
		this.hashGenerator = hashGenerator;
	}

	private List<IModelListener> getModelListeners() {
		return modelListeners;
	}

	private void setModelListeners(List<IModelListener> modelListeners) {
		this.modelListeners = modelListeners;
	}

	public void findFile() {
		FindFileAction.actionPerformed( this.getBlockChain(), this.getHashGenerator() );		
	}

	public void exit() {
		System.exit(0);		
	}

}
