package de.upb.sede.beta;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.google.common.base.MoreObjects;
import com.google.common.primitives.Booleans;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import com.google.errorprone.annotations.Var;
import de.upb.sede.IFieldContainer;
import de.upb.sede.IQualifiable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.annotation.CheckReturnValue;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import javax.annotation.concurrent.Immutable;
import javax.annotation.concurrent.NotThreadSafe;
import org.immutables.value.Generated;

/**
 * Immutable implementation of {@link IDataPutRequest}.
 * <p>
 * Use the builder to create immutable instances:
 * {@code DataPutRequest.builder()}.
 */
@Generated(from = "IDataPutRequest", generator = "Immutables")
@SuppressWarnings({"all"})
@ParametersAreNonnullByDefault
@javax.annotation.Generated("org.immutables.processor.ProxyProcessor")
@Immutable
@CheckReturnValue
public final class DataPutRequest implements IDataPutRequest {
  private final boolean isUnavailable;
  private final String qualifier;
  private final String simpleName;
  private final String fieldName;

  private DataPutRequest(DataPutRequest.Builder builder) {
    this.isUnavailable = builder.isUnavailable;
    this.qualifier = builder.qualifier;
    this.fieldName = builder.fieldName;
    this.simpleName = builder.simpleName != null
        ? builder.simpleName
        : Objects.requireNonNull(IDataPutRequest.super.getSimpleName(), "simpleName");
  }

  private DataPutRequest(
      boolean isUnavailable,
      String qualifier,
      String simpleName,
      String fieldName) {
    this.isUnavailable = isUnavailable;
    this.qualifier = qualifier;
    this.simpleName = simpleName;
    this.fieldName = fieldName;
  }

  /**
   * @return The value of the {@code isUnavailable} attribute
   */
  @JsonProperty("isUnavailable")
  @Override
  public boolean isUnavailable() {
    return isUnavailable;
  }

  /**
   * @return The value of the {@code qualifier} attribute
   */
  @JsonProperty("qualifier")
  @Override
  public String getQualifier() {
    return qualifier;
  }

  /**
   * @return The value of the {@code simpleName} attribute
   */
  @JsonProperty("simpleName")
  @Override
  public String getSimpleName() {
    return simpleName;
  }

  /**
   * Returns the field name that is being refered at.
   * @return Referenced field name
   */
  @JsonProperty("fieldName")
  @Override
  public String getFieldName() {
    return fieldName;
  }

  /**
   * Copy the current immutable object by setting a value for the {@link IDataPutRequest#isUnavailable() isUnavailable} attribute.
   * A value equality check is used to prevent copying of the same value by returning {@code this}.
   * @param value A new value for isUnavailable
   * @return A modified copy of the {@code this} object
   */
  public final DataPutRequest withIsUnavailable(boolean value) {
    if (this.isUnavailable == value) return this;
    return new DataPutRequest(value, this.qualifier, this.simpleName, this.fieldName);
  }

  /**
   * Copy the current immutable object by setting a value for the {@link IDataPutRequest#getQualifier() qualifier} attribute.
   * An equals check used to prevent copying of the same value by returning {@code this}.
   * @param value A new value for qualifier
   * @return A modified copy of the {@code this} object
   */
  public final DataPutRequest withQualifier(String value) {
    String newValue = Objects.requireNonNull(value, "qualifier");
    if (this.qualifier.equals(newValue)) return this;
    return new DataPutRequest(this.isUnavailable, newValue, this.simpleName, this.fieldName);
  }

  /**
   * Copy the current immutable object by setting a value for the {@link IDataPutRequest#getSimpleName() simpleName} attribute.
   * An equals check used to prevent copying of the same value by returning {@code this}.
   * @param value A new value for simpleName
   * @return A modified copy of the {@code this} object
   */
  public final DataPutRequest withSimpleName(String value) {
    String newValue = Objects.requireNonNull(value, "simpleName");
    if (this.simpleName.equals(newValue)) return this;
    return new DataPutRequest(this.isUnavailable, this.qualifier, newValue, this.fieldName);
  }

  /**
   * Copy the current immutable object by setting a value for the {@link IDataPutRequest#getFieldName() fieldName} attribute.
   * An equals check used to prevent copying of the same value by returning {@code this}.
   * @param value A new value for fieldName
   * @return A modified copy of the {@code this} object
   */
  public final DataPutRequest withFieldName(String value) {
    String newValue = Objects.requireNonNull(value, "fieldName");
    if (this.fieldName.equals(newValue)) return this;
    return new DataPutRequest(this.isUnavailable, this.qualifier, this.simpleName, newValue);
  }

  /**
   * This instance is equal to all instances of {@code DataPutRequest} that have equal attribute values.
   * @return {@code true} if {@code this} is equal to {@code another} instance
   */
  @Override
  public boolean equals(@Nullable Object another) {
    if (this == another) return true;
    return another instanceof DataPutRequest
        && equalTo((DataPutRequest) another);
  }

