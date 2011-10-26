/*
 * JBoss, Home of Professional Open Source
 * Copyright 2011, Red Hat, Inc. and individual contributors
 * by the @authors tag. See the copyright.txt in the distribution for a
 * full listing of individual contributors.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */

package org.mobicents.protocols.ss7.m3ua.impl.message;

import java.nio.ByteBuffer;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mobicents.protocols.ss7.m3ua.message.MessageType;
import org.mobicents.protocols.ss7.m3ua.message.transfer.PayloadData;
import org.mobicents.protocols.ss7.m3ua.parameter.ProtocolData;

import static org.junit.Assert.*;

/**
 * @author amit bhayani
 * 
 */
public class MessageFactoryTest {

	private MessageFactoryImpl messageFactory = new MessageFactoryImpl();

	/**
	 * 
	 */
	public MessageFactoryTest() {
		// TODO Auto-generated constructor stub
	}

	@BeforeClass
	public static void setUpClass() throws Exception {
	}

	@AfterClass
	public static void tearDownClass() throws Exception {
	}

	@Before
	public void setUp() {
	}

	@After
	public void tearDown() {
	}

	/**
	 * This test is from Cisco ITP for SST with padding at last
	 */
	@Test
	public void testSst() {
		byte[] data = new byte[] { 0x01, 0x00, 0x01, 0x01, 0x00, 0x00, 0x00, 0x3c, 0x02, 0x00, 0x00, 0x08, 0x00, 0x00,
				0x00, 0x00, 0x00, 0x06, 0x00, 0x08, 0x00, 0x00, 0x00, 0x19, 0x02, 0x10, 0x00, 0x21, 0x00, 0x00, 0x17,
				(byte) 0x9d, 0x00, 0x00, 0x18, 0x1c, 0x03, 0x03, 0x00, 0x02, 0x09, 0x00, 0x03, 0x05, 0x07, 0x02, 0x42,
				0x01, 0x02, 0x42, 0x01, 0x05, 0x03, (byte) 0xd5, 0x1c, 0x18, 0x00, 0x00, 0x00, 0x00 };

		ByteBuffer byteBuffer = ByteBuffer.allocate(256);
		byteBuffer.put(data);
		byteBuffer.flip();
		M3UAMessageImpl messageImpl = messageFactory.createMessage(byteBuffer);

		assertEquals(MessageType.PAYLOAD, messageImpl.getMessageType());
		PayloadData payloadData = (PayloadData) messageImpl;
		assertEquals(0l, payloadData.getNetworkAppearance().getNetApp());
		assertEquals(1, payloadData.getRoutingContext().getRoutingContexts().length);
		assertEquals(25l, payloadData.getRoutingContext().getRoutingContexts()[0]);
		ProtocolData protocolData = payloadData.getData();
		assertNotNull(protocolData);
		assertEquals(6045, protocolData.getOpc());
		assertEquals(6172, protocolData.getDpc());
		assertEquals(3, protocolData.getSI());
		assertEquals(2, protocolData.getSLS());
		assertEquals(3, protocolData.getNI());
		assertEquals(0, protocolData.getMP());

		// Test with sctp
		M3UAMessageImpl messageImpl1 = messageFactory.createSctpMessage(data);
		assertEquals(MessageType.PAYLOAD, messageImpl1.getMessageType());
		PayloadData payloadData1 = (PayloadData) messageImpl1;
		assertEquals(0l, payloadData1.getNetworkAppearance().getNetApp());
		assertEquals(1, payloadData1.getRoutingContext().getRoutingContexts().length);
		assertEquals(25l, payloadData1.getRoutingContext().getRoutingContexts()[0]);
		ProtocolData protocolData1 = payloadData1.getData();
		assertNotNull(protocolData1);
		assertEquals(6045, protocolData1.getOpc());
		assertEquals(6172, protocolData1.getDpc());
		assertEquals(3, protocolData1.getSI());
		assertEquals(2, protocolData1.getSLS());
		assertEquals(3, protocolData1.getNI());
		assertEquals(0, protocolData1.getMP());

	}

