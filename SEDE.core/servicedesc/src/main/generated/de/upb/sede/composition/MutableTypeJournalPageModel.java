package de.upb.sede.composition;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.MoreObjects;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import de.upb.sede.composition.graphs.types.TypeClass;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import javax.annotation.concurrent.NotThreadSafe;
import org.immutables.value.Generated;

/**
 * A modifiable implementation of the {@link ITypeJournalPageModel ITypeJournalPageModel} type.
 * <p>Use the {@link #create()} static factory methods to create new instances.
 * Use the {@link #toImmutable()} method to convert to canonical immutable instances.
 * <p><em>MutableTypeJournalPageModel is not thread-safe</em>
 * @see TypeJournalPageModel
 */
@Generated(from = "ITypeJournalPageModel", generator = "Modifiables")
@SuppressWarnings({"all"})
@ParametersAreNonnullByDefault
@javax.annotation.Generated({"Modifiables.generator", "ITypeJournalPageModel"})
@NotThreadSafe
public final class MutableTypeJournalPageModel implements ITypeJournalPageModel {
  private static final long INIT_BIT_FIELDNAME = 0x1L;
  private static final long INIT_BIT_TYPE = 0x2L;
  private long initBits = 0x3L;

  private String fieldname;
  private TypeClass type;

  private MutableTypeJournalPageModel() {}

  /**
   * Construct a modifiable instance of {@code ITypeJournalPageModel}.
   * @return A new modifiable instance
   */
  public static MutableTypeJournalPageModel create() {
    return new MutableTypeJournalPageModel();
  }

  /**
   * @return value of {@code fieldname} attribute
   */
  @JsonProperty("fieldname")
  @Override
  public final String getFieldname() {
    if (!fieldnameIsSet()) {
      checkRequiredAttributes();
    }
    return fieldname;
  }

  /**
   * @return value of {@code type} attribute
   */
  @JsonProperty("type")
  @Override
  public final TypeClass getType() {
    if (!typeIsSet()) {
      checkRequiredAttributes();
    }
    return type;
  }

  /**
   * Clears the object by setting all attributes to their initial values.
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public MutableTypeJournalPageModel clear() {
    initBits = 0x3L;
    fieldname = null;
    type = null;
    return this;
  }

  /**
   * Fill this modifiable instance with attribute values from the provided {@link ITypeJournalPageModel} instance.
   * Regular attribute values will be overridden, i.e. replaced with ones of an instance.
   * Any of the instance's absent optional values will not be copied (will not override current values).
   * @param instance The instance from which to copy values
   * @return {@code this} for use in a chained invocation
   */
  public MutableTypeJournalPageModel from(ITypeJournalPageModel instance) {
    Objects.requireNonNull(instance, "instance");
    if (instance instanceof MutableTypeJournalPageModel) {
      from((MutableTypeJournalPageModel) instance);
      return this;
    }
    setFieldname(instance.getFieldname());
    setType(instance.getType());
    return this;
  }

  /**
   * Fill this modifiable instance with attribute values from the provided {@link ITypeJournalPageModel} instance.
   * Regular attribute values will be overridden, i.e. replaced with ones of an instance.
   * Any of the instance's absent optional values will not be copied (will not override current values).
   * @param instance The instance from which to copy values
   * @return {@code this} for use in a chained invocation
   */
  public MutableTypeJournalPageModel from(MutableTypeJournalPageModel instance) {
    Objects.requireNonNull(instance, "instance");
    if (instance.fieldnameIsSet()) {
      setFieldname(instance.getFieldname());
    }
    if (instance.typeIsSet()) {
      setType(instance.getType());
    }
    return this;
  }

  /**
   * Assigns a value to the {@link ITypeJournalPageModel#getFieldname() fieldname} attribute.
   * @param fieldname The value for fieldname
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public MutableTypeJournalPageModel setFieldname(String fieldname) {
    this.fieldname = Objects.requireNonNull(fieldname, "fieldname");
    initBits &= ~INIT_BIT_FIELDNAME;
    return this;
  }

  /**
   * Assigns a value to the {@link ITypeJournalPageModel#getType() type} attribute.
   * @param type The value for type
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public MutableTypeJournalPageModel setType(TypeClass type) {
    this.type = Objects.requireNonNull(type, "type");
    initBits &= ~INIT_BIT_TYPE;
    return this;
  }

  /**
   * Returns {@code true} if the required attribute {@link ITypeJournalPageModel#getFieldname() fieldname} is set.
   * @return {@code true} if set
   */
  public final boolean fieldnameIsSet() {
    return (initBits & INIT_BIT_FIELDNAME) == 0;
  }

  /**
   * Returns {@code true} if the required attribute {@link ITypeJournalPageModel#getType() type} is set.
   * @return {@code true} if set
   */
  public final boolean typeIsSet() {
    return (initBits & INIT_BIT_TYPE) == 0;
  }


  /**
   * Reset an attribute to its initial value.
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public final MutableTypeJournalPageModel unsetFieldname() {
    initBits |= INIT_BIT_FIELDNAME;
    fieldname = null;
    return this;
  }

  /**
   * Reset an attribute to its initial value.
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public final MutableTypeJournalPageModel unsetType() {
    initBits |= INIT_BIT_TYPE;
    type = null;
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
    if (!fieldnameIsSet()) attributes.add("fieldname");
    if (!typeIsSet()) attributes.add("type");
    return "TypeJournalPageModel is not initialized, some of the required attributes are not set " + attributes;
  }

  /**
   * Converts to {@link TypeJournalPageModel TypeJournalPageModel}.
   * @return An immutable instance of TypeJournalPageModel
   */
  public final TypeJournalPageModel toImmutable() {
    checkRequiredAttributes();
    return TypeJournalPageModel.copyOf(this);
  }

  /**
   * This instance is equal to all instances of {@code MutableTypeJournalPageModel} that have equal attribute values.
   * An uninitialized instance is equal only to itself.
   * @return {@code true} if {@code this} is equal to {@code another} instance
   */
  @Override
  public boolean equals(@Nullable Object another) {
    if (this == another) return true;
    if (!(another instanceof MutableTypeJournalPageModel)) return false;
    MutableTypeJournalPageModel other = (MutableTypeJournalPageModel) another;
    if (!isInitialized() || !other.isInitialized()) {
      return false;
    }
    return equalTo(other);
  }

  private boolean equalTo(MutableTypeJournalPageModel another) {
    return fieldname.equals(another.fieldname)
        && type.equals(another.type);
  }

  /**
   * Computes a hash code from attributes: {@code fieldname}, {@code type}.
   * @return hashCode value
   */
  @Override
  public int hashCode() {
    int h = 5381;
    h += (h << 5) + fieldname.hashCode();
    h += (h << 5) + type.hashCode();
    return h;
  }

  /**
   * Generates a string representation of this {@code ITypeJournalPageModel}.
   * If uninitialized, some attribute values may appear as question marks.
   * @return A string representation
   */
  @Override
  public String toString() {
    return MoreObjects.toStringHelper("MutableTypeJournalPageModel")
        .add("fieldname", fieldnameIsSet() ? getFieldname() : "?")
        .add("type", typeIsSet() ? getType() : "?")
        .toString();
  }
}
