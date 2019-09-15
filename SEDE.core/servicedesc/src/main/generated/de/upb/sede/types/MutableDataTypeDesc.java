package de.upb.sede.types;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.MoreObjects;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import de.upb.sede.ICommented;
import de.upb.sede.IQualifiable;
import de.upb.sede.types.aux.IJavaTypeAux;
import de.upb.sede.types.aux.IPythonTypeAux;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import javax.annotation.concurrent.NotThreadSafe;
import org.immutables.value.Generated;

/**
 * A modifiable implementation of the {@link IDataTypeDesc IDataTypeDesc} type.
 * <p>Use the {@link #create()} static factory methods to create new instances.
 * Use the {@link #toImmutable()} method to convert to canonical immutable instances.
 * <p><em>MutableDataTypeDesc is not thread-safe</em>
 * @see DataTypeDesc
 */
@Generated(from = "IDataTypeDesc", generator = "Modifiables")
@SuppressWarnings({"all"})
@ParametersAreNonnullByDefault
@javax.annotation.Generated({"Modifiables.generator", "IDataTypeDesc"})
@NotThreadSafe
public final class MutableDataTypeDesc implements IDataTypeDesc {
  private static final long INIT_BIT_SEMANTIC_TYPE = 0x1L;
  private static final long INIT_BIT_QUALIFIER = 0x2L;
  private long initBits = 0x3L;

  private String semanticType;
  private @Nullable IJavaTypeAux javaTypeAuxiliaries;
  private @Nullable IPythonTypeAux pythonTypeAuxiliaries;
  private String qualifier;
  private String simpleName;
  private final ArrayList<String> comments = new ArrayList<String>();

  private MutableDataTypeDesc() {}

  /**
   * Construct a modifiable instance of {@code IDataTypeDesc}.
   * @return A new modifiable instance
   */
  public static MutableDataTypeDesc create() {
    return new MutableDataTypeDesc();
  }

  /**
   * @return value of {@code semanticType} attribute
   */
  @JsonProperty("semanticType")
  @Override
  public final String getSemanticType() {
    if (!semanticTypeIsSet()) {
      checkRequiredAttributes();
    }
    return semanticType;
  }

  /**
   * @return value of {@code javaTypeAuxiliaries} attribute, may be {@code null}
   */
  @JsonProperty("javaTypeAuxiliaries")
  @Override
  public final @Nullable IJavaTypeAux getJavaTypeAuxiliaries() {
    return javaTypeAuxiliaries;
  }

