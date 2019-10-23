package de.upb.sede.composition.graphs.nodes;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.MoreObjects;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import de.upb.sede.exec.IExecutorContactInfo;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import javax.annotation.concurrent.NotThreadSafe;
import org.immutables.value.Generated;

/**
 * A modifiable implementation of the {@link ISendGraphNode ISendGraphNode} type.
 * <p>Use the {@link #create()} static factory methods to create new instances.
 * Use the {@link #toImmutable()} method to convert to canonical immutable instances.
 * <p><em>MutableSendGraphNode is not thread-safe</em>
 * @see SendGraphNode
 */
@Generated(from = "ISendGraphNode", generator = "Modifiables")
@SuppressWarnings({"all"})
@ParametersAreNonnullByDefault
@javax.annotation.Generated({"Modifiables.generator", "ISendGraphNode"})
@NotThreadSafe
public final class MutableSendGraphNode implements ISendGraphNode {
  private static final long INIT_BIT_GRAPH = 0x1L;
  private static final long INIT_BIT_CONTACT_INFO = 0x2L;
  private long initBits = 0x3L;

  private String graph;
  private IExecutorContactInfo contactInfo;
  private String nodeType;

  private MutableSendGraphNode() {}

  /**
   * Construct a modifiable instance of {@code ISendGraphNode}.
   * @return A new modifiable instance
   */
  public static MutableSendGraphNode create() {
    return new MutableSendGraphNode();
  }

  /**
   * @return value of {@code graph} attribute
   */
  @JsonProperty("graph")
  @Override
  public final String getGraph() {
    if (!graphIsSet()) {
      checkRequiredAttributes();
    }
    return graph;
  }

  /**
   * @return value of {@code contactInfo} attribute
   */
  @JsonProperty("contactInfo")
  @Override
  public final IExecutorContactInfo getContactInfo() {
    if (!contactInfoIsSet()) {
      checkRequiredAttributes();
    }
    return contactInfo;
  }

  /**
   * @return assigned or, otherwise, newly computed, not cached value of {@code nodeType} attribute
   */
  @JsonProperty("nodeType")
  @Override
  public final String getNodeType() {
    return nodeTypeIsSet()
        ? nodeType
        : ISendGraphNode.super.getNodeType();
  }

  /**
   * Clears the object by setting all attributes to their initial values.
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public MutableSendGraphNode clear() {
    initBits = 0x3L;
    graph = null;
    contactInfo = null;
    nodeType = null;
    return this;
  }

  /**
   * Fill this modifiable instance with attribute values from the provided {@link de.upb.sede.composition.graphs.nodes.ISendGraphNode} instance.
   * @param instance The instance from which to copy values
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public MutableSendGraphNode from(ISendGraphNode instance) {
    Objects.requireNonNull(instance, "instance");
    from((Object) instance);
    return this;
  }

  /**
   * Fill this modifiable instance with attribute values from the provided {@link de.upb.sede.composition.graphs.nodes.BaseNode} instance.
   * @param instance The instance from which to copy values
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public MutableSendGraphNode from(BaseNode instance) {
    Objects.requireNonNull(instance, "instance");
    from((Object) instance);
    return this;
  }

  /**
   * Fill this modifiable instance with attribute values from the provided {@link ISendGraphNode} instance.
   * Regular attribute values will be overridden, i.e. replaced with ones of an instance.
   * Any of the instance's absent optional values will not be copied (will not override current values).
   * @param instance The instance from which to copy values
   * @return {@code this} for use in a chained invocation
   */
  public MutableSendGraphNode from(MutableSendGraphNode instance) {
    Objects.requireNonNull(instance, "instance");
    from((Object) instance);
    return this;
  }

  private void from(Object object) {
    if (object instanceof MutableSendGraphNode) {
      MutableSendGraphNode instance = (MutableSendGraphNode) object;
      if (instance.graphIsSet()) {
        setGraph(instance.getGraph());
      }
      if (instance.contactInfoIsSet()) {
        setContactInfo(instance.getContactInfo());
      }
      setNodeType(instance.getNodeType());
      return;
    }
    if (object instanceof ISendGraphNode) {
      ISendGraphNode instance = (ISendGraphNode) object;
      setContactInfo(instance.getContactInfo());
      setGraph(instance.getGraph());
    }
    if (object instanceof BaseNode) {
      BaseNode instance = (BaseNode) object;
      setNodeType(instance.getNodeType());
    }
  }

