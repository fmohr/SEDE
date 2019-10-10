package de.upb.sede.exec;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
 * Immutable implementation of {@link IExecutorHandle}.
 * <p>
 * Use the builder to create immutable instances:
 * {@code ExecutorHandle.builder()}.
 */
@Generated(from = "IExecutorHandle", generator = "Immutables")
@SuppressWarnings({"all"})
@ParametersAreNonnullByDefault
@javax.annotation.Generated("org.immutables.processor.ProxyProcessor")
@Immutable
@CheckReturnValue
public final class ExecutorHandle implements IExecutorHandle {
  private final IExecutorContactInfo contactInfo;
  private final IExecutorCapabilities capabilities;
  private transient final String qualifier;
  private final String simpleName;

  private ExecutorHandle(ExecutorHandle.Builder builder) {
    this.contactInfo = builder.contactInfo;
    this.capabilities = builder.capabilities;
    if (builder.simpleName != null) {
      initShim.simpleName(builder.simpleName);
    }
    this.qualifier = initShim.getQualifier();
    this.simpleName = initShim.getSimpleName();
    this.initShim = null;
  }

  private ExecutorHandle(
      IExecutorContactInfo contactInfo,
      IExecutorCapabilities capabilities,
      String simpleName) {
    this.contactInfo = contactInfo;
    this.capabilities = capabilities;
    initShim.simpleName(simpleName);
    this.qualifier = initShim.getQualifier();
    this.simpleName = initShim.getSimpleName();
    this.initShim = null;
  }

  private static final byte STAGE_INITIALIZING = -1;
  private static final byte STAGE_UNINITIALIZED = 0;
  private static final byte STAGE_INITIALIZED = 1;
  @SuppressWarnings("Immutable")
  private transient volatile InitShim initShim = new InitShim();

  @Generated(from = "IExecutorHandle", generator = "Immutables")
  private final class InitShim {
    private byte qualifierBuildStage = STAGE_UNINITIALIZED;
    private String qualifier;

    String getQualifier() {
      if (qualifierBuildStage == STAGE_INITIALIZING) throw new IllegalStateException(formatInitCycleMessage());
      if (qualifierBuildStage == STAGE_UNINITIALIZED) {
        qualifierBuildStage = STAGE_INITIALIZING;
        this.qualifier = Objects.requireNonNull(getQualifierInitialize(), "qualifier");
        qualifierBuildStage = STAGE_INITIALIZED;
      }
      return this.qualifier;
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
      if (qualifierBuildStage == STAGE_INITIALIZING) attributes.add("qualifier");
      if (simpleNameBuildStage == STAGE_INITIALIZING) attributes.add("simpleName");
      return "Cannot build ExecutorHandle, attribute initializers form cycle " + attributes;
    }
  }

  private String getQualifierInitialize() {
    return IExecutorHandle.super.getQualifier();
  }

  private String getSimpleNameInitialize() {
    return IExecutorHandle.super.getSimpleName();
  }

  /**
   * @return The value of the {@code contactInfo} attribute
   */
  @JsonProperty("contactInfo")
  @Override
  public IExecutorContactInfo getContactInfo() {
    return contactInfo;
  }

  /**
   * @return The value of the {@code capabilities} attribute
   */
  @JsonProperty("capabilities")
  @Override
  public IExecutorCapabilities getCapabilities() {
    return capabilities;
  }