  /**
   * @return value of {@code pythonTypeAuxiliaries} attribute, may be {@code null}
   */
  @JsonProperty("pythonTypeAuxiliaries")
  @Override
  public final @Nullable IPythonTypeAux getPythonTypeAuxiliaries() {
    return pythonTypeAuxiliaries;
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
        : IDataTypeDesc.super.getSimpleName();
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
   * Clears the object by setting all attributes to their initial values.
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public MutableDataTypeDesc clear() {
    initBits = 0x3L;
    semanticType = null;
    javaTypeAuxiliaries = null;
    pythonTypeAuxiliaries = null;
    qualifier = null;
    simpleName = null;
    comments.clear();
    return this;
  }

  /**
   * Fill this modifiable instance with attribute values from the provided {@link de.upb.sede.ICommented} instance.
   * @param instance The instance from which to copy values
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public MutableDataTypeDesc from(ICommented instance) {
    Objects.requireNonNull(instance, "instance");
    from((Object) instance);
    return this;
  }

  /**
   * Fill this modifiable instance with attribute values from the provided {@link de.upb.sede.types.IDataTypeDesc} instance.
   * @param instance The instance from which to copy values
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public MutableDataTypeDesc from(IDataTypeDesc instance) {
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
  public MutableDataTypeDesc from(IQualifiable instance) {
    Objects.requireNonNull(instance, "instance");
    from((Object) instance);
    return this;
  }

  /**
   * Fill this modifiable instance with attribute values from the provided {@link IDataTypeDesc} instance.
   * Regular attribute values will be overridden, i.e. replaced with ones of an instance.
   * Any of the instance's absent optional values will not be copied (will not override current values).
   * Collection elements and entries will be added, not replaced.
   * @param instance The instance from which to copy values
   * @return {@code this} for use in a chained invocation
   */
  public MutableDataTypeDesc from(MutableDataTypeDesc instance) {
    Objects.requireNonNull(instance, "instance");
    from((Object) instance);
    return this;
  }

  private void from(Object object) {
    if (object instanceof MutableDataTypeDesc) {
      MutableDataTypeDesc instance = (MutableDataTypeDesc) object;
      if (instance.semanticTypeIsSet()) {
        setSemanticType(instance.getSemanticType());
      }
      @Nullable IJavaTypeAux javaTypeAuxiliariesValue = instance.getJavaTypeAuxiliaries();
      if (javaTypeAuxiliariesValue != null) {
        setJavaTypeAuxiliaries(javaTypeAuxiliariesValue);
      }
      @Nullable IPythonTypeAux pythonTypeAuxiliariesValue = instance.getPythonTypeAuxiliaries();
      if (pythonTypeAuxiliariesValue != null) {
        setPythonTypeAuxiliaries(pythonTypeAuxiliariesValue);
      }
      if (instance.qualifierIsSet()) {
        setQualifier(instance.getQualifier());
      }
      setSimpleName(instance.getSimpleName());
      addAllComments(instance.getComments());
      return;
    }
    if (object instanceof ICommented) {
      ICommented instance = (ICommented) object;
      addAllComments(instance.getComments());
    }
    if (object instanceof IDataTypeDesc) {
      IDataTypeDesc instance = (IDataTypeDesc) object;
      @Nullable IPythonTypeAux pythonTypeAuxiliariesValue = instance.getPythonTypeAuxiliaries();
      if (pythonTypeAuxiliariesValue != null) {
        setPythonTypeAuxiliaries(pythonTypeAuxiliariesValue);
      }
      @Nullable IJavaTypeAux javaTypeAuxiliariesValue = instance.getJavaTypeAuxiliaries();
      if (javaTypeAuxiliariesValue != null) {
        setJavaTypeAuxiliaries(javaTypeAuxiliariesValue);
      }
      setSemanticType(instance.getSemanticType());
    }
    if (object instanceof IQualifiable) {
      IQualifiable instance = (IQualifiable) object;
      setSimpleName(instance.getSimpleName());
      setQualifier(instance.getQualifier());
    }
  }

  /**
   * Assigns a value to the {@link IDataTypeDesc#getSemanticType() semanticType} attribute.
   * @param semanticType The value for semanticType
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public MutableDataTypeDesc setSemanticType(String semanticType) {
    this.semanticType = Objects.requireNonNull(semanticType, "semanticType");
    initBits &= ~INIT_BIT_SEMANTIC_TYPE;
    return this;
  }

  /**
   * Assigns a value to the {@link IDataTypeDesc#getJavaTypeAuxiliaries() javaTypeAuxiliaries} attribute.
   * @param javaTypeAuxiliaries The value for javaTypeAuxiliaries, can be {@code null}
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public MutableDataTypeDesc setJavaTypeAuxiliaries(@Nullable IJavaTypeAux javaTypeAuxiliaries) {
    this.javaTypeAuxiliaries = javaTypeAuxiliaries;
    return this;
  }

  /**
   * Assigns a value to the {@link IDataTypeDesc#getPythonTypeAuxiliaries() pythonTypeAuxiliaries} attribute.
   * @param pythonTypeAuxiliaries The value for pythonTypeAuxiliaries, can be {@code null}
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public MutableDataTypeDesc setPythonTypeAuxiliaries(@Nullable IPythonTypeAux pythonTypeAuxiliaries) {
    this.pythonTypeAuxiliaries = pythonTypeAuxiliaries;
    return this;
  }

  /**
   * Assigns a value to the {@link IDataTypeDesc#getQualifier() qualifier} attribute.
   * @param qualifier The value for qualifier
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public MutableDataTypeDesc setQualifier(String qualifier) {
    this.qualifier = Objects.requireNonNull(qualifier, "qualifier");
    initBits &= ~INIT_BIT_QUALIFIER;
    return this;
  }

  /**
   * Assigns a value to the {@link IDataTypeDesc#getSimpleName() simpleName} attribute.
   * <p><em>If not set, this attribute will have a default value returned by the initializer of {@link IDataTypeDesc#getSimpleName() simpleName}.</em>
   * @param simpleName The value for simpleName
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public MutableDataTypeDesc setSimpleName(String simpleName) {
    this.simpleName = Objects.requireNonNull(simpleName, "simpleName");
    return this;
  }

  /**
   * Adds one element to {@link IDataTypeDesc#getComments() comments} list.
   * @param element The comments element
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public MutableDataTypeDesc addComments(String element) {
    Objects.requireNonNull(element, "comments element");
    this.comments.add(element);
    return this;
  }

  /**
   * Adds elements to {@link IDataTypeDesc#getComments() comments} list.
   * @param elements An array of comments elements
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public final MutableDataTypeDesc addComments(String... elements) {
    for (String e : elements) {
      addComments(e);
    }
    return this;
  }

  /**
   * Sets or replaces all elements for {@link IDataTypeDesc#getComments() comments} list.
   * @param elements An iterable of comments elements
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public MutableDataTypeDesc setComments(Iterable<String> elements) {
    this.comments.clear();
    addAllComments(elements);
    return this;
  }

  /**
   * Adds elements to {@link IDataTypeDesc#getComments() comments} list.
   * @param elements An iterable of comments elements
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public MutableDataTypeDesc addAllComments(Iterable<String> elements) {
    for (String e : elements) {
      addComments(e);
    }
    return this;
  }

  /**
   * Returns {@code true} if the required attribute {@link IDataTypeDesc#getSemanticType() semanticType} is set.
   * @return {@code true} if set
   */
  public final boolean semanticTypeIsSet() {
    return (initBits & INIT_BIT_SEMANTIC_TYPE) == 0;
  }

  /**
   * Returns {@code true} if the required attribute {@link IDataTypeDesc#getQualifier() qualifier} is set.
   * @return {@code true} if set
   */
  public final boolean qualifierIsSet() {
    return (initBits & INIT_BIT_QUALIFIER) == 0;
  }

  /**
   * Returns {@code true} if the default attribute {@link IDataTypeDesc#getSimpleName() simpleName} is set.
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
  public final MutableDataTypeDesc unsetSemanticType() {
    initBits |= INIT_BIT_SEMANTIC_TYPE;
    semanticType = null;
    return this;
  }

  /**
   * Reset an attribute to its initial value.
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public final MutableDataTypeDesc unsetQualifier() {
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
    if (!semanticTypeIsSet()) attributes.add("semanticType");
    if (!qualifierIsSet()) attributes.add("qualifier");
    return "DataTypeDesc is not initialized, some of the required attributes are not set " + attributes;
  }

  /**
   * Converts to {@link DataTypeDesc DataTypeDesc}.
   * @return An immutable instance of DataTypeDesc
   */
  public final DataTypeDesc toImmutable() {
    checkRequiredAttributes();
    return DataTypeDesc.copyOf(this);
  }

  /**
   * This instance is equal to all instances of {@code MutableDataTypeDesc} that have equal attribute values.
   * An uninitialized instance is equal only to itself.
   * @return {@code true} if {@code this} is equal to {@code another} instance
   */
  @Override
  public boolean equals(@Nullable Object another) {
    if (this == another) return true;
    if (!(another instanceof MutableDataTypeDesc)) return false;
    MutableDataTypeDesc other = (MutableDataTypeDesc) another;
    if (!isInitialized() || !other.isInitialized()) {
      return false;
    }
    return equalTo(other);
  }

  private boolean equalTo(MutableDataTypeDesc another) {
    String simpleName = getSimpleName();
    return semanticType.equals(another.semanticType)
        && Objects.equals(javaTypeAuxiliaries, another.javaTypeAuxiliaries)
        && Objects.equals(pythonTypeAuxiliaries, another.pythonTypeAuxiliaries)
        && qualifier.equals(another.qualifier)
        && simpleName.equals(another.getSimpleName())
        && comments.equals(another.comments);
  }

  /**
   * Computes a hash code from attributes: {@code semanticType}, {@code javaTypeAuxiliaries}, {@code pythonTypeAuxiliaries}, {@code qualifier}, {@code simpleName}, {@code comments}.
   * @return hashCode value
   */
  @Override
  public int hashCode() {
    int h = 5381;
    h += (h << 5) + semanticType.hashCode();
    h += (h << 5) + Objects.hashCode(javaTypeAuxiliaries);
    h += (h << 5) + Objects.hashCode(pythonTypeAuxiliaries);
    h += (h << 5) + qualifier.hashCode();
    String simpleName = getSimpleName();
    h += (h << 5) + simpleName.hashCode();
    h += (h << 5) + comments.hashCode();
    return h;
  }

  /**
   * Generates a string representation of this {@code IDataTypeDesc}.
   * If uninitialized, some attribute values may appear as question marks.
   * @return A string representation
   */
  @Override
  public String toString() {
    return MoreObjects.toStringHelper("MutableDataTypeDesc")
        .add("semanticType", semanticTypeIsSet() ? getSemanticType() : "?")
        .add("javaTypeAuxiliaries", getJavaTypeAuxiliaries())
        .add("pythonTypeAuxiliaries", getPythonTypeAuxiliaries())
        .add("qualifier", qualifierIsSet() ? getQualifier() : "?")
        .add("simpleName", getSimpleName())
        .add("comments", getComments())
        .toString();
  }
}