  /**
   * Assigns a value to the {@link ISendGraphNode#getGraph() graph} attribute.
   * @param graph The value for graph
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public MutableSendGraphNode setGraph(String graph) {
    this.graph = Objects.requireNonNull(graph, "graph");
    initBits &= ~INIT_BIT_GRAPH;
    return this;
  }

  /**
   * Assigns a value to the {@link ISendGraphNode#getContactInfo() contactInfo} attribute.
   * @param contactInfo The value for contactInfo
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public MutableSendGraphNode setContactInfo(IExecutorContactInfo contactInfo) {
    this.contactInfo = Objects.requireNonNull(contactInfo, "contactInfo");
    initBits &= ~INIT_BIT_CONTACT_INFO;
    return this;
  }

  /**
   * Assigns a value to the {@link ISendGraphNode#getNodeType() nodeType} attribute.
   * <p><em>If not set, this attribute will have a default value returned by the initializer of {@link ISendGraphNode#getNodeType() nodeType}.</em>
   * @param nodeType The value for nodeType
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public MutableSendGraphNode setNodeType(String nodeType) {
    this.nodeType = Objects.requireNonNull(nodeType, "nodeType");
    return this;
  }

  /**
   * Returns {@code true} if the required attribute {@link ISendGraphNode#getGraph() graph} is set.
   * @return {@code true} if set
   */
  public final boolean graphIsSet() {
    return (initBits & INIT_BIT_GRAPH) == 0;
  }

  /**
   * Returns {@code true} if the required attribute {@link ISendGraphNode#getContactInfo() contactInfo} is set.
   * @return {@code true} if set
   */
  public final boolean contactInfoIsSet() {
    return (initBits & INIT_BIT_CONTACT_INFO) == 0;
  }

  /**
   * Returns {@code true} if the default attribute {@link ISendGraphNode#getNodeType() nodeType} is set.
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
  public final MutableSendGraphNode unsetGraph() {
    initBits |= INIT_BIT_GRAPH;
    graph = null;
    return this;
  }

  /**
   * Reset an attribute to its initial value.
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public final MutableSendGraphNode unsetContactInfo() {
    initBits |= INIT_BIT_CONTACT_INFO;
    contactInfo = null;
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
    if (!graphIsSet()) attributes.add("graph");
    if (!contactInfoIsSet()) attributes.add("contactInfo");
    return "SendGraphNode is not initialized, some of the required attributes are not set " + attributes;
  }

  /**
   * Converts to {@link SendGraphNode SendGraphNode}.
   * @return An immutable instance of SendGraphNode
   */
  public final SendGraphNode toImmutable() {
    checkRequiredAttributes();
    return SendGraphNode.copyOf(this);
  }

  /**
   * This instance is equal to all instances of {@code MutableSendGraphNode} that have equal attribute values.
   * An uninitialized instance is equal only to itself.
   * @return {@code true} if {@code this} is equal to {@code another} instance
   */
  @Override
  public boolean equals(@Nullable Object another) {
    if (this == another) return true;
    if (!(another instanceof MutableSendGraphNode)) return false;
    MutableSendGraphNode other = (MutableSendGraphNode) another;
    if (!isInitialized() || !other.isInitialized()) {
      return false;
    }
    return equalTo(other);
  }

  private boolean equalTo(MutableSendGraphNode another) {
    String nodeType = getNodeType();
    return graph.equals(another.graph)
        && contactInfo.equals(another.contactInfo)
        && nodeType.equals(another.getNodeType());
  }

  /**
   * Computes a hash code from attributes: {@code graph}, {@code contactInfo}, {@code nodeType}.
   * @return hashCode value
   */
  @Override
  public int hashCode() {
    int h = 5381;
    h += (h << 5) + graph.hashCode();
    h += (h << 5) + contactInfo.hashCode();
    String nodeType = getNodeType();
    h += (h << 5) + nodeType.hashCode();
    return h;
  }

  /**
   * Generates a string representation of this {@code ISendGraphNode}.
   * If uninitialized, some attribute values may appear as question marks.
   * @return A string representation
   */
  @Override
  public String toString() {
    return MoreObjects.toStringHelper("MutableSendGraphNode")
        .add("graph", graphIsSet() ? getGraph() : "?")
        .add("contactInfo", contactInfoIsSet() ? getContactInfo() : "?")
        .add("nodeType", getNodeType())
        .toString();
  }
}
