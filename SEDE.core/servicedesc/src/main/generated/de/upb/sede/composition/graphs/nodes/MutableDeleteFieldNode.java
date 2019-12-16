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
 * A modifiable implementation of the {@link IDeleteFieldNode IDeleteFieldNode} type.
 * <p>Use the {@link #create()} static factory methods to create new instances.
 * Use the {@link #toImmutable()} method to convert to canonical immutable instances.
 * <p><em>MutableDeleteFieldNode is not thread-safe</em>
 * @see DeleteFieldNode
 */
@Generated(from = "IDeleteFieldNode", generator = "Modifiables")
@SuppressWarnings({"all"})
@ParametersAreNonnullByDefault
@javax.annotation.Generated({"Modifiables.generator", "IDeleteFieldNode"})
@NotThreadSafe
public final class MutableDeleteFieldNode implements IDeleteFieldNode {
  private static final long INIT_BIT_FIELD_NAME = 0x1L;
  private long initBits = 0x1L;

  private String fieldName;

  private MutableDeleteFieldNode() {}

  /**
   * Construct a modifiable instance of {@code IDeleteFieldNode}.
   * @return A new modifiable instance
   */
  public static MutableDeleteFieldNode create() {
    return new MutableDeleteFieldNode();
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
  public MutableDeleteFieldNode clear() {
    initBits = 0x1L;
    fieldName = null;
    return this;
  }

  /**
   * Fill this modifiable instance with attribute values from the provided {@link de.upb.sede.IFieldContainer} instance.
   * @param instance The instance from which to copy values
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public MutableDeleteFieldNode from(IFieldContainer instance) {
    Objects.requireNonNull(instance, "instance");
    from((Object) instance);
    return this;
  }

  /**
   * Fill this modifiable instance with attribute values from the provided {@link de.upb.sede.composition.graphs.nodes.IDeleteFieldNode} instance.
   * @param instance The instance from which to copy values
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public MutableDeleteFieldNode from(IDeleteFieldNode instance) {
    Objects.requireNonNull(instance, "instance");
    from((Object) instance);
    return this;
  }

  /**
   * Fill this modifiable instance with attribute values from the provided {@link IDeleteFieldNode} instance.
   * Regular attribute values will be overridden, i.e. replaced with ones of an instance.
   * Any of the instance's absent optional values will not be copied (will not override current values).
   * @param instance The instance from which to copy values
   * @return {@code this} for use in a chained invocation
   */
  public MutableDeleteFieldNode from(MutableDeleteFieldNode instance) {
    Objects.requireNonNull(instance, "instance");
    from((Object) instance);
    return this;
  }

  private void from(Object object) {
    if (object instanceof MutableDeleteFieldNode) {
      MutableDeleteFieldNode instance = (MutableDeleteFieldNode) object;
      if (instance.fieldNameIsSet()) {
        setFieldName(instance.getFieldName());
      }
      return;
    }
    if (object instanceof IFieldContainer) {
      IFieldContainer instance = (IFieldContainer) object;
      setFieldName(instance.getFieldName());
    }
  }

  /**
   * Assigns a value to the {@link IDeleteFieldNode#getFieldName() fieldName} attribute.
   * @param fieldName The value for fieldName
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public MutableDeleteFieldNode setFieldName(String fieldName) {
    this.fieldName = Objects.requireNonNull(fieldName, "fieldName");
    initBits &= ~INIT_BIT_FIELD_NAME;
    return this;
  }

  /**
   * Returns {@code true} if the required attribute {@link IDeleteFieldNode#getFieldName() fieldName} is set.
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
  public final MutableDeleteFieldNode unsetFieldName() {
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
    return "DeleteFieldNode is not initialized, some of the required attributes are not set " + attributes;
  }

  /**
   * Converts to {@link DeleteFieldNode DeleteFieldNode}.
   * @return An immutable instance of DeleteFieldNode
   */
  public final DeleteFieldNode toImmutable() {
    checkRequiredAttributes();
    return DeleteFieldNode.copyOf(this);
  }

  /**
   * This instance is equal to all instances of {@code MutableDeleteFieldNode} that have equal attribute values.
   * An uninitialized instance is equal only to itself.
   * @return {@code true} if {@code this} is equal to {@code another} instance
   */
  @Override
  public boolean equals(@Nullable Object another) {
    if (this == another) return true;
    if (!(another instanceof MutableDeleteFieldNode)) return false;
    MutableDeleteFieldNode other = (MutableDeleteFieldNode) another;
    if (!isInitialized() || !other.isInitialized()) {
      return false;
    }
    return equalTo(other);
  }

  private boolean equalTo(MutableDeleteFieldNode another) {
    return fieldName.equals(another.fieldName);
  }

  /**
   * Computes a hash code from attributes: {@code fieldName}.
   * @return hashCode value
   */
  @Override
  public int hashCode() {
    int h = 5381;
    h += (h << 5) + fieldName.hashCode();
    return h;
  }

  /**
   * Generates a string representation of this {@code IDeleteFieldNode}.
   * If uninitialized, some attribute values may appear as question marks.
   * @return A string representation
   */
  @Override
  public String toString() {
    return MoreObjects.toStringHelper("MutableDeleteFieldNode")
        .add("fieldName", fieldNameIsSet() ? getFieldName() : "?")
        .toString();
  }
}
