package de.upb.sede.exec;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableList;
import com.google.common.primitives.Booleans;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import com.google.errorprone.annotations.Var;
import de.upb.sede.ICommented;
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
 * Immutable implementation of {@link IMethodDesc}.
 * <p>
 * Use the builder to create immutable instances:
 * {@code MethodDesc.builder()}.
 */
@Generated(from = "IMethodDesc", generator = "Immutables")
@SuppressWarnings({"all"})
@ParametersAreNonnullByDefault
@javax.annotation.Generated("org.immutables.processor.ProxyProcessor")
@Immutable
@CheckReturnValue
public final class MethodDesc implements IMethodDesc {
  private final ImmutableList<ISignatureDesc> signatures;
  private final boolean isPure;
  private final String qualifier;
  private final String simpleName;
  private final ImmutableList<String> comments;

  private MethodDesc(MethodDesc.Builder builder) {
    this.signatures = builder.signatures.build();
    this.qualifier = builder.qualifier;
    this.comments = builder.comments.build();
    if (builder.isPureIsSet()) {
      initShim.isPure(builder.isPure);
    }
    if (builder.simpleName != null) {
      initShim.simpleName(builder.simpleName);
    }
    this.isPure = initShim.isPure();
    this.simpleName = initShim.getSimpleName();
    this.initShim = null;
  }

  private MethodDesc(
      ImmutableList<ISignatureDesc> signatures,
      boolean isPure,
      String qualifier,
      String simpleName,
      ImmutableList<String> comments) {
    this.signatures = signatures;
    this.isPure = isPure;
    this.qualifier = qualifier;
    this.simpleName = simpleName;
    this.comments = comments;
    this.initShim = null;
  }

  private static final byte STAGE_INITIALIZING = -1;
  private static final byte STAGE_UNINITIALIZED = 0;
  private static final byte STAGE_INITIALIZED = 1;
  @SuppressWarnings("Immutable")
  private transient volatile InitShim initShim = new InitShim();

  @Generated(from = "IMethodDesc", generator = "Immutables")
  private final class InitShim {
    private byte isPureBuildStage = STAGE_UNINITIALIZED;
    private boolean isPure;

    boolean isPure() {
      if (isPureBuildStage == STAGE_INITIALIZING) throw new IllegalStateException(formatInitCycleMessage());
      if (isPureBuildStage == STAGE_UNINITIALIZED) {
        isPureBuildStage = STAGE_INITIALIZING;
        this.isPure = isPureInitialize();
        isPureBuildStage = STAGE_INITIALIZED;
      }
      return this.isPure;
    }

    void isPure(boolean isPure) {
      this.isPure = isPure;
      isPureBuildStage = STAGE_INITIALIZED;
    }

    private byte simpleNameBuildStage = STAGE_UNINITIALIZED;
    private String simpleName;

    String getSimpleName() {
      if (simpleNameBuildStage == STAGE_INITIALIZING) throw new IllegalStateException(formatInitCycleMessage());
      if (simpleNameBuildStage == STAGE_UNINITIALIZED) {
        simpleNameBuildStage = STAGE_INITIALIZING;
        this.simpleName = Objects.requireNonNull(getSimpleNameInitialize(), "simpleName");
        simpleNameBuildStage = STAGE_INITIALIZED;
      }
      return this.simpleName;
    }

    void simpleName(String simpleName) {
      this.simpleName = simpleName;
      simpleNameBuildStage = STAGE_INITIALIZED;
    }

    private String formatInitCycleMessage() {
      List<String> attributes = new ArrayList<>();
      if (isPureBuildStage == STAGE_INITIALIZING) attributes.add("isPure");
      if (simpleNameBuildStage == STAGE_INITIALIZING) attributes.add("simpleName");
      return "Cannot build MethodDesc, attribute initializers form cycle " + attributes;
    }
  }

  private boolean isPureInitialize() {
    return IMethodDesc.super.isPure();
  }

  private String getSimpleNameInitialize() {
    return IMethodDesc.super.getSimpleName();
  }

