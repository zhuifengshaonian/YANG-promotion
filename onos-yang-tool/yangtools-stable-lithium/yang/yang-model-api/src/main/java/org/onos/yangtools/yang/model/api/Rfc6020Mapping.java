/*
 * Copyright (c) 2015 Cisco Systems, Inc. and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.onos.yangtools.yang.model.api;

import com.google.common.annotations.Beta;
import com.google.common.base.Preconditions;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.onos.yangtools.yang.common.QName;
import org.onos.yangtools.yang.common.YangConstants;
import org.onos.yangtools.yang.model.api.meta.DeclaredStatement;
import org.onos.yangtools.yang.model.api.meta.StatementDefinition;
import org.onos.yangtools.yang.model.api.stmt.AnyxmlStatement;
import org.onos.yangtools.yang.model.api.stmt.ArgumentStatement;
import org.onos.yangtools.yang.model.api.stmt.AugmentStatement;
import org.onos.yangtools.yang.model.api.stmt.BaseStatement;
import org.onos.yangtools.yang.model.api.stmt.BelongsToStatement;
import org.onos.yangtools.yang.model.api.stmt.BitStatement;
import org.onos.yangtools.yang.model.api.stmt.CaseStatement;
import org.onos.yangtools.yang.model.api.stmt.ChoiceStatement;
import org.onos.yangtools.yang.model.api.stmt.ConfigStatement;
import org.onos.yangtools.yang.model.api.stmt.ContactStatement;
import org.onos.yangtools.yang.model.api.stmt.ContainerStatement;
import org.onos.yangtools.yang.model.api.stmt.DefaultStatement;
import org.onos.yangtools.yang.model.api.stmt.DescriptionStatement;
import org.onos.yangtools.yang.model.api.stmt.DeviateStatement;
import org.onos.yangtools.yang.model.api.stmt.DeviationStatement;
import org.onos.yangtools.yang.model.api.stmt.EnumStatement;
import org.onos.yangtools.yang.model.api.stmt.ErrorAppTagStatement;
import org.onos.yangtools.yang.model.api.stmt.ErrorMessageStatement;
import org.onos.yangtools.yang.model.api.stmt.ExtensionStatement;
import org.onos.yangtools.yang.model.api.stmt.FeatureStatement;
import org.onos.yangtools.yang.model.api.stmt.FractionDigitsStatement;
import org.onos.yangtools.yang.model.api.stmt.GroupingStatement;
import org.onos.yangtools.yang.model.api.stmt.IdentityStatement;
import org.onos.yangtools.yang.model.api.stmt.IfFeatureStatement;
import org.onos.yangtools.yang.model.api.stmt.ImportStatement;
import org.onos.yangtools.yang.model.api.stmt.IncludeStatement;
import org.onos.yangtools.yang.model.api.stmt.InputStatement;
import org.onos.yangtools.yang.model.api.stmt.KeyStatement;
import org.onos.yangtools.yang.model.api.stmt.LeafListStatement;
import org.onos.yangtools.yang.model.api.stmt.LeafStatement;
import org.onos.yangtools.yang.model.api.stmt.LengthStatement;
import org.onos.yangtools.yang.model.api.stmt.ListStatement;
import org.onos.yangtools.yang.model.api.stmt.MandatoryStatement;
import org.onos.yangtools.yang.model.api.stmt.MaxElementsStatement;
import org.onos.yangtools.yang.model.api.stmt.MinElementsStatement;
import org.onos.yangtools.yang.model.api.stmt.ModuleStatement;
import org.onos.yangtools.yang.model.api.stmt.MustStatement;
import org.onos.yangtools.yang.model.api.stmt.NamespaceStatement;
import org.onos.yangtools.yang.model.api.stmt.NotificationStatement;
import org.onos.yangtools.yang.model.api.stmt.OrderedByStatement;
import org.onos.yangtools.yang.model.api.stmt.OrganizationStatement;
import org.onos.yangtools.yang.model.api.stmt.OutputStatement;
import org.onos.yangtools.yang.model.api.stmt.PathStatement;
import org.onos.yangtools.yang.model.api.stmt.PatternStatement;
import org.onos.yangtools.yang.model.api.stmt.PositionStatement;
import org.onos.yangtools.yang.model.api.stmt.PrefixStatement;
import org.onos.yangtools.yang.model.api.stmt.PresenceStatement;
import org.onos.yangtools.yang.model.api.stmt.RangeStatement;
import org.onos.yangtools.yang.model.api.stmt.ReferenceStatement;
import org.onos.yangtools.yang.model.api.stmt.RefineStatement;
import org.onos.yangtools.yang.model.api.stmt.RequireInstanceStatement;
import org.onos.yangtools.yang.model.api.stmt.RevisionDateStatement;
import org.onos.yangtools.yang.model.api.stmt.RevisionStatement;
import org.onos.yangtools.yang.model.api.stmt.RpcStatement;
import org.onos.yangtools.yang.model.api.stmt.StatusStatement;
import org.onos.yangtools.yang.model.api.stmt.SubmoduleStatement;
import org.onos.yangtools.yang.model.api.stmt.TypeStatement;
import org.onos.yangtools.yang.model.api.stmt.TypedefStatement;
import org.onos.yangtools.yang.model.api.stmt.UniqueStatement;
import org.onos.yangtools.yang.model.api.stmt.UnitsStatement;
import org.onos.yangtools.yang.model.api.stmt.UsesStatement;
import org.onos.yangtools.yang.model.api.stmt.ValueStatement;
import org.onos.yangtools.yang.model.api.stmt.WhenStatement;
import org.onos.yangtools.yang.model.api.stmt.YangVersionStatement;
import org.onos.yangtools.yang.model.api.stmt.YinElementStatement;


@Beta
public enum Rfc6020Mapping implements StatementDefinition {
    ANYXML(AnyxmlStatement.class, "anyxml", "name"),
    ARGUMENT(ArgumentStatement.class, "argument", "name"),
    AUGMENT(AugmentStatement.class, "augment", "target-node"),
    BASE(BaseStatement.class, "base", "name"),
    BELONGS_TO(BelongsToStatement.class, "belongs-to", "module"),
    BIT(BitStatement.class, "bit", "name"),
    CASE(CaseStatement.class, "case", "name"),
    CHOICE(ChoiceStatement.class, "choice", "name"),
    CONFIG(ConfigStatement.class, "config", "value"),
    CONTACT(ContactStatement.class, "contact", "text", true),
    CONTAINER(ContainerStatement.class, "container", "name"),
    DEFAULT(DefaultStatement.class, "default", "value"),
    DESCRIPTION(DescriptionStatement.class, "description", "text", true),
    DEVIATE(DeviateStatement.class, "deviate", "value"),
    DEVIATION(DeviationStatement.class, "deviation", "target-node"),
    ENUM(EnumStatement.class, "enum", "name"),
    ERROR_APP_TAG(ErrorAppTagStatement.class, "error-app-tag", "value"),
    ERROR_MESSAGE(ErrorMessageStatement.class, "error-message", "value", true),
    EXTENSION(ExtensionStatement.class, "extension", "name"),
    FEATURE(FeatureStatement.class, "feature", "name"),
    FRACTION_DIGITS(FractionDigitsStatement.class, "fraction-digits", "value"),
    GROUPING(GroupingStatement.class, "grouping", "name"),
    IDENTITY(IdentityStatement.class, "identity", "name"),
    IF_FEATURE(IfFeatureStatement.class, "if-feature", "name"),
    IMPORT(ImportStatement.class, "import", "module"),
    INCLUDE(IncludeStatement.class, "include", "module"),
    INPUT(InputStatement.class, "input"),
    KEY(KeyStatement.class, "key", "value"),
    LEAF(LeafStatement.class, "leaf", "name"),
    LEAF_LIST(LeafListStatement.class, "leaf-list", "name"),
    LENGTH(LengthStatement.class, "length", "value"),
    LIST(ListStatement.class, "list", "name"),
    MANDATORY(MandatoryStatement.class, "mandatory", "value"),
    MAX_ELEMENTS(MaxElementsStatement.class, "max-elements", "value"),
    MIN_ELEMENTS(MinElementsStatement.class, "min-elements", "value"),
    MODULE(ModuleStatement.class, "module", "name"),
    MUST(MustStatement.class, "must", "condition"),
    NAMESPACE(NamespaceStatement.class, "namespace", "uri"),
    NOTIFICATION(NotificationStatement.class, "notification", "name"),
    ORDERED_BY(OrderedByStatement.class, "ordered-by", "value"),
    ORGANIZATION(OrganizationStatement.class, "organization", "text", true),
    OUTPUT(OutputStatement.class, "output"),
    PATH(PathStatement.class, "path", "value"),
    PATTERN(PatternStatement.class, "pattern", "value"),
    POSITION(PositionStatement.class, "position", "value"),
    PREFIX(PrefixStatement.class, "prefix", "value"),
    PRESENCE(PresenceStatement.class, "presence", "value"),
    RANGE(RangeStatement.class, "range", "value"),
    REFERENCE(ReferenceStatement.class, "reference", "text", true),
    REFINE(RefineStatement.class, "refine", "target-node"),
    REQUIRE_INSTANCE(RequireInstanceStatement.class, "require-instance", "value"),
    REVISION(RevisionStatement.class, "revision", "date"),
    REVISION_DATE(RevisionDateStatement.class, "revision-date", "date"),
    RPC(RpcStatement.class, "rpc", "name"),
    STATUS(StatusStatement.class, "status", "value"),
    SUBMODULE(SubmoduleStatement.class, "submodule", "name"),
    TYPE(TypeStatement.class, "type", "name"),
    TYPEDEF(TypedefStatement.class, "typedef", "name"),
    UNIQUE(UniqueStatement.class, "unique", "tag"),
    UNITS(UnitsStatement.class, "units", "name"),
    USES(UsesStatement.class, "uses", "name"),
    VALUE(ValueStatement.class, "value", "value"),
    WHEN(WhenStatement.class, "when", "condition"),
    YANG_VERSION(YangVersionStatement.class, "yang-version", "value"),
    YIN_ELEMENT(YinElementStatement.class, "yin-element", "value");

    private final Class<? extends DeclaredStatement<?>> type;
    private final QName name;
    private final QName argument;
    private final boolean yinElement;

    private Rfc6020Mapping(Class<? extends DeclaredStatement<?>> clz, final String nameStr) {
        type = Preconditions.checkNotNull(clz);
        name = yinQName(nameStr);
        argument = null;
        yinElement = false;
    }

    private Rfc6020Mapping(Class<? extends DeclaredStatement<?>> clz, final String nameStr, final String argumentStr) {
        type = Preconditions.checkNotNull(clz);
        name = yinQName(nameStr);
        argument = yinQName(argumentStr);
        this.yinElement = false;
    }

    private Rfc6020Mapping(Class<? extends DeclaredStatement<?>> clz, final String nameStr, final String argumentStr,
            final boolean yinElement) {
        type = Preconditions.checkNotNull(clz);
        name = yinQName(nameStr);
        argument = yinQName(argumentStr);
        this.yinElement = yinElement;
    }

    @Nonnull private static QName yinQName(String nameStr) {
        return QName.cachedReference(QName.create(YangConstants.RFC6020_YIN_MODULE, nameStr));
    }

    @Override
    public QName getStatementName() {
        return name;
    }

    @Override
    @Nullable public QName getArgumentName() {
        return argument;
    }

    @Override
    @Nonnull public Class<? extends DeclaredStatement<?>> getDeclaredRepresentationClass() {
        return type;
    }

    @Override
    public Class<? extends DeclaredStatement<?>> getEffectiveRepresentationClass() {
        // FIXME: Add support once these interfaces are defined.
        throw new UnsupportedOperationException("Not defined yet.");
    }

    public boolean isArgumentYinElement() {
        return yinElement;
    }
}

