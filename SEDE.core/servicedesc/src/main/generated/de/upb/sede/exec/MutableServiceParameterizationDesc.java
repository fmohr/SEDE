package de.upb.sede.exec;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.MoreObjects;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import javax.annotation.concurrent.NotThreadSafe;
import org.immutables.value.Generated;

/**
 * A modifiable implementation of the {@link IServiceParameterizationDesc IServiceParameterizationDesc} type.
 * <p>Use the {@link #create()} static factory methods to create new instances.
 * Use the {@link #toImmutable()} method to convert to canonical immutable instances.
 * <p><em>MutableServiceParameterizationDesc is not thread-safe</em>
 * @see ServiceParameterizationDesc
 */
@Generated(from = "IServiceParameterizationDesc", generator = "Modifiables")
@SuppressWarnings({"all"})
@ParametersAreNonnullByDefault
@javax.annotation.Generated({"Modifiables.generator", "IServiceParameterizationDesc"})
@NotThreadSafe
public final class MutableServiceParameterizationDesc implements IServiceParameterizationDesc {
  private final ArrayList<IServiceParameterDesc> serviceParameterDesc = new ArrayList<IServiceParameterDesc>();
  private @Nullable IJavaParameterizationAux javaParameterizationAuxiliaries;

  private MutableServiceParameterizationDesc() {}

  /**
   * Construct a modifiable instance of {@code IServiceParameterizationDesc}.
   * @return A new modifiable instance
   */
  public static MutableServiceParameterizationDesc create() {
    return new MutableServiceParameterizationDesc();
  }

  /**
   * @return modifiable list {@code serviceParameterDesc}
   */
  @JsonProperty("serviceParameterDesc")
  @Override
  public final List<IServiceParameterDesc> getServiceParameterDesc() {
    return serviceParameterDesc;
  }

  /**
   * @return value of {@code javaParameterizationAuxiliaries} attribute, may be {@code null}
   */
  @JsonProperty("javaParameterizationAuxiliaries")
  @Override
  public final @Nullable IJavaParameterizationAux getJavaParameterizationAuxiliaries() {
    return javaParameterizationAuxiliaries;
  }

  /**
   * Clears the object by setting all attributes to their initial values.
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public MutableServiceParameterizationDesc clear() {
    serviceParameterDesc.clear();
    javaParameterizationAuxiliaries = null;
    return this;
  }

  /**
   * Fill this modifiable instance with attribute values from the provided {@link IServiceParameterizationDesc} instance.
   * Regular attribute values will be overridden, i.e. replaced with ones of an instance.
   * Any of the instance's absent optional values will not be copied (will not override current values).
   * Collection elements and entries will be added, not replaced.
   * @param instance The instance from which to copy values
   * @return {@code this} for use in a chained invocation
   */
  public MutableServiceParameterizationDesc from(IServiceParameterizationDesc instance) {
    Objects.requireNonNull(instance, "instance");
    if (instance instanceof MutableServiceParameterizationDesc) {
      from((MutableServiceParameterizationDesc) instance);
      return this;
    }
    addAllServiceParameterDesc(instance.getServiceParameterDesc());
    @Nullable IJavaParameterizationAux javaParameterizationAuxiliariesValue = instance.getJavaParameterizationAuxiliaries();
    if (javaParameterizationAuxiliariesValue != null) {
      setJavaParameterizationAuxiliaries(javaParameterizationAuxiliariesValue);
    }
    return this;
  }

  /**
   * Fill this modifiable instance with attribute values from the provided {@link IServiceParameterizationDesc} instance.
   * Regular attribute values will be overridden, i.e. replaced with ones of an instance.
   * Any of the instance's absent optional values will not be copied (will not override current values).
   * Collection elements and entries will be added, not replaced.
   * @param instance The instance from which to copy values
   * @return {@code this} for use in a chained invocation
   */
  public MutableServiceParameterizationDesc from(MutableServiceParameterizationDesc instance) {
    Objects.requireNonNull(instance, "instance");
    addAllServiceParameterDesc(instance.getServiceParameterDesc());
    @Nullable IJavaParameterizationAux javaParameterizationAuxiliariesValue = instance.getJavaParameterizationAuxiliaries();
    if (javaParameterizationAuxiliariesValue != null) {
      setJavaParameterizationAuxiliaries(javaParameterizationAuxiliariesValue);
    }
    return this;
  }

