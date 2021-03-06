/*******************************************************************************
 * Copyright (C) 2012 Raphfrk
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy of
 * this software and associated documentation files (the "Software"), to deal in
 * the Software without restriction, including without limitation the rights to
 * use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies
 * of the Software, and to permit persons to whom the Software is furnished to do
 * so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 ******************************************************************************/
package com.raphfrk.protocol;

public class Packet09Respawn extends Packet {
	
	public Packet09Respawn(Packet packet) {
		super(packet, 9);
	}

	public Packet09Respawn(byte dimension, byte unknown, byte creative, short height, long seed, String levelType) {
		super(14 + levelType.length() + 2);
		super.writeByte((byte)0x09);
		super.writeByte(dimension);
		super.writeByte(unknown);
		super.writeByte(creative);
		super.writeShort(height);
		super.writeLong(seed);
		super.writeString16(levelType);
	}
	
	public byte getDimension() {
		return getByte(1);
	}
	
	public byte getUnknownField() {
		return getByte(2);
	}
	
	public byte getCreative() {
		return getByte(3);
	}
	
	public short getHeight() {
		return getShort(4);
	}
	
	public long getLong() {
		return getLong(6);
	}
	
}
