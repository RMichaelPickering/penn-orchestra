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
package edu.upenn.cis.orchestra.datamodel;


public class IntPeerID extends AbstractPeerID {
	private static final long serialVersionUID = 1L;

	static final byte typeId = 1;
	static final String typeName = "INTEGER";
	
	public IntPeerID() {
		_id = -1;
	}
	
	public IntPeerID(int value) {
		_id = value;
	}
	
	public int getID() {
		return _id;
	}
	
	public int hashCode() {
		return _id;
	}
	
	public boolean equals(Object o) {
		if (o == null || o.getClass() != this.getClass())
			return false;
		IntPeerID ipi = (IntPeerID) o;
		return (_id == ipi._id);
	}
	
	public int compareTo(AbstractPeerID o) {
		if (!(o instanceof IntPeerID)) {
			return typeId - getIdForClass(o.getClass());
		}

		IntPeerID ipi = (IntPeerID) o;
		if (_id < ipi._id)
			return -1;
		else if (_id > ipi._id)
			return 1;
		else
			return 0;
	}
	
	public String toString() {
		return "@" + _id;
	}
	
	public IntPeerID duplicate() {
		// Safe since IntPeerID is immutable
		return this;
	}
	
	private final int _id;

	@Override
	protected byte[] getSubclassBytes() {
		return IntType.getBytes(_id);
	}

	/**
	 * Decode the ID from a byte array generated by a class to getSubclassBytes
	 * 
	 * @param bytes		The data to read from
	 * @param offset	The start of the data for the ID
	 * @param length	The length of the data for the ID
	 * @return			The object encoding the ID
	 */
	protected static AbstractPeerID fromSubclassBytes(byte[] bytes, int offset, int length) {
		return new IntPeerID(IntType.getValFromBytes(bytes, offset));
	}
	
	protected static AbstractPeerID fromStringRep(String rep) {
		return new IntPeerID(Integer.parseInt(rep));
	}

	@Override
	protected String getSubclassString() {
		return Integer.toString(_id);
	}
}