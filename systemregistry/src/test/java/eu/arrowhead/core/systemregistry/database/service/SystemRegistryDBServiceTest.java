/********************************************************************************
 * Copyright (c) 2019 AITIA
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   AITIA - implementation
 *   Arrowhead Consortia - conceptualization
 ********************************************************************************/

package eu.arrowhead.core.systemregistry.database.service;

import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;

import eu.arrowhead.common.SecurityUtilities;
import eu.arrowhead.common.database.entity.System;
import eu.arrowhead.common.database.entity.SystemRegistry;
import eu.arrowhead.common.database.repository.DeviceRepository;
import eu.arrowhead.common.database.repository.SystemRegistryRepository;
import eu.arrowhead.common.database.repository.SystemRepository;
import eu.arrowhead.common.drivers.CertificateAuthorityDriver;
import eu.arrowhead.common.dto.shared.SystemRegistryRequestDTO;
import eu.arrowhead.common.dto.shared.SystemRequestDTO;
import eu.arrowhead.common.exception.InvalidParameterException;
import eu.arrowhead.common.processor.NetworkAddressPreProcessor;
import eu.arrowhead.common.verifier.CommonNamePartVerifier;
import eu.arrowhead.common.verifier.NetworkAddressVerifier;

@RunWith (SpringRunner.class)
public class SystemRegistryDBServiceTest {
	
	//=================================================================================================
	// members
	
	@InjectMocks
	private SystemRegistryDBService systemRegistryDBService; 
	
	@Mock
    private SystemRegistryRepository systemRegistryRepository;
	
	@Mock
    private SystemRepository systemRepository;
	
	@Mock
    private DeviceRepository deviceRepository;
	
	@Mock
    private SecurityUtilities securityUtilities;
	
	@Mock
    private CertificateAuthorityDriver caDriver;
	
	@Spy
    private CommonNamePartVerifier cnVerifier;
	
	@Spy
    private NetworkAddressPreProcessor networkAddressPreProcessor;
	
	@Spy
    private NetworkAddressVerifier networkAddressVerifier;
	
	//=================================================================================================
	// methods
	
	//-------------------------------------------------------------------------------------------------
	@Before
	public void setup() {
		ReflectionTestUtils.setField(networkAddressVerifier, "cnVerifier", cnVerifier);
	}	
	
	//-------------------------------------------------------------------------------------------------
	@Test(expected = InvalidParameterException.class)
	public void createSystemByIdWrongSystemNameTest() {
		final String systemName0 = "test_name";
		final String address0 = "testAddress0";
		final int port0 = 1;
		final String authenticationInfo0 = null;
		
		try {
			systemRegistryDBService.createSystem(systemName0, address0, port0, authenticationInfo0, null);
		} catch (final InvalidParameterException ex) {
			Assert.assertEquals("System name has invalid format. Name must match with the following regular expression: " + CommonNamePartVerifier.COMMON_NAME_PART_PATTERN_STRING, ex.getMessage());			
			
			throw ex;
		}
	}
	
	//-------------------------------------------------------------------------------------------------
	@Test(expected = InvalidParameterException.class)
	public void createSystemByIdWrongSystemAddressTest() {
		final String systemName0 = "testname";
		final String address0 = "0.0.0.0";
		final int port0 = 1;
		final String authenticationInfo0 = null;
		
		try {
			systemRegistryDBService.createSystem(systemName0, address0, port0, authenticationInfo0, null);
		} catch (final InvalidParameterException ex) {
			Assert.assertEquals("0.0.0.0 ipv4 network address is invalid: placeholder address is denied.", ex.getMessage());			
			
			throw ex;
		}
	}
	
	//-------------------------------------------------------------------------------------------------
	@Test(expected = InvalidParameterException.class)
	public void updateSystemByIdWrongSystemNameTest() {
		final String systemName0 = "test_name";
		final String address0 = "testAddress0";
		final int port0 = 1;
		final long testId0 = 1;
		final String authenticationInfo0 = null;
		
		try {
			systemRegistryDBService.updateSystem(testId0, systemName0, address0, port0, authenticationInfo0, null);
		} catch (final InvalidParameterException ex) {
			Assert.assertEquals("System name has invalid format. Name must match with the following regular expression: " + CommonNamePartVerifier.COMMON_NAME_PART_PATTERN_STRING, ex.getMessage());			
			
			throw ex;
		}
	}
	
	//-------------------------------------------------------------------------------------------------
	@Test(expected = InvalidParameterException.class)
	public void updateSystemByIdWrongSystemAddressTest() {
		final String systemName0 = "testname";
		final String address0 = "0.0.0.0";
		final int port0 = 1;
		final long testId0 = 1;
		final String authenticationInfo0 = null;
		
		try {
			systemRegistryDBService.updateSystem(testId0, systemName0, address0, port0, authenticationInfo0, null);
		} catch (final InvalidParameterException ex) {
			Assert.assertEquals("0.0.0.0 ipv4 network address is invalid: placeholder address is denied.", ex.getMessage());
			
			throw ex;
		}
	}
	
	//-------------------------------------------------------------------------------------------------
	@Test(expected = InvalidParameterException.class)
	public void mergeSystemByIdWrongSystemNameTest() {
		final String systemName0 = "test_name";
		final String address0 = "testAddress0";
		final int port0 = 1;
		final long testId0 = 1;
		final String authenticationInfo0 = null;
		
		try {
			systemRegistryDBService.mergeSystem(testId0, systemName0, address0, port0, authenticationInfo0, null);
		} catch (final InvalidParameterException ex) {
			Assert.assertEquals("System name has invalid format. Name must match with the following regular expression: " + CommonNamePartVerifier.COMMON_NAME_PART_PATTERN_STRING, ex.getMessage());			
			
			throw ex;
		}
	}
	
