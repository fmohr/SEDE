package de.upb.sede.composition.graphs.nodes;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableList;
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
 * Immutable implementation of {@link ICollectErrorsNode}.
 * <p>
 * Use the builder to create immutable instances:
 * {@code CollectErrorsNode.builder()}.
 */
@Generated(from = "ICollectErrorsNode", generator = "Immutables")
@SuppressWarnings({"all"})
@ParametersAreNonnullByDefault
@javax.annotation.Generated("org.immutables.processor.ProxyProcessor")
@Immutable
@CheckReturnValue
public final class CollectErrorsNode implements ICollectErrorsNode {
  private final ImmutableList<String> errorFields;
  private final String fieldName;

  private CollectErrorsNode(ImmutableList<String> errorFields, String fieldName) {
    this.errorFields = errorFields;
    this.fieldName = fieldName;
  }

  /**
   * @return The value of the {@code errorFields} attribute
   */
  @JsonProperty("errorFields")
  @Override
  public ImmutableList<String> getErrorFields() {
    return errorFields;
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
   * Copy the current immutable object with elements that replace the content of {@link ICollectErrorsNode#getErrorFields() errorFields}.
   * @param elements The elements to set
   * @return A modified copy of {@code this} object
   */
  public final CollectErrorsNode withErrorFields(String... elements) {
    ImmutableList<String> newValue = ImmutableList.copyOf(elements);
    return new CollectErrorsNode(newValue, this.fieldName);
  }

  /**
   * Copy the current immutable object with elements that replace the content of {@link ICollectErrorsNode#getErrorFields() errorFields}.
   * A shallow reference equality check is used to prevent copying of the same value by returning {@code this}.
   * @param elements An iterable of errorFields elements to set
   * @return A modified copy of {@code this} object
   */
  public final CollectErrorsNode withErrorFields(Iterable<String> elements) {
    if (this.errorFields == elements) return this;
    ImmutableList<String> newValue = ImmutableList.copyOf(elements);
    return new CollectErrorsNode(newValue, this.fieldName);
  }

  /**
   * Copy the current immutable object by setting a value for the {@link ICollectErrorsNode#getFieldName() fieldName} attribute.
   * An equals check used to prevent copying of the same value by returning {@code this}.
   * @param value A new value for fieldName
   * @return A modified copy of the {@code this} object
   */
  public final CollectErrorsNode withFieldName(String value) {
    String newValue = Objects.requireNonNull(value, "fieldName");
    if (this.fieldName.equals(newValue)) return this;
    return new CollectErrorsNode(this.errorFields, newValue);
  }

  /**
   * This instance is equal to all instances of {@code CollectErrorsNode} that have equal attribute values.
   * @return {@code true} if {@code this} is equal to {@code another} instance
   */
  @Override
  public boolean equals(@Nullable Object another) {
    if (this == another) return true;
    return another instanceof CollectErrorsNode
        && equalTo((CollectErrorsNode) another);
  }

  private boolean equalTo(CollectErrorsNode another) {
    return errorFields.equals(another.errorFields)
        && fieldName.equals(another.fieldName);
  }

  /**
   * Computes a hash code from attributes: {@code errorFields}, {@code fieldName}.
   * @return hashCode value
   */
  @Override
  public int hashCode() {
    @Var int h = 5381;
    h += (h << 5) + errorFields.hashCode();
    h += (h << 5) + fieldName.hashCode();
    return h;
  }

  /**
   * Prints the immutable value {@code CollectErrorsNode} with attribute values.
   * @return A string representation of the value
   */
  @Override
  public String toString() {
    return MoreObjects.toStringHelper("CollectErrorsNode")
        .omitNullValues()
        .add("errorFields", errorFields)
        .add("fieldName", fieldName)
        .toString();
  }

  /**
   * Utility type used to correctly read immutable object from JSON representation.
   * @deprecated Do not use this type directly, it exists only for the <em>Jackson</em>-binding infrastructure
   */
  @Generated(from = "ICollectErrorsNode", generator = "Immutables")
  @Deprecated
  @SuppressWarnings("Immutable")
  @JsonDeserialize
  @JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.NONE)
  static final class Json implements ICollectErrorsNode {
    @Nullable List<String> errorFields = ImmutableList.of();
    @Nullable String fieldName;
    @JsonProperty("errorFields")
    public void setErrorFields(List<String> errorFields) {
      this.errorFields = errorFields;
    }
    @JsonProperty("fieldName")
    public void setFieldName(String fieldName) {
      this.fieldName = fieldName;
    }
    @Override
    public List<String> getErrorFields() { throw new UnsupportedOperationException(); }
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
  static CollectErrorsNode fromJson(Json json) {
    CollectErrorsNode.Builder builder = CollectErrorsNode.builder();
    if (json.errorFields != null) {
      builder.addAllErrorFields(json.errorFields);
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
   * Returns a lazily initialized value of the {@link ICollectErrorsNode#getNodeType() nodeType} attribute.
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
          this.nodeType = Objects.requireNonNull(ICollectErrorsNode.super.getNodeType(), "nodeType");
          lazyInitBitmap |= NODE_TYPE_LAZY_INIT_BIT;
        }
      }
    }
    return nodeType;
  }

  /**
   * Creates an immutable copy of a {@link ICollectErrorsNode} value.
   * Uses accessors to get values to initialize the new immutable instance.
   * If an instance is already immutable, it is returned as is.
   * @param instance The instance to copy
   * @return A copied immutable CollectErrorsNode instance
   */
  public static CollectErrorsNode copyOf(ICollectErrorsNode instance) {
    if (instance instanceof CollectErrorsNode) {
      return (CollectErrorsNode) instance;
    }
    return CollectErrorsNode.builder()
        .from(instance)
        .build();
  }

  /**
   * Creates a builder for {@link CollectErrorsNode CollectErrorsNode}.
   * <pre>
   * CollectErrorsNode.builder()
   *    .addErrorFields|addAllErrorFields(String) // {@link ICollectErrorsNode#getErrorFields() errorFields} elements
   *    .fieldName(String) // required {@link ICollectErrorsNode#getFieldName() fieldName}
   *    .build();
   * </pre>
   * @return A new CollectErrorsNode builder
   */
  public static CollectErrorsNode.Builder builder() {
    return new CollectErrorsNode.Builder();
  }

  /**
   * Builds instances of type {@link CollectErrorsNode CollectErrorsNode}.
   * Initialize attributes and then invoke the {@link #build()} method to create an
   * immutable instance.
   * <p><em>{@code Builder} is not thread-safe and generally should not be stored in a field or collection,
   * but instead used immediately to create instances.</em>
   */
  @Generated(from = "ICollectErrorsNode", generator = "Immutables")
  @NotThreadSafe
  public static final class Builder {
    private static final long INIT_BIT_FIELD_NAME = 0x1L;
    private long initBits = 0x1L;

    private ImmutableList.Builder<String> errorFields = ImmutableList.builder();
    private @Nullable String fieldName;

    private Builder() {
    }

    /**
     * Fill a builder with attribute values from the provided {@code MutableCollectErrorsNode} instance.
     * @param instance The instance from which to copy values
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    public final Builder from(MutableCollectErrorsNode instance) {
      Objects.requireNonNull(instance, "instance");
      addAllErrorFields(instance.getErrorFields());
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
     * Fill a builder with attribute values from the provided {@code de.upb.sede.composition.graphs.nodes.ICollectErrorsNode} instance.
     * @param instance The instance from which to copy values
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    public final Builder from(ICollectErrorsNode instance) {
      Objects.requireNonNull(instance, "instance");
      from((Object) instance);
      return this;
    }

    private void from(Object object) {
      if (object instanceof MutableCollectErrorsNode) {
        from((MutableCollectErrorsNode) object);
        return;
      }
      if (object instanceof IFieldContainer) {
        IFieldContainer instance = (IFieldContainer) object;
        fieldName(instance.getFieldName());
      }
      if (object instanceof ICollectErrorsNode) {
        ICollectErrorsNode instance = (ICollectErrorsNode) object;
        addAllErrorFields(instance.getErrorFields());
      }
    }

    /**
     * Adds one element to {@link ICollectErrorsNode#getErrorFields() errorFields} list.
     * @param element A errorFields element
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    public final Builder addErrorFields(String element) {
      this.errorFields.add(element);
      return this;
    }

    /**
     * Adds elements to {@link ICollectErrorsNode#getErrorFields() errorFields} list.
     * @param elements An array of errorFields elements
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    public final Builder addErrorFields(String... elements) {
      this.errorFields.add(elements);
      return this;
    }


    /**
     * Sets or replaces all elements for {@link ICollectErrorsNode#getErrorFields() errorFields} list.
     * @param elements An iterable of errorFields elements
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    @JsonProperty("errorFields")
    public final Builder errorFields(Iterable<String> elements) {
      this.errorFields = ImmutableList.builder();
      return addAllErrorFields(elements);
    }

    /**
     * Adds elements to {@link ICollectErrorsNode#getErrorFields() errorFields} list.
     * @param elements An iterable of errorFields elements
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    public final Builder addAllErrorFields(Iterable<String> elements) {
      this.errorFields.addAll(elements);
      return this;
    }

    /**
     * Initializes the value for the {@link ICollectErrorsNode#getFieldName() fieldName} attribute.
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
     * Builds a new {@link CollectErrorsNode CollectErrorsNode}.
     * @return An immutable instance of CollectErrorsNode
     * @throws java.lang.IllegalStateException if any required attributes are missing
     */
    public CollectErrorsNode build() {
      if (initBits != 0) {
        throw new IllegalStateException(formatRequiredAttributesMessage());
      }
      return new CollectErrorsNode(errorFields.build(), fieldName);
    }

    private String formatRequiredAttributesMessage() {
      List<String> attributes = new ArrayList<>();
      if ((initBits & INIT_BIT_FIELD_NAME) != 0) attributes.add("fieldName");
      return "Cannot build CollectErrorsNode, some of required attributes are not set " + attributes;
    }
  }
}
