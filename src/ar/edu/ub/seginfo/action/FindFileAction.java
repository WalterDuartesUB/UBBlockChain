package ar.edu.ub.seginfo.action;

import ar.edu.ub.seginfo.cipher.hashgenerator.IHashGenerator;
import ar.edu.ub.seginfo.controller.FindFileController;
import ar.edu.ub.seginfo.model.BlockChain;
import ar.edu.ub.seginfo.view.FindFileView;

public class FindFileAction {
	public static void actionPerformed(BlockChain blockChain, IHashGenerator hashGenerator) {
		FindFileView view = new FindFileView(hashGenerator);
		FindFileController controller = new FindFileController(blockChain, hashGenerator); 
		
		controller.setView(view);
		view.setController(controller);
		
		view.setVisible(true); 
		
	}
}