	//-------------------------------------------------------------------------------------------------
	@Test(expected = InvalidParameterException.class)
	public void mergeSystemByIdWrongSystemAddressTest() {
		final String systemName0 = "testname";
		final String address0 = "0.0.0.0";
		final int port0 = 1;
		final long testId0 = 1;
		final String authenticationInfo0 = null;
		
		try {
			systemRegistryDBService.mergeSystem(testId0, systemName0, address0, port0, authenticationInfo0, null);
		} catch (final InvalidParameterException ex) {
			Assert.assertEquals("0.0.0.0 ipv4 network address is invalid: placeholder address is denied.", ex.getMessage());
			
			throw ex;
		}
	}
	
	//-------------------------------------------------------------------------------------------------
	@Test(expected = IllegalArgumentException.class)
	public void registerSystemRegistryWrongSystemNameTest() {
		final SystemRequestDTO system = new SystemRequestDTO();
		system.setSystemName("test_name");
		final SystemRegistryRequestDTO request = new SystemRegistryRequestDTO();
		request.setSystem(system);

		try {
			systemRegistryDBService.registerSystemRegistry(request);
		} catch (final IllegalArgumentException ex) {
			Assert.assertEquals("System name has invalid format. Name must match with the following regular expression: " + CommonNamePartVerifier.COMMON_NAME_PART_PATTERN_STRING, ex.getMessage());			
			
			throw ex;
		}
	}
	
	//-------------------------------------------------------------------------------------------------
	@Test(expected = IllegalArgumentException.class)
	public void registerSystemRegistryWrongSystemAddressTest() {
		final SystemRequestDTO system = new SystemRequestDTO();
		system.setSystemName("testname");
		system.setAddress("0.0.0.0");
		final SystemRegistryRequestDTO request = new SystemRegistryRequestDTO();
		request.setSystem(system);

		try {
			systemRegistryDBService.registerSystemRegistry(request);
		} catch (final IllegalArgumentException ex) {
			Assert.assertEquals("0.0.0.0 ipv4 network address is invalid: placeholder address is denied.", ex.getMessage());
			
			throw ex;
		}
	}
	
	//-------------------------------------------------------------------------------------------------
	@Test(expected = IllegalArgumentException.class)
	public void updateSystemRegistryByIdWrongSystemNameTest() {
		final SystemRequestDTO system = new SystemRequestDTO();
		system.setSystemName("test_name");
		final SystemRegistryRequestDTO request = new SystemRegistryRequestDTO();
		request.setSystem(system);

		try {
			systemRegistryDBService.updateSystemRegistryById(1, request);
		} catch (final IllegalArgumentException ex) {
			Assert.assertEquals("System name has invalid format. Name must match with the following regular expression: " + CommonNamePartVerifier.COMMON_NAME_PART_PATTERN_STRING, ex.getMessage());			
			
			throw ex;
		}
	}
	
	//-------------------------------------------------------------------------------------------------
	@Test(expected = IllegalArgumentException.class)
	public void updateSystemRegistryByIdWrongSystemAddressTest() {
		final SystemRequestDTO system = new SystemRequestDTO();
		system.setSystemName("testname");
		system.setAddress("0.0.0.0");
		final SystemRegistryRequestDTO request = new SystemRegistryRequestDTO();
		request.setSystem(system);

		try {
			systemRegistryDBService.updateSystemRegistryById(1, request);
		} catch (final IllegalArgumentException ex) {
			Assert.assertEquals("0.0.0.0 ipv4 network address is invalid: placeholder address is denied.", ex.getMessage());
			
			throw ex;
		}
	}
	
	//-------------------------------------------------------------------------------------------------
	@Test(expected = IllegalArgumentException.class)
	public void mergeSystemRegistryByIdWrongSystemNameTest() {
		final SystemRegistry entry = new SystemRegistry();
		entry.setSystem(new System());
		when(systemRegistryRepository.findById(1L)).thenReturn(Optional.of(entry));
		
		final SystemRequestDTO system = new SystemRequestDTO();
		system.setSystemName("test_name");
		final SystemRegistryRequestDTO request = new SystemRegistryRequestDTO();
		request.setSystem(system);

		try {
			systemRegistryDBService.mergeSystemRegistryById(1, request);
		} catch (final IllegalArgumentException ex) {
			Assert.assertEquals("System name has invalid format. Name must match with the following regular expression: " + CommonNamePartVerifier.COMMON_NAME_PART_PATTERN_STRING, ex.getMessage());			
			
			throw ex;
		}
	}
	
	//-------------------------------------------------------------------------------------------------
	@Test(expected = InvalidParameterException.class)
	public void mergeSystemRegistryByIdWrongSystemAddressTest() {
		final SystemRegistry entry = new SystemRegistry();
		entry.setSystem(new System());
		when(systemRegistryRepository.findById(1L)).thenReturn(Optional.of(entry));
		
		final SystemRequestDTO system = new SystemRequestDTO();
		system.setSystemName("testname");
		system.setAddress("0.0.0.0");
		final SystemRegistryRequestDTO request = new SystemRegistryRequestDTO();
		request.setSystem(system);

		try {
			systemRegistryDBService.mergeSystemRegistryById(1, request);
		} catch (final InvalidParameterException ex) {
			Assert.assertEquals("0.0.0.0 ipv4 network address is invalid: placeholder address is denied.", ex.getMessage());
			
			throw ex;
		}
	}
}