package de.upb.sede.composition.gc;


import java.io.Serializable;
import java.util.Objects;

public class ServiceInstanceHandle implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/* default values */
	private static final String OWN_HOST = "home";

	private static final String not_serialized_id = "NULL";

	private static final String not_specified_classpath = "NO_CLASS_SPECIFIED";

	/**
	 *
	 */
	private final String host;
	private final String classpath;
	private final String id;

	/**
	   * Standard constructor
	   *
	   * @param id
	   *          Id which the service can be accessed through
	   * @param service
	   *          inner service
	   */
	  public ServiceInstanceHandle(final String host, final String classpath, final String id) {
	    super();
	    this.host = Objects.requireNonNull(host);
	    this.classpath = Objects.requireNonNull(classpath);
	    this.id = Objects.requireNonNull(id);
	  }

	/**
	   * Contructor for local host.
	   */
	  public ServiceInstanceHandle(final String classpath, final String id) {
	    this(OWN_HOST, classpath, id);
	  }

	/**
	   * Empty constructor to create service handle with default values.
	   */
	  public ServiceInstanceHandle() {
	    this(OWN_HOST, not_specified_classpath, not_serialized_id);
	  }

	/**
	 * Returns the id of the service. Throws Runtime-Exception if wasSerialized()
	 * returns false.
	 */
	public String getId() {
		return this.id;
	}

	/**
	 *
	 * @return Host of this service.
	 */
	public String getHost() {
		return this.host;
	}

	public String getClasspath() {
		return this.classpath;
	}

	/**
	 * @return True if the service is only remotely accessible.
	 */
	public boolean isRemote() {
		return !OWN_HOST.equals(this.getHost());
	}

	/**
	 *
	 * @return The address under which the service is accessible.
	 */
	public String getServiceAddress() {
		assert (this.isRemote()); // No need to access address if you are the host.
		return this.getHost() + "/" + this.getClasspath() + "/" + this.getId();
	}

	public boolean isSerialized() {
		return !this.id.equals(not_serialized_id);
	}

	public boolean isClassPathSpecified() {
		return !this.classpath.equals(not_specified_classpath);
	}

	/**
	 * Returns a copy of this object with the given host.
	 */
	public ServiceInstanceHandle withExternalHost(final String otherHost) {
		Objects.requireNonNull(otherHost);
		if (otherHost.equals(this.getHost())) {
			// otherhost changes nothing
			return this;
		}
		return new ServiceInstanceHandle(otherHost, this.getClasspath(), this.getId());
	}

	public ServiceInstanceHandle withLocalHost() {
		return this.withExternalHost(OWN_HOST);
	}

	/**
	 * Returns a copy of this object with the given host.
	 */
	public ServiceInstanceHandle withClassPath(final String classPath) {
		Objects.requireNonNull(classPath);
		if (classPath.equals(this.getClasspath())) {
			// otherhost changes nothing
			return this;
		}
		return new ServiceInstanceHandle(this.getHost(), classPath, this.getId());
	}


	public ServiceInstanceHandle withId(final String id2) {
		Objects.requireNonNull(id2);
		return new ServiceInstanceHandle(this.getHost(), this.getClasspath(), id2);
	}

	/**
	 * Returns a copy of this object with empty id.
	 */
	public ServiceInstanceHandle unsuccessedSerialize() {
		return new ServiceInstanceHandle(this.getHost(), this.getClasspath(), not_serialized_id);
	}
}