  /**
   * @return The value of the {@code signatures} attribute
   */
  @JsonProperty("signatures")
  @Override
  public ImmutableList<ISignatureDesc> getSignatures() {
    return signatures;
  }

  /**
   * @return The value of the {@code isPure} attribute
   */
  @JsonProperty("isPure")
  @Override
  public boolean isPure() {
    InitShim shim = this.initShim;
    return shim != null
        ? shim.isPure()
        : this.isPure;
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
    InitShim shim = this.initShim;
    return shim != null
        ? shim.getSimpleName()
        : this.simpleName;
  }

  /**
   * @return The value of the {@code comments} attribute
   */
  @JsonProperty("comments")
  @Override
  public ImmutableList<String> getComments() {
    return comments;
  }

  /**
   * Copy the current immutable object with elements that replace the content of {@link IMethodDesc#getSignatures() signatures}.
   * @param elements The elements to set
   * @return A modified copy of {@code this} object
   */
  public final MethodDesc withSignatures(ISignatureDesc... elements) {
    ImmutableList<ISignatureDesc> newValue = ImmutableList.copyOf(elements);
    return new MethodDesc(newValue, this.isPure, this.qualifier, this.simpleName, this.comments);
  }

  /**
   * Copy the current immutable object with elements that replace the content of {@link IMethodDesc#getSignatures() signatures}.
   * A shallow reference equality check is used to prevent copying of the same value by returning {@code this}.
   * @param elements An iterable of signatures elements to set
   * @return A modified copy of {@code this} object
   */
  public final MethodDesc withSignatures(Iterable<? extends ISignatureDesc> elements) {
    if (this.signatures == elements) return this;
    ImmutableList<ISignatureDesc> newValue = ImmutableList.copyOf(elements);
    return new MethodDesc(newValue, this.isPure, this.qualifier, this.simpleName, this.comments);
  }

  /**
   * Copy the current immutable object by setting a value for the {@link IMethodDesc#isPure() isPure} attribute.
   * A value equality check is used to prevent copying of the same value by returning {@code this}.
   * @param value A new value for isPure
   * @return A modified copy of the {@code this} object
   */
  public final MethodDesc withIsPure(boolean value) {
    if (this.isPure == value) return this;
    return new MethodDesc(this.signatures, value, this.qualifier, this.simpleName, this.comments);
  }

  /**
   * Copy the current immutable object by setting a value for the {@link IMethodDesc#getQualifier() qualifier} attribute.
   * An equals check used to prevent copying of the same value by returning {@code this}.
   * @param value A new value for qualifier
   * @return A modified copy of the {@code this} object
   */
  public final MethodDesc withQualifier(String value) {
    String newValue = Objects.requireNonNull(value, "qualifier");
    if (this.qualifier.equals(newValue)) return this;
    return new MethodDesc(this.signatures, this.isPure, newValue, this.simpleName, this.comments);
  }

  /**
   * Copy the current immutable object by setting a value for the {@link IMethodDesc#getSimpleName() simpleName} attribute.
   * An equals check used to prevent copying of the same value by returning {@code this}.
   * @param value A new value for simpleName
   * @return A modified copy of the {@code this} object
   */
  public final MethodDesc withSimpleName(String value) {
    String newValue = Objects.requireNonNull(value, "simpleName");
    if (this.simpleName.equals(newValue)) return this;
    return new MethodDesc(this.signatures, this.isPure, this.qualifier, newValue, this.comments);
  }

  /**
   * Copy the current immutable object with elements that replace the content of {@link IMethodDesc#getComments() comments}.
   * @param elements The elements to set
   * @return A modified copy of {@code this} object
   */
  public final MethodDesc withComments(String... elements) {
    ImmutableList<String> newValue = ImmutableList.copyOf(elements);
    return new MethodDesc(this.signatures, this.isPure, this.qualifier, this.simpleName, newValue);
  }

