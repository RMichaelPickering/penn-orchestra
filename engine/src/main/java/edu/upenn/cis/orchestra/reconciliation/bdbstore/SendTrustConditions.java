/*
 * Copyright (C) 2010 Trustees of the University of Pennsylvania
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS of ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package edu.upenn.cis.orchestra.reconciliation.bdbstore;

import java.io.Serializable;

import edu.upenn.cis.orchestra.datamodel.Schema;
import edu.upenn.cis.orchestra.datamodel.TrustConditions;
import edu.upenn.cis.orchestra.reconciliation.ISchemaIDBinding;
import edu.upenn.cis.orchestra.reconciliation.SchemaIDBinding;

class SendTrustConditions implements Serializable {
	private static final long serialVersionUID = 1L;
	private final byte[] tcBytes;
	transient ISchemaIDBinding sch;
	final Schema s;
	
	SendTrustConditions(TrustConditions tc, ISchemaIDBinding sch, Schema s) {
		tcBytes = tc.getBytes(sch);
		this.sch = sch;
		this.s = s;
	}
	
	TrustConditions getTrustConditions(SchemaIDBinding sch) {
		this.sch = sch;
		return new TrustConditions(tcBytes,sch);
	}	
}
