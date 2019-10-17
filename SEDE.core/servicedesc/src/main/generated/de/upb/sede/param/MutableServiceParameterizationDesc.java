package de.upb.sede.param;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.MoreObjects;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import de.upb.sede.param.auxiliary.IJavaParameterizationAux;
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
public final class MutableServiceParameterizationDesc
    implements IServiceParameterizationDesc {
  private final ArrayList<IParameter> parameters = new ArrayList<IParameter>();
  private final ArrayList<IParameterDependencyDesc> parameterDependencies = new ArrayList<IParameterDependencyDesc>();
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
   * @return modifiable list {@code parameters}
   */
  @JsonProperty("parameters")
  @Override
  public final List<IParameter> getParameters() {
    return parameters;
  }

  /**
   * @return modifiable list {@code parameterDependencies}
   */
  @JsonProperty("parameterDependencies")
  @Override
  public final List<IParameterDependencyDesc> getParameterDependencies() {
    return parameterDependencies;
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
    parameters.clear();
    parameterDependencies.clear();
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
    addAllParameters(instance.getParameters());
    addAllParameterDependencies(instance.getParameterDependencies());
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
    addAllParameters(instance.getParameters());
    addAllParameterDependencies(instance.getParameterDependencies());
    @Nullable IJavaParameterizationAux javaParameterizationAuxiliariesValue = instance.getJavaParameterizationAuxiliaries();
    if (javaParameterizationAuxiliariesValue != null) {
      setJavaParameterizationAuxiliaries(javaParameterizationAuxiliariesValue);
    }
    return this;
  }

  /**
   * Adds one element to {@link IServiceParameterizationDesc#getParameters() parameters} list.
   * @param element The parameters element
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public MutableServiceParameterizationDesc addParameters(IParameter element) {
    Objects.requireNonNull(element, "parameters element");
    this.parameters.add(element);
    return this;
  }

  /**
   * Adds elements to {@link IServiceParameterizationDesc#getParameters() parameters} list.
   * @param elements An array of parameters elements
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public final MutableServiceParameterizationDesc addParameters(IParameter... elements) {
    for (IParameter e : elements) {
      addParameters(e);
    }
    return this;
  }

  /**
   * Sets or replaces all elements for {@link IServiceParameterizationDesc#getParameters() parameters} list.
   * @param elements An iterable of parameters elements
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public MutableServiceParameterizationDesc setParameters(Iterable<? extends IParameter> elements) {
    this.parameters.clear();
    addAllParameters(elements);
    return this;
  }

  /**
   * Adds elements to {@link IServiceParameterizationDesc#getParameters() parameters} list.
   * @param elements An iterable of parameters elements
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public MutableServiceParameterizationDesc addAllParameters(Iterable<? extends IParameter> elements) {
    for (IParameter e : elements) {
      addParameters(e);
    }
    return this;
  }

  /**
   * Adds one element to {@link IServiceParameterizationDesc#getParameterDependencies() parameterDependencies} list.
   * @param element The parameterDependencies element
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public MutableServiceParameterizationDesc addParameterDependencies(IParameterDependencyDesc element) {
    Objects.requireNonNull(element, "parameterDependencies element");
    this.parameterDependencies.add(element);
    return this;
  }

  /**
   * Adds elements to {@link IServiceParameterizationDesc#getParameterDependencies() parameterDependencies} list.
   * @param elements An array of parameterDependencies elements
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public final MutableServiceParameterizationDesc addParameterDependencies(IParameterDependencyDesc... elements) {
    for (IParameterDependencyDesc e : elements) {
      addParameterDependencies(e);
    }
    return this;
  }

  /**
   * Sets or replaces all elements for {@link IServiceParameterizationDesc#getParameterDependencies() parameterDependencies} list.
   * @param elements An iterable of parameterDependencies elements
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public MutableServiceParameterizationDesc setParameterDependencies(Iterable<? extends IParameterDependencyDesc> elements) {
    this.parameterDependencies.clear();
    addAllParameterDependencies(elements);
    return this;
  }

  /**
   * Adds elements to {@link IServiceParameterizationDesc#getParameterDependencies() parameterDependencies} list.
   * @param elements An iterable of parameterDependencies elements
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public MutableServiceParameterizationDesc addAllParameterDependencies(Iterable<? extends IParameterDependencyDesc> elements) {
    for (IParameterDependencyDesc e : elements) {
      addParameterDependencies(e);
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
    return parameters.equals(another.parameters)
        && parameterDependencies.equals(another.parameterDependencies)
        && Objects.equals(javaParameterizationAuxiliaries, another.javaParameterizationAuxiliaries);
  }

  /**
   * Computes a hash code from attributes: {@code parameters}, {@code parameterDependencies}, {@code javaParameterizationAuxiliaries}.
   * @return hashCode value
   */
  @Override
  public int hashCode() {
    int h = 5381;
    h += (h << 5) + parameters.hashCode();
    h += (h << 5) + parameterDependencies.hashCode();
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
        .add("parameters", getParameters())
        .add("parameterDependencies", getParameterDependencies())
        .add("javaParameterizationAuxiliaries", getJavaParameterizationAuxiliaries())
        .toString();
  }
}
