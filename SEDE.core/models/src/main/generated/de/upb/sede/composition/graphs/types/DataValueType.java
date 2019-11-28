package de.upb.sede.composition.graphs.types;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.google.common.base.MoreObjects;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import com.google.errorprone.annotations.Var;
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
 * Immutable implementation of {@link IDataValueType}.
 * <p>
 * Use the builder to create immutable instances:
 * {@code DataValueType.builder()}.
 */
@Generated(from = "IDataValueType", generator = "Immutables")
@SuppressWarnings({"all"})
@ParametersAreNonnullByDefault
@javax.annotation.Generated("org.immutables.processor.ProxyProcessor")
@Immutable
@CheckReturnValue
public final class DataValueType implements IDataValueType {
  private final String qualifier;

  private DataValueType(String qualifier) {
    this.qualifier = qualifier;
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
   * Copy the current immutable object by setting a value for the {@link IDataValueType#getQualifier() qualifier} attribute.
   * An equals check used to prevent copying of the same value by returning {@code this}.
   * @param value A new value for qualifier
   * @return A modified copy of the {@code this} object
   */
  public final DataValueType withQualifier(String value) {
    String newValue = Objects.requireNonNull(value, "qualifier");
    if (this.qualifier.equals(newValue)) return this;
    return new DataValueType(newValue);
  }

  /**
   * This instance is equal to all instances of {@code DataValueType} that have equal attribute values.
   * @return {@code true} if {@code this} is equal to {@code another} instance
   */
  @Override
  public boolean equals(@Nullable Object another) {
    if (this == another) return true;
    return another instanceof DataValueType
        && equalTo((DataValueType) another);
  }

  private boolean equalTo(DataValueType another) {
    return qualifier.equals(another.qualifier);
  }

  /**
   * Computes a hash code from attributes: {@code qualifier}.
   * @return hashCode value
   */
  @Override
  public int hashCode() {
    @Var int h = 5381;
    h += (h << 5) + qualifier.hashCode();
    return h;
  }

  /**
   * Prints the immutable value {@code DataValueType} with attribute values.
   * @return A string representation of the value
   */
  @Override
  public String toString() {
    return MoreObjects.toStringHelper("DataValueType")
        .omitNullValues()
        .add("qualifier", qualifier)
        .toString();
  }

  /**
   * Utility type used to correctly read immutable object from JSON representation.
   * @deprecated Do not use this type directly, it exists only for the <em>Jackson</em>-binding infrastructure
   */
  @Generated(from = "IDataValueType", generator = "Immutables")
  @Deprecated
  @SuppressWarnings("Immutable")
  @JsonDeserialize
  @JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.NONE)
  static final class Json implements IDataValueType {
    @Nullable String qualifier;
    @JsonProperty("qualifier")
    public void setQualifier(String qualifier) {
      this.qualifier = qualifier;
    }
    @Override
    public String getQualifier() { throw new UnsupportedOperationException(); }
    @Override
    public String getTypeClass() { throw new UnsupportedOperationException(); }
    @Override
    public String getTypeQualifier() { throw new UnsupportedOperationException(); }
    @Override
    public String getSimpleName() { throw new UnsupportedOperationException(); }
    @Override
    public List<String> getMetaTags() { throw new UnsupportedOperationException(); }
  }

  /**
   * @param json A JSON-bindable data structure
   * @return An immutable value type
   * @deprecated Do not use this method directly, it exists only for the <em>Jackson</em>-binding infrastructure
   */
  @Deprecated
  @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
  static DataValueType fromJson(Json json) {
    DataValueType.Builder builder = DataValueType.builder();
    if (json.qualifier != null) {
      builder.qualifier(json.qualifier);
    }
    return builder.build();
  }

  @SuppressWarnings("Immutable")
  private transient volatile long lazyInitBitmap;

  private static final long TYPE_CLASS_LAZY_INIT_BIT = 0x1L;

  @SuppressWarnings("Immutable")
  private transient String typeClass;

  /**
   * {@inheritDoc}
   * <p>
   * Returns a lazily initialized value of the {@link IDataValueType#getTypeClass() typeClass} attribute.
   * Initialized once and only once and stored for subsequent access with proper synchronization.
   * In case of any exception or error thrown by the lazy value initializer,
   * the result will not be memoised (i.e. remembered) and on next call computation
   * will be attempted again.
   * @return A lazily initialized value of the {@code typeClass} attribute
   */
  @Override
  public String getTypeClass() {
    if ((lazyInitBitmap & TYPE_CLASS_LAZY_INIT_BIT) == 0) {
      synchronized (this) {
        if ((lazyInitBitmap & TYPE_CLASS_LAZY_INIT_BIT) == 0) {
          this.typeClass = Objects.requireNonNull(IDataValueType.super.getTypeClass(), "typeClass");
          lazyInitBitmap |= TYPE_CLASS_LAZY_INIT_BIT;
        }
      }
    }
    return typeClass;
  }

  private static final long TYPE_QUALIFIER_LAZY_INIT_BIT = 0x2L;

  @SuppressWarnings("Immutable")
  private transient String typeQualifier;

  /**
   * {@inheritDoc}
   * <p>
   * Returns a lazily initialized value of the {@link IDataValueType#getTypeQualifier() typeQualifier} attribute.
   * Initialized once and only once and stored for subsequent access with proper synchronization.
   * In case of any exception or error thrown by the lazy value initializer,
   * the result will not be memoised (i.e. remembered) and on next call computation
   * will be attempted again.
   * @return A lazily initialized value of the {@code typeQualifier} attribute
   */
  @Override
  public String getTypeQualifier() {
    if ((lazyInitBitmap & TYPE_QUALIFIER_LAZY_INIT_BIT) == 0) {
      synchronized (this) {
        if ((lazyInitBitmap & TYPE_QUALIFIER_LAZY_INIT_BIT) == 0) {
          this.typeQualifier = Objects.requireNonNull(IDataValueType.super.getTypeQualifier(), "typeQualifier");
          lazyInitBitmap |= TYPE_QUALIFIER_LAZY_INIT_BIT;
        }
      }
    }
    return typeQualifier;
  }

  private static final long SIMPLE_NAME_LAZY_INIT_BIT = 0x4L;

  @SuppressWarnings("Immutable")
  private transient String simpleName;

  /**
   * {@inheritDoc}
   * <p>
   * Returns a lazily initialized value of the {@link IDataValueType#getSimpleName() simpleName} attribute.
   * Initialized once and only once and stored for subsequent access with proper synchronization.
   * In case of any exception or error thrown by the lazy value initializer,
   * the result will not be memoised (i.e. remembered) and on next call computation
   * will be attempted again.
   * @return A lazily initialized value of the {@code simpleName} attribute
   */
  @Override
  public String getSimpleName() {
    if ((lazyInitBitmap & SIMPLE_NAME_LAZY_INIT_BIT) == 0) {
      synchronized (this) {
        if ((lazyInitBitmap & SIMPLE_NAME_LAZY_INIT_BIT) == 0) {
          this.simpleName = Objects.requireNonNull(IDataValueType.super.getSimpleName(), "simpleName");
          lazyInitBitmap |= SIMPLE_NAME_LAZY_INIT_BIT;
        }
      }
    }
    return simpleName;
  }

  private static final long META_TAGS_LAZY_INIT_BIT = 0x8L;

  @SuppressWarnings("Immutable")
  private transient List<String> metaTags;

  /**
   * {@inheritDoc}
   * <p>
   * Returns a lazily initialized value of the {@link IDataValueType#getMetaTags() metaTags} attribute.
   * Initialized once and only once and stored for subsequent access with proper synchronization.
   * In case of any exception or error thrown by the lazy value initializer,
   * the result will not be memoised (i.e. remembered) and on next call computation
   * will be attempted again.
   * @return A lazily initialized value of the {@code metaTags} attribute
   */
  @Override
  public List<String> getMetaTags() {
    if ((lazyInitBitmap & META_TAGS_LAZY_INIT_BIT) == 0) {
      synchronized (this) {
        if ((lazyInitBitmap & META_TAGS_LAZY_INIT_BIT) == 0) {
          this.metaTags = Objects.requireNonNull(IDataValueType.super.getMetaTags(), "metaTags");
          lazyInitBitmap |= META_TAGS_LAZY_INIT_BIT;
        }
      }
    }
    return metaTags;
  }

  /**
   * Creates an immutable copy of a {@link IDataValueType} value.
   * Uses accessors to get values to initialize the new immutable instance.
   * If an instance is already immutable, it is returned as is.
   * @param instance The instance to copy
   * @return A copied immutable DataValueType instance
   */
  public static DataValueType copyOf(IDataValueType instance) {
    if (instance instanceof DataValueType) {
      return (DataValueType) instance;
    }
    return DataValueType.builder()
        .from(instance)
        .build();
  }

  /**
   * Creates a builder for {@link DataValueType DataValueType}.
   * <pre>
   * DataValueType.builder()
   *    .qualifier(String) // required {@link IDataValueType#getQualifier() qualifier}
   *    .build();
   * </pre>
   * @return A new DataValueType builder
   */
  public static DataValueType.Builder builder() {
    return new DataValueType.Builder();
  }

  /**
   * Builds instances of type {@link DataValueType DataValueType}.
   * Initialize attributes and then invoke the {@link #build()} method to create an
   * immutable instance.
   * <p><em>{@code Builder} is not thread-safe and generally should not be stored in a field or collection,
   * but instead used immediately to create instances.</em>
   */
  @Generated(from = "IDataValueType", generator = "Immutables")
  @NotThreadSafe
  public static final class Builder {
    private static final long INIT_BIT_QUALIFIER = 0x1L;
    private long initBits = 0x1L;

    private @Nullable String qualifier;

    private Builder() {
    }

    /**
     * Fill a builder with attribute values from the provided {@code MutableDataValueType} instance.
     * @param instance The instance from which to copy values
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    public final Builder from(MutableDataValueType instance) {
      Objects.requireNonNull(instance, "instance");
      if (instance.qualifierIsSet()) {
        qualifier(instance.getQualifier());
      }
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

    /**
     * Fill a builder with attribute values from the provided {@code de.upb.sede.composition.graphs.types.IDataValueType} instance.
     * @param instance The instance from which to copy values
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    public final Builder from(IDataValueType instance) {
      Objects.requireNonNull(instance, "instance");
      from((Object) instance);
      return this;
    }

    private void from(Object object) {
      if (object instanceof MutableDataValueType) {
        from((MutableDataValueType) object);
        return;
      }
      if (object instanceof IQualifiable) {
        IQualifiable instance = (IQualifiable) object;
        qualifier(instance.getQualifier());
      }
    }

    /**
     * Initializes the value for the {@link IDataValueType#getQualifier() qualifier} attribute.
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
     * Builds a new {@link DataValueType DataValueType}.
     * @return An immutable instance of DataValueType
     * @throws java.lang.IllegalStateException if any required attributes are missing
     */
    public DataValueType build() {
      if (initBits != 0) {
        throw new IllegalStateException(formatRequiredAttributesMessage());
      }
      return new DataValueType(qualifier);
    }

    private String formatRequiredAttributesMessage() {
      List<String> attributes = new ArrayList<>();
      if ((initBits & INIT_BIT_QUALIFIER) != 0) attributes.add("qualifier");
      return "Cannot build DataValueType, some of required attributes are not set " + attributes;
    }
  }
}
