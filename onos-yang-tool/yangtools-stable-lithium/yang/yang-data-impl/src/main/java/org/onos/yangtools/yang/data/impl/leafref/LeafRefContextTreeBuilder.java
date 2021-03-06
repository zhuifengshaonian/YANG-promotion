/**
 * Copyright (c) 2015 Cisco Systems, Inc. and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.onos.yangtools.yang.data.impl.leafref;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Set;
import org.onos.yangtools.yang.model.api.ChoiceCaseNode;
import org.onos.yangtools.yang.model.api.ChoiceSchemaNode;
import org.onos.yangtools.yang.model.api.DataNodeContainer;
import org.onos.yangtools.yang.model.api.DataSchemaNode;
import org.onos.yangtools.yang.model.api.LeafListSchemaNode;
import org.onos.yangtools.yang.model.api.LeafSchemaNode;
import org.onos.yangtools.yang.model.api.Module;
import org.onos.yangtools.yang.model.api.SchemaContext;
import org.onos.yangtools.yang.model.api.TypeDefinition;
import org.onos.yangtools.yang.model.util.Leafref;

class LeafRefContextTreeBuilder {
    private final SchemaContext schemaContext;
    private final LinkedList<LeafRefContext> leafRefs;

    public LeafRefContextTreeBuilder(final SchemaContext schemaContext) {
        this.schemaContext = schemaContext;
        this.leafRefs = new LinkedList<LeafRefContext>();
    }

    public LeafRefContext buildLeafRefContextTree() throws IOException,
            LeafRefYangSyntaxErrorException {
        final LeafRefContextBuilder rootBuilder = new LeafRefContextBuilder(
                schemaContext.getQName(), schemaContext.getPath(),
                schemaContext);

        final Set<Module> modules = schemaContext.getModules();
        for (final Module module : modules) {
            final Collection<DataSchemaNode> childNodes = module.getChildNodes();
            for (final DataSchemaNode childNode : childNodes) {
                final LeafRefContext childLeafRefContext = buildLeafRefContextReferencingTree(
                        childNode, module);

                if (childLeafRefContext.hasReferencingChild()
                        || childLeafRefContext.isReferencing()) {
                    rootBuilder.addReferencingChild(childLeafRefContext,
                            childLeafRefContext.getNodeName());
                }
            }
        }

        for (final Module module : modules) {
            final Collection<DataSchemaNode> childNodes = module.getChildNodes();
            for (final DataSchemaNode childNode : childNodes) {
                final LeafRefContext childLeafRefContext = buildLeafRefContextReferencedByTree(
                        childNode, module);

                if (childLeafRefContext.hasReferencedChild()
                        || childLeafRefContext.isReferenced()) {
                    rootBuilder.addReferencedByChild(childLeafRefContext,
                            childLeafRefContext.getNodeName());
                }
            }
        }

        // FIXME: it might be useful to merge these subtrees (i.e. referencing
        // and referencedBy subtree)

        return rootBuilder.build();
    }

    private LeafRefContext buildLeafRefContextReferencingTree(
            final DataSchemaNode node, final Module currentModule) throws IOException,
            LeafRefYangSyntaxErrorException {

        final LeafRefContextBuilder currentLeafRefContextBuilder = new LeafRefContextBuilder(
                node.getQName(), node.getPath(), schemaContext);

        if (node instanceof DataNodeContainer) {
            final DataNodeContainer dataNodeContainer = (DataNodeContainer) node;
            final Collection<DataSchemaNode> childNodes = dataNodeContainer
                    .getChildNodes();

            for (final DataSchemaNode childNode : childNodes) {
                final LeafRefContext childLeafRefContext = buildLeafRefContextReferencingTree(
                        childNode, currentModule);

                if (childLeafRefContext.hasReferencingChild()
                        || childLeafRefContext.isReferencing()) {
                    currentLeafRefContextBuilder.addReferencingChild(
                            childLeafRefContext,
                            childLeafRefContext.getNodeName());
                }
            }
        } else if (node instanceof ChoiceSchemaNode) {

            final ChoiceSchemaNode choice = (ChoiceSchemaNode) node;
            final Set<ChoiceCaseNode> cases = choice.getCases();
            // :FIXME choice without case

            for (final ChoiceCaseNode caseNode : cases) {
                final LeafRefContext childLeafRefContext = buildLeafRefContextReferencingTree(
                        caseNode, currentModule);

                if (childLeafRefContext.hasReferencingChild()
                        || childLeafRefContext.isReferencing()) {
                    currentLeafRefContextBuilder.addReferencingChild(
                            childLeafRefContext,
                            childLeafRefContext.getNodeName());
                }
            }

        } else if (node instanceof LeafSchemaNode
                || node instanceof LeafListSchemaNode) {

            TypeDefinition<?> type = null;

            if (node instanceof LeafSchemaNode) {
                type = ((LeafSchemaNode) node).getType();
            } else {
                type = ((LeafListSchemaNode) node).getType();
            }

            // FIXME: fix case when type is e.g. typdef -> typedef -> leafref
            if (type instanceof Leafref) {
                final Leafref leafrefType = (Leafref) type;
                final String leafRefPathString = leafrefType.getPathStatement()
                        .toString();

                currentLeafRefContextBuilder
                        .setLeafRefTargetPathString(leafRefPathString);
                currentLeafRefContextBuilder.setReferencing(true);

                final LeafRefPathParserImpl leafRefPathParser = new LeafRefPathParserImpl(
                        schemaContext, currentModule, node);

                final ByteArrayInputStream leafRefPathInputStream = new ByteArrayInputStream(
                        leafRefPathString.getBytes(Charset.forName("UTF-8")));
                final LeafRefPath leafRefPath = leafRefPathParser
                        .parseLeafRefPathSourceToSchemaPath(leafRefPathInputStream);

                currentLeafRefContextBuilder.setLeafRefTargetPath(leafRefPath);

                final LeafRefContext currentLeafRefContext = currentLeafRefContextBuilder
                        .build();
                leafRefs.add(currentLeafRefContext);
                return currentLeafRefContext;
            }
        }

        return currentLeafRefContextBuilder.build();
    }

    private LeafRefContext buildLeafRefContextReferencedByTree(
            final DataSchemaNode node, final Module currentModule) throws IOException,
            LeafRefYangSyntaxErrorException {

        final LeafRefContextBuilder currentLeafRefContextBuilder = new LeafRefContextBuilder(
                node.getQName(), node.getPath(), schemaContext);

        if (node instanceof DataNodeContainer) {
            final DataNodeContainer dataNodeContainer = (DataNodeContainer) node;
            final Collection<DataSchemaNode> childNodes = dataNodeContainer
                    .getChildNodes();

            for (final DataSchemaNode childNode : childNodes) {
                final LeafRefContext childLeafRefContext = buildLeafRefContextReferencedByTree(
                        childNode, currentModule);

                if (childLeafRefContext.hasReferencedChild()
                        || childLeafRefContext.isReferenced()) {
                    currentLeafRefContextBuilder.addReferencedByChild(
                            childLeafRefContext,
                            childLeafRefContext.getNodeName());
                }
            }
        } else if (node instanceof ChoiceSchemaNode) {

            final ChoiceSchemaNode choice = (ChoiceSchemaNode) node;
            final Set<ChoiceCaseNode> cases = choice.getCases();

            for (final ChoiceCaseNode caseNode : cases) {
                final LeafRefContext childLeafRefContext = buildLeafRefContextReferencedByTree(
                        caseNode, currentModule);

                if (childLeafRefContext.hasReferencedChild()
                        || childLeafRefContext.isReferenced()) {
                    currentLeafRefContextBuilder.addReferencedByChild(
                            childLeafRefContext,
                            childLeafRefContext.getNodeName());
                }
            }

        } else if (node instanceof LeafSchemaNode
                || node instanceof LeafListSchemaNode) {

            final LinkedList<LeafRefContext> foundLeafRefs = getLeafRefsFor(node,
                    currentModule);
            if (!foundLeafRefs.isEmpty()) {
                currentLeafRefContextBuilder.setReferencedBy(true);
                for (final LeafRefContext leafRef : foundLeafRefs) {
                    currentLeafRefContextBuilder.addReferencedByLeafRefCtx(
                            leafRef.getNodeName(), leafRef);
                }
            }
        }

        return currentLeafRefContextBuilder.build();
    }

    private LinkedList<LeafRefContext> getLeafRefsFor(final DataSchemaNode node,
            final Module module) {
        final LeafRefPath nodeXPath = LeafRefUtils.schemaPathToLeafRefPath(
                node.getPath(), module);

        final LinkedList<LeafRefContext> foundLeafRefs = new LinkedList<LeafRefContext>();

        for (final LeafRefContext leafref : leafRefs) {
            final LeafRefPath leafRefTargetPath = leafref
                    .getAbsoluteLeafRefTargetPath();
            if (leafRefTargetPath.equals(nodeXPath)) {
                foundLeafRefs.add(leafref);
            }
        }

        return foundLeafRefs;
    }

    // private LeafRefContext buildLeafRefContextTreeFor(LeafRefContext parent,
    // Module module) {
    //
    // Collection<DataSchemaNode> childNodes = module.getChildNodes();
    // for (DataSchemaNode childNode : childNodes) {
    // LeafRefContext childLeafRefContext = buildLeafRefContextTreeFor(parent,
    // childNode);
    //
    // if(childLeafRefContext.hasReferencedByChild() ||
    // childLeafRefContext.isReferencedBy()) {
    // parent.addReferencedByChild(childLeafRefContext,
    // childLeafRefContext.getCurrentNodeQName());
    // }
    // if(childLeafRefContext.hasReferencingChild() ||
    // childLeafRefContext.isReferencing()) {
    // parent.addReferencingChild(childLeafRefContext,
    // childLeafRefContext.getCurrentNodeQName());
    // }
    // }
    //
    // return node;
    // }

}