  private boolean equalTo(DataPutRequest another) {
    return isUnavailable == another.isUnavailable
        && qualifier.equals(another.qualifier)
        && simpleName.equals(another.simpleName)
        && fieldName.equals(another.fieldName);
  }

  /**
   * Computes a hash code from attributes: {@code isUnavailable}, {@code qualifier}, {@code simpleName}, {@code fieldName}.
   * @return hashCode value
   */
  @Override
  public int hashCode() {
    @Var int h = 5381;
    h += (h << 5) + Booleans.hashCode(isUnavailable);
    h += (h << 5) + qualifier.hashCode();
    h += (h << 5) + simpleName.hashCode();
    h += (h << 5) + fieldName.hashCode();
    return h;
  }

  /**
   * Prints the immutable value {@code DataPutRequest} with attribute values.
   * @return A string representation of the value
   */
  @Override
  public String toString() {
    return MoreObjects.toStringHelper("DataPutRequest")
        .omitNullValues()
        .add("isUnavailable", isUnavailable)
        .add("qualifier", qualifier)
        .add("simpleName", simpleName)
        .add("fieldName", fieldName)
        .toString();
  }

  /**
   * Utility type used to correctly read immutable object from JSON representation.
   * @deprecated Do not use this type directly, it exists only for the <em>Jackson</em>-binding infrastructure
   */
  @Generated(from = "IDataPutRequest", generator = "Immutables")
  @Deprecated
  @SuppressWarnings("Immutable")
  @JsonDeserialize
  @JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.NONE)
  static final class Json implements IDataPutRequest {
    boolean isUnavailable;
    boolean isUnavailableIsSet;
    @Nullable String qualifier;
    @Nullable String simpleName;
    @Nullable String fieldName;
    @JsonProperty("isUnavailable")
    public void setIsUnavailable(boolean isUnavailable) {
      this.isUnavailable = isUnavailable;
      this.isUnavailableIsSet = true;
    }
    @JsonProperty("qualifier")
    public void setQualifier(String qualifier) {
      this.qualifier = qualifier;
    }
    @JsonProperty("simpleName")
    public void setSimpleName(String simpleName) {
      this.simpleName = simpleName;
    }
    @JsonProperty("fieldName")
    public void setFieldName(String fieldName) {
      this.fieldName = fieldName;
    }
    @Override
    public boolean isUnavailable() { throw new UnsupportedOperationException(); }
    @Override
    public String getQualifier() { throw new UnsupportedOperationException(); }
    @Override
    public String getSimpleName() { throw new UnsupportedOperationException(); }
    @Override
    public String getFieldName() { throw new UnsupportedOperationException(); }
  }

  /**
   * @param json A JSON-bindable data structure
   * @return An immutable value type
   * @deprecated Do not use this method directly, it exists only for the <em>Jackson</em>-binding infrastructure
   */
  @Deprecated
  @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
  static DataPutRequest fromJson(Json json) {
    DataPutRequest.Builder builder = DataPutRequest.builder();
    if (json.isUnavailableIsSet) {
      builder.isUnavailable(json.isUnavailable);
    }
    if (json.qualifier != null) {
      builder.qualifier(json.qualifier);
    }
    if (json.simpleName != null) {
      builder.simpleName(json.simpleName);
    }
    if (json.fieldName != null) {
      builder.fieldName(json.fieldName);
    }
    return builder.build();
  }

  /**
   * Creates an immutable copy of a {@link IDataPutRequest} value.
   * Uses accessors to get values to initialize the new immutable instance.
   * If an instance is already immutable, it is returned as is.
   * @param instance The instance to copy
   * @return A copied immutable DataPutRequest instance
   */
  public static DataPutRequest copyOf(IDataPutRequest instance) {
    if (instance instanceof DataPutRequest) {
      return (DataPutRequest) instance;
    }
    return DataPutRequest.builder()
        .from(instance)
        .build();
  }

  /**
   * Creates a builder for {@link DataPutRequest DataPutRequest}.
   * <pre>
   * DataPutRequest.builder()
   *    .isUnavailable(boolean) // required {@link IDataPutRequest#isUnavailable() isUnavailable}
   *    .qualifier(String) // required {@link IDataPutRequest#getQualifier() qualifier}
   *    .simpleName(String) // optional {@link IDataPutRequest#getSimpleName() simpleName}
   *    .fieldName(String) // required {@link IDataPutRequest#getFieldName() fieldName}
   *    .build();
   * </pre>
   * @return A new DataPutRequest builder
   */
  public static DataPutRequest.Builder builder() {
    return new DataPutRequest.Builder();
  }

  /**
   * Builds instances of type {@link DataPutRequest DataPutRequest}.
   * Initialize attributes and then invoke the {@link #build()} method to create an
   * immutable instance.
   * <p><em>{@code Builder} is not thread-safe and generally should not be stored in a field or collection,
   * but instead used immediately to create instances.</em>
   */
  @Generated(from = "IDataPutRequest", generator = "Immutables")
  @NotThreadSafe
  public static final class Builder {
    private static final long INIT_BIT_IS_UNAVAILABLE = 0x1L;
    private static final long INIT_BIT_QUALIFIER = 0x2L;
    private static final long INIT_BIT_FIELD_NAME = 0x4L;
    private long initBits = 0x7L;

    private boolean isUnavailable;
    private @Nullable String qualifier;
    private @Nullable String simpleName;
    private @Nullable String fieldName;

    private Builder() {
    }

    /**
     * Fill a builder with attribute values from the provided {@code MutableDataPutRequest} instance.
     * @param instance The instance from which to copy values
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    public final Builder from(MutableDataPutRequest instance) {
      Objects.requireNonNull(instance, "instance");
      if (instance.isUnavailableIsSet()) {
        isUnavailable(instance.isUnavailable());
      }
      if (instance.qualifierIsSet()) {
        qualifier(instance.getQualifier());
      }
      simpleName(instance.getSimpleName());
      if (instance.fieldNameIsSet()) {
        fieldName(instance.getFieldName());
      }
      return this;
    }

    /**
     * Fill a builder with attribute values from the provided {@code de.upb.sede.IFieldContainer} instance.
     * @param instance The instance from which to copy values
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    public final Builder from(IFieldContainer instance) {
      Objects.requireNonNull(instance, "instance");
      from((Object) instance);
      return this;
    }

    /**
     * Fill a builder with attribute values from the provided {@code de.upb.sede.beta.IDataPutRequest} instance.
     * @param instance The instance from which to copy values
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    public final Builder from(IDataPutRequest instance) {
      Objects.requireNonNull(instance, "instance");
      from((Object) instance);
      return this;
    }

    /**
     * Fill a builder with attribute values from the provided {@code de.upb.sede.IQualifiable} instance.
     * @param instance The instance from which to copy values
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    public final Builder from(IQualifiable instance) {
      Objects.requireNonNull(instance, "instance");
      from((Object) instance);
      return this;
    }

    private void from(Object object) {
      if (object instanceof MutableDataPutRequest) {
        from((MutableDataPutRequest) object);
        return;
      }
      if (object instanceof IFieldContainer) {
        IFieldContainer instance = (IFieldContainer) object;
        fieldName(instance.getFieldName());
      }
      if (object instanceof IDataPutRequest) {
        IDataPutRequest instance = (IDataPutRequest) object;
        isUnavailable(instance.isUnavailable());
      }
      if (object instanceof IQualifiable) {
        IQualifiable instance = (IQualifiable) object;
        simpleName(instance.getSimpleName());
        qualifier(instance.getQualifier());
      }
    }

    /**
     * Initializes the value for the {@link IDataPutRequest#isUnavailable() isUnavailable} attribute.
     * @param isUnavailable The value for isUnavailable 
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    @JsonProperty("isUnavailable")
    public final Builder isUnavailable(boolean isUnavailable) {
      this.isUnavailable = isUnavailable;
      initBits &= ~INIT_BIT_IS_UNAVAILABLE;
      return this;
    }

    /**
     * Initializes the value for the {@link IDataPutRequest#getQualifier() qualifier} attribute.
     * @param qualifier The value for qualifier 
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    @JsonProperty("qualifier")
    public final Builder qualifier(String qualifier) {
      this.qualifier = Objects.requireNonNull(qualifier, "qualifier");
      initBits &= ~INIT_BIT_QUALIFIER;
      return this;
    }

    /**
     * Initializes the value for the {@link IDataPutRequest#getSimpleName() simpleName} attribute.
     * <p><em>If not set, this attribute will have a default value as returned by the initializer of {@link IDataPutRequest#getSimpleName() simpleName}.</em>
     * @param simpleName The value for simpleName 
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    @JsonProperty("simpleName")
    public final Builder simpleName(String simpleName) {
      this.simpleName = Objects.requireNonNull(simpleName, "simpleName");
      return this;
    }

    /**
     * Initializes the value for the {@link IDataPutRequest#getFieldName() fieldName} attribute.
     * @param fieldName The value for fieldName 
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    @JsonProperty("fieldName")
    public final Builder fieldName(String fieldName) {
      this.fieldName = Objects.requireNonNull(fieldName, "fieldName");
      initBits &= ~INIT_BIT_FIELD_NAME;
      return this;
    }

    /**
     * Builds a new {@link DataPutRequest DataPutRequest}.
     * @return An immutable instance of DataPutRequest
     * @throws java.lang.IllegalStateException if any required attributes are missing
     */
    public DataPutRequest build() {
      if (initBits != 0) {
        throw new IllegalStateException(formatRequiredAttributesMessage());
      }
      return new DataPutRequest(this);
    }

    private String formatRequiredAttributesMessage() {
      List<String> attributes = new ArrayList<>();
      if ((initBits & INIT_BIT_IS_UNAVAILABLE) != 0) attributes.add("isUnavailable");
      if ((initBits & INIT_BIT_QUALIFIER) != 0) attributes.add("qualifier");
      if ((initBits & INIT_BIT_FIELD_NAME) != 0) attributes.add("fieldName");
      return "Cannot build DataPutRequest, some of required attributes are not set " + attributes;
    }
  }
}
