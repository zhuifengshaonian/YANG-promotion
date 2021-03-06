/*
 * Copyright (c) 2013 Cisco Systems, Inc. and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

/**
 * Definition of structures and DOM like API of effected YANG schema
 *
 * <p>
 * This package is structured into following logical units:
 * <dl>
 * <dt>YANG Meta model</dt>
 * <dd>Meta model of YANG, which defines basic concepts and building blocks of YANG models
 * such as {@link org.onos.yangtools.yang.model.api.meta.ModelStatement}.</dd>
 * <dt>YANG Statement model</dt>
 * <dd>Concrete java model of YANG statements, which defines basic relationship between statements
 * and represents these statements.</dd>
 *
 * <dt>YANG Effective model</dt>
 * <dd>Effective model of processed YANG models, which represents semantic interpretation
 * of YANG models and provides convenience views for interpreting models.
 * </dd>
 * </dl>
 *
 *
 * <h2>YANG Effective model</h2>
 * <h3>Effective model statement mapping</h3>
 *
 * <dl>
 * <dt>anyxml
 *   <dd>{@link org.onos.yangtools.yang.model.api.AnyXmlSchemaNode}
 *
 * <dt>argument
 *   <dd>{@link org.onos.yangtools.yang.model.api.ExtensionDefinition#getArgument()}
 *
 *
 * <dt>augment
 *   <dd>{@link org.onos.yangtools.yang.model.api.AugmentationSchema}
 *
 * <dt>base
 *   <dd>{@link org.onos.yangtools.yang.model.api.type.IdentityrefTypeDefinition#getIdentity()}
 *
 * <dt>belongs-to
 *   <dd>
 *
 * <dt>bit
 *   <dd>{@link org.onos.yangtools.yang.model.api.type.BitsTypeDefinition.Bit}
 *   <dd>{@link org.onos.yangtools.yang.model.api.type.BitsTypeDefinition#getBits()}
 *
 * <dt>case
 *   <dd>{@link org.onos.yangtools.yang.model.api.ChoiceCaseNode}
 *
 * <dt>choice
 *   <dd>{@link org.onos.yangtools.yang.model.api.ChoiceSchemaNode}
 *
 * <dt>config
 *   <dd>{@link org.onos.yangtools.yang.model.api.DataSchemaNode#isConfiguration()}
 *
 * <dt>contact
 *   <dd>{@link org.onos.yangtools.yang.model.api.Module#getContact()}
 *
 * <dt>container
 *   <dd>{@link org.onos.yangtools.yang.model.api.ContainerSchemaNode}
 *
 * <dt>default
 *   <dd>
 *
 * <dt>description
 *   <dd>{@link org.onos.yangtools.yang.model.api.SchemaNode#getDescription()}
 *   <dd>{@link org.onos.yangtools.yang.model.api.ConstraintMetaDefinition#getDescription()}
 *
 * <dt>enum
 *   <dd>{@link org.onos.yangtools.yang.model.api.type.EnumTypeDefinition.EnumPair}
 *   <dd>{@link org.onos.yangtools.yang.model.api.type.EnumTypeDefinition#getValues()}
 *
 * <dt>error-app-tag
 *   <dd>{@link org.onos.yangtools.yang.model.api.ConstraintMetaDefinition#getErrorAppTag()}
 *
 * <dt>error-message
 *   <dd>{@link org.onos.yangtools.yang.model.api.ConstraintMetaDefinition#getErrorMessage()}
 *
 * <dt>extension
 *   <dd>{@link org.onos.yangtools.yang.model.api.ExtensionDefinition}
 *
 * <dt>deviation
 *   <dd>{@link org.onos.yangtools.yang.model.api.Deviation}
 *
 * <dt>deviate
 *   <dd>
 *
 * <dt>feature
 *   <dd>{@link org.onos.yangtools.yang.model.api.FeatureDefinition}
 *
 * <dt>fraction-digits
 *   <dd>{@link org.onos.yangtools.yang.model.api.type.DecimalTypeDefinition#getFractionDigits()}
 *
 * <dt>grouping
 *   <dd>{@link org.onos.yangtools.yang.model.api.GroupingDefinition}
 *
 * <dt>identity
 *   <dd>
 *
 * <dt>if-feature
 *   <dd>
 *
 * <dt>import
 *   <dd>{@link org.onos.yangtools.yang.model.api.ModuleImport}
 *
 * <dt>include
 *   <dd>
 *
 * <dt>input
 *   <dd>{@link org.onos.yangtools.yang.model.api.RpcDefinition#getInput()}
 *
 * <dt>key
 *   <dd>{@link org.onos.yangtools.yang.model.api.ListSchemaNode#getKeyDefinition()}
 *
 * <dt>leaf
 *   <dd>{@link org.onos.yangtools.yang.model.api.LeafSchemaNode}
 *
 * <dt>leaf-list
 *   <dd>{@link org.onos.yangtools.yang.model.api.LeafListSchemaNode}
 *
 * <dt>length
 *   <dd>{@link org.onos.yangtools.yang.model.api.type.LengthConstraint}
 *   <dd>{@link org.onos.yangtools.yang.model.api.type.StringTypeDefinition#getLengthConstraints()}
 *
 * <dt>list
 *   <dd>{@link org.onos.yangtools.yang.model.api.ListSchemaNode}
 *
 * <dt>mandatory
 *   <dd>{@link org.onos.yangtools.yang.model.api.ConstraintDefinition#isMandatory()}
 *
 * <dt>max-elements
 *   <dd>{@link org.onos.yangtools.yang.model.api.ConstraintDefinition#getMinElements()}
 *
 * <dt>min-elements
 *   <dd>{@link org.onos.yangtools.yang.model.api.ConstraintDefinition#getMaxElements()}
 *
 * <dt>module
 *   <dd>{@link org.onos.yangtools.yang.model.api.Module}
 *
 * <dt>must
 *   <dd>{@link org.onos.yangtools.yang.model.api.ConstraintDefinition#getMustConstraints()}
 *   <dd>{@link org.onos.yangtools.yang.model.api.MustDefinition}
 *
 * <dt>namespace
 *   <dd>{@link org.onos.yangtools.yang.model.api.Module#getNamespace()}
 *
 * <dt>notification
 *   <dd>{@link org.onos.yangtools.yang.model.api.NotificationDefinition}
 *
 * <dt>ordered-by
 *   <dd>{@link org.onos.yangtools.yang.model.api.ListSchemaNode#isUserOrdered()}
 *   <dd>{@link org.onos.yangtools.yang.model.api.LeafListSchemaNode#isUserOrdered()}
 *
 * <dt>organization
 *   <dd>{@link org.onos.yangtools.yang.model.api.Module#getOrganization()}
 *
 * <dt>output
 *   <dd>{@link org.onos.yangtools.yang.model.api.RpcDefinition#getOutput()}
 *
 * <dt>path
 *   <dd>{@link org.onos.yangtools.yang.model.api.type.LeafrefTypeDefinition#getPathStatement()}
 *
 * <dt>pattern
 *   <dd>{@link org.onos.yangtools.yang.model.api.type.PatternConstraint}
 *   <dd>{@link org.onos.yangtools.yang.model.api.type.StringTypeDefinition}
 *
 * <dt>position
 *   <dd>{@link org.onos.yangtools.yang.model.api.type.BitsTypeDefinition.Bit#getPosition()}
 *
 * <dt>prefix
 *   <dd>{@link org.onos.yangtools.yang.model.api.Module#getPrefix()}
 *   <dd>{@link org.onos.yangtools.yang.model.api.ModuleImport#getPrefix()}
 *
 * <dt>presence
 *   <dd>{@link org.onos.yangtools.yang.model.api.ContainerSchemaNode#isPresenceContainer()}
 *
 * <dt>range
 *   <dd>{@link org.onos.yangtools.yang.model.api.type.RangeConstraint}
 *   <dd>{@link org.onos.yangtools.yang.model.api.type.IntegerTypeDefinition#getRangeConstraints()}
 *   <dd>{@link org.onos.yangtools.yang.model.api.type.UnsignedIntegerTypeDefinition#getRangeConstraints()}
 *
 * <dt>reference
 *   <dd>{@link org.onos.yangtools.yang.model.api.SchemaNode#getReference()}
 *
 * <dt>refine
 *   <dd>
 *
 * <dt>require-instance
 *   <dd>{@link org.onos.yangtools.yang.model.api.type.InstanceIdentifierTypeDefinition#requireInstance()}
 *
 * <dt>revision
 *   <dd>{@link org.onos.yangtools.yang.model.api.Module#getRevision()}
 *
 * <dt>revision-date
 *   <dd>{@link org.onos.yangtools.yang.model.api.ModuleImport#getRevision()}
 *
 * <dt>rpc
 *   <dd>{@link org.onos.yangtools.yang.model.api.RpcDefinition}
 *
 * <dt>status
 *   <dd>{@link org.onos.yangtools.yang.model.api.SchemaNode#getStatus()}
 *
 * <dt>submodule
 *   <dd>
 *
 * <dt>type
 *   <dd>{@link org.onos.yangtools.yang.model.api.TypeDefinition}
 *   <dd>{@link org.onos.yangtools.yang.model.api.LeafSchemaNode#getType()}
 *   <dd>{@link org.onos.yangtools.yang.model.api.LeafListSchemaNode#getType()}
 *
 * <dt>typedef
 *   <dd>{@link org.onos.yangtools.yang.model.api.TypeDefinition}
 *
 * <dt>unique
 *   <dd>
 *
 * <dt>units
 *   <dd>{@link org.onos.yangtools.yang.model.api.TypeDefinition#getUnits()}
 *
 * <dt>uses
 *   <dd>{@link org.onos.yangtools.yang.model.api.UsesNode}
 *   <dd>{@link org.onos.yangtools.yang.model.api.DataNodeContainer#getUses()}
 *
 * <dt>value
 *   <dd>{@link org.onos.yangtools.yang.model.api.type.EnumTypeDefinition.EnumPair#getValue()}
 *
 * <dt>when
 *   <dd>{@link org.onos.yangtools.yang.model.api.ConstraintDefinition#getWhenCondition()}
 *
 * <dt>yang-version
 *
 * <dt>yin-element
 *   <dd>
 *
 *
 *
 *
 * <dt>add
 *   <dd>
 *
 * <dt>current
 *   <dd>
 *
 * <dt>delete
 *   <dd>
 *
 * <dt>deprecated
 *   <dd>
 *
 * <dt>false
 *   <dd>
 *
 * <dt>max
 *   <dd>
 *
 * <dt>min
 *   <dd>
 *
 * <dt>not-supported
 *   <dd>
 *
 * <dt>obsolete
 *   <dd>
 *
 * <dt>replace
 *   <dd>
 *
 * <dt>system
 *   <dd>
 *
 * <dt>true
 *   <dd>
 *
 * <dt>unbounded
 *   <dd>
 *
 * <dt>user
 *   <dd>
 * </dl>
 *
 *
 * <h3>YANG Base Type Mapping</h3>
 *
 *
 * <dl>
 * <dt>Integer built-in type
 *   <dd>{@link org.onos.yangtools.yang.model.api.type.IntegerTypeDefinition}
 *
 * <dt>Unsigned integer built-in type
 *   <dd>{@link org.onos.yangtools.yang.model.api.type.UnsignedIntegerTypeDefinition}
 *
 * <dt>Decimal64 built-ib type
 *   <dd>{@link org.onos.yangtools.yang.model.api.type.DecimalTypeDefinition}
 *
 * <dt>Boolean built-in type
 *   <dd>{@link org.onos.yangtools.yang.model.api.type.BooleanTypeDefinition}
 *
 * <dt>Enumeration built-in type
 *   <dd>{@link org.onos.yangtools.yang.model.api.type.EnumTypeDefinition}
 *
 * <dt>Bits Built-In Type
 *   <dd>{@link org.onos.yangtools.yang.model.api.type.BitsTypeDefinition}
 *
 * <dt>The binary Built-In Type
 *   <dd>{@link org.onos.yangtools.yang.model.api.type.BinaryTypeDefinition}
 *
 * <dt>The leafref Built-In Type
 *   <dd>{@link org.onos.yangtools.yang.model.api.type.LeafrefTypeDefinition}
 *
 * <dt>The identityref Built-In Type
 *   <dd>{@link org.onos.yangtools.yang.model.api.type.IdentityrefTypeDefinition}
 *
 * <dt>The empty Built-In Type
 *   <dd>{@link org.onos.yangtools.yang.model.api.type.EmptyTypeDefinition}
 *
 * <dt>The union Built-In Type
 *   <dd>{@link org.onos.yangtools.yang.model.api.type.UnionTypeDefinition}
 * <dt>The instance-identifier Built-In Type
 *   <dd>{@link org.onos.yangtools.yang.model.api.type.InstanceIdentifierTypeDefinition}
 *
 * </dl>
 */
package org.onos.yangtools.yang.model.api;

