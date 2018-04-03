package de.upb.sede.exec;

import java.util.concurrent.Semaphore;

public class ResourceAllocator {

	private static final Semaphore UNAVAILABLE_RESOURCE = new Semaphore(0);

	private int maxNumberFPGA, maxNumberGPU, maxNumberCPU;
	private Semaphore semFPGA = UNAVAILABLE_RESOURCE;
	private Semaphore semCPU = UNAVAILABLE_RESOURCE;
	private Semaphore semGPU = UNAVAILABLE_RESOURCE;

	public ResourceAllocator(AvailableResources resources) {
		if (resources.getFPGANumber() > 0) {
			semFPGA = new Semaphore(resources.getFPGANumber(), true);
			maxNumberFPGA = resources.getFPGANumber();
		}
		if (resources.getCPUNumber() > 0) {
			semCPU = new Semaphore(resources.getCPUNumber(), true);
			maxNumberCPU = resources.getCPUNumber();
		}
		if (resources.getGPUNumber() > 0) {
			semGPU = new Semaphore(resources.getGPUNumber(), true);
			maxNumberGPU = resources.getGPUNumber();
		}
	}

	public void aquireFPGA() {
		if (semFPGA != UNAVAILABLE_RESOURCE) {
			try {
				semFPGA.acquire();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public void aquireGPU() {
		if (semGPU != UNAVAILABLE_RESOURCE) {
			try {
				semGPU.acquire();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public void aquireCPU() {
		if (semCPU != UNAVAILABLE_RESOURCE) {
			try {
				semCPU.acquire();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public void releaseFPGA() {
		if (semFPGA != UNAVAILABLE_RESOURCE && semFPGA.availablePermits() < maxNumberFPGA)
			semFPGA.release();
	}

	public void releaseGPU() {
		if (semGPU != UNAVAILABLE_RESOURCE && semGPU.availablePermits() < maxNumberGPU)
			semGPU.release();
	}

	public void releaseCPU() {
		if (semCPU != UNAVAILABLE_RESOURCE && semFPGA.availablePermits() < maxNumberCPU)
			semCPU.release();
	}
}
