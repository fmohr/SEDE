package de.upb.sede.beta;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.MoreObjects;
import com.google.common.primitives.Booleans;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import de.upb.sede.IFieldContainer;
import de.upb.sede.IQualifiable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import javax.annotation.concurrent.NotThreadSafe;
import org.immutables.value.Generated;

/**
 * A modifiable implementation of the {@link IDataPutRequest IDataPutRequest} type.
 * <p>Use the {@link #create()} static factory methods to create new instances.
 * Use the {@link #toImmutable()} method to convert to canonical immutable instances.
 * <p><em>MutableDataPutRequest is not thread-safe</em>
 * @see DataPutRequest
 */
@Generated(from = "IDataPutRequest", generator = "Modifiables")
@SuppressWarnings({"all"})
@ParametersAreNonnullByDefault
@javax.annotation.Generated({"Modifiables.generator", "IDataPutRequest"})
@NotThreadSafe
public final class MutableDataPutRequest implements IDataPutRequest {
  private static final long INIT_BIT_IS_UNAVAILABLE = 0x1L;
  private static final long INIT_BIT_QUALIFIER = 0x2L;
  private static final long INIT_BIT_FIELD_NAME = 0x4L;
  private long initBits = 0x7L;

  private boolean isUnavailable;
  private String qualifier;
  private final ArrayList<String> metaTags = new ArrayList<String>();
  private String simpleName;
  private String fieldName;

  private MutableDataPutRequest() {}

  /**
   * Construct a modifiable instance of {@code IDataPutRequest}.
   * @return A new modifiable instance
   */
  public static MutableDataPutRequest create() {
    return new MutableDataPutRequest();
  }

