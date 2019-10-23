package de.upb.sede.composition.graphs.nodes;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.MoreObjects;
import com.google.common.primitives.Booleans;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import javax.annotation.concurrent.NotThreadSafe;
import org.immutables.value.Generated;

/**
 * A modifiable implementation of the {@link IServiceInstanceStorageNode IServiceInstanceStorageNode} type.
 * <p>Use the {@link #create()} static factory methods to create new instances.
 * Use the {@link #toImmutable()} method to convert to canonical immutable instances.
 * <p><em>MutableServiceInstanceStorageNode is not thread-safe</em>
 * @see ServiceInstanceStorageNode
 */
@Generated(from = "IServiceInstanceStorageNode", generator = "Modifiables")
@SuppressWarnings({"all"})
@ParametersAreNonnullByDefault
@javax.annotation.Generated({"Modifiables.generator", "IServiceInstanceStorageNode"})
@NotThreadSafe
public final class MutableServiceInstanceStorageNode
    implements IServiceInstanceStorageNode {
  private static final long INIT_BIT_SERVICE_INSTANCE_FIELD_NAME = 0x1L;
  private static final long INIT_BIT_SERVICE_CLASSPATH = 0x2L;
  private long initBits = 0x3L;

  private @Nullable String instanceId;
  private String serviceInstanceFieldName;
  private String serviceClasspath;
  private String nodeType;

  private MutableServiceInstanceStorageNode() {}

  /**
   * Construct a modifiable instance of {@code IServiceInstanceStorageNode}.
   * @return A new modifiable instance
   */
  public static MutableServiceInstanceStorageNode create() {
    return new MutableServiceInstanceStorageNode();
  }

  /**
   * @return value of {@code instanceId} attribute, may be {@code null}
   */
  @JsonProperty("instanceId")
  @Override
  public final @Nullable String getInstanceId() {
    return instanceId;
  }

  /**
   * @return value of {@code serviceInstanceFieldName} attribute
   */
  @JsonProperty("serviceInstanceFieldName")
  @Override
  public final String getServiceInstanceFieldName() {
    if (!serviceInstanceFieldNameIsSet()) {
      checkRequiredAttributes();
    }
    return serviceInstanceFieldName;
  }

  /**
   * @return value of {@code serviceClasspath} attribute
   */
  @JsonProperty("serviceClasspath")
  @Override
  public final String getServiceClasspath() {
    if (!serviceClasspathIsSet()) {
      checkRequiredAttributes();
    }
    return serviceClasspath;
  }

  /**
   * @return newly computed, not cached value of {@code isLoadInstruction} attribute
   */
  @JsonProperty("isLoadInstruction")
  @Override
  public final boolean isLoadInstruction() {
    return IServiceInstanceStorageNode.super.isLoadInstruction();
  }

  /**
   * @return assigned or, otherwise, newly computed, not cached value of {@code nodeType} attribute
   */
  @JsonProperty("nodeType")
  @Override
  public final String getNodeType() {
    return nodeTypeIsSet()
        ? nodeType
        : IServiceInstanceStorageNode.super.getNodeType();
  }

  /**
   * Clears the object by setting all attributes to their initial values.
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public MutableServiceInstanceStorageNode clear() {
    initBits = 0x3L;
    instanceId = null;
    serviceInstanceFieldName = null;
    serviceClasspath = null;
    nodeType = null;
    return this;
  }

  /**
   * Fill this modifiable instance with attribute values from the provided {@link de.upb.sede.composition.graphs.nodes.BaseNode} instance.
   * @param instance The instance from which to copy values
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public MutableServiceInstanceStorageNode from(BaseNode instance) {
    Objects.requireNonNull(instance, "instance");
    from((Object) instance);
    return this;
  }

  /**
   * Fill this modifiable instance with attribute values from the provided {@link de.upb.sede.composition.graphs.nodes.IServiceInstanceStorageNode} instance.
   * @param instance The instance from which to copy values
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public MutableServiceInstanceStorageNode from(IServiceInstanceStorageNode instance) {
    Objects.requireNonNull(instance, "instance");
    from((Object) instance);
    return this;
  }

  /**
   * Fill this modifiable instance with attribute values from the provided {@link IServiceInstanceStorageNode} instance.
   * Regular attribute values will be overridden, i.e. replaced with ones of an instance.
   * Any of the instance's absent optional values will not be copied (will not override current values).
   * @param instance The instance from which to copy values
   * @return {@code this} for use in a chained invocation
   */
  public MutableServiceInstanceStorageNode from(MutableServiceInstanceStorageNode instance) {
    Objects.requireNonNull(instance, "instance");
    from((Object) instance);
    return this;
  }

  private void from(Object object) {
    if (object instanceof MutableServiceInstanceStorageNode) {
      MutableServiceInstanceStorageNode instance = (MutableServiceInstanceStorageNode) object;
      @Nullable String instanceIdValue = instance.getInstanceId();
      if (instanceIdValue != null) {
        setInstanceId(instanceIdValue);
      }
      if (instance.serviceInstanceFieldNameIsSet()) {
        setServiceInstanceFieldName(instance.getServiceInstanceFieldName());
      }
      if (instance.serviceClasspathIsSet()) {
        setServiceClasspath(instance.getServiceClasspath());
      }
      setNodeType(instance.getNodeType());
      return;
    }
    if (object instanceof BaseNode) {
      BaseNode instance = (BaseNode) object;
      setNodeType(instance.getNodeType());
    }
    if (object instanceof IServiceInstanceStorageNode) {
      IServiceInstanceStorageNode instance = (IServiceInstanceStorageNode) object;
      setServiceInstanceFieldName(instance.getServiceInstanceFieldName());
      setServiceClasspath(instance.getServiceClasspath());
      @Nullable String instanceIdValue = instance.getInstanceId();
      if (instanceIdValue != null) {
        setInstanceId(instanceIdValue);
      }
    }
  }

  /**
   * Assigns a value to the {@link IServiceInstanceStorageNode#getInstanceId() instanceId} attribute.
   * @param instanceId The value for instanceId, can be {@code null}
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public MutableServiceInstanceStorageNode setInstanceId(@Nullable String instanceId) {
    this.instanceId = instanceId;
    return this;
  }

  /**
   * Assigns a value to the {@link IServiceInstanceStorageNode#getServiceInstanceFieldName() serviceInstanceFieldName} attribute.
   * @param serviceInstanceFieldName The value for serviceInstanceFieldName
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public MutableServiceInstanceStorageNode setServiceInstanceFieldName(String serviceInstanceFieldName) {
    this.serviceInstanceFieldName = Objects.requireNonNull(serviceInstanceFieldName, "serviceInstanceFieldName");
    initBits &= ~INIT_BIT_SERVICE_INSTANCE_FIELD_NAME;
    return this;
  }

  /**
   * Assigns a value to the {@link IServiceInstanceStorageNode#getServiceClasspath() serviceClasspath} attribute.
   * @param serviceClasspath The value for serviceClasspath
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public MutableServiceInstanceStorageNode setServiceClasspath(String serviceClasspath) {
    this.serviceClasspath = Objects.requireNonNull(serviceClasspath, "serviceClasspath");
    initBits &= ~INIT_BIT_SERVICE_CLASSPATH;
    return this;
  }

  /**
   * Assigns a value to the {@link IServiceInstanceStorageNode#getNodeType() nodeType} attribute.
   * <p><em>If not set, this attribute will have a default value returned by the initializer of {@link IServiceInstanceStorageNode#getNodeType() nodeType}.</em>
   * @param nodeType The value for nodeType
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public MutableServiceInstanceStorageNode setNodeType(String nodeType) {
    this.nodeType = Objects.requireNonNull(nodeType, "nodeType");
    return this;
  }

  /**
   * Returns {@code true} if the required attribute {@link IServiceInstanceStorageNode#getServiceInstanceFieldName() serviceInstanceFieldName} is set.
   * @return {@code true} if set
   */
  public final boolean serviceInstanceFieldNameIsSet() {
    return (initBits & INIT_BIT_SERVICE_INSTANCE_FIELD_NAME) == 0;
  }

  /**
   * Returns {@code true} if the required attribute {@link IServiceInstanceStorageNode#getServiceClasspath() serviceClasspath} is set.
   * @return {@code true} if set
   */
  public final boolean serviceClasspathIsSet() {
    return (initBits & INIT_BIT_SERVICE_CLASSPATH) == 0;
  }

  /**
   * Returns {@code true} if the default attribute {@link IServiceInstanceStorageNode#getNodeType() nodeType} is set.
   * @return {@code true} if set
   */
  public final boolean nodeTypeIsSet() {
    return nodeType != null;
  }


  /**
   * Reset an attribute to its initial value.
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public final MutableServiceInstanceStorageNode unsetServiceInstanceFieldName() {
    initBits |= INIT_BIT_SERVICE_INSTANCE_FIELD_NAME;
    serviceInstanceFieldName = null;
    return this;
  }

  /**
   * Reset an attribute to its initial value.
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public final MutableServiceInstanceStorageNode unsetServiceClasspath() {
    initBits |= INIT_BIT_SERVICE_CLASSPATH;
    serviceClasspath = null;
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
    if (!serviceInstanceFieldNameIsSet()) attributes.add("serviceInstanceFieldName");
    if (!serviceClasspathIsSet()) attributes.add("serviceClasspath");
    return "ServiceInstanceStorageNode is not initialized, some of the required attributes are not set " + attributes;
  }

  /**
   * Converts to {@link ServiceInstanceStorageNode ServiceInstanceStorageNode}.
   * @return An immutable instance of ServiceInstanceStorageNode
   */
  public final ServiceInstanceStorageNode toImmutable() {
    checkRequiredAttributes();
    return ServiceInstanceStorageNode.copyOf(this);
  }

  /**
   * This instance is equal to all instances of {@code MutableServiceInstanceStorageNode} that have equal attribute values.
   * An uninitialized instance is equal only to itself.
   * @return {@code true} if {@code this} is equal to {@code another} instance
   */
  @Override
  public boolean equals(@Nullable Object another) {
    if (this == another) return true;
    if (!(another instanceof MutableServiceInstanceStorageNode)) return false;
    MutableServiceInstanceStorageNode other = (MutableServiceInstanceStorageNode) another;
    if (!isInitialized() || !other.isInitialized()) {
      return false;
    }
    return equalTo(other);
  }

  private boolean equalTo(MutableServiceInstanceStorageNode another) {
    boolean isLoadInstruction = isLoadInstruction();
    String nodeType = getNodeType();
    return Objects.equals(instanceId, another.instanceId)
        && serviceInstanceFieldName.equals(another.serviceInstanceFieldName)
        && serviceClasspath.equals(another.serviceClasspath)
        && isLoadInstruction == another.isLoadInstruction()
        && nodeType.equals(another.getNodeType());
  }

  /**
   * Computes a hash code from attributes: {@code instanceId}, {@code serviceInstanceFieldName}, {@code serviceClasspath}, {@code isLoadInstruction}, {@code nodeType}.
   * @return hashCode value
   */
  @Override
  public int hashCode() {
    int h = 5381;
    h += (h << 5) + Objects.hashCode(instanceId);
    h += (h << 5) + serviceInstanceFieldName.hashCode();
    h += (h << 5) + serviceClasspath.hashCode();
    boolean isLoadInstruction = isLoadInstruction();
    h += (h << 5) + Booleans.hashCode(isLoadInstruction);
    String nodeType = getNodeType();
    h += (h << 5) + nodeType.hashCode();
    return h;
  }

  /**
   * Generates a string representation of this {@code IServiceInstanceStorageNode}.
   * If uninitialized, some attribute values may appear as question marks.
   * @return A string representation
   */
  @Override
  public String toString() {
    return MoreObjects.toStringHelper("MutableServiceInstanceStorageNode")
        .add("instanceId", getInstanceId())
        .add("serviceInstanceFieldName", serviceInstanceFieldNameIsSet() ? getServiceInstanceFieldName() : "?")
        .add("serviceClasspath", serviceClasspathIsSet() ? getServiceClasspath() : "?")
        .add("nodeType", getNodeType())
        .toString();
  }
}
