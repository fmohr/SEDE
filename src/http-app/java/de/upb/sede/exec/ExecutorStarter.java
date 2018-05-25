package de.upb.sede.exec;

import org.apache.commons.cli.Options;

import java.util.HashMap;
import java.util.Map;

public class ExecutorStarter {


	public static void main(String[] args) {
		ExecutorStarter starter = new ExecutorStarter();
		starter.parse(args);
	}

	private void parse(String[] args) {
		if(startExecutor(args)){

		}

	}

	private boolean startExecutor(String[] args) {
		Options startOptions = new Options();
		return true;
	}


}
