package de.upb.sede.exec;

public class AvailableResources {
	public static final AvailableResources UNDEFINED_RESOURCES = new AvailableResources();
	
	private int fpga, gpu, cpu;

	public int getFPGANumber() {
		return fpga;
	}

	public int getCPUNumber() {
		return cpu;
	}

	public int getGPUNumber() {
		return gpu;
	}

	public void setGPUNumber(int gpu) {
		this.gpu = gpu;
	}

	public void setFPGANumber(int fpga) {
		this.fpga = fpga;
	}

	public void setCPUNumber(int cpu) {
		this.cpu = cpu;
	}

	public boolean hasFPGA() {
		return fpga > 0;
	}

	public boolean hasGPU() {
		return gpu > 0;
	}

	public boolean hasCPU() {
		return cpu > 0;
	}
}