  /**
   * @return The computed-at-construction value of the {@code qualifier} attribute
   */
  @JsonProperty("qualifier")
  @Override
  public String getQualifier() {
    InitShim shim = this.initShim;
    return shim != null
        ? shim.getQualifier()
        : this.qualifier;
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
   * Copy the current immutable object by setting a value for the {@link IExecutorHandle#getContactInfo() contactInfo} attribute.
   * A shallow reference equality check is used to prevent copying of the same value by returning {@code this}.
   * @param value A new value for contactInfo
   * @return A modified copy of the {@code this} object
   */
  public final ExecutorHandle withContactInfo(IExecutorContactInfo value) {
    if (this.contactInfo == value) return this;
    IExecutorContactInfo newValue = Objects.requireNonNull(value, "contactInfo");
    return new ExecutorHandle(newValue, this.capabilities, this.simpleName);
  }

  /**
   * Copy the current immutable object by setting a value for the {@link IExecutorHandle#getCapabilities() capabilities} attribute.
   * A shallow reference equality check is used to prevent copying of the same value by returning {@code this}.
   * @param value A new value for capabilities
   * @return A modified copy of the {@code this} object
   */
  public final ExecutorHandle withCapabilities(IExecutorCapabilities value) {
    if (this.capabilities == value) return this;
    IExecutorCapabilities newValue = Objects.requireNonNull(value, "capabilities");
    return new ExecutorHandle(this.contactInfo, newValue, this.simpleName);
  }

  /**
   * Copy the current immutable object by setting a value for the {@link IExecutorHandle#getSimpleName() simpleName} attribute.
   * An equals check used to prevent copying of the same value by returning {@code this}.
   * @param value A new value for simpleName
   * @return A modified copy of the {@code this} object
   */
  public final ExecutorHandle withSimpleName(String value) {
    String newValue = Objects.requireNonNull(value, "simpleName");
    if (this.simpleName.equals(newValue)) return this;
    return new ExecutorHandle(this.contactInfo, this.capabilities, newValue);
  }

  /**
   * This instance is equal to all instances of {@code ExecutorHandle} that have equal attribute values.
   * @return {@code true} if {@code this} is equal to {@code another} instance
   */
  @Override
  public boolean equals(@Nullable Object another) {
    if (this == another) return true;
    return another instanceof ExecutorHandle
        && equalTo((ExecutorHandle) another);
  }

  private boolean equalTo(ExecutorHandle another) {
    return contactInfo.equals(another.contactInfo)
        && capabilities.equals(another.capabilities)
        && qualifier.equals(another.qualifier)
        && simpleName.equals(another.simpleName);
  }

  /**
   * Computes a hash code from attributes: {@code contactInfo}, {@code capabilities}, {@code qualifier}, {@code simpleName}.
   * @return hashCode value
   */
  @Override
  public int hashCode() {
    @Var int h = 5381;
    h += (h << 5) + contactInfo.hashCode();
    h += (h << 5) + capabilities.hashCode();
    h += (h << 5) + qualifier.hashCode();
    h += (h << 5) + simpleName.hashCode();
    return h;
  }

  /**
   * Prints the immutable value {@code ExecutorHandle} with attribute values.
   * @return A string representation of the value
   */
  @Override
  public String toString() {
    return MoreObjects.toStringHelper("ExecutorHandle")
        .omitNullValues()
        .add("contactInfo", contactInfo)
        .add("capabilities", capabilities)
        .add("qualifier", qualifier)
        .add("simpleName", simpleName)
        .toString();
  }

  /**
   * Utility type used to correctly read immutable object from JSON representation.
   * @deprecated Do not use this type directly, it exists only for the <em>Jackson</em>-binding infrastructure
   */
  @Generated(from = "IExecutorHandle", generator = "Immutables")
  @Deprecated
  @SuppressWarnings("Immutable")
  @JsonDeserialize
  @JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.NONE)
  static final class Json implements IExecutorHandle {
    @Nullable IExecutorContactInfo contactInfo;
    @Nullable IExecutorCapabilities capabilities;
    @Nullable String simpleName;
    @JsonProperty("contactInfo")
    public void setContactInfo(IExecutorContactInfo contactInfo) {
      this.contactInfo = contactInfo;
    }
    @JsonProperty("capabilities")
    public void setCapabilities(IExecutorCapabilities capabilities) {
      this.capabilities = capabilities;
    }
    @JsonProperty("simpleName")
    public void setSimpleName(String simpleName) {
      this.simpleName = simpleName;
    }
    @Override
    public IExecutorContactInfo getContactInfo() { throw new UnsupportedOperationException(); }
    @Override
    public IExecutorCapabilities getCapabilities() { throw new UnsupportedOperationException(); }
    @JsonIgnore
    @Override
    public String getQualifier() { throw new UnsupportedOperationException(); }
    @Override
    public String getSimpleName() { throw new UnsupportedOperationException(); }
  }

  /**
   * @param json A JSON-bindable data structure
   * @return An immutable value type
   * @deprecated Do not use this method directly, it exists only for the <em>Jackson</em>-binding infrastructure
   */
  @Deprecated
  @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
  static ExecutorHandle fromJson(Json json) {
    ExecutorHandle.Builder builder = ExecutorHandle.builder();
    if (json.contactInfo != null) {
      builder.contactInfo(json.contactInfo);
    }
    if (json.capabilities != null) {
      builder.capabilities(json.capabilities);
    }
    if (json.simpleName != null) {
      builder.simpleName(json.simpleName);
    }
    return builder.build();
  }

  /**
   * Creates an immutable copy of a {@link IExecutorHandle} value.
   * Uses accessors to get values to initialize the new immutable instance.
   * If an instance is already immutable, it is returned as is.
   * @param instance The instance to copy
   * @return A copied immutable ExecutorHandle instance
   */
  public static ExecutorHandle copyOf(IExecutorHandle instance) {
    if (instance instanceof ExecutorHandle) {
      return (ExecutorHandle) instance;
    }
    return ExecutorHandle.builder()
        .from(instance)
        .build();
  }

  /**
   * Creates a builder for {@link ExecutorHandle ExecutorHandle}.
   * <pre>
   * ExecutorHandle.builder()
   *    .contactInfo(de.upb.sede.exec.IExecutorContactInfo) // required {@link IExecutorHandle#getContactInfo() contactInfo}
   *    .capabilities(de.upb.sede.exec.IExecutorCapabilities) // required {@link IExecutorHandle#getCapabilities() capabilities}
   *    .simpleName(String) // optional {@link IExecutorHandle#getSimpleName() simpleName}
   *    .build();
   * </pre>
   * @return A new ExecutorHandle builder
   */
  public static ExecutorHandle.Builder builder() {
    return new ExecutorHandle.Builder();
  }

  /**
   * Builds instances of type {@link ExecutorHandle ExecutorHandle}.
   * Initialize attributes and then invoke the {@link #build()} method to create an
   * immutable instance.
   * <p><em>{@code Builder} is not thread-safe and generally should not be stored in a field or collection,
   * but instead used immediately to create instances.</em>
   */
  @Generated(from = "IExecutorHandle", generator = "Immutables")
  @NotThreadSafe
  public static final class Builder {
    private static final long INIT_BIT_CONTACT_INFO = 0x1L;
    private static final long INIT_BIT_CAPABILITIES = 0x2L;
    private long initBits = 0x3L;

    private @Nullable IExecutorContactInfo contactInfo;
    private @Nullable IExecutorCapabilities capabilities;
    private @Nullable String simpleName;

    private Builder() {
    }

    /**
     * Fill a builder with attribute values from the provided {@code MutableExecutorHandle} instance.
     * @param instance The instance from which to copy values
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    public final Builder from(MutableExecutorHandle instance) {
      Objects.requireNonNull(instance, "instance");
      if (instance.contactInfoIsSet()) {
        contactInfo(instance.getContactInfo());
      }
      if (instance.capabilitiesIsSet()) {
        capabilities(instance.getCapabilities());
      }
      simpleName(instance.getSimpleName());
      return this;
    }

    /**
     * Fill a builder with attribute values from the provided {@code de.upb.sede.exec.IExecutorHandle} instance.
     * @param instance The instance from which to copy values
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    public final Builder from(IExecutorHandle instance) {
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
      if (object instanceof MutableExecutorHandle) {
        from((MutableExecutorHandle) object);
        return;
      }
      if (object instanceof IExecutorHandle) {
        IExecutorHandle instance = (IExecutorHandle) object;
        contactInfo(instance.getContactInfo());
        capabilities(instance.getCapabilities());
      }
      if (object instanceof IQualifiable) {
        IQualifiable instance = (IQualifiable) object;
        simpleName(instance.getSimpleName());
      }
    }

    /**
     * Initializes the value for the {@link IExecutorHandle#getContactInfo() contactInfo} attribute.
     * @param contactInfo The value for contactInfo 
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    @JsonProperty("contactInfo")
    public final Builder contactInfo(IExecutorContactInfo contactInfo) {
      this.contactInfo = Objects.requireNonNull(contactInfo, "contactInfo");
      initBits &= ~INIT_BIT_CONTACT_INFO;
      return this;
    }

    /**
     * Initializes the value for the {@link IExecutorHandle#getCapabilities() capabilities} attribute.
     * @param capabilities The value for capabilities 
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    @JsonProperty("capabilities")
    public final Builder capabilities(IExecutorCapabilities capabilities) {
      this.capabilities = Objects.requireNonNull(capabilities, "capabilities");
      initBits &= ~INIT_BIT_CAPABILITIES;
      return this;
    }

    /**
     * Initializes the value for the {@link IExecutorHandle#getSimpleName() simpleName} attribute.
     * <p><em>If not set, this attribute will have a default value as returned by the initializer of {@link IExecutorHandle#getSimpleName() simpleName}.</em>
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
     * Builds a new {@link ExecutorHandle ExecutorHandle}.
     * @return An immutable instance of ExecutorHandle
     * @throws java.lang.IllegalStateException if any required attributes are missing
     */
    public ExecutorHandle build() {
      if (initBits != 0) {
        throw new IllegalStateException(formatRequiredAttributesMessage());
      }
      return new ExecutorHandle(this);
    }

    private String formatRequiredAttributesMessage() {
      List<String> attributes = new ArrayList<>();
      if ((initBits & INIT_BIT_CONTACT_INFO) != 0) attributes.add("contactInfo");
      if ((initBits & INIT_BIT_CAPABILITIES) != 0) attributes.add("capabilities");
      return "Cannot build ExecutorHandle, some of required attributes are not set " + attributes;
    }
  }
}
