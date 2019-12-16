package de.upb.sede.composition.graphs.nodes;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.google.common.base.MoreObjects;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import com.google.errorprone.annotations.Var;
import de.upb.sede.IFieldContainer;
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
 * Immutable implementation of {@link IAcceptDataNode}.
 * <p>
 * Use the builder to create immutable instances:
 * {@code AcceptDataNode.builder()}.
 */
@Generated(from = "IAcceptDataNode", generator = "Immutables")
@SuppressWarnings({"all"})
@ParametersAreNonnullByDefault
@javax.annotation.Generated("org.immutables.processor.ProxyProcessor")
@Immutable
@CheckReturnValue
public final class AcceptDataNode implements IAcceptDataNode {
  private final @Nullable ICastTypeNode cast;
  private final String fieldName;

  private AcceptDataNode(
      @Nullable ICastTypeNode cast,
      String fieldName) {
    this.cast = cast;
    this.fieldName = fieldName;
  }

  /**
   * @return The value of the {@code cast} attribute
   */
  @JsonProperty("cast")
  @Override
  public @Nullable ICastTypeNode getCast() {
    return cast;
  }

  /**
   * @return The value of the {@code fieldName} attribute
   */
  @JsonProperty("fieldName")
  @Override
  public String getFieldName() {
    return fieldName;
  }

  /**
   * Copy the current immutable object by setting a value for the {@link IAcceptDataNode#getCast() cast} attribute.
   * A shallow reference equality check is used to prevent copying of the same value by returning {@code this}.
   * @param value A new value for cast (can be {@code null})
   * @return A modified copy of the {@code this} object
   */
  public final AcceptDataNode withCast(@Nullable ICastTypeNode value) {
    if (this.cast == value) return this;
    return new AcceptDataNode(value, this.fieldName);
  }

  /**
   * Copy the current immutable object by setting a value for the {@link IAcceptDataNode#getFieldName() fieldName} attribute.
   * An equals check used to prevent copying of the same value by returning {@code this}.
   * @param value A new value for fieldName
   * @return A modified copy of the {@code this} object
   */
  public final AcceptDataNode withFieldName(String value) {
    String newValue = Objects.requireNonNull(value, "fieldName");
    if (this.fieldName.equals(newValue)) return this;
    return new AcceptDataNode(this.cast, newValue);
  }

  /**
   * This instance is equal to all instances of {@code AcceptDataNode} that have equal attribute values.
   * @return {@code true} if {@code this} is equal to {@code another} instance
   */
  @Override
  public boolean equals(@Nullable Object another) {
    if (this == another) return true;
    return another instanceof AcceptDataNode
        && equalTo((AcceptDataNode) another);
  }

  private boolean equalTo(AcceptDataNode another) {
    return Objects.equals(cast, another.cast)
        && fieldName.equals(another.fieldName);
  }

  /**
   * Computes a hash code from attributes: {@code cast}, {@code fieldName}.
   * @return hashCode value
   */
  @Override
  public int hashCode() {
    @Var int h = 5381;
    h += (h << 5) + Objects.hashCode(cast);
    h += (h << 5) + fieldName.hashCode();
    return h;
  }

  /**
   * Prints the immutable value {@code AcceptDataNode} with attribute values.
   * @return A string representation of the value
   */
  @Override
  public String toString() {
    return MoreObjects.toStringHelper("AcceptDataNode")
        .omitNullValues()
        .add("cast", cast)
        .add("fieldName", fieldName)
        .toString();
  }