  /**
   * Copy the current immutable object with elements that replace the content of {@link IMethodDesc#getComments() comments}.
   * A shallow reference equality check is used to prevent copying of the same value by returning {@code this}.
   * @param elements An iterable of comments elements to set
   * @return A modified copy of {@code this} object
   */
  public final MethodDesc withComments(Iterable<String> elements) {
    if (this.comments == elements) return this;
    ImmutableList<String> newValue = ImmutableList.copyOf(elements);
    return new MethodDesc(this.signatures, this.isPure, this.qualifier, this.simpleName, newValue);
  }

  /**
   * This instance is equal to all instances of {@code MethodDesc} that have equal attribute values.
   * @return {@code true} if {@code this} is equal to {@code another} instance
   */
  @Override
  public boolean equals(@Nullable Object another) {
    if (this == another) return true;
    return another instanceof MethodDesc
        && equalTo((MethodDesc) another);
  }

  private boolean equalTo(MethodDesc another) {
    return signatures.equals(another.signatures)
        && isPure == another.isPure
        && qualifier.equals(another.qualifier)
        && simpleName.equals(another.simpleName)
        && comments.equals(another.comments);
  }

  /**
   * Computes a hash code from attributes: {@code signatures}, {@code isPure}, {@code qualifier}, {@code simpleName}, {@code comments}.
   * @return hashCode value
   */
  @Override
  public int hashCode() {
    @Var int h = 5381;
    h += (h << 5) + signatures.hashCode();
    h += (h << 5) + Booleans.hashCode(isPure);
    h += (h << 5) + qualifier.hashCode();
    h += (h << 5) + simpleName.hashCode();
    h += (h << 5) + comments.hashCode();
    return h;
  }

  /**
   * Prints the immutable value {@code MethodDesc} with attribute values.
   * @return A string representation of the value
   */
  @Override
  public String toString() {
    return MoreObjects.toStringHelper("MethodDesc")
        .omitNullValues()
        .add("signatures", signatures)
        .add("isPure", isPure)
        .add("qualifier", qualifier)
        .add("simpleName", simpleName)
        .add("comments", comments)
        .toString();
  }

