package de.upb.sede.exec;

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
 * Immutable implementation of {@link IExecutorContactInfo}.
 * <p>
 * Use the builder to create immutable instances:
 * {@code ExecutorContactInfo.builder()}.
 */
@Generated(from = "IExecutorContactInfo", generator = "Immutables")
@SuppressWarnings({"all"})
@ParametersAreNonnullByDefault
@javax.annotation.Generated("org.immutables.processor.ProxyProcessor")
@Immutable
@CheckReturnValue
public final class ExecutorContactInfo implements IExecutorContactInfo {
  private final @Nullable String hostAddress;
  private final String qualifier;
  private final String simpleName;

  private ExecutorContactInfo(ExecutorContactInfo.Builder builder) {
    this.hostAddress = builder.hostAddress;
    this.qualifier = builder.qualifier;
    this.simpleName = builder.simpleName != null
        ? builder.simpleName
        : Objects.requireNonNull(IExecutorContactInfo.super.getSimpleName(), "simpleName");
  }

  private ExecutorContactInfo(
      @Nullable String hostAddress,
      String qualifier,
      String simpleName) {
    this.hostAddress = hostAddress;
    this.qualifier = qualifier;
    this.simpleName = simpleName;
  }

  /**
   * @return The value of the {@code hostAddress} attribute
   */
  @JsonProperty("hostAddress")
  @Override
  public @Nullable String getHostAddress() {
    return hostAddress;
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
   * Copy the current immutable object by setting a value for the {@link IExecutorContactInfo#getHostAddress() hostAddress} attribute.
   * An equals check used to prevent copying of the same value by returning {@code this}.
   * @param value A new value for hostAddress (can be {@code null})
   * @return A modified copy of the {@code this} object
   */
  public final ExecutorContactInfo withHostAddress(@Nullable String value) {
    if (Objects.equals(this.hostAddress, value)) return this;
    return new ExecutorContactInfo(value, this.qualifier, this.simpleName);
  }

  /**
   * Copy the current immutable object by setting a value for the {@link IExecutorContactInfo#getQualifier() qualifier} attribute.
   * An equals check used to prevent copying of the same value by returning {@code this}.
   * @param value A new value for qualifier
   * @return A modified copy of the {@code this} object
   */
  public final ExecutorContactInfo withQualifier(String value) {
    String newValue = Objects.requireNonNull(value, "qualifier");
    if (this.qualifier.equals(newValue)) return this;
    return new ExecutorContactInfo(this.hostAddress, newValue, this.simpleName);
  }

  /**
   * Copy the current immutable object by setting a value for the {@link IExecutorContactInfo#getSimpleName() simpleName} attribute.
   * An equals check used to prevent copying of the same value by returning {@code this}.
   * @param value A new value for simpleName
   * @return A modified copy of the {@code this} object
   */
  public final ExecutorContactInfo withSimpleName(String value) {
    String newValue = Objects.requireNonNull(value, "simpleName");
    if (this.simpleName.equals(newValue)) return this;
    return new ExecutorContactInfo(this.hostAddress, this.qualifier, newValue);
  }

  /**
   * This instance is equal to all instances of {@code ExecutorContactInfo} that have equal attribute values.
   * @return {@code true} if {@code this} is equal to {@code another} instance
   */
  @Override
  public boolean equals(@Nullable Object another) {
    if (this == another) return true;
    return another instanceof ExecutorContactInfo
        && equalTo((ExecutorContactInfo) another);
  }

  private boolean equalTo(ExecutorContactInfo another) {
    return Objects.equals(hostAddress, another.hostAddress)
        && qualifier.equals(another.qualifier)
        && simpleName.equals(another.simpleName);
  }

  /**
   * Computes a hash code from attributes: {@code hostAddress}, {@code qualifier}, {@code simpleName}.
   * @return hashCode value
   */
  @Override
  public int hashCode() {
    @Var int h = 5381;
    h += (h << 5) + Objects.hashCode(hostAddress);
    h += (h << 5) + qualifier.hashCode();
    h += (h << 5) + simpleName.hashCode();
    return h;
  }

  /**
   * Prints the immutable value {@code ExecutorContactInfo} with attribute values.
   * @return A string representation of the value
   */
  @Override
  public String toString() {
    return MoreObjects.toStringHelper("ExecutorContactInfo")
        .omitNullValues()
        .add("hostAddress", hostAddress)
        .add("qualifier", qualifier)
        .add("simpleName", simpleName)
        .toString();
  }

  /**
   * Utility type used to correctly read immutable object from JSON representation.
   * @deprecated Do not use this type directly, it exists only for the <em>Jackson</em>-binding infrastructure
   */
  @Generated(from = "IExecutorContactInfo", generator = "Immutables")
  @Deprecated
  @SuppressWarnings("Immutable")
  @JsonDeserialize
  @JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.NONE)
  static final class Json implements IExecutorContactInfo {
    @Nullable String hostAddress;
    @Nullable String qualifier;
    @Nullable String simpleName;
    @JsonProperty("hostAddress")
    public void setHostAddress(@Nullable String hostAddress) {
      this.hostAddress = hostAddress;
    }
    @JsonProperty("qualifier")
    public void setQualifier(String qualifier) {
      this.qualifier = qualifier;
    }
    @JsonProperty("simpleName")
    public void setSimpleName(String simpleName) {
      this.simpleName = simpleName;
    }
    @Override
    public String getHostAddress() { throw new UnsupportedOperationException(); }
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
  static ExecutorContactInfo fromJson(Json json) {
    ExecutorContactInfo.Builder builder = ExecutorContactInfo.builder();
    if (json.hostAddress != null) {
      builder.hostAddress(json.hostAddress);
    }
    if (json.qualifier != null) {
      builder.qualifier(json.qualifier);
    }
    if (json.simpleName != null) {
      builder.simpleName(json.simpleName);
    }
    return builder.build();
  }

  /**
   * Creates an immutable copy of a {@link IExecutorContactInfo} value.
   * Uses accessors to get values to initialize the new immutable instance.
   * If an instance is already immutable, it is returned as is.
   * @param instance The instance to copy
   * @return A copied immutable ExecutorContactInfo instance
   */
  public static ExecutorContactInfo copyOf(IExecutorContactInfo instance) {
    if (instance instanceof ExecutorContactInfo) {
      return (ExecutorContactInfo) instance;
    }
    return ExecutorContactInfo.builder()
        .from(instance)
        .build();
  }

  /**
   * Creates a builder for {@link ExecutorContactInfo ExecutorContactInfo}.
   * <pre>
   * ExecutorContactInfo.builder()
   *    .hostAddress(String | null) // nullable {@link IExecutorContactInfo#getHostAddress() hostAddress}
   *    .qualifier(String) // required {@link IExecutorContactInfo#getQualifier() qualifier}
   *    .simpleName(String) // optional {@link IExecutorContactInfo#getSimpleName() simpleName}
   *    .build();
   * </pre>
   * @return A new ExecutorContactInfo builder
   */
  public static ExecutorContactInfo.Builder builder() {
    return new ExecutorContactInfo.Builder();
  }

  /**
   * Builds instances of type {@link ExecutorContactInfo ExecutorContactInfo}.
   * Initialize attributes and then invoke the {@link #build()} method to create an
   * immutable instance.
   * <p><em>{@code Builder} is not thread-safe and generally should not be stored in a field or collection,
   * but instead used immediately to create instances.</em>
   */
  @Generated(from = "IExecutorContactInfo", generator = "Immutables")
  @NotThreadSafe
  public static final class Builder {
    private static final long INIT_BIT_QUALIFIER = 0x1L;
    private long initBits = 0x1L;

    private @Nullable String hostAddress;
    private @Nullable String qualifier;
    private @Nullable String simpleName;

    private Builder() {
    }

    /**
     * Fill a builder with attribute values from the provided {@code MutableExecutorContactInfo} instance.
     * @param instance The instance from which to copy values
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    public final Builder from(MutableExecutorContactInfo instance) {
      Objects.requireNonNull(instance, "instance");
      @Nullable String hostAddressValue = instance.getHostAddress();
      if (hostAddressValue != null) {
        hostAddress(hostAddressValue);
      }
      if (instance.qualifierIsSet()) {
        qualifier(instance.getQualifier());
      }
      simpleName(instance.getSimpleName());
      return this;
    }

    /**
     * Fill a builder with attribute values from the provided {@code de.upb.sede.exec.IExecutorContactInfo} instance.
     * @param instance The instance from which to copy values
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    public final Builder from(IExecutorContactInfo instance) {
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
      if (object instanceof MutableExecutorContactInfo) {
        from((MutableExecutorContactInfo) object);
        return;
      }
      if (object instanceof IExecutorContactInfo) {
        IExecutorContactInfo instance = (IExecutorContactInfo) object;
        @Nullable String hostAddressValue = instance.getHostAddress();
        if (hostAddressValue != null) {
          hostAddress(hostAddressValue);
        }
      }
      if (object instanceof IQualifiable) {
        IQualifiable instance = (IQualifiable) object;
        simpleName(instance.getSimpleName());
        qualifier(instance.getQualifier());
      }
    }

    /**
     * Initializes the value for the {@link IExecutorContactInfo#getHostAddress() hostAddress} attribute.
     * @param hostAddress The value for hostAddress (can be {@code null})
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    @JsonProperty("hostAddress")
    public final Builder hostAddress(@Nullable String hostAddress) {
      this.hostAddress = hostAddress;
      return this;
    }

    /**
     * Initializes the value for the {@link IExecutorContactInfo#getQualifier() qualifier} attribute.
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
     * Initializes the value for the {@link IExecutorContactInfo#getSimpleName() simpleName} attribute.
     * <p><em>If not set, this attribute will have a default value as returned by the initializer of {@link IExecutorContactInfo#getSimpleName() simpleName}.</em>
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
     * Builds a new {@link ExecutorContactInfo ExecutorContactInfo}.
     * @return An immutable instance of ExecutorContactInfo
     * @throws java.lang.IllegalStateException if any required attributes are missing
     */
    public ExecutorContactInfo build() {
      if (initBits != 0) {
        throw new IllegalStateException(formatRequiredAttributesMessage());
      }
      return new ExecutorContactInfo(this);
    }

    private String formatRequiredAttributesMessage() {
      List<String> attributes = new ArrayList<>();
      if ((initBits & INIT_BIT_QUALIFIER) != 0) attributes.add("qualifier");
      return "Cannot build ExecutorContactInfo, some of required attributes are not set " + attributes;
    }
  }
}
