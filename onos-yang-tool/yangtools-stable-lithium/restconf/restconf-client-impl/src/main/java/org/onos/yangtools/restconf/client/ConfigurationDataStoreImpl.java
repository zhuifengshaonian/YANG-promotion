/*
 * Copyright (c) 2013 Cisco Systems, Inc. and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.onos.yangtools.restconf.client;

import com.google.common.base.Function;
import com.google.common.base.Optional;
import com.google.common.util.concurrent.ListenableFuture;
import com.sun.jersey.api.client.ClientResponse;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import javax.ws.rs.core.MediaType;
import org.onos.yangtools.binding.data.codec.impl.BindingNormalizedNodeCodecRegistry;
import org.onos.yangtools.restconf.client.api.data.ConfigurationDatastore;
import org.onos.yangtools.restconf.client.to.RestRpcError;
import org.onos.yangtools.restconf.client.to.RestRpcResult;
import org.onos.yangtools.restconf.common.ResourceUri;
import org.onos.yangtools.restconf.utils.RestconfUtils;
import org.onos.yangtools.yang.binding.DataObject;
import org.onos.yangtools.yang.binding.InstanceIdentifier;
import org.onos.yangtools.yang.common.RpcError;
import org.onos.yangtools.yang.common.RpcResult;
import org.onos.yangtools.yang.data.api.YangInstanceIdentifier;
import org.onos.yangtools.yang.model.api.DataSchemaNode;
import org.onos.yangtools.yang.model.api.SchemaContext;

public class ConfigurationDataStoreImpl extends AbstractDataStore implements ConfigurationDatastore  {

    @Override
    protected String getStorePrefix() {
        return ResourceUri.CONFIG.getPath();
    }

    public ConfigurationDataStoreImpl(RestconfClientImpl client) {
        super(client);
    }


    @Override
    public ListenableFuture<RpcResult<Boolean>> deleteData(final InstanceIdentifier<?> path) {
        final SchemaContext schemaContext = getClient().getSchemaContext();
        final BindingNormalizedNodeCodecRegistry mappingService = getClient().getMappingService();
        final YangInstanceIdentifier domPath = mappingService.toYangInstanceIdentifier(path);
        final Map.Entry<String, DataSchemaNode> pathWithSchema = RestconfUtils.toRestconfIdentifier(domPath, schemaContext);
        final String restconfPath = getStorePrefix() + pathWithSchema.getKey();
        return getClient().delete(restconfPath,MediaType.APPLICATION_XML,new Function<ClientResponse, RpcResult<Boolean>>() {
            @SuppressWarnings("unchecked")
            @Override
            public RpcResult<Boolean> apply(ClientResponse clientResponse) {
                if (clientResponse.getStatus() != 200) {
                    RpcError rpcError = new RestRpcError(RpcError.ErrorSeverity.ERROR,RpcError.ErrorType.RPC,null,null,"HTTP status "+clientResponse.getStatus(),null,null);
                    Collection<RpcError> errors = new ArrayList<RpcError>();
                    errors.add(rpcError);
                    RestRpcResult rpcResult = new RestRpcResult(false,null,errors);
                    return (RpcResult<Boolean>) Optional.of(rpcResult);
                }
                DataObject dataObject = deserialize(domPath, clientResponse.getEntityInputStream());
                RestRpcResult rpcResult = new RestRpcResult(true,dataObject,null);
                return (RpcResult<Boolean>) Optional.of(rpcResult);
            }
        });
    }

    @Override
    public ListenableFuture<RpcResult<Boolean>> putData(final InstanceIdentifier<?> path) {
        final SchemaContext schemaContext = getClient().getSchemaContext();
        final BindingNormalizedNodeCodecRegistry mappingService = getClient().getMappingService();
        final YangInstanceIdentifier domPath = mappingService.toYangInstanceIdentifier(path);
        final Map.Entry<String, DataSchemaNode> pathWithSchema = RestconfUtils.toRestconfIdentifier(domPath, schemaContext);
        final String restconfPath = getStorePrefix() + pathWithSchema.getKey();

        return getClient().put(restconfPath,MediaType.APPLICATION_XML,new Function<ClientResponse, RpcResult<Boolean>>() {
            @SuppressWarnings("unchecked")
            @Override
            public RpcResult<Boolean> apply(ClientResponse clientResponse) {
                if (clientResponse.getStatus() != 200) {
                    RpcError rpcError = new RestRpcError(RpcError.ErrorSeverity.ERROR,RpcError.ErrorType.RPC,null,null,"HTTP status "+clientResponse.getStatus(),null,null);
                    Collection<RpcError> errors = new ArrayList<RpcError>();
                    errors.add(rpcError);
                    RestRpcResult rpcResult = new RestRpcResult(false,null,errors);
                    return (RpcResult<Boolean>) Optional.of(rpcResult);
                }
                DataObject dataObject = deserialize(domPath, clientResponse.getEntityInputStream());
                RestRpcResult rpcResult = new RestRpcResult(true,dataObject);
                return (RpcResult<Boolean>) Optional.of(rpcResult);
            }
        });
    }
}
