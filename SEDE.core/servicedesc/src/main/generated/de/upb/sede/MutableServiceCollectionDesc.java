package de.upb.sede;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.MoreObjects;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import de.upb.sede.exec.IServiceDesc;
import de.upb.sede.types.IDataTypeDesc;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import javax.annotation.concurrent.NotThreadSafe;
import org.immutables.value.Generated;

/**
 * A modifiable implementation of the {@link IServiceCollectionDesc IServiceCollectionDesc} type.
 * <p>Use the {@link #create()} static factory methods to create new instances.
 * Use the {@link #toImmutable()} method to convert to canonical immutable instances.
 * <p><em>MutableServiceCollectionDesc is not thread-safe</em>
 * @see ServiceCollectionDesc
 */
@Generated(from = "IServiceCollectionDesc", generator = "Modifiables")
@SuppressWarnings({"all"})
@ParametersAreNonnullByDefault
@javax.annotation.Generated({"Modifiables.generator", "IServiceCollectionDesc"})
@NotThreadSafe
public final class MutableServiceCollectionDesc implements IServiceCollectionDesc {
  private static final long INIT_BIT_QUALIFIER = 0x1L;
  private long initBits = 0x1L;

  private final ArrayList<IServiceDesc> services = new ArrayList<IServiceDesc>();
  private final ArrayList<IDataTypeDesc> dataTypes = new ArrayList<IDataTypeDesc>();
  private final ArrayList<String> comments = new ArrayList<String>();
  private String qualifier;
  private String simpleName;

  private MutableServiceCollectionDesc() {}

  /**
   * Construct a modifiable instance of {@code IServiceCollectionDesc}.
   * @return A new modifiable instance
   */
  public static MutableServiceCollectionDesc create() {
    return new MutableServiceCollectionDesc();
  }

  /**
   * @return modifiable list {@code services}
   */
  @JsonProperty("services")
  @Override
  public final List<IServiceDesc> getServices() {
    return services;
  }

  /**
   * @return modifiable list {@code dataTypes}
   */
  @JsonProperty("dataTypes")
  @Override
  public final List<IDataTypeDesc> getDataTypes() {
    return dataTypes;
  }

  /**
   * @return modifiable list {@code comments}
   */
  @JsonProperty("comments")
  @Override
  public final List<String> getComments() {
    return comments;
  }

  /**
   * @return value of {@code qualifier} attribute
   */
  @JsonProperty("qualifier")
  @Override
  public final String getQualifier() {
    if (!qualifierIsSet()) {
      checkRequiredAttributes();
    }
    return qualifier;
  }

  /**
   * @return assigned or, otherwise, newly computed, not cached value of {@code simpleName} attribute
   */
  @JsonProperty("simpleName")
  @Override
  public final String getSimpleName() {
    return simpleNameIsSet()
        ? simpleName
        : IServiceCollectionDesc.super.getSimpleName();
  }

  /**
   * Clears the object by setting all attributes to their initial values.
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public MutableServiceCollectionDesc clear() {
    initBits = 0x1L;
    services.clear();
    dataTypes.clear();
    comments.clear();
    qualifier = null;
    simpleName = null;
    return this;
  }

  /**
   * Fill this modifiable instance with attribute values from the provided {@link de.upb.sede.ICommented} instance.
   * @param instance The instance from which to copy values
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public MutableServiceCollectionDesc from(ICommented instance) {
    Objects.requireNonNull(instance, "instance");
    from((Object) instance);
    return this;
  }

  /**
   * Fill this modifiable instance with attribute values from the provided {@link de.upb.sede.IServiceCollectionDesc} instance.
   * @param instance The instance from which to copy values
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public MutableServiceCollectionDesc from(IServiceCollectionDesc instance) {
    Objects.requireNonNull(instance, "instance");
    from((Object) instance);
    return this;
  }

  /**
   * Fill this modifiable instance with attribute values from the provided {@link de.upb.sede.IQualifiable} instance.
   * @param instance The instance from which to copy values
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public MutableServiceCollectionDesc from(IQualifiable instance) {
    Objects.requireNonNull(instance, "instance");
    from((Object) instance);
    return this;
  }

  /**
   * Fill this modifiable instance with attribute values from the provided {@link IServiceCollectionDesc} instance.
   * Regular attribute values will be overridden, i.e. replaced with ones of an instance.
   * Any of the instance's absent optional values will not be copied (will not override current values).
   * Collection elements and entries will be added, not replaced.
   * @param instance The instance from which to copy values
   * @return {@code this} for use in a chained invocation
   */
  public MutableServiceCollectionDesc from(MutableServiceCollectionDesc instance) {
    Objects.requireNonNull(instance, "instance");
    from((Object) instance);
    return this;
  }

