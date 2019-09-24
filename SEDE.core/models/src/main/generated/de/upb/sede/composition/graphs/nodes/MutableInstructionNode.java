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
 * A modifiable implementation of the {@link IInstructionNode IInstructionNode} type.
 * <p>Use the {@link #create()} static factory methods to create new instances.
 * Use the {@link #toImmutable()} method to convert to canonical immutable instances.
 * <p><em>MutableInstructionNode is not thread-safe</em>
 * @see InstructionNode
 */
@Generated(from = "IInstructionNode", generator = "Modifiables")
@SuppressWarnings({"all", "deprecation"})
@ParametersAreNonnullByDefault
@javax.annotation.Generated({"Modifiables.generator", "IInstructionNode"})
@NotThreadSafe
public final class MutableInstructionNode implements IInstructionNode {
  private static final long INIT_BIT_F_M_INSTRUCTION = 0x1L;
  private static final long INIT_BIT_FIELD_TYPE = 0x2L;
  private static final long INIT_BIT_FIELD_CLASS = 0x4L;
  private static final long INIT_BIT_HOST = 0x8L;
  private static final long INIT_BIT_CONTEXT = 0x10L;
  private static final long INIT_BIT_CONTEXT_IS_FIELD_FLAG = 0x20L;
  private static final long INIT_BIT_METHOD = 0x40L;
  private static final long INIT_BIT_FIELD_NAME = 0x80L;
  private static final long OPT_BIT_OUTPUT_INDEX = 0x1L;
  private long initBits = 0xffL;
  private long optBits;

  private String fMInstruction;
  private String fieldType;
  private String fieldClass;
  private String host;
  private String context;
  private boolean contextIsFieldFlag;
  private String method;
  private int outputIndex;
  private final ArrayList<String> parameterFields = new ArrayList<String>();
  private final ArrayList<String> parameterTypes = new ArrayList<String>();
  private String nodeType;
  private String fieldName;

  private MutableInstructionNode() {}

  /**
   * Construct a modifiable instance of {@code IInstructionNode}.
   * @return A new modifiable instance
   */
  public static MutableInstructionNode create() {
    return new MutableInstructionNode();
  }

  /**
   * @return value of {@code fMInstruction} attribute
   */
  @JsonProperty("fMInstruction")
  @Override
  public final String getFMInstruction() {
    if (!fMInstructionIsSet()) {
      checkRequiredAttributes();
    }
    return fMInstruction;
  }

  /**
   * @return value of {@code fieldType} attribute
   */
  @JsonProperty("fieldType")
  @Override
  public final String getFieldType() {
    if (!fieldTypeIsSet()) {
      checkRequiredAttributes();
    }
    return fieldType;
  }

  /**
   * @return value of {@code fieldClass} attribute
   */
  @JsonProperty("fieldClass")
  @Override
  public final String getFieldClass() {
    if (!fieldClassIsSet()) {
      checkRequiredAttributes();
    }
    return fieldClass;
  }

  /**
   * @return value of {@code host} attribute
   */
  @JsonProperty("host")
  @Deprecated
  @Override
  public final String getHost() {
    if (!hostIsSet()) {
      checkRequiredAttributes();
    }
    return host;
  }

  /**
   * @return value of {@code context} attribute
   */
  @JsonProperty("context")
  @Override
  public final String getContext() {
    if (!contextIsSet()) {
      checkRequiredAttributes();
    }
    return context;
  }

  /**
   * @return value of {@code contextIsFieldFlag} attribute
   */
  @JsonProperty("contextIsFieldFlag")
  @Override
  public final boolean getContextIsFieldFlag() {
    if (!contextIsFieldFlagIsSet()) {
      checkRequiredAttributes();
    }
    return contextIsFieldFlag;
  }

  /**
   * @return value of {@code method} attribute
   */
  @JsonProperty("method")
  @Override
  public final String getMethod() {
    if (!methodIsSet()) {
      checkRequiredAttributes();
    }
    return method;
  }

  /**
   * Index of instruction output.
   * -1 means that the return value of the instruction is the output.
   * Any other value >= 0 will have the instruction return the ith parameter as output.
   */
  @JsonProperty("outputIndex")
  @Override
  public final int getOutputIndex() {
    return outputIndexIsSet()
        ? outputIndex
        : IInstructionNode.super.getOutputIndex();
  }

  /**
   * Parameters for method or constructor invocation. The order of the parameters
   * has to be kept unchanged. May either contain field-names referencing to data
   * or else constants like numbers or strings. e.g.: ["a1", "b1", "10", "\"a\""]
   * The first two are fieldnames. The third is a constant number. The fourth is a
   * constant string.
   * <p>
   * <p>
   * The list itself is read-only.
   */
  @JsonProperty("parameterFields")
  @Override
  public final List<String> getParameterFields() {
    return parameterFields;
  }

  /**
   * @return modifiable list {@code parameterTypes}
   */
  @JsonProperty("parameterTypes")
  @Override
  public final List<String> getParameterTypes() {
    return parameterTypes;
  }

  /**
   * @return assigned or, otherwise, newly computed, not cached value of {@code nodeType} attribute
   */
  @JsonProperty("nodeType")
  @Override
  public final String getNodeType() {
    return nodeTypeIsSet()
        ? nodeType
        : IInstructionNode.super.getNodeType();
  }

  /**
   * Returns the field name that this node is referencing.
   * @return Referenced field name
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
  public MutableInstructionNode clear() {
    initBits = 0xffL;
    optBits = 0;
    fMInstruction = null;
    fieldType = null;
    fieldClass = null;
    host = null;
    context = null;
    contextIsFieldFlag = false;
    method = null;
    outputIndex = 0;
    parameterFields.clear();
    parameterTypes.clear();
    nodeType = null;
    fieldName = null;
    return this;
  }

  /**
   * Fill this modifiable instance with attribute values from the provided {@link de.upb.sede.composition.graphs.nodes.IFieldNameAware} instance.
   * @param instance The instance from which to copy values
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public MutableInstructionNode from(IFieldNameAware instance) {
    Objects.requireNonNull(instance, "instance");
    from((Object) instance);
    return this;
  }

  /**
   * Fill this modifiable instance with attribute values from the provided {@link de.upb.sede.composition.graphs.nodes.IInstructionNode} instance.
   * @param instance The instance from which to copy values
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public MutableInstructionNode from(IInstructionNode instance) {
    Objects.requireNonNull(instance, "instance");
    from((Object) instance);
    return this;
  }

  /**
   * Fill this modifiable instance with attribute values from the provided {@link de.upb.sede.composition.graphs.nodes.IBaseNode} instance.
   * @param instance The instance from which to copy values
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public MutableInstructionNode from(IBaseNode instance) {
    Objects.requireNonNull(instance, "instance");
    from((Object) instance);
    return this;
  }

  /**
   * Fill this modifiable instance with attribute values from the provided {@link IInstructionNode} instance.
   * Regular attribute values will be overridden, i.e. replaced with ones of an instance.
   * Any of the instance's absent optional values will not be copied (will not override current values).
   * Collection elements and entries will be added, not replaced.
   * @param instance The instance from which to copy values
   * @return {@code this} for use in a chained invocation
   */
  public MutableInstructionNode from(MutableInstructionNode instance) {
    Objects.requireNonNull(instance, "instance");
    from((Object) instance);
    return this;
  }

  private void from(Object object) {
    if (object instanceof MutableInstructionNode) {
      MutableInstructionNode instance = (MutableInstructionNode) object;
      if (instance.fMInstructionIsSet()) {
        setFMInstruction(instance.getFMInstruction());
      }
      if (instance.fieldTypeIsSet()) {
        setFieldType(instance.getFieldType());
      }
      if (instance.fieldClassIsSet()) {
        setFieldClass(instance.getFieldClass());
      }
      if (instance.hostIsSet()) {
        setHost(instance.getHost());
      }
      if (instance.contextIsSet()) {
        setContext(instance.getContext());
      }
      if (instance.contextIsFieldFlagIsSet()) {
        setContextIsFieldFlag(instance.getContextIsFieldFlag());
      }
      if (instance.methodIsSet()) {
        setMethod(instance.getMethod());
      }
      setOutputIndex(instance.getOutputIndex());
      addAllParameterFields(instance.getParameterFields());
      addAllParameterTypes(instance.getParameterTypes());
      setNodeType(instance.getNodeType());
      if (instance.fieldNameIsSet()) {
        setFieldName(instance.getFieldName());
      }
      return;
    }
    if (object instanceof IFieldNameAware) {
      IFieldNameAware instance = (IFieldNameAware) object;
      setFieldName(instance.getFieldName());
    }
    if (object instanceof IInstructionNode) {
      IInstructionNode instance = (IInstructionNode) object;
      addAllParameterFields(instance.getParameterFields());
      setFMInstruction(instance.getFMInstruction());
      setContextIsFieldFlag(instance.getContextIsFieldFlag());
      setOutputIndex(instance.getOutputIndex());
      setMethod(instance.getMethod());
      addAllParameterTypes(instance.getParameterTypes());
      setHost(instance.getHost());
      setContext(instance.getContext());
      setFieldType(instance.getFieldType());
      setFieldClass(instance.getFieldClass());
    }
    if (object instanceof IBaseNode) {
      IBaseNode instance = (IBaseNode) object;
      setNodeType(instance.getNodeType());
    }
  }

  /**
   * Assigns a value to the {@link IInstructionNode#getFMInstruction() fMInstruction} attribute.
   * @param fMInstruction The value for fMInstruction
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public MutableInstructionNode setFMInstruction(String fMInstruction) {
    this.fMInstruction = Objects.requireNonNull(fMInstruction, "fMInstruction");
    initBits &= ~INIT_BIT_F_M_INSTRUCTION;
    return this;
  }

  /**
   * Assigns a value to the {@link IInstructionNode#getFieldType() fieldType} attribute.
   * @param fieldType The value for fieldType
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public MutableInstructionNode setFieldType(String fieldType) {
    this.fieldType = Objects.requireNonNull(fieldType, "fieldType");
    initBits &= ~INIT_BIT_FIELD_TYPE;
    return this;
  }

  /**
   * Assigns a value to the {@link IInstructionNode#getFieldClass() fieldClass} attribute.
   * @param fieldClass The value for fieldClass
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public MutableInstructionNode setFieldClass(String fieldClass) {
    this.fieldClass = Objects.requireNonNull(fieldClass, "fieldClass");
    initBits &= ~INIT_BIT_FIELD_CLASS;
    return this;
  }

  /**
   * Assigns a value to the {@link IInstructionNode#getHost() host} attribute.
   * @param host The value for host
   * @return {@code this} for use in a chained invocation
   */
  @Deprecated
  @CanIgnoreReturnValue
  public MutableInstructionNode setHost(String host) {
    this.host = Objects.requireNonNull(host, "host");
    initBits &= ~INIT_BIT_HOST;
    return this;
  }

  /**
   * Assigns a value to the {@link IInstructionNode#getContext() context} attribute.
   * @param context The value for context
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public MutableInstructionNode setContext(String context) {
    this.context = Objects.requireNonNull(context, "context");
    initBits &= ~INIT_BIT_CONTEXT;
    return this;
  }

  /**
   * Assigns a value to the {@link IInstructionNode#getContextIsFieldFlag() contextIsFieldFlag} attribute.
   * @param contextIsFieldFlag The value for contextIsFieldFlag
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public MutableInstructionNode setContextIsFieldFlag(boolean contextIsFieldFlag) {
    this.contextIsFieldFlag = contextIsFieldFlag;
    initBits &= ~INIT_BIT_CONTEXT_IS_FIELD_FLAG;
    return this;
  }

  /**
   * Assigns a value to the {@link IInstructionNode#getMethod() method} attribute.
   * @param method The value for method
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public MutableInstructionNode setMethod(String method) {
    this.method = Objects.requireNonNull(method, "method");
    initBits &= ~INIT_BIT_METHOD;
    return this;
  }

  /**
   * Assigns a value to the {@link IInstructionNode#getOutputIndex() outputIndex} attribute.
   * <p><em>If not set, this attribute will have a default value returned by the initializer of {@link IInstructionNode#getOutputIndex() outputIndex}.</em>
   * @param outputIndex The value for outputIndex
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public MutableInstructionNode setOutputIndex(int outputIndex) {
    this.outputIndex = outputIndex;
    optBits |= OPT_BIT_OUTPUT_INDEX;
    return this;
  }

  /**
   * Adds one element to {@link IInstructionNode#getParameterFields() parameterFields} list.
   * @param element The parameterFields element
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public MutableInstructionNode addParameterFields(String element) {
    Objects.requireNonNull(element, "parameterFields element");
    this.parameterFields.add(element);
    return this;
  }

  /**
   * Adds elements to {@link IInstructionNode#getParameterFields() parameterFields} list.
   * @param elements An array of parameterFields elements
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public final MutableInstructionNode addParameterFields(String... elements) {
    for (String e : elements) {
      addParameterFields(e);
    }
    return this;
  }

  /**
   * Sets or replaces all elements for {@link IInstructionNode#getParameterFields() parameterFields} list.
   * @param elements An iterable of parameterFields elements
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public MutableInstructionNode setParameterFields(Iterable<String> elements) {
    this.parameterFields.clear();
    addAllParameterFields(elements);
    return this;
  }

  /**
   * Adds elements to {@link IInstructionNode#getParameterFields() parameterFields} list.
   * @param elements An iterable of parameterFields elements
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public MutableInstructionNode addAllParameterFields(Iterable<String> elements) {
    for (String e : elements) {
      addParameterFields(e);
    }
    return this;
  }

  /**
   * Adds one element to {@link IInstructionNode#getParameterTypes() parameterTypes} list.
   * @param element The parameterTypes element
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public MutableInstructionNode addParameterTypes(String element) {
    Objects.requireNonNull(element, "parameterTypes element");
    this.parameterTypes.add(element);
    return this;
  }

  /**
   * Adds elements to {@link IInstructionNode#getParameterTypes() parameterTypes} list.
   * @param elements An array of parameterTypes elements
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public final MutableInstructionNode addParameterTypes(String... elements) {
    for (String e : elements) {
      addParameterTypes(e);
    }
    return this;
  }

  /**
   * Sets or replaces all elements for {@link IInstructionNode#getParameterTypes() parameterTypes} list.
   * @param elements An iterable of parameterTypes elements
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public MutableInstructionNode setParameterTypes(Iterable<String> elements) {
    this.parameterTypes.clear();
    addAllParameterTypes(elements);
    return this;
  }

  /**
   * Adds elements to {@link IInstructionNode#getParameterTypes() parameterTypes} list.
   * @param elements An iterable of parameterTypes elements
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public MutableInstructionNode addAllParameterTypes(Iterable<String> elements) {
    for (String e : elements) {
      addParameterTypes(e);
    }
    return this;
  }

  /**
   * Assigns a value to the {@link IInstructionNode#getNodeType() nodeType} attribute.
   * <p><em>If not set, this attribute will have a default value returned by the initializer of {@link IInstructionNode#getNodeType() nodeType}.</em>
   * @param nodeType The value for nodeType
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public MutableInstructionNode setNodeType(String nodeType) {
    this.nodeType = Objects.requireNonNull(nodeType, "nodeType");
    return this;
  }

  /**
   * Assigns a value to the {@link IInstructionNode#getFieldName() fieldName} attribute.
   * @param fieldName The value for fieldName
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public MutableInstructionNode setFieldName(String fieldName) {
    this.fieldName = Objects.requireNonNull(fieldName, "fieldName");
    initBits &= ~INIT_BIT_FIELD_NAME;
    return this;
  }

  /**
   * Returns {@code true} if the required attribute {@link IInstructionNode#getFMInstruction() fMInstruction} is set.
   * @return {@code true} if set
   */
  public final boolean fMInstructionIsSet() {
    return (initBits & INIT_BIT_F_M_INSTRUCTION) == 0;
  }

  /**
   * Returns {@code true} if the required attribute {@link IInstructionNode#getFieldType() fieldType} is set.
   * @return {@code true} if set
   */
  public final boolean fieldTypeIsSet() {
    return (initBits & INIT_BIT_FIELD_TYPE) == 0;
  }

  /**
   * Returns {@code true} if the required attribute {@link IInstructionNode#getFieldClass() fieldClass} is set.
   * @return {@code true} if set
   */
  public final boolean fieldClassIsSet() {
    return (initBits & INIT_BIT_FIELD_CLASS) == 0;
  }

  /**
   * Returns {@code true} if the required attribute {@link IInstructionNode#getHost() host} is set.
   * @return {@code true} if set
   */
  public final boolean hostIsSet() {
    return (initBits & INIT_BIT_HOST) == 0;
  }

  /**
   * Returns {@code true} if the required attribute {@link IInstructionNode#getContext() context} is set.
   * @return {@code true} if set
   */
  public final boolean contextIsSet() {
    return (initBits & INIT_BIT_CONTEXT) == 0;
  }

  /**
   * Returns {@code true} if the required attribute {@link IInstructionNode#getContextIsFieldFlag() contextIsFieldFlag} is set.
   * @return {@code true} if set
   */
  public final boolean contextIsFieldFlagIsSet() {
    return (initBits & INIT_BIT_CONTEXT_IS_FIELD_FLAG) == 0;
  }

  /**
   * Returns {@code true} if the required attribute {@link IInstructionNode#getMethod() method} is set.
   * @return {@code true} if set
   */
  public final boolean methodIsSet() {
    return (initBits & INIT_BIT_METHOD) == 0;
  }

  /**
   * Returns {@code true} if the required attribute {@link IInstructionNode#getFieldName() fieldName} is set.
   * @return {@code true} if set
   */
  public final boolean fieldNameIsSet() {
    return (initBits & INIT_BIT_FIELD_NAME) == 0;
  }

  /**
   * Returns {@code true} if the default attribute {@link IInstructionNode#getOutputIndex() outputIndex} is set.
   * @return {@code true} if set
   */
  public final boolean outputIndexIsSet() {
    return (optBits & OPT_BIT_OUTPUT_INDEX) != 0;
  }

  /**
   * Returns {@code true} if the default attribute {@link IInstructionNode#getNodeType() nodeType} is set.
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
  public final MutableInstructionNode unsetFMInstruction() {
    initBits |= INIT_BIT_F_M_INSTRUCTION;
    fMInstruction = null;
    return this;
  }

  /**
   * Reset an attribute to its initial value.
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public final MutableInstructionNode unsetFieldType() {
    initBits |= INIT_BIT_FIELD_TYPE;
    fieldType = null;
    return this;
  }

  /**
   * Reset an attribute to its initial value.
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public final MutableInstructionNode unsetFieldClass() {
    initBits |= INIT_BIT_FIELD_CLASS;
    fieldClass = null;
    return this;
  }

  /**
   * Reset an attribute to its initial value.
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public final MutableInstructionNode unsetHost() {
    initBits |= INIT_BIT_HOST;
    host = null;
    return this;
  }

  /**
   * Reset an attribute to its initial value.
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public final MutableInstructionNode unsetContext() {
    initBits |= INIT_BIT_CONTEXT;
    context = null;
    return this;
  }

  /**
   * Reset an attribute to its initial value.
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public final MutableInstructionNode unsetContextIsFieldFlag() {
    initBits |= INIT_BIT_CONTEXT_IS_FIELD_FLAG;
    contextIsFieldFlag = false;
    return this;
  }

  /**
   * Reset an attribute to its initial value.
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public final MutableInstructionNode unsetMethod() {
    initBits |= INIT_BIT_METHOD;
    method = null;
    return this;
  }

  /**
   * Reset an attribute to its initial value.
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public final MutableInstructionNode unsetFieldName() {
    initBits |= INIT_BIT_FIELD_NAME;
    fieldName = null;
    return this;
  }
  /**
   * Reset an attribute to its initial value.
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public final MutableInstructionNode unsetOutputIndex() {
    optBits |= 0;
    outputIndex = 0;
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
    if (!fMInstructionIsSet()) attributes.add("fMInstruction");
    if (!fieldTypeIsSet()) attributes.add("fieldType");
    if (!fieldClassIsSet()) attributes.add("fieldClass");
    if (!hostIsSet()) attributes.add("host");
    if (!contextIsSet()) attributes.add("context");
    if (!contextIsFieldFlagIsSet()) attributes.add("contextIsFieldFlag");
    if (!methodIsSet()) attributes.add("method");
    if (!fieldNameIsSet()) attributes.add("fieldName");
    return "InstructionNode is not initialized, some of the required attributes are not set " + attributes;
  }

  /**
   * Converts to {@link InstructionNode InstructionNode}.
   * @return An immutable instance of InstructionNode
   */
  public final InstructionNode toImmutable() {
    checkRequiredAttributes();
    return InstructionNode.copyOf(this);
  }

  /**
   * This instance is equal to all instances of {@code MutableInstructionNode} that have equal attribute values.
   * An uninitialized instance is equal only to itself.
   * @return {@code true} if {@code this} is equal to {@code another} instance
   */
  @Override
  public boolean equals(@Nullable Object another) {
    if (this == another) return true;
    if (!(another instanceof MutableInstructionNode)) return false;
    MutableInstructionNode other = (MutableInstructionNode) another;
    if (!isInitialized() || !other.isInitialized()) {
      return false;
    }
    return equalTo(other);
  }

  private boolean equalTo(MutableInstructionNode another) {
    int outputIndex = getOutputIndex();
    String nodeType = getNodeType();
    return fMInstruction.equals(another.fMInstruction)
        && fieldType.equals(another.fieldType)
        && fieldClass.equals(another.fieldClass)
        && host.equals(another.host)
        && context.equals(another.context)
        && contextIsFieldFlag == another.contextIsFieldFlag
        && method.equals(another.method)
        && outputIndex == another.getOutputIndex()
        && parameterFields.equals(another.parameterFields)
        && parameterTypes.equals(another.parameterTypes)
        && nodeType.equals(another.getNodeType())
        && fieldName.equals(another.fieldName);
  }

  /**
   * Computes a hash code from attributes: {@code fMInstruction}, {@code fieldType}, {@code fieldClass}, {@code host}, {@code context}, {@code contextIsFieldFlag}, {@code method}, {@code outputIndex}, {@code parameterFields}, {@code parameterTypes}, {@code nodeType}, {@code fieldName}.
   * @return hashCode value
   */
  @Override
  public int hashCode() {
    int h = 5381;
    h += (h << 5) + fMInstruction.hashCode();
    h += (h << 5) + fieldType.hashCode();
    h += (h << 5) + fieldClass.hashCode();
    h += (h << 5) + host.hashCode();
    h += (h << 5) + context.hashCode();
    h += (h << 5) + Booleans.hashCode(contextIsFieldFlag);
    h += (h << 5) + method.hashCode();
    int outputIndex = getOutputIndex();
    h += (h << 5) + outputIndex;
    h += (h << 5) + parameterFields.hashCode();
    h += (h << 5) + parameterTypes.hashCode();
    String nodeType = getNodeType();
    h += (h << 5) + nodeType.hashCode();
    h += (h << 5) + fieldName.hashCode();
    return h;
  }

  /**
   * Generates a string representation of this {@code IInstructionNode}.
   * If uninitialized, some attribute values may appear as question marks.
   * @return A string representation
   */
  @Override
  public String toString() {
    return MoreObjects.toStringHelper("MutableInstructionNode")
        .add("fMInstruction", fMInstructionIsSet() ? getFMInstruction() : "?")
        .add("fieldType", fieldTypeIsSet() ? getFieldType() : "?")
        .add("fieldClass", fieldClassIsSet() ? getFieldClass() : "?")
        .add("host", hostIsSet() ? getHost() : "?")
        .add("context", contextIsSet() ? getContext() : "?")
        .add("contextIsFieldFlag", contextIsFieldFlagIsSet() ? getContextIsFieldFlag() : "?")
        .add("method", methodIsSet() ? getMethod() : "?")
        .add("outputIndex", getOutputIndex())
        .add("parameterFields", getParameterFields())
        .add("parameterTypes", getParameterTypes())
        .add("nodeType", getNodeType())
        .add("fieldName", fieldNameIsSet() ? getFieldName() : "?")
        .toString();
  }
}