	@Test
	public void test2() {
		byte[] data = new byte[] { 0x01, 0x00, 0x01, 0x01, 0x00, 0x00, 0x01, 0x08, 0x00, 0x06, 0x00, 0x08, 0x00, 0x00,
				0x00, 0x01, 0x02, 0x10, 0x00, (byte) 0xf8, 0x00, 0x00, 0x00, 0x02, 0x00, 0x00, 0x00, 0x01, 0x03, 0x02,
				0x00, 0x01, 0x09, 0x01, 0x03, 0x10, 0x1d, 0x0d, 0x53, 0x01, 0x00, (byte) 0x91, 0x00, 0x12, 0x04, 0x19,
				0x09, 0x31, (byte) 0x91, 0x39, 0x08, 0x0d, 0x53, 0x02, 0x00, (byte) 0x92, 0x00, 0x12, 0x04, 0x19, 0x09,
				0x31, (byte) 0x91, 0x39, 0x09, (byte) 0xc6, 0x62, (byte) 0x81, (byte) 0xc3, 0x48, 0x04, 0x00, 0x08,
				0x00, 0x10, 0x6b, 0x1a, 0x28, 0x18, 0x06, 0x07, 0x00, 0x11, (byte) 0x86, 0x05, 0x01, 0x01, 0x01,
				(byte) 0xa0, 0x0d, 0x60, 0x0b, (byte) 0xa1, 0x09, 0x06, 0x07, 0x04, 0x00, 0x00, 0x01, 0x00, 0x32, 0x01,
				0x6c, (byte) 0x81, (byte) 0x9e, (byte) 0xa1, (byte) 0x81, (byte) 0x9b, 0x02, 0x01, 0x01, 0x02, 0x01,
				0x00, 0x30, (byte) 0x81, (byte) 0x92, (byte) 0x80, 0x01, 0x0c, (byte) 0x82, 0x09, 0x03, 0x10, 0x13,
				0x60, (byte) 0x99, (byte) 0x86, 0x00, 0x00, 0x02, (byte) 0x83, 0x08, 0x04, 0x13, 0x19, (byte) 0x89,
				0x17, (byte) 0x97, 0x31, 0x72, (byte) 0x85, 0x01, 0x0a, (byte) 0x88, 0x01, 0x01, (byte) 0x8a, 0x05,
				0x04, 0x13, 0x19, (byte) 0x89, (byte) 0x86, (byte) 0xbb, 0x04, (byte) 0x80, 0x02, (byte) 0x80,
				(byte) 0x90, (byte) 0x9c, 0x01, 0x0c, (byte) 0x9f, 0x32, 0x08, 0x04, 0x64, 0x58, 0x05, (byte) 0x94,
				0x74, 0x34, (byte) 0xf3, (byte) 0xbf, 0x33, 0x02, (byte) 0x80, 0x00, (byte) 0xbf, 0x34, 0x2b, 0x02,
				0x01, 0x23, (byte) 0x80, 0x08, 0x10, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, (byte) 0x81, 0x07,
				(byte) 0x91, 0x19, (byte) 0x89, (byte) 0x86, (byte) 0x91, 0x01, (byte) 0x82, (byte) 0x82, 0x08, 0x04,
				(byte) 0x97, 0x19, (byte) 0x89, (byte) 0x86, (byte) 0x91, 0x01, (byte) 0x82, (byte) 0xa3, 0x09,
				(byte) 0x80, 0x07, 0x04, (byte) 0xf4, (byte) 0x86, 0x00, 0x65, 0x18, (byte) 0xd1, (byte) 0xbf, 0x35,
				0x03, (byte) 0x83, 0x01, 0x11, (byte) 0x9f, 0x36, 0x08, (byte) 0xd2, 0x25, 0x00, 0x00, 0x0d, 0x62,
				0x0b, (byte) 0x88, (byte) 0x9f, 0x37, 0x07, (byte) 0x91, 0x19, (byte) 0x89, (byte) 0x86, (byte) 0x95,
				(byte) 0x99, (byte) 0x89, (byte) 0x9f, 0x39, 0x08, 0x02, 0x11, 0x20, 0x10, (byte) 0x91, 0x45, 0x51,
				0x23 };

		ByteBuffer byteBuffer = ByteBuffer.allocate(8192);
		byteBuffer.put(data);
		byteBuffer.flip();
		M3UAMessageImpl messageImpl = messageFactory.createMessage(byteBuffer);

		assertEquals(MessageType.PAYLOAD, messageImpl.getMessageType());
		PayloadData payloadData = (PayloadData) messageImpl;
		assertNull(payloadData.getNetworkAppearance());
		assertEquals(1, payloadData.getRoutingContext().getRoutingContexts().length);
		assertEquals(1l, payloadData.getRoutingContext().getRoutingContexts()[0]);
		ProtocolData protocolData = payloadData.getData();
		assertNotNull(protocolData);
		assertEquals(2, protocolData.getOpc());
		assertEquals(1, protocolData.getDpc());
		assertEquals(3, protocolData.getSI());
		assertEquals(1, protocolData.getSLS());
		assertEquals(2, protocolData.getNI());
		assertEquals(0, protocolData.getMP());

		// Test with SCTP
		messageImpl = messageFactory.createSctpMessage(data);

		assertEquals(MessageType.PAYLOAD, messageImpl.getMessageType());
		payloadData = (PayloadData) messageImpl;
		assertNull(payloadData.getNetworkAppearance());
		assertEquals(1, payloadData.getRoutingContext().getRoutingContexts().length);
		assertEquals(1l, payloadData.getRoutingContext().getRoutingContexts()[0]);
		protocolData = payloadData.getData();
		assertNotNull(protocolData);
		assertEquals(2, protocolData.getOpc());
		assertEquals(1, protocolData.getDpc());
		assertEquals(3, protocolData.getSI());
		assertEquals(1, protocolData.getSLS());
		assertEquals(2, protocolData.getNI());
		assertEquals(0, protocolData.getMP());

	}

