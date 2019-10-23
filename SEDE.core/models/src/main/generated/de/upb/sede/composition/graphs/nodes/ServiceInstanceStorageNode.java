package de.upb.sede.composition.graphs.nodes;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.google.common.base.MoreObjects;
import com.google.common.primitives.Booleans;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import com.google.errorprone.annotations.Var;
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
 * Immutable implementation of {@link IServiceInstanceStorageNode}.
 * <p>
 * Use the builder to create immutable instances:
 * {@code ServiceInstanceStorageNode.builder()}.
 */
@Generated(from = "IServiceInstanceStorageNode", generator = "Immutables")
@SuppressWarnings({"all"})
@ParametersAreNonnullByDefault
@javax.annotation.Generated("org.immutables.processor.ProxyProcessor")
@Immutable
@CheckReturnValue
public final class ServiceInstanceStorageNode
    implements IServiceInstanceStorageNode {
  private final @Nullable String instanceId;
  private final String serviceInstanceFieldName;
  private final String serviceClasspath;
  private transient final boolean isLoadInstruction;
  private final String nodeType;

  private ServiceInstanceStorageNode(ServiceInstanceStorageNode.Builder builder) {
    this.instanceId = builder.instanceId;
    this.serviceInstanceFieldName = builder.serviceInstanceFieldName;
    this.serviceClasspath = builder.serviceClasspath;
    if (builder.nodeType != null) {
      initShim.nodeType(builder.nodeType);
    }
    this.isLoadInstruction = initShim.isLoadInstruction();
    this.nodeType = initShim.getNodeType();
    this.initShim = null;
  }

  private ServiceInstanceStorageNode(
      @Nullable String instanceId,
      String serviceInstanceFieldName,
      String serviceClasspath,
      String nodeType) {
    this.instanceId = instanceId;
    this.serviceInstanceFieldName = serviceInstanceFieldName;
    this.serviceClasspath = serviceClasspath;
    initShim.nodeType(nodeType);
    this.isLoadInstruction = initShim.isLoadInstruction();
    this.nodeType = initShim.getNodeType();
    this.initShim = null;
  }

  private static final byte STAGE_INITIALIZING = -1;
  private static final byte STAGE_UNINITIALIZED = 0;
  private static final byte STAGE_INITIALIZED = 1;
  @SuppressWarnings("Immutable")
  private transient volatile InitShim initShim = new InitShim();

  @Generated(from = "IServiceInstanceStorageNode", generator = "Immutables")
  private final class InitShim {
    private byte isLoadInstructionBuildStage = STAGE_UNINITIALIZED;
    private boolean isLoadInstruction;

    boolean isLoadInstruction() {
      if (isLoadInstructionBuildStage == STAGE_INITIALIZING) throw new IllegalStateException(formatInitCycleMessage());
      if (isLoadInstructionBuildStage == STAGE_UNINITIALIZED) {
        isLoadInstructionBuildStage = STAGE_INITIALIZING;
        this.isLoadInstruction = isLoadInstructionInitialize();
        isLoadInstructionBuildStage = STAGE_INITIALIZED;
      }
      return this.isLoadInstruction;
    }

    private byte nodeTypeBuildStage = STAGE_UNINITIALIZED;
    private String nodeType;

    String getNodeType() {
      if (nodeTypeBuildStage == STAGE_INITIALIZING) throw new IllegalStateException(formatInitCycleMessage());
      if (nodeTypeBuildStage == STAGE_UNINITIALIZED) {
        nodeTypeBuildStage = STAGE_INITIALIZING;
        this.nodeType = Objects.requireNonNull(getNodeTypeInitialize(), "nodeType");
        nodeTypeBuildStage = STAGE_INITIALIZED;
      }
      return this.nodeType;
    }

    void nodeType(String nodeType) {
      this.nodeType = nodeType;
      nodeTypeBuildStage = STAGE_INITIALIZED;
    }

    private String formatInitCycleMessage() {
      List<String> attributes = new ArrayList<>();
      if (isLoadInstructionBuildStage == STAGE_INITIALIZING) attributes.add("isLoadInstruction");
      if (nodeTypeBuildStage == STAGE_INITIALIZING) attributes.add("nodeType");
      return "Cannot build ServiceInstanceStorageNode, attribute initializers form cycle " + attributes;
    }
  }

  private boolean isLoadInstructionInitialize() {
    return IServiceInstanceStorageNode.super.isLoadInstruction();
  }

  private String getNodeTypeInitialize() {
    return IServiceInstanceStorageNode.super.getNodeType();
  }

  /**
   * @return The value of the {@code instanceId} attribute
   */
  @JsonProperty("instanceId")
  @Override
  public @Nullable String getInstanceId() {
    return instanceId;
  }

  /**
   * @return The value of the {@code serviceInstanceFieldName} attribute
   */
  @JsonProperty("serviceInstanceFieldName")
  @Override
  public String getServiceInstanceFieldName() {
    return serviceInstanceFieldName;
  }

  /**
   * @return The value of the {@code serviceClasspath} attribute
   */
  @JsonProperty("serviceClasspath")
  @Override
  public String getServiceClasspath() {
    return serviceClasspath;
  }

  /**
   * @return The computed-at-construction value of the {@code isLoadInstruction} attribute
   */
  @JsonProperty("isLoadInstruction")
  @Override
  public boolean isLoadInstruction() {
    InitShim shim = this.initShim;
    return shim != null
        ? shim.isLoadInstruction()
        : this.isLoadInstruction;
  }

  /**
   * @return The value of the {@code nodeType} attribute
   */
  @JsonProperty("nodeType")
  @Override
  public String getNodeType() {
    InitShim shim = this.initShim;
    return shim != null
        ? shim.getNodeType()
        : this.nodeType;
  }

  /**
   * Copy the current immutable object by setting a value for the {@link IServiceInstanceStorageNode#getInstanceId() instanceId} attribute.
   * An equals check used to prevent copying of the same value by returning {@code this}.
   * @param value A new value for instanceId (can be {@code null})
   * @return A modified copy of the {@code this} object
   */
  public final ServiceInstanceStorageNode withInstanceId(@Nullable String value) {
    if (Objects.equals(this.instanceId, value)) return this;
    return new ServiceInstanceStorageNode(value, this.serviceInstanceFieldName, this.serviceClasspath, this.nodeType);
  }

  /**
   * Copy the current immutable object by setting a value for the {@link IServiceInstanceStorageNode#getServiceInstanceFieldName() serviceInstanceFieldName} attribute.
   * An equals check used to prevent copying of the same value by returning {@code this}.
   * @param value A new value for serviceInstanceFieldName
   * @return A modified copy of the {@code this} object
   */
  public final ServiceInstanceStorageNode withServiceInstanceFieldName(String value) {
    String newValue = Objects.requireNonNull(value, "serviceInstanceFieldName");
    if (this.serviceInstanceFieldName.equals(newValue)) return this;
    return new ServiceInstanceStorageNode(this.instanceId, newValue, this.serviceClasspath, this.nodeType);
  }

  /**
   * Copy the current immutable object by setting a value for the {@link IServiceInstanceStorageNode#getServiceClasspath() serviceClasspath} attribute.
   * An equals check used to prevent copying of the same value by returning {@code this}.
   * @param value A new value for serviceClasspath
   * @return A modified copy of the {@code this} object
   */
  public final ServiceInstanceStorageNode withServiceClasspath(String value) {
    String newValue = Objects.requireNonNull(value, "serviceClasspath");
    if (this.serviceClasspath.equals(newValue)) return this;
    return new ServiceInstanceStorageNode(this.instanceId, this.serviceInstanceFieldName, newValue, this.nodeType);
  }

  /**
   * Copy the current immutable object by setting a value for the {@link IServiceInstanceStorageNode#getNodeType() nodeType} attribute.
   * An equals check used to prevent copying of the same value by returning {@code this}.
   * @param value A new value for nodeType
   * @return A modified copy of the {@code this} object
   */
  public final ServiceInstanceStorageNode withNodeType(String value) {
    String newValue = Objects.requireNonNull(value, "nodeType");
    if (this.nodeType.equals(newValue)) return this;
    return new ServiceInstanceStorageNode(this.instanceId, this.serviceInstanceFieldName, this.serviceClasspath, newValue);
  }

  /**
   * This instance is equal to all instances of {@code ServiceInstanceStorageNode} that have equal attribute values.
   * @return {@code true} if {@code this} is equal to {@code another} instance
   */
  @Override
  public boolean equals(@Nullable Object another) {
    if (this == another) return true;
    return another instanceof ServiceInstanceStorageNode
        && equalTo((ServiceInstanceStorageNode) another);
  }

  private boolean equalTo(ServiceInstanceStorageNode another) {
    return Objects.equals(instanceId, another.instanceId)
        && serviceInstanceFieldName.equals(another.serviceInstanceFieldName)
        && serviceClasspath.equals(another.serviceClasspath)
        && isLoadInstruction == another.isLoadInstruction
        && nodeType.equals(another.nodeType);
  }

  /**
   * Computes a hash code from attributes: {@code instanceId}, {@code serviceInstanceFieldName}, {@code serviceClasspath}, {@code isLoadInstruction}, {@code nodeType}.
   * @return hashCode value
   */
  @Override
  public int hashCode() {
    @Var int h = 5381;
    h += (h << 5) + Objects.hashCode(instanceId);
    h += (h << 5) + serviceInstanceFieldName.hashCode();
    h += (h << 5) + serviceClasspath.hashCode();
    h += (h << 5) + Booleans.hashCode(isLoadInstruction);
    h += (h << 5) + nodeType.hashCode();
    return h;
  }

  /**
   * Prints the immutable value {@code ServiceInstanceStorageNode} with attribute values.
   * @return A string representation of the value
   */
  @Override
  public String toString() {
    return MoreObjects.toStringHelper("ServiceInstanceStorageNode")
        .omitNullValues()
        .add("instanceId", instanceId)
        .add("serviceInstanceFieldName", serviceInstanceFieldName)
        .add("serviceClasspath", serviceClasspath)
        .add("isLoadInstruction", isLoadInstruction)
        .add("nodeType", nodeType)
        .toString();
  }

  /**
   * Utility type used to correctly read immutable object from JSON representation.
   * @deprecated Do not use this type directly, it exists only for the <em>Jackson</em>-binding infrastructure
   */
  @Generated(from = "IServiceInstanceStorageNode", generator = "Immutables")
  @Deprecated
  @SuppressWarnings("Immutable")
  @JsonDeserialize
  @JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.NONE)
  static final class Json implements IServiceInstanceStorageNode {
    @Nullable String instanceId;
    @Nullable String serviceInstanceFieldName;
    @Nullable String serviceClasspath;
    @Nullable String nodeType;
    @JsonProperty("instanceId")
    public void setInstanceId(@Nullable String instanceId) {
      this.instanceId = instanceId;
    }
    @JsonProperty("serviceInstanceFieldName")
    public void setServiceInstanceFieldName(String serviceInstanceFieldName) {
      this.serviceInstanceFieldName = serviceInstanceFieldName;
    }
    @JsonProperty("serviceClasspath")
    public void setServiceClasspath(String serviceClasspath) {
      this.serviceClasspath = serviceClasspath;
    }
    @JsonProperty("nodeType")
    public void setNodeType(String nodeType) {
      this.nodeType = nodeType;
    }
    @Override
    public String getInstanceId() { throw new UnsupportedOperationException(); }
    @Override
    public String getServiceInstanceFieldName() { throw new UnsupportedOperationException(); }
    @Override
    public String getServiceClasspath() { throw new UnsupportedOperationException(); }
    @JsonIgnore
    @Override
    public boolean isLoadInstruction() { throw new UnsupportedOperationException(); }
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
  static ServiceInstanceStorageNode fromJson(Json json) {
    ServiceInstanceStorageNode.Builder builder = ServiceInstanceStorageNode.builder();
    if (json.instanceId != null) {
      builder.instanceId(json.instanceId);
    }
    if (json.serviceInstanceFieldName != null) {
      builder.serviceInstanceFieldName(json.serviceInstanceFieldName);
    }
    if (json.serviceClasspath != null) {
      builder.serviceClasspath(json.serviceClasspath);
    }
    if (json.nodeType != null) {
      builder.nodeType(json.nodeType);
    }
    return builder.build();
  }

  /**
   * Creates an immutable copy of a {@link IServiceInstanceStorageNode} value.
   * Uses accessors to get values to initialize the new immutable instance.
   * If an instance is already immutable, it is returned as is.
   * @param instance The instance to copy
   * @return A copied immutable ServiceInstanceStorageNode instance
   */
  public static ServiceInstanceStorageNode copyOf(IServiceInstanceStorageNode instance) {
    if (instance instanceof ServiceInstanceStorageNode) {
      return (ServiceInstanceStorageNode) instance;
    }
    return ServiceInstanceStorageNode.builder()
        .from(instance)
        .build();
  }

  /**
   * Creates a builder for {@link ServiceInstanceStorageNode ServiceInstanceStorageNode}.
   * <pre>
   * ServiceInstanceStorageNode.builder()
   *    .instanceId(String | null) // nullable {@link IServiceInstanceStorageNode#getInstanceId() instanceId}
   *    .serviceInstanceFieldName(String) // required {@link IServiceInstanceStorageNode#getServiceInstanceFieldName() serviceInstanceFieldName}
   *    .serviceClasspath(String) // required {@link IServiceInstanceStorageNode#getServiceClasspath() serviceClasspath}
   *    .nodeType(String) // optional {@link IServiceInstanceStorageNode#getNodeType() nodeType}
   *    .build();
   * </pre>
   * @return A new ServiceInstanceStorageNode builder
   */
  public static ServiceInstanceStorageNode.Builder builder() {
    return new ServiceInstanceStorageNode.Builder();
  }

  /**
   * Builds instances of type {@link ServiceInstanceStorageNode ServiceInstanceStorageNode}.
   * Initialize attributes and then invoke the {@link #build()} method to create an
   * immutable instance.
   * <p><em>{@code Builder} is not thread-safe and generally should not be stored in a field or collection,
   * but instead used immediately to create instances.</em>
   */
  @Generated(from = "IServiceInstanceStorageNode", generator = "Immutables")
  @NotThreadSafe
  public static final class Builder {
    private static final long INIT_BIT_SERVICE_INSTANCE_FIELD_NAME = 0x1L;
    private static final long INIT_BIT_SERVICE_CLASSPATH = 0x2L;
    private long initBits = 0x3L;

    private @Nullable String instanceId;
    private @Nullable String serviceInstanceFieldName;
    private @Nullable String serviceClasspath;
    private @Nullable String nodeType;

    private Builder() {
    }

    /**
     * Fill a builder with attribute values from the provided {@code MutableServiceInstanceStorageNode} instance.
     * @param instance The instance from which to copy values
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    public final Builder from(MutableServiceInstanceStorageNode instance) {
      Objects.requireNonNull(instance, "instance");
      @Nullable String instanceIdValue = instance.getInstanceId();
      if (instanceIdValue != null) {
        instanceId(instanceIdValue);
      }
      if (instance.serviceInstanceFieldNameIsSet()) {
        serviceInstanceFieldName(instance.getServiceInstanceFieldName());
      }
      if (instance.serviceClasspathIsSet()) {
        serviceClasspath(instance.getServiceClasspath());
      }
      nodeType(instance.getNodeType());
      return this;
    }

    /**
     * Fill a builder with attribute values from the provided {@code de.upb.sede.composition.graphs.nodes.BaseNode} instance.
     * @param instance The instance from which to copy values
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    public final Builder from(BaseNode instance) {
      Objects.requireNonNull(instance, "instance");
      from((Object) instance);
      return this;
    }

    /**
     * Fill a builder with attribute values from the provided {@code de.upb.sede.composition.graphs.nodes.IServiceInstanceStorageNode} instance.
     * @param instance The instance from which to copy values
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    public final Builder from(IServiceInstanceStorageNode instance) {
      Objects.requireNonNull(instance, "instance");
      from((Object) instance);
      return this;
    }

    private void from(Object object) {
      if (object instanceof MutableServiceInstanceStorageNode) {
        from((MutableServiceInstanceStorageNode) object);
        return;
      }
      if (object instanceof BaseNode) {
        BaseNode instance = (BaseNode) object;
        nodeType(instance.getNodeType());
      }
      if (object instanceof IServiceInstanceStorageNode) {
        IServiceInstanceStorageNode instance = (IServiceInstanceStorageNode) object;
        serviceInstanceFieldName(instance.getServiceInstanceFieldName());
        serviceClasspath(instance.getServiceClasspath());
        @Nullable String instanceIdValue = instance.getInstanceId();
        if (instanceIdValue != null) {
          instanceId(instanceIdValue);
        }
      }
    }

    /**
     * Initializes the value for the {@link IServiceInstanceStorageNode#getInstanceId() instanceId} attribute.
     * @param instanceId The value for instanceId (can be {@code null})
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    @JsonProperty("instanceId")
    public final Builder instanceId(@Nullable String instanceId) {
      this.instanceId = instanceId;
      return this;
    }

    /**
     * Initializes the value for the {@link IServiceInstanceStorageNode#getServiceInstanceFieldName() serviceInstanceFieldName} attribute.
     * @param serviceInstanceFieldName The value for serviceInstanceFieldName 
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    @JsonProperty("serviceInstanceFieldName")
    public final Builder serviceInstanceFieldName(String serviceInstanceFieldName) {
      this.serviceInstanceFieldName = Objects.requireNonNull(serviceInstanceFieldName, "serviceInstanceFieldName");
      initBits &= ~INIT_BIT_SERVICE_INSTANCE_FIELD_NAME;
      return this;
    }

    /**
     * Initializes the value for the {@link IServiceInstanceStorageNode#getServiceClasspath() serviceClasspath} attribute.
     * @param serviceClasspath The value for serviceClasspath 
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    @JsonProperty("serviceClasspath")
    public final Builder serviceClasspath(String serviceClasspath) {
      this.serviceClasspath = Objects.requireNonNull(serviceClasspath, "serviceClasspath");
      initBits &= ~INIT_BIT_SERVICE_CLASSPATH;
      return this;
    }

    /**
     * Initializes the value for the {@link IServiceInstanceStorageNode#getNodeType() nodeType} attribute.
     * <p><em>If not set, this attribute will have a default value as returned by the initializer of {@link IServiceInstanceStorageNode#getNodeType() nodeType}.</em>
     * @param nodeType The value for nodeType 
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    @JsonProperty("nodeType")
    public final Builder nodeType(String nodeType) {
      this.nodeType = Objects.requireNonNull(nodeType, "nodeType");
      return this;
    }

    /**
     * Builds a new {@link ServiceInstanceStorageNode ServiceInstanceStorageNode}.
     * @return An immutable instance of ServiceInstanceStorageNode
     * @throws java.lang.IllegalStateException if any required attributes are missing
     */
    public ServiceInstanceStorageNode build() {
      if (initBits != 0) {
        throw new IllegalStateException(formatRequiredAttributesMessage());
      }
      return new ServiceInstanceStorageNode(this);
    }

    private String formatRequiredAttributesMessage() {
      List<String> attributes = new ArrayList<>();
      if ((initBits & INIT_BIT_SERVICE_INSTANCE_FIELD_NAME) != 0) attributes.add("serviceInstanceFieldName");
      if ((initBits & INIT_BIT_SERVICE_CLASSPATH) != 0) attributes.add("serviceClasspath");
      return "Cannot build ServiceInstanceStorageNode, some of required attributes are not set " + attributes;
    }
  }
}
