package ar.edu.ub.seginfo.action;

import ar.edu.ub.seginfo.cipher.hashgenerator.IHashGenerator;
import ar.edu.ub.seginfo.controller.FindFileController;
import ar.edu.ub.seginfo.model.BlockChain;
import ar.edu.ub.seginfo.view.FindFileView;
import ar.edu.ub.seginfo.view.MainWindowView;

public class FindFileAction {
	public static void actionPerformed(BlockChain blockChain, IHashGenerator hashGenerator, MainWindowView mainWindowView) {
		FindFileView view = new FindFileView( mainWindowView );
		FindFileController controller = new FindFileController(blockChain, hashGenerator); 
		
		controller.setView(view);
		view.setController(controller);
		
		view.setVisible(true); 
		
	}
}