  /**
   * Utility type used to correctly read immutable object from JSON representation.
   * @deprecated Do not use this type directly, it exists only for the <em>Jackson</em>-binding infrastructure
   */
  @Generated(from = "IMethodDesc", generator = "Immutables")
  @Deprecated
  @SuppressWarnings("Immutable")
  @JsonDeserialize
  @JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.NONE)
  static final class Json implements IMethodDesc {
    @Nullable List<ISignatureDesc> signatures = ImmutableList.of();
    boolean isPure;
    boolean isPureIsSet;
    @Nullable String qualifier;
    @Nullable String simpleName;
    @Nullable List<String> comments = ImmutableList.of();
    @JsonProperty("signatures")
    public void setSignatures(List<ISignatureDesc> signatures) {
      this.signatures = signatures;
    }
    @JsonProperty("isPure")
    public void setIsPure(boolean isPure) {
      this.isPure = isPure;
      this.isPureIsSet = true;
    }
    @JsonProperty("qualifier")
    public void setQualifier(String qualifier) {
      this.qualifier = qualifier;
    }
    @JsonProperty("simpleName")
    public void setSimpleName(String simpleName) {
      this.simpleName = simpleName;
    }
    @JsonProperty("comments")
    public void setComments(List<String> comments) {
      this.comments = comments;
    }
    @Override
    public List<ISignatureDesc> getSignatures() { throw new UnsupportedOperationException(); }
    @Override
    public boolean isPure() { throw new UnsupportedOperationException(); }
    @Override
    public String getQualifier() { throw new UnsupportedOperationException(); }
    @Override
    public String getSimpleName() { throw new UnsupportedOperationException(); }
    @Override
    public List<String> getComments() { throw new UnsupportedOperationException(); }
  }

  /**
   * @param json A JSON-bindable data structure
   * @return An immutable value type
   * @deprecated Do not use this method directly, it exists only for the <em>Jackson</em>-binding infrastructure
   */
  @Deprecated
  @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
  static MethodDesc fromJson(Json json) {
    MethodDesc.Builder builder = MethodDesc.builder();
    if (json.signatures != null) {
      builder.addAllSignatures(json.signatures);
    }
    if (json.isPureIsSet) {
      builder.isPure(json.isPure);
    }
    if (json.qualifier != null) {
      builder.qualifier(json.qualifier);
    }
    if (json.simpleName != null) {
      builder.simpleName(json.simpleName);
    }
    if (json.comments != null) {
      builder.addAllComments(json.comments);
    }
    return builder.build();
  }

  /**
   * Creates an immutable copy of a {@link IMethodDesc} value.
   * Uses accessors to get values to initialize the new immutable instance.
   * If an instance is already immutable, it is returned as is.
   * @param instance The instance to copy
   * @return A copied immutable MethodDesc instance
   */
  public static MethodDesc copyOf(IMethodDesc instance) {
    if (instance instanceof MethodDesc) {
      return (MethodDesc) instance;
    }
    return MethodDesc.builder()
        .from(instance)
        .build();
  }

  /**
   * Creates a builder for {@link MethodDesc MethodDesc}.
   * <pre>
   * MethodDesc.builder()
   *    .addSignatures|addAllSignatures(de.upb.sede.exec.ISignatureDesc) // {@link IMethodDesc#getSignatures() signatures} elements
   *    .isPure(boolean) // optional {@link IMethodDesc#isPure() isPure}
   *    .qualifier(String) // required {@link IMethodDesc#getQualifier() qualifier}
   *    .simpleName(String) // optional {@link IMethodDesc#getSimpleName() simpleName}
   *    .addComments|addAllComments(String) // {@link IMethodDesc#getComments() comments} elements
   *    .build();
   * </pre>
   * @return A new MethodDesc builder
   */
  public static MethodDesc.Builder builder() {
    return new MethodDesc.Builder();
  }

  /**
   * Builds instances of type {@link MethodDesc MethodDesc}.
   * Initialize attributes and then invoke the {@link #build()} method to create an
   * immutable instance.
   * <p><em>{@code Builder} is not thread-safe and generally should not be stored in a field or collection,
   * but instead used immediately to create instances.</em>
   */
  @Generated(from = "IMethodDesc", generator = "Immutables")
  @NotThreadSafe
  public static final class Builder {
    private static final long INIT_BIT_QUALIFIER = 0x1L;
    private static final long OPT_BIT_IS_PURE = 0x1L;
    private long initBits = 0x1L;
    private long optBits;

    private ImmutableList.Builder<ISignatureDesc> signatures = ImmutableList.builder();
    private boolean isPure;
    private @Nullable String qualifier;
    private @Nullable String simpleName;
    private ImmutableList.Builder<String> comments = ImmutableList.builder();

    private Builder() {
    }

    /**
     * Fill a builder with attribute values from the provided {@code MutableMethodDesc} instance.
     * @param instance The instance from which to copy values
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    public final Builder from(MutableMethodDesc instance) {
      Objects.requireNonNull(instance, "instance");
      addAllSignatures(instance.getSignatures());
      isPure(instance.isPure());
      if (instance.qualifierIsSet()) {
        qualifier(instance.getQualifier());
      }
      simpleName(instance.getSimpleName());
      addAllComments(instance.getComments());
      return this;
    }

    /**
     * Fill a builder with attribute values from the provided {@code de.upb.sede.ICommented} instance.
     * @param instance The instance from which to copy values
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    public final Builder from(ICommented instance) {
      Objects.requireNonNull(instance, "instance");
      from((Object) instance);
      return this;
    }

    /**
     * Fill a builder with attribute values from the provided {@code de.upb.sede.exec.IMethodDesc} instance.
     * @param instance The instance from which to copy values
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    public final Builder from(IMethodDesc instance) {
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
      if (object instanceof MutableMethodDesc) {
        from((MutableMethodDesc) object);
        return;
      }
      if (object instanceof ICommented) {
        ICommented instance = (ICommented) object;
        addAllComments(instance.getComments());
      }
      if (object instanceof IMethodDesc) {
        IMethodDesc instance = (IMethodDesc) object;
        isPure(instance.isPure());
        addAllSignatures(instance.getSignatures());
      }
      if (object instanceof IQualifiable) {
        IQualifiable instance = (IQualifiable) object;
        simpleName(instance.getSimpleName());
        qualifier(instance.getQualifier());
      }
    }

    /**
     * Adds one element to {@link IMethodDesc#getSignatures() signatures} list.
     * @param element A signatures element
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    public final Builder addSignatures(ISignatureDesc element) {
      this.signatures.add(element);
      return this;
    }

    /**
     * Adds elements to {@link IMethodDesc#getSignatures() signatures} list.
     * @param elements An array of signatures elements
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    public final Builder addSignatures(ISignatureDesc... elements) {
      this.signatures.add(elements);
      return this;
    }


    /**
     * Sets or replaces all elements for {@link IMethodDesc#getSignatures() signatures} list.
     * @param elements An iterable of signatures elements
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    @JsonProperty("signatures")
    public final Builder signatures(Iterable<? extends ISignatureDesc> elements) {
      this.signatures = ImmutableList.builder();
      return addAllSignatures(elements);
    }

    /**
     * Adds elements to {@link IMethodDesc#getSignatures() signatures} list.
     * @param elements An iterable of signatures elements
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    public final Builder addAllSignatures(Iterable<? extends ISignatureDesc> elements) {
      this.signatures.addAll(elements);
      return this;
    }

    /**
     * Initializes the value for the {@link IMethodDesc#isPure() isPure} attribute.
     * <p><em>If not set, this attribute will have a default value as returned by the initializer of {@link IMethodDesc#isPure() isPure}.</em>
     * @param isPure The value for isPure 
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    @JsonProperty("isPure")
    public final Builder isPure(boolean isPure) {
      this.isPure = isPure;
      optBits |= OPT_BIT_IS_PURE;
      return this;
    }

    /**
     * Initializes the value for the {@link IMethodDesc#getQualifier() qualifier} attribute.
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
     * Initializes the value for the {@link IMethodDesc#getSimpleName() simpleName} attribute.
     * <p><em>If not set, this attribute will have a default value as returned by the initializer of {@link IMethodDesc#getSimpleName() simpleName}.</em>
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
     * Adds one element to {@link IMethodDesc#getComments() comments} list.
     * @param element A comments element
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    public final Builder addComments(String element) {
      this.comments.add(element);
      return this;
    }

    /**
     * Adds elements to {@link IMethodDesc#getComments() comments} list.
     * @param elements An array of comments elements
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    public final Builder addComments(String... elements) {
      this.comments.add(elements);
      return this;
    }


    /**
     * Sets or replaces all elements for {@link IMethodDesc#getComments() comments} list.
     * @param elements An iterable of comments elements
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    @JsonProperty("comments")
    public final Builder comments(Iterable<String> elements) {
      this.comments = ImmutableList.builder();
      return addAllComments(elements);
    }

    /**
     * Adds elements to {@link IMethodDesc#getComments() comments} list.
     * @param elements An iterable of comments elements
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    public final Builder addAllComments(Iterable<String> elements) {
      this.comments.addAll(elements);
      return this;
    }

    /**
     * Builds a new {@link MethodDesc MethodDesc}.
     * @return An immutable instance of MethodDesc
     * @throws java.lang.IllegalStateException if any required attributes are missing
     */
    public MethodDesc build() {
      if (initBits != 0) {
        throw new IllegalStateException(formatRequiredAttributesMessage());
      }
      return new MethodDesc(this);
    }

    private boolean isPureIsSet() {
      return (optBits & OPT_BIT_IS_PURE) != 0;
    }

    private String formatRequiredAttributesMessage() {
      List<String> attributes = new ArrayList<>();
      if ((initBits & INIT_BIT_QUALIFIER) != 0) attributes.add("qualifier");
      return "Cannot build MethodDesc, some of required attributes are not set " + attributes;
    }
  }
}