	@Test
	public void test3() {
		byte[] data = new byte[] { 0x01, 0x00, 0x01, 0x01, 0x00, 0x00, 0x00, (byte) 0xec, 0x00, 0x06, 0x00, 0x08, 0x00,
				0x00, 0x00, 0x01, 0x02, 0x10, 0x00, (byte) 0xdc, 0x00, 0x00, 0x00, 0x02, 0x00, 0x00, 0x00, 0x01, 0x03,
				0x02, 0x00, 0x0f, 0x09, 0x01, 0x03, 0x10, 0x1d, 0x0d, 0x53, 0x01, 0x00, (byte) 0x91, 0x00, 0x12, 0x04,
				0x19, 0x09, 0x31, (byte) 0x91, 0x39, 0x08, 0x0d, 0x53, 0x02, 0x00, (byte) 0x92, 0x00, 0x12, 0x04, 0x19,
				0x09, 0x31, (byte) 0x91, 0x39, 0x09, (byte) 0xaa, 0x62, (byte) 0x81, (byte) 0xa7, 0x48, 0x04, 0x00,
				0x07, (byte) 0xff, (byte) 0xf0, 0x6c, (byte) 0x81, (byte) 0x9e, (byte) 0xa1, (byte) 0x81, (byte) 0x9b,
				0x02, 0x01, 0x01, 0x02, 0x01, 0x00, 0x30, (byte) 0x81, (byte) 0x92, (byte) 0x80, 0x01, 0x0c,
				(byte) 0x82, 0x09, 0x03, 0x10, 0x13, 0x60, (byte) 0x99, (byte) 0x86, 0x00, 0x00, 0x02, (byte) 0x83,
				0x08, 0x04, 0x13, 0x19, (byte) 0x89, 0x17, (byte) 0x97, 0x31, 0x72, (byte) 0x85, 0x01, 0x0a,
				(byte) 0x88, 0x01, 0x01, (byte) 0x8a, 0x05, 0x04, 0x13, 0x19, (byte) 0x89, (byte) 0x86, (byte) 0xbb,
				0x04, (byte) 0x80, 0x02, (byte) 0x80, (byte) 0x90, (byte) 0x9c, 0x01, 0x0c, (byte) 0x9f, 0x32, 0x08,
				0x04, 0x64, 0x58, 0x05, (byte) 0x94, 0x74, 0x34, (byte) 0xf3, (byte) 0xbf, 0x33, 0x02, (byte) 0x80,
				0x00, (byte) 0xbf, 0x34, 0x2b, 0x02, 0x01, 0x23, (byte) 0x80, 0x08, 0x10, 0x00, 0x00, 0x00, 0x00, 0x00,
				0x00, 0x00, (byte) 0x81, 0x07, (byte) 0x91, 0x19, (byte) 0x89, (byte) 0x86, (byte) 0x91, 0x01,
				(byte) 0x82, (byte) 0x82, 0x08, 0x04, (byte) 0x97, 0x19, (byte) 0x89, (byte) 0x86, (byte) 0x91, 0x01,
				(byte) 0x82, (byte) 0xa3, 0x09, (byte) 0x80, 0x07, 0x04, (byte) 0xf4, (byte) 0x86, 0x00, 0x65, 0x18,
				(byte) 0xd1, (byte) 0xbf, 0x35, 0x03, (byte) 0x83, 0x01, 0x11, (byte) 0x9f, 0x36, 0x08, (byte) 0xd2,
				0x25, 0x00, 0x00, 0x0d, 0x62, 0x0b, (byte) 0x88, (byte) 0x9f, 0x37, 0x07, (byte) 0x91, 0x19,
				(byte) 0x89, (byte) 0x86, (byte) 0x95, (byte) 0x99, (byte) 0x89, (byte) 0x9f, 0x39, 0x08, 0x02, 0x11,
				0x20, 0x10, (byte) 0x91, 0x45, 0x51, 0x23 };

		ByteBuffer byteBuffer = ByteBuffer.allocate(8192);
		byteBuffer.put(data);
		byteBuffer.flip();
		M3UAMessageImpl messageImpl = messageFactory.createMessage(byteBuffer);

		assertEquals(MessageType.PAYLOAD, messageImpl.getMessageType());
		PayloadData payloadData = (PayloadData) messageImpl;
		assertNull(payloadData.getNetworkAppearance());
		assertEquals(1, payloadData.getRoutingContext().getRoutingContexts().length);
		assertEquals(1l, payloadData.getRoutingContext().getRoutingContexts()[0]);
		ProtocolData protocolData = payloadData.getData();
		assertNotNull(protocolData);
		assertEquals(2, protocolData.getOpc());
		assertEquals(1, protocolData.getDpc());
		assertEquals(3, protocolData.getSI());
		assertEquals(15, protocolData.getSLS());
		assertEquals(2, protocolData.getNI());
		assertEquals(0, protocolData.getMP());

		// Test with SCTP
		messageImpl = messageFactory.createSctpMessage(data);

		assertEquals(MessageType.PAYLOAD, messageImpl.getMessageType());
		payloadData = (PayloadData) messageImpl;
		assertNull(payloadData.getNetworkAppearance());
		assertEquals(1, payloadData.getRoutingContext().getRoutingContexts().length);
		assertEquals(1l, payloadData.getRoutingContext().getRoutingContexts()[0]);
		protocolData = payloadData.getData();
		assertNotNull(protocolData);
		assertEquals(2, protocolData.getOpc());
		assertEquals(1, protocolData.getDpc());
		assertEquals(3, protocolData.getSI());
		assertEquals(15, protocolData.getSLS());
		assertEquals(2, protocolData.getNI());
		assertEquals(0, protocolData.getMP());

	}

}
