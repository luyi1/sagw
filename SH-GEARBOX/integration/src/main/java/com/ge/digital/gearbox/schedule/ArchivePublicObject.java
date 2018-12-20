package com.ge.digital.gearbox.schedule;

/**
 * 归档公用类
 * 统计总的线程数和已经完成的线程数
 * @author jquer
 *
 */
public class ArchivePublicObject {

	int totalThreadCount;
	int finishedThreadCount;

	public int getTotalThreadCount() {
		return totalThreadCount;
	}

	public void setTotalThreadCount(int totalThreadCount) {
		this.totalThreadCount = totalThreadCount;
	}

	public int getFinishedThreadCount() {
		return finishedThreadCount;
	}

	public synchronized int finishedThreadCount() {
		this.finishedThreadCount++;
		return this.finishedThreadCount;
	}

}
