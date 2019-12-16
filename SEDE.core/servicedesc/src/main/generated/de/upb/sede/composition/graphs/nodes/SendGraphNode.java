package de.upb.sede.composition.graphs.nodes;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.google.common.base.MoreObjects;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import com.google.errorprone.annotations.Var;
import de.upb.sede.exec.IExecutorContactInfo;
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
 * Immutable implementation of {@link ISendGraphNode}.
 * <p>
 * Use the builder to create immutable instances:
 * {@code SendGraphNode.builder()}.
 */
@Generated(from = "ISendGraphNode", generator = "Immutables")
@SuppressWarnings({"all"})
@ParametersAreNonnullByDefault
@javax.annotation.Generated("org.immutables.processor.ProxyProcessor")
@Immutable
@CheckReturnValue
public final class SendGraphNode implements ISendGraphNode {
  private final String graph;
  private final IExecutorContactInfo contactInfo;

  private SendGraphNode(String graph, IExecutorContactInfo contactInfo) {
    this.graph = graph;
    this.contactInfo = contactInfo;
  }

  /**
   * @return The value of the {@code graph} attribute
   */
  @JsonProperty("graph")
  @Override
  public String getGraph() {
    return graph;
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
   * Copy the current immutable object by setting a value for the {@link ISendGraphNode#getGraph() graph} attribute.
   * An equals check used to prevent copying of the same value by returning {@code this}.
   * @param value A new value for graph
   * @return A modified copy of the {@code this} object
   */
  public final SendGraphNode withGraph(String value) {
    String newValue = Objects.requireNonNull(value, "graph");
    if (this.graph.equals(newValue)) return this;
    return new SendGraphNode(newValue, this.contactInfo);
  }

  /**
   * Copy the current immutable object by setting a value for the {@link ISendGraphNode#getContactInfo() contactInfo} attribute.
   * A shallow reference equality check is used to prevent copying of the same value by returning {@code this}.
   * @param value A new value for contactInfo
   * @return A modified copy of the {@code this} object
   */
  public final SendGraphNode withContactInfo(IExecutorContactInfo value) {
    if (this.contactInfo == value) return this;
    IExecutorContactInfo newValue = Objects.requireNonNull(value, "contactInfo");
    return new SendGraphNode(this.graph, newValue);
  }

  /**
   * This instance is equal to all instances of {@code SendGraphNode} that have equal attribute values.
   * @return {@code true} if {@code this} is equal to {@code another} instance
   */
  @Override
  public boolean equals(@Nullable Object another) {
    if (this == another) return true;
    return another instanceof SendGraphNode
        && equalTo((SendGraphNode) another);
  }

  private boolean equalTo(SendGraphNode another) {
    return graph.equals(another.graph)
        && contactInfo.equals(another.contactInfo);
  }

  /**
   * Computes a hash code from attributes: {@code graph}, {@code contactInfo}.
   * @return hashCode value
   */
  @Override
  public int hashCode() {
    @Var int h = 5381;
    h += (h << 5) + graph.hashCode();
    h += (h << 5) + contactInfo.hashCode();
    return h;
  }

  /**
   * Prints the immutable value {@code SendGraphNode} with attribute values.
   * @return A string representation of the value
   */
  @Override
  public String toString() {
    return MoreObjects.toStringHelper("SendGraphNode")
        .omitNullValues()
        .add("graph", graph)
        .add("contactInfo", contactInfo)
        .toString();
  }

  /**
   * Utility type used to correctly read immutable object from JSON representation.
   * @deprecated Do not use this type directly, it exists only for the <em>Jackson</em>-binding infrastructure
   */
  @Generated(from = "ISendGraphNode", generator = "Immutables")
  @Deprecated
  @SuppressWarnings("Immutable")
  @JsonDeserialize
  @JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.NONE)
  static final class Json implements ISendGraphNode {
    @Nullable String graph;
    @Nullable IExecutorContactInfo contactInfo;
    @JsonProperty("graph")
    public void setGraph(String graph) {
      this.graph = graph;
    }
    @JsonProperty("contactInfo")
    public void setContactInfo(IExecutorContactInfo contactInfo) {
      this.contactInfo = contactInfo;
    }
    @Override
    public String getGraph() { throw new UnsupportedOperationException(); }
    @Override
    public IExecutorContactInfo getContactInfo() { throw new UnsupportedOperationException(); }
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
  static SendGraphNode fromJson(Json json) {
    SendGraphNode.Builder builder = SendGraphNode.builder();
    if (json.graph != null) {
      builder.graph(json.graph);
    }
    if (json.contactInfo != null) {
      builder.contactInfo(json.contactInfo);
    }
    return builder.build();
  }

  @SuppressWarnings("Immutable")
  private transient volatile long lazyInitBitmap;

  private static final long NODE_TYPE_LAZY_INIT_BIT = 0x1L;

  @SuppressWarnings("Immutable")
  private transient String nodeType;

  /**
   * {@inheritDoc}
   * <p>
   * Returns a lazily initialized value of the {@link ISendGraphNode#getNodeType() nodeType} attribute.
   * Initialized once and only once and stored for subsequent access with proper synchronization.
   * In case of any exception or error thrown by the lazy value initializer,
   * the result will not be memoised (i.e. remembered) and on next call computation
   * will be attempted again.
   * @return A lazily initialized value of the {@code nodeType} attribute
   */
  @Override
  public String getNodeType() {
    if ((lazyInitBitmap & NODE_TYPE_LAZY_INIT_BIT) == 0) {
      synchronized (this) {
        if ((lazyInitBitmap & NODE_TYPE_LAZY_INIT_BIT) == 0) {
          this.nodeType = Objects.requireNonNull(ISendGraphNode.super.getNodeType(), "nodeType");
          lazyInitBitmap |= NODE_TYPE_LAZY_INIT_BIT;
        }
      }
    }
    return nodeType;
  }

  /**
   * Creates an immutable copy of a {@link ISendGraphNode} value.
   * Uses accessors to get values to initialize the new immutable instance.
   * If an instance is already immutable, it is returned as is.
   * @param instance The instance to copy
   * @return A copied immutable SendGraphNode instance
   */
  public static SendGraphNode copyOf(ISendGraphNode instance) {
    if (instance instanceof SendGraphNode) {
      return (SendGraphNode) instance;
    }
    return SendGraphNode.builder()
        .from(instance)
        .build();
  }

  /**
   * Creates a builder for {@link SendGraphNode SendGraphNode}.
   * <pre>
   * SendGraphNode.builder()
   *    .graph(String) // required {@link ISendGraphNode#getGraph() graph}
   *    .contactInfo(de.upb.sede.exec.IExecutorContactInfo) // required {@link ISendGraphNode#getContactInfo() contactInfo}
   *    .build();
   * </pre>
   * @return A new SendGraphNode builder
   */
  public static SendGraphNode.Builder builder() {
    return new SendGraphNode.Builder();
  }

  /**
   * Builds instances of type {@link SendGraphNode SendGraphNode}.
   * Initialize attributes and then invoke the {@link #build()} method to create an
   * immutable instance.
   * <p><em>{@code Builder} is not thread-safe and generally should not be stored in a field or collection,
   * but instead used immediately to create instances.</em>
   */
  @Generated(from = "ISendGraphNode", generator = "Immutables")
  @NotThreadSafe
  public static final class Builder {
    private static final long INIT_BIT_GRAPH = 0x1L;
    private static final long INIT_BIT_CONTACT_INFO = 0x2L;
    private long initBits = 0x3L;

    private @Nullable String graph;
    private @Nullable IExecutorContactInfo contactInfo;

    private Builder() {
    }

    /**
     * Fill a builder with attribute values from the provided {@code MutableSendGraphNode} instance.
     * @param instance The instance from which to copy values
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    public final Builder from(MutableSendGraphNode instance) {
      Objects.requireNonNull(instance, "instance");
      if (instance.graphIsSet()) {
        graph(instance.getGraph());
      }
      if (instance.contactInfoIsSet()) {
        contactInfo(instance.getContactInfo());
      }
      return this;
    }

    /**
     * Fill a builder with attribute values from the provided {@code ISendGraphNode} instance.
     * Regular attribute values will be replaced with those from the given instance.
     * Absent optional values will not replace present values.
     * @param instance The instance from which to copy values
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    public final Builder from(ISendGraphNode instance) {
      Objects.requireNonNull(instance, "instance");
      if (instance instanceof MutableSendGraphNode) {
        return from((MutableSendGraphNode) instance);
      }
      graph(instance.getGraph());
      contactInfo(instance.getContactInfo());
      return this;
    }

    /**
     * Initializes the value for the {@link ISendGraphNode#getGraph() graph} attribute.
     * @param graph The value for graph 
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    @JsonProperty("graph")
    public final Builder graph(String graph) {
      this.graph = Objects.requireNonNull(graph, "graph");
      initBits &= ~INIT_BIT_GRAPH;
      return this;
    }

    /**
     * Initializes the value for the {@link ISendGraphNode#getContactInfo() contactInfo} attribute.
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
     * Builds a new {@link SendGraphNode SendGraphNode}.
     * @return An immutable instance of SendGraphNode
     * @throws java.lang.IllegalStateException if any required attributes are missing
     */
    public SendGraphNode build() {
      if (initBits != 0) {
        throw new IllegalStateException(formatRequiredAttributesMessage());
      }
      return new SendGraphNode(graph, contactInfo);
    }

    private String formatRequiredAttributesMessage() {
      List<String> attributes = new ArrayList<>();
      if ((initBits & INIT_BIT_GRAPH) != 0) attributes.add("graph");
      if ((initBits & INIT_BIT_CONTACT_INFO) != 0) attributes.add("contactInfo");
      return "Cannot build SendGraphNode, some of required attributes are not set " + attributes;
    }
  }
}
