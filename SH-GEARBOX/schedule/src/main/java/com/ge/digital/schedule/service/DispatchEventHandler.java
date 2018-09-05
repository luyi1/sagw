package com.ge.digital.schedule.service;

import com.jacob.com.Variant;

public class DispatchEventHandler {
	public void SimulationFinished(Variant[] i) {

		System.out.println("this is java event executed " + i[0]);

	}

	public void FireSimTalkMessage(Variant[] i) {

		System.out.println("this is java event executed " + i[0]);

	}
}
