/*
 * Copyright 2015 Open Networking Laboratory
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.onosproject.net;

import java.util.Objects;

import static com.google.common.base.MoreObjects.toStringHelper;

/**
 * Implementation of ODU client port (Optical channel Data Unit).
 * Also referred to as a T-port or wide band port.
 * See ITU G.709 "Interfaces for the Optical Transport Network (OTN)"
 */

public class OduCltPort extends DefaultPort {

    public enum SignalType {
        CLT_1GBE,
        CLT_10GBE,
        CLT_40GBE,
        CLT_100GBE
    }

    private final SignalType signalType;


    /**
     * Creates an ODU client port in the specified network element.
     *
     * @param element           parent network element
     * @param number            port number
     * @param isEnabled         port enabled state
     * @param signalType        ODU client signal type
     * @param annotations       optional key/value annotations
     */
    public OduCltPort(Element element, PortNumber number, boolean isEnabled,
                      SignalType signalType, Annotations... annotations) {
        super(element, number, isEnabled, Type.ODUCLT, 0, annotations);
        this.signalType = signalType;
    }

    /**
     * Returns ODU client signal type.
     *
     * @return ODU client signal type
     */
    public SignalType signalType() {
        return signalType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(number(), isEnabled(), type(), signalType, annotations());
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof OduCltPort) {
            final OduCltPort other = (OduCltPort) obj;
            return Objects.equals(this.element().id(), other.element().id()) &&
                    Objects.equals(this.number(), other.number()) &&
                    Objects.equals(this.isEnabled(), other.isEnabled()) &&
                    Objects.equals(this.signalType, other.signalType) &&
                    Objects.equals(this.annotations(), other.annotations());
        }
        return false;
    }


    @Override
    public String toString() {
        return toStringHelper(this)
                .add("element", element().id())
                .add("number", number())
                .add("isEnabled", isEnabled())
                .add("type", type())
                .add("signalType", signalType)
                .toString();
    }

}
