import java.util.List;

import org.onos.yang.gen.v1.urn.onos.controller.network.rev2013520.ControllerNetworkData;
import org.onos.yang.gen.v1.urn.onos.controller.network.rev2013520.Network;
import org.onos.yang.gen.v1.urn.onos.controller.network.rev2013520.network.topologies.Topology;
import org.onos.yang.gen.v1.urn.onos.controller.network.rev2013520.network.topologies.topology.nodes.Node;
import org.onos.yang.gen.v1.urn.onos.controller.openflow.rev2013520.Node1;

/*
 * Copyright (c) 2013 Cisco Systems, Inc. and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

public class Example {
    

    
    void udpateTopology(ControllerNetworkData network) {
        List<Topology> topologies = network.getNetwork().getTopologies().getTopology();
        for (Topology topology : topologies) {
            List<Node> nodes = topology.getNodes().getNode();
            for (Node node : nodes) {
                
                
                Node1 ofNode = node.getAugmentation(Node1.class);
                ofNode.getDatapathId();
            }
        }
        
    }
}