  private void from(Object object) {
    if (object instanceof MutableServiceCollectionDesc) {
      MutableServiceCollectionDesc instance = (MutableServiceCollectionDesc) object;
      addAllServices(instance.getServices());
      addAllDataTypes(instance.getDataTypes());
      addAllComments(instance.getComments());
      if (instance.qualifierIsSet()) {
        setQualifier(instance.getQualifier());
      }
      setSimpleName(instance.getSimpleName());
      return;
    }
    if (object instanceof ICommented) {
      ICommented instance = (ICommented) object;
      addAllComments(instance.getComments());
    }
    if (object instanceof IServiceCollectionDesc) {
      IServiceCollectionDesc instance = (IServiceCollectionDesc) object;
      addAllDataTypes(instance.getDataTypes());
      addAllServices(instance.getServices());
    }
    if (object instanceof IQualifiable) {
      IQualifiable instance = (IQualifiable) object;
      setSimpleName(instance.getSimpleName());
      setQualifier(instance.getQualifier());
    }
  }

  /**
   * Adds one element to {@link IServiceCollectionDesc#getServices() services} list.
   * @param element The services element
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public MutableServiceCollectionDesc addServices(IServiceDesc element) {
    Objects.requireNonNull(element, "services element");
    this.services.add(element);
    return this;
  }

  /**
   * Adds elements to {@link IServiceCollectionDesc#getServices() services} list.
   * @param elements An array of services elements
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public final MutableServiceCollectionDesc addServices(IServiceDesc... elements) {
    for (IServiceDesc e : elements) {
      addServices(e);
    }
    return this;
  }

  /**
   * Sets or replaces all elements for {@link IServiceCollectionDesc#getServices() services} list.
   * @param elements An iterable of services elements
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public MutableServiceCollectionDesc setServices(Iterable<? extends IServiceDesc> elements) {
    this.services.clear();
    addAllServices(elements);
    return this;
  }

  /**
   * Adds elements to {@link IServiceCollectionDesc#getServices() services} list.
   * @param elements An iterable of services elements
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public MutableServiceCollectionDesc addAllServices(Iterable<? extends IServiceDesc> elements) {
    for (IServiceDesc e : elements) {
      addServices(e);
    }
    return this;
  }

  /**
   * Adds one element to {@link IServiceCollectionDesc#getDataTypes() dataTypes} list.
   * @param element The dataTypes element
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public MutableServiceCollectionDesc addDataTypes(IDataTypeDesc element) {
    Objects.requireNonNull(element, "dataTypes element");
    this.dataTypes.add(element);
    return this;
  }

  /**
   * Adds elements to {@link IServiceCollectionDesc#getDataTypes() dataTypes} list.
   * @param elements An array of dataTypes elements
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public final MutableServiceCollectionDesc addDataTypes(IDataTypeDesc... elements) {
    for (IDataTypeDesc e : elements) {
      addDataTypes(e);
    }
    return this;
  }

  /**
   * Sets or replaces all elements for {@link IServiceCollectionDesc#getDataTypes() dataTypes} list.
   * @param elements An iterable of dataTypes elements
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public MutableServiceCollectionDesc setDataTypes(Iterable<? extends IDataTypeDesc> elements) {
    this.dataTypes.clear();
    addAllDataTypes(elements);
    return this;
  }

  /**
   * Adds elements to {@link IServiceCollectionDesc#getDataTypes() dataTypes} list.
   * @param elements An iterable of dataTypes elements
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public MutableServiceCollectionDesc addAllDataTypes(Iterable<? extends IDataTypeDesc> elements) {
    for (IDataTypeDesc e : elements) {
      addDataTypes(e);
    }
    return this;
  }

  /**
   * Adds one element to {@link IServiceCollectionDesc#getComments() comments} list.
   * @param element The comments element
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public MutableServiceCollectionDesc addComments(String element) {
    Objects.requireNonNull(element, "comments element");
    this.comments.add(element);
    return this;
  }

  /**
   * Adds elements to {@link IServiceCollectionDesc#getComments() comments} list.
   * @param elements An array of comments elements
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public final MutableServiceCollectionDesc addComments(String... elements) {
    for (String e : elements) {
      addComments(e);
    }
    return this;
  }

  /**
   * Sets or replaces all elements for {@link IServiceCollectionDesc#getComments() comments} list.
   * @param elements An iterable of comments elements
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public MutableServiceCollectionDesc setComments(Iterable<String> elements) {
    this.comments.clear();
    addAllComments(elements);
    return this;
  }

  /**
   * Adds elements to {@link IServiceCollectionDesc#getComments() comments} list.
   * @param elements An iterable of comments elements
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public MutableServiceCollectionDesc addAllComments(Iterable<String> elements) {
    for (String e : elements) {
      addComments(e);
    }
    return this;
  }

  /**
   * Assigns a value to the {@link IServiceCollectionDesc#getQualifier() qualifier} attribute.
   * @param qualifier The value for qualifier
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public MutableServiceCollectionDesc setQualifier(String qualifier) {
    this.qualifier = Objects.requireNonNull(qualifier, "qualifier");
    initBits &= ~INIT_BIT_QUALIFIER;
    return this;
  }

  /**
   * Assigns a value to the {@link IServiceCollectionDesc#getSimpleName() simpleName} attribute.
   * <p><em>If not set, this attribute will have a default value returned by the initializer of {@link IServiceCollectionDesc#getSimpleName() simpleName}.</em>
   * @param simpleName The value for simpleName
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public MutableServiceCollectionDesc setSimpleName(String simpleName) {
    this.simpleName = Objects.requireNonNull(simpleName, "simpleName");
    return this;
  }

  /**
   * Returns {@code true} if the required attribute {@link IServiceCollectionDesc#getQualifier() qualifier} is set.
   * @return {@code true} if set
   */
  public final boolean qualifierIsSet() {
    return (initBits & INIT_BIT_QUALIFIER) == 0;
  }