  /**
   * @return value of {@code isUnavailable} attribute
   */
  @JsonProperty("isUnavailable")
  @Override
  public final boolean isUnavailable() {
    if (!isUnavailableIsSet()) {
      checkRequiredAttributes();
    }
    return isUnavailable;
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
   * @return modifiable list {@code metaTags}
   */
  @JsonProperty("metaTags")
  @Override
  public final List<String> getMetaTags() {
    return metaTags;
  }

  /**
   * @return assigned or, otherwise, newly computed, not cached value of {@code simpleName} attribute
   */
  @JsonProperty("simpleName")
  @Override
  public final String getSimpleName() {
    return simpleNameIsSet()
        ? simpleName
        : IDataPutRequest.super.getSimpleName();
  }

  /**
   * @return value of {@code fieldName} attribute
   */
  @JsonProperty("fieldName")
  @Override
  public final String getFieldName() {
    if (!fieldNameIsSet()) {
      checkRequiredAttributes();
    }
    return fieldName;
  }

  /**
   * Clears the object by setting all attributes to their initial values.
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public MutableDataPutRequest clear() {
    initBits = 0x7L;
    isUnavailable = false;
    qualifier = null;
    metaTags.clear();
    simpleName = null;
    fieldName = null;
    return this;
  }

  /**
   * Fill this modifiable instance with attribute values from the provided {@link de.upb.sede.IFieldContainer} instance.
   * @param instance The instance from which to copy values
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public MutableDataPutRequest from(IFieldContainer instance) {
    Objects.requireNonNull(instance, "instance");
    from((Object) instance);
    return this;
  }

  /**
   * Fill this modifiable instance with attribute values from the provided {@link de.upb.sede.beta.IDataPutRequest} instance.
   * @param instance The instance from which to copy values
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public MutableDataPutRequest from(IDataPutRequest instance) {
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
  public MutableDataPutRequest from(IQualifiable instance) {
    Objects.requireNonNull(instance, "instance");
    from((Object) instance);
    return this;
  }

  /**
   * Fill this modifiable instance with attribute values from the provided {@link IDataPutRequest} instance.
   * Regular attribute values will be overridden, i.e. replaced with ones of an instance.
   * Any of the instance's absent optional values will not be copied (will not override current values).
   * Collection elements and entries will be added, not replaced.
   * @param instance The instance from which to copy values
   * @return {@code this} for use in a chained invocation
   */
  public MutableDataPutRequest from(MutableDataPutRequest instance) {
    Objects.requireNonNull(instance, "instance");
    from((Object) instance);
    return this;
  }

  private void from(Object object) {
    if (object instanceof MutableDataPutRequest) {
      MutableDataPutRequest instance = (MutableDataPutRequest) object;
      if (instance.isUnavailableIsSet()) {
        setIsUnavailable(instance.isUnavailable());
      }
      if (instance.qualifierIsSet()) {
        setQualifier(instance.getQualifier());
      }
      addAllMetaTags(instance.getMetaTags());
      setSimpleName(instance.getSimpleName());
      if (instance.fieldNameIsSet()) {
        setFieldName(instance.getFieldName());
      }
      return;
    }
    if (object instanceof IFieldContainer) {
      IFieldContainer instance = (IFieldContainer) object;
      setFieldName(instance.getFieldName());
    }
    if (object instanceof IDataPutRequest) {
      IDataPutRequest instance = (IDataPutRequest) object;
      setIsUnavailable(instance.isUnavailable());
    }
    if (object instanceof IQualifiable) {
      IQualifiable instance = (IQualifiable) object;
      addAllMetaTags(instance.getMetaTags());
      setSimpleName(instance.getSimpleName());
      setQualifier(instance.getQualifier());
    }
  }

  /**
   * Assigns a value to the {@link IDataPutRequest#isUnavailable() isUnavailable} attribute.
   * @param isUnavailable The value for isUnavailable
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public MutableDataPutRequest setIsUnavailable(boolean isUnavailable) {
    this.isUnavailable = isUnavailable;
    initBits &= ~INIT_BIT_IS_UNAVAILABLE;
    return this;
  }

  /**
   * Assigns a value to the {@link IDataPutRequest#getQualifier() qualifier} attribute.
   * @param qualifier The value for qualifier
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public MutableDataPutRequest setQualifier(String qualifier) {
    this.qualifier = Objects.requireNonNull(qualifier, "qualifier");
    initBits &= ~INIT_BIT_QUALIFIER;
    return this;
  }

  /**
   * Adds one element to {@link IDataPutRequest#getMetaTags() metaTags} list.
   * @param element The metaTags element
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public MutableDataPutRequest addMetaTags(String element) {
    Objects.requireNonNull(element, "metaTags element");
    this.metaTags.add(element);
    return this;
  }

  /**
   * Adds elements to {@link IDataPutRequest#getMetaTags() metaTags} list.
   * @param elements An array of metaTags elements
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public final MutableDataPutRequest addMetaTags(String... elements) {
    for (String e : elements) {
      addMetaTags(e);
    }
    return this;
  }

  /**
   * Sets or replaces all elements for {@link IDataPutRequest#getMetaTags() metaTags} list.
   * @param elements An iterable of metaTags elements
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public MutableDataPutRequest setMetaTags(Iterable<String> elements) {
    this.metaTags.clear();
    addAllMetaTags(elements);
    return this;
  }

  /**
   * Adds elements to {@link IDataPutRequest#getMetaTags() metaTags} list.
   * @param elements An iterable of metaTags elements
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public MutableDataPutRequest addAllMetaTags(Iterable<String> elements) {
    for (String e : elements) {
      addMetaTags(e);
    }
    return this;
  }

  /**
   * Assigns a value to the {@link IDataPutRequest#getSimpleName() simpleName} attribute.
   * <p><em>If not set, this attribute will have a default value returned by the initializer of {@link IDataPutRequest#getSimpleName() simpleName}.</em>
   * @param simpleName The value for simpleName
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public MutableDataPutRequest setSimpleName(String simpleName) {
    this.simpleName = Objects.requireNonNull(simpleName, "simpleName");
    return this;
  }

  /**
   * Assigns a value to the {@link IDataPutRequest#getFieldName() fieldName} attribute.
   * @param fieldName The value for fieldName
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public MutableDataPutRequest setFieldName(String fieldName) {
    this.fieldName = Objects.requireNonNull(fieldName, "fieldName");
    initBits &= ~INIT_BIT_FIELD_NAME;
    return this;
  }

  /**
   * Returns {@code true} if the required attribute {@link IDataPutRequest#isUnavailable() isUnavailable} is set.
   * @return {@code true} if set
   */
  public final boolean isUnavailableIsSet() {
    return (initBits & INIT_BIT_IS_UNAVAILABLE) == 0;
  }

  /**
   * Returns {@code true} if the required attribute {@link IDataPutRequest#getQualifier() qualifier} is set.
   * @return {@code true} if set
   */
  public final boolean qualifierIsSet() {
    return (initBits & INIT_BIT_QUALIFIER) == 0;
  }

  /**
   * Returns {@code true} if the required attribute {@link IDataPutRequest#getFieldName() fieldName} is set.
   * @return {@code true} if set
   */
  public final boolean fieldNameIsSet() {
    return (initBits & INIT_BIT_FIELD_NAME) == 0;
  }

  /**
   * Returns {@code true} if the default attribute {@link IDataPutRequest#getSimpleName() simpleName} is set.
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
  public final MutableDataPutRequest unsetIsUnavailable() {
    initBits |= INIT_BIT_IS_UNAVAILABLE;
    isUnavailable = false;
    return this;
  }

  /**
   * Reset an attribute to its initial value.
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public final MutableDataPutRequest unsetQualifier() {
    initBits |= INIT_BIT_QUALIFIER;
    qualifier = null;
    return this;
  }

  /**
   * Reset an attribute to its initial value.
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public final MutableDataPutRequest unsetFieldName() {
    initBits |= INIT_BIT_FIELD_NAME;
    fieldName = null;
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
    if (!isUnavailableIsSet()) attributes.add("isUnavailable");
    if (!qualifierIsSet()) attributes.add("qualifier");
    if (!fieldNameIsSet()) attributes.add("fieldName");
    return "DataPutRequest is not initialized, some of the required attributes are not set " + attributes;
  }

  /**
   * Converts to {@link DataPutRequest DataPutRequest}.
   * @return An immutable instance of DataPutRequest
   */
  public final DataPutRequest toImmutable() {
    checkRequiredAttributes();
    return DataPutRequest.copyOf(this);
  }

  /**
   * This instance is equal to all instances of {@code MutableDataPutRequest} that have equal attribute values.
   * An uninitialized instance is equal only to itself.
   * @return {@code true} if {@code this} is equal to {@code another} instance
   */
  @Override
  public boolean equals(@Nullable Object another) {
    if (this == another) return true;
    if (!(another instanceof MutableDataPutRequest)) return false;
    MutableDataPutRequest other = (MutableDataPutRequest) another;
    if (!isInitialized() || !other.isInitialized()) {
      return false;
    }
    return equalTo(other);
  }

  private boolean equalTo(MutableDataPutRequest another) {
    return isUnavailable == another.isUnavailable
        && qualifier.equals(another.qualifier)
        && fieldName.equals(another.fieldName);
  }

  /**
   * Computes a hash code from attributes: {@code isUnavailable}, {@code qualifier}, {@code fieldName}.
   * @return hashCode value
   */
  @Override
  public int hashCode() {
    int h = 5381;
    h += (h << 5) + Booleans.hashCode(isUnavailable);
    h += (h << 5) + qualifier.hashCode();
    h += (h << 5) + fieldName.hashCode();
    return h;
  }

  /**
   * Generates a string representation of this {@code IDataPutRequest}.
   * If uninitialized, some attribute values may appear as question marks.
   * @return A string representation
   */
  @Override
  public String toString() {
    return MoreObjects.toStringHelper("MutableDataPutRequest")
        .add("isUnavailable", isUnavailableIsSet() ? isUnavailable() : "?")
        .add("qualifier", qualifierIsSet() ? getQualifier() : "?")
        .add("fieldName", fieldNameIsSet() ? getFieldName() : "?")
        .toString();
  }
}