  /**
   * Adds one element to {@link IServiceParameterizationDesc#getServiceParameterDesc() serviceParameterDesc} list.
   * @param element The serviceParameterDesc element
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public MutableServiceParameterizationDesc addServiceParameterDesc(IServiceParameterDesc element) {
    Objects.requireNonNull(element, "serviceParameterDesc element");
    this.serviceParameterDesc.add(element);
    return this;
  }

  /**
   * Adds elements to {@link IServiceParameterizationDesc#getServiceParameterDesc() serviceParameterDesc} list.
   * @param elements An array of serviceParameterDesc elements
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public final MutableServiceParameterizationDesc addServiceParameterDesc(IServiceParameterDesc... elements) {
    for (IServiceParameterDesc e : elements) {
      addServiceParameterDesc(e);
    }
    return this;
  }

  /**
   * Sets or replaces all elements for {@link IServiceParameterizationDesc#getServiceParameterDesc() serviceParameterDesc} list.
   * @param elements An iterable of serviceParameterDesc elements
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public MutableServiceParameterizationDesc setServiceParameterDesc(Iterable<? extends IServiceParameterDesc> elements) {
    this.serviceParameterDesc.clear();
    addAllServiceParameterDesc(elements);
    return this;
  }

  /**
   * Adds elements to {@link IServiceParameterizationDesc#getServiceParameterDesc() serviceParameterDesc} list.
   * @param elements An iterable of serviceParameterDesc elements
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public MutableServiceParameterizationDesc addAllServiceParameterDesc(Iterable<? extends IServiceParameterDesc> elements) {
    for (IServiceParameterDesc e : elements) {
      addServiceParameterDesc(e);
    }
    return this;
  }

  /**
   * Assigns a value to the {@link IServiceParameterizationDesc#getJavaParameterizationAuxiliaries() javaParameterizationAuxiliaries} attribute.
   * @param javaParameterizationAuxiliaries The value for javaParameterizationAuxiliaries, can be {@code null}
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public MutableServiceParameterizationDesc setJavaParameterizationAuxiliaries(@Nullable IJavaParameterizationAux javaParameterizationAuxiliaries) {
    this.javaParameterizationAuxiliaries = javaParameterizationAuxiliaries;
    return this;
  }


  /**
   * Returns {@code true} if all required attributes are set, indicating that the object is initialized.
   * @return {@code true} if set
   */
  public final boolean isInitialized() {
    return true;
  }

  /**
   * Converts to {@link ServiceParameterizationDesc ServiceParameterizationDesc}.
   * @return An immutable instance of ServiceParameterizationDesc
   */
  public final ServiceParameterizationDesc toImmutable() {
    return ServiceParameterizationDesc.copyOf(this);
  }

  /**
   * This instance is equal to all instances of {@code MutableServiceParameterizationDesc} that have equal attribute values.
   * @return {@code true} if {@code this} is equal to {@code another} instance
   */
  @Override
  public boolean equals(@Nullable Object another) {
    if (this == another) return true;
    if (!(another instanceof MutableServiceParameterizationDesc)) return false;
    MutableServiceParameterizationDesc other = (MutableServiceParameterizationDesc) another;
    return equalTo(other);
  }

  private boolean equalTo(MutableServiceParameterizationDesc another) {
    return serviceParameterDesc.equals(another.serviceParameterDesc)
        && Objects.equals(javaParameterizationAuxiliaries, another.javaParameterizationAuxiliaries);
  }

  /**
   * Computes a hash code from attributes: {@code serviceParameterDesc}, {@code javaParameterizationAuxiliaries}.
   * @return hashCode value
   */
  @Override
  public int hashCode() {
    int h = 5381;
    h += (h << 5) + serviceParameterDesc.hashCode();
    h += (h << 5) + Objects.hashCode(javaParameterizationAuxiliaries);
    return h;
  }

  /**
   * Generates a string representation of this {@code IServiceParameterizationDesc}.
   * If uninitialized, some attribute values may appear as question marks.
   * @return A string representation
   */
  @Override
  public String toString() {
    return MoreObjects.toStringHelper("MutableServiceParameterizationDesc")
        .add("serviceParameterDesc", getServiceParameterDesc())
        .add("javaParameterizationAuxiliaries", getJavaParameterizationAuxiliaries())
        .toString();
  }
}
