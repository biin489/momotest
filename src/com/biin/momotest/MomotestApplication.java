package com.biin.momotest;

import com.biin.momotest.controller.SodaMachineController;
import com.biin.momotest.model.SodaMachine;
import com.biin.momotest.view.View;

public class MomotestApplication {

	public static void main(String[] args) {
		SodaMachineController sodaMachineController = new SodaMachineController(new View(), new SodaMachine());
		sodaMachineController.run();
	}

}
