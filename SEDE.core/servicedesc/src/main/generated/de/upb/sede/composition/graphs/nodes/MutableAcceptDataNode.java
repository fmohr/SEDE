package de.upb.sede.composition.graphs.nodes;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.MoreObjects;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import de.upb.sede.IFieldContainer;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import javax.annotation.concurrent.NotThreadSafe;
import org.immutables.value.Generated;

/**
 * A modifiable implementation of the {@link IAcceptDataNode IAcceptDataNode} type.
 * <p>Use the {@link #create()} static factory methods to create new instances.
 * Use the {@link #toImmutable()} method to convert to canonical immutable instances.
 * <p><em>MutableAcceptDataNode is not thread-safe</em>
 * @see AcceptDataNode
 */
@Generated(from = "IAcceptDataNode", generator = "Modifiables")
@SuppressWarnings({"all"})
@ParametersAreNonnullByDefault
@javax.annotation.Generated({"Modifiables.generator", "IAcceptDataNode"})
@NotThreadSafe
public final class MutableAcceptDataNode implements IAcceptDataNode {
  private static final long INIT_BIT_FIELD_NAME = 0x1L;
  private long initBits = 0x1L;

  private @Nullable ICastTypeNode cast;
  private String fieldName;

  private MutableAcceptDataNode() {}

  /**
   * Construct a modifiable instance of {@code IAcceptDataNode}.
   * @return A new modifiable instance
   */
  public static MutableAcceptDataNode create() {
    return new MutableAcceptDataNode();
  }

  /**
   * @return value of {@code cast} attribute, may be {@code null}
   */
  @JsonProperty("cast")
  @Override
  public final @Nullable ICastTypeNode getCast() {
    return cast;
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
  public MutableAcceptDataNode clear() {
    initBits = 0x1L;
    cast = null;
    fieldName = null;
    return this;
  }

  /**
   * Fill this modifiable instance with attribute values from the provided {@link de.upb.sede.IFieldContainer} instance.
   * @param instance The instance from which to copy values
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public MutableAcceptDataNode from(IFieldContainer instance) {
    Objects.requireNonNull(instance, "instance");
    from((Object) instance);
    return this;
  }

  /**
   * Fill this modifiable instance with attribute values from the provided {@link de.upb.sede.composition.graphs.nodes.IAcceptDataNode} instance.
   * @param instance The instance from which to copy values
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public MutableAcceptDataNode from(IAcceptDataNode instance) {
    Objects.requireNonNull(instance, "instance");
    from((Object) instance);
    return this;
  }

  /**
   * Fill this modifiable instance with attribute values from the provided {@link IAcceptDataNode} instance.
   * Regular attribute values will be overridden, i.e. replaced with ones of an instance.
   * Any of the instance's absent optional values will not be copied (will not override current values).
   * @param instance The instance from which to copy values
   * @return {@code this} for use in a chained invocation
   */
  public MutableAcceptDataNode from(MutableAcceptDataNode instance) {
    Objects.requireNonNull(instance, "instance");
    from((Object) instance);
    return this;
  }

  private void from(Object object) {
    if (object instanceof MutableAcceptDataNode) {
      MutableAcceptDataNode instance = (MutableAcceptDataNode) object;
      @Nullable ICastTypeNode castValue = instance.getCast();
      if (castValue != null) {
        setCast(castValue);
      }
      if (instance.fieldNameIsSet()) {
        setFieldName(instance.getFieldName());
      }
      return;
    }
    if (object instanceof IFieldContainer) {
      IFieldContainer instance = (IFieldContainer) object;
      setFieldName(instance.getFieldName());
    }
    if (object instanceof IAcceptDataNode) {
      IAcceptDataNode instance = (IAcceptDataNode) object;
      @Nullable ICastTypeNode castValue = instance.getCast();
      if (castValue != null) {
        setCast(castValue);
      }
    }
  }

  /**
   * Assigns a value to the {@link IAcceptDataNode#getCast() cast} attribute.
   * @param cast The value for cast, can be {@code null}
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public MutableAcceptDataNode setCast(@Nullable ICastTypeNode cast) {
    this.cast = cast;
    return this;
  }

  /**
   * Assigns a value to the {@link IAcceptDataNode#getFieldName() fieldName} attribute.
   * @param fieldName The value for fieldName
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public MutableAcceptDataNode setFieldName(String fieldName) {
    this.fieldName = Objects.requireNonNull(fieldName, "fieldName");
    initBits &= ~INIT_BIT_FIELD_NAME;
    return this;
  }

  /**
   * Returns {@code true} if the required attribute {@link IAcceptDataNode#getFieldName() fieldName} is set.
   * @return {@code true} if set
   */
  public final boolean fieldNameIsSet() {
    return (initBits & INIT_BIT_FIELD_NAME) == 0;
  }


  /**
   * Reset an attribute to its initial value.
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public final MutableAcceptDataNode unsetFieldName() {
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
    if (!fieldNameIsSet()) attributes.add("fieldName");
    return "AcceptDataNode is not initialized, some of the required attributes are not set " + attributes;
  }

  /**
   * Converts to {@link AcceptDataNode AcceptDataNode}.
   * @return An immutable instance of AcceptDataNode
   */
  public final AcceptDataNode toImmutable() {
    checkRequiredAttributes();
    return AcceptDataNode.copyOf(this);
  }

  /**
   * This instance is equal to all instances of {@code MutableAcceptDataNode} that have equal attribute values.
   * An uninitialized instance is equal only to itself.
   * @return {@code true} if {@code this} is equal to {@code another} instance
   */
  @Override
  public boolean equals(@Nullable Object another) {
    if (this == another) return true;
    if (!(another instanceof MutableAcceptDataNode)) return false;
    MutableAcceptDataNode other = (MutableAcceptDataNode) another;
    if (!isInitialized() || !other.isInitialized()) {
      return false;
    }
    return equalTo(other);
  }

  private boolean equalTo(MutableAcceptDataNode another) {
    return Objects.equals(cast, another.cast)
        && fieldName.equals(another.fieldName);
  }

  /**
   * Computes a hash code from attributes: {@code cast}, {@code fieldName}.
   * @return hashCode value
   */
  @Override
  public int hashCode() {
    int h = 5381;
    h += (h << 5) + Objects.hashCode(cast);
    h += (h << 5) + fieldName.hashCode();
    return h;
  }

  /**
   * Generates a string representation of this {@code IAcceptDataNode}.
   * If uninitialized, some attribute values may appear as question marks.
   * @return A string representation
   */
  @Override
  public String toString() {
    return MoreObjects.toStringHelper("MutableAcceptDataNode")
        .add("cast", getCast())
        .add("fieldName", fieldNameIsSet() ? getFieldName() : "?")
        .toString();
  }
}