  /**
   * Returns {@code true} if the default attribute {@link IServiceCollectionDesc#getSimpleName() simpleName} is set.
   * @return {@code true} if set
   */
  public final boolean simpleNameIsSet() {
    return simpleName != null;
  }


  /**
   * Reset an attribute to its initial value.
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public final MutableServiceCollectionDesc unsetQualifier() {
    initBits |= INIT_BIT_QUALIFIER;
    qualifier = null;
    return this;
  }

  /**
   * Returns {@code true} if all required attributes are set, indicating that the object is initialized.
   * @return {@code true} if set
   */
  public final boolean isInitialized() {
    return initBits == 0;
  }

  private void checkRequiredAttributes() {
    if (!isInitialized()) {
      throw new IllegalStateException(formatRequiredAttributesMessage());
    }
  }

  private String formatRequiredAttributesMessage() {
    List<String> attributes = new ArrayList<>();
    if (!qualifierIsSet()) attributes.add("qualifier");
    return "ServiceCollectionDesc is not initialized, some of the required attributes are not set " + attributes;
  }

  /**
   * Converts to {@link ServiceCollectionDesc ServiceCollectionDesc}.
   * @return An immutable instance of ServiceCollectionDesc
   */
  public final ServiceCollectionDesc toImmutable() {
    checkRequiredAttributes();
    return ServiceCollectionDesc.copyOf(this);
  }

  /**
   * This instance is equal to all instances of {@code MutableServiceCollectionDesc} that have equal attribute values.
   * An uninitialized instance is equal only to itself.
   * @return {@code true} if {@code this} is equal to {@code another} instance
   */
  @Override
  public boolean equals(@Nullable Object another) {
    if (this == another) return true;
    if (!(another instanceof MutableServiceCollectionDesc)) return false;
    MutableServiceCollectionDesc other = (MutableServiceCollectionDesc) another;
    if (!isInitialized() || !other.isInitialized()) {
      return false;
    }
    return equalTo(other);
  }

  private boolean equalTo(MutableServiceCollectionDesc another) {
    String simpleName = getSimpleName();
    return services.equals(another.services)
        && dataTypes.equals(another.dataTypes)
        && comments.equals(another.comments)
        && qualifier.equals(another.qualifier)
        && simpleName.equals(another.getSimpleName());
  }

  /**
   * Computes a hash code from attributes: {@code services}, {@code dataTypes}, {@code comments}, {@code qualifier}, {@code simpleName}.
   * @return hashCode value
   */
  @Override
  public int hashCode() {
    int h = 5381;
    h += (h << 5) + services.hashCode();
    h += (h << 5) + dataTypes.hashCode();
    h += (h << 5) + comments.hashCode();
    h += (h << 5) + qualifier.hashCode();
    String simpleName = getSimpleName();
    h += (h << 5) + simpleName.hashCode();
    return h;
  }

  /**
   * Generates a string representation of this {@code IServiceCollectionDesc}.
   * If uninitialized, some attribute values may appear as question marks.
   * @return A string representation
   */
  @Override
  public String toString() {
    return MoreObjects.toStringHelper("MutableServiceCollectionDesc")
        .add("services", getServices())
        .add("dataTypes", getDataTypes())
        .add("comments", getComments())
        .add("qualifier", qualifierIsSet() ? getQualifier() : "?")
        .add("simpleName", getSimpleName())
        .toString();
  }
}
