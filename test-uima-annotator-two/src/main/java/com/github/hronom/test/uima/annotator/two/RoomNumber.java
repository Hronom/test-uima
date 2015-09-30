/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package com.github.hronom.test.uima.annotator.two;

import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;
import org.apache.uima.jcas.tcas.Annotation;

public class RoomNumber extends Annotation {
    public final static int typeIndexID = JCasRegistry.register(RoomNumber.class);

    public final static int type = typeIndexID;

    protected RoomNumber() {
    }

    public RoomNumber(int addr, TOP_Type type) {
        super(addr, type);
        readObject();
    }

    public RoomNumber(JCas jcas) {
        super(jcas);
        readObject();
    }

    public RoomNumber(JCas jcas, int begin, int end) {
        super(jcas);
        setBegin(begin);
        setEnd(end);
        readObject();
    }

    public int getTypeIndexID() {
        return typeIndexID;
    }

    private void readObject() {
    }

    // *--------------*
    // * Feature: building
    public String getBuilding() {
        if (RoomNumber_Type.featOkTst && ((RoomNumber_Type) jcasType).casFeat_building == null) {
            this.jcasType.jcas.throwFeatMissing("building", "TestAnnot2.RoomNumber");
        }
        return jcasType.ll_cas.ll_getStringValue(
            addr, ((RoomNumber_Type) jcasType).casFeatCode_building
        );
    }

    public void setBuilding(String v) {
        if (RoomNumber_Type.featOkTst && ((RoomNumber_Type) jcasType).casFeat_building == null) {
            this.jcasType.jcas.throwFeatMissing("building", "TestAnnot2.RoomNumber");
        }
        jcasType.ll_cas
            .ll_setStringValue(addr, ((RoomNumber_Type) jcasType).casFeatCode_building, v);
    }
}