  /**
   * Utility type used to correctly read immutable object from JSON representation.
   * @deprecated Do not use this type directly, it exists only for the <em>Jackson</em>-binding infrastructure
   */
  @Generated(from = "IAcceptDataNode", generator = "Immutables")
  @Deprecated
  @SuppressWarnings("Immutable")
  @JsonDeserialize
  @JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.NONE)
  static final class Json implements IAcceptDataNode {
    @Nullable ICastTypeNode cast;
    @Nullable String fieldName;
    @JsonProperty("cast")
    public void setCast(@Nullable ICastTypeNode cast) {
      this.cast = cast;
    }
    @JsonProperty("fieldName")
    public void setFieldName(String fieldName) {
      this.fieldName = fieldName;
    }
    @Override
    public ICastTypeNode getCast() { throw new UnsupportedOperationException(); }
    @Override
    public String getFieldName() { throw new UnsupportedOperationException(); }
    @Override
    public String getNodeType() { throw new UnsupportedOperationException(); }
  }

  /**
   * @param json A JSON-bindable data structure
   * @return An immutable value type
   * @deprecated Do not use this method directly, it exists only for the <em>Jackson</em>-binding infrastructure
   */
  @Deprecated
  @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
  static AcceptDataNode fromJson(Json json) {
    AcceptDataNode.Builder builder = AcceptDataNode.builder();
    if (json.cast != null) {
      builder.cast(json.cast);
    }
    if (json.fieldName != null) {
      builder.fieldName(json.fieldName);
    }
    return builder.build();
  }

  @SuppressWarnings("Immutable")
  private transient volatile long lazyInitBitmap;

  private static final long NODE_TYPE_LAZY_INIT_BIT = 0x1L;

  @SuppressWarnings("Immutable")
  private transient String nodeType;

  /**
   * {@inheritDoc}
   * <p>
   * Returns a lazily initialized value of the {@link IAcceptDataNode#getNodeType() nodeType} attribute.
   * Initialized once and only once and stored for subsequent access with proper synchronization.
   * In case of any exception or error thrown by the lazy value initializer,
   * the result will not be memoised (i.e. remembered) and on next call computation
   * will be attempted again.
   * @return A lazily initialized value of the {@code nodeType} attribute
   */
  @Override
  public String getNodeType() {
    if ((lazyInitBitmap & NODE_TYPE_LAZY_INIT_BIT) == 0) {
      synchronized (this) {
        if ((lazyInitBitmap & NODE_TYPE_LAZY_INIT_BIT) == 0) {
          this.nodeType = Objects.requireNonNull(IAcceptDataNode.super.getNodeType(), "nodeType");
          lazyInitBitmap |= NODE_TYPE_LAZY_INIT_BIT;
        }
      }
    }
    return nodeType;
  }

  /**
   * Creates an immutable copy of a {@link IAcceptDataNode} value.
   * Uses accessors to get values to initialize the new immutable instance.
   * If an instance is already immutable, it is returned as is.
   * @param instance The instance to copy
   * @return A copied immutable AcceptDataNode instance
   */
  public static AcceptDataNode copyOf(IAcceptDataNode instance) {
    if (instance instanceof AcceptDataNode) {
      return (AcceptDataNode) instance;
    }
    return AcceptDataNode.builder()
        .from(instance)
        .build();
  }

  /**
   * Creates a builder for {@link AcceptDataNode AcceptDataNode}.
   * <pre>
   * AcceptDataNode.builder()
   *    .cast(de.upb.sede.composition.graphs.nodes.ICastTypeNode | null) // nullable {@link IAcceptDataNode#getCast() cast}
   *    .fieldName(String) // required {@link IAcceptDataNode#getFieldName() fieldName}
   *    .build();
   * </pre>
   * @return A new AcceptDataNode builder
   */
  public static AcceptDataNode.Builder builder() {
    return new AcceptDataNode.Builder();
  }

  /**
   * Builds instances of type {@link AcceptDataNode AcceptDataNode}.
   * Initialize attributes and then invoke the {@link #build()} method to create an
   * immutable instance.
   * <p><em>{@code Builder} is not thread-safe and generally should not be stored in a field or collection,
   * but instead used immediately to create instances.</em>
   */
  @Generated(from = "IAcceptDataNode", generator = "Immutables")
  @NotThreadSafe
  public static final class Builder {
    private static final long INIT_BIT_FIELD_NAME = 0x1L;
    private long initBits = 0x1L;

    private @Nullable ICastTypeNode cast;
    private @Nullable String fieldName;

    private Builder() {
    }

    /**
     * Fill a builder with attribute values from the provided {@code MutableAcceptDataNode} instance.
     * @param instance The instance from which to copy values
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    public final Builder from(MutableAcceptDataNode instance) {
      Objects.requireNonNull(instance, "instance");
      @Nullable ICastTypeNode castValue = instance.getCast();
      if (castValue != null) {
        cast(castValue);
      }
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
     * Fill a builder with attribute values from the provided {@code de.upb.sede.composition.graphs.nodes.IAcceptDataNode} instance.
     * @param instance The instance from which to copy values
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    public final Builder from(IAcceptDataNode instance) {
      Objects.requireNonNull(instance, "instance");
      from((Object) instance);
      return this;
    }

    private void from(Object object) {
      if (object instanceof MutableAcceptDataNode) {
        from((MutableAcceptDataNode) object);
        return;
      }
      if (object instanceof IFieldContainer) {
        IFieldContainer instance = (IFieldContainer) object;
        fieldName(instance.getFieldName());
      }
      if (object instanceof IAcceptDataNode) {
        IAcceptDataNode instance = (IAcceptDataNode) object;
        @Nullable ICastTypeNode castValue = instance.getCast();
        if (castValue != null) {
          cast(castValue);
        }
      }
    }

    /**
     * Initializes the value for the {@link IAcceptDataNode#getCast() cast} attribute.
     * @param cast The value for cast (can be {@code null})
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    @JsonProperty("cast")
    public final Builder cast(@Nullable ICastTypeNode cast) {
      this.cast = cast;
      return this;
    }

    /**
     * Initializes the value for the {@link IAcceptDataNode#getFieldName() fieldName} attribute.
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
     * Builds a new {@link AcceptDataNode AcceptDataNode}.
     * @return An immutable instance of AcceptDataNode
     * @throws java.lang.IllegalStateException if any required attributes are missing
     */
    public AcceptDataNode build() {
      if (initBits != 0) {
        throw new IllegalStateException(formatRequiredAttributesMessage());
      }
      return new AcceptDataNode(cast, fieldName);
    }

    private String formatRequiredAttributesMessage() {
      List<String> attributes = new ArrayList<>();
      if ((initBits & INIT_BIT_FIELD_NAME) != 0) attributes.add("fieldName");
      return "Cannot build AcceptDataNode, some of required attributes are not set " + attributes;
    }
  }
}
