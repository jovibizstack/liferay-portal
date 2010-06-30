/*
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portal.monitoring.statistics.service;

import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.monitoring.MonitoringException;
import com.liferay.portal.monitoring.statistics.DataSampleProcessor;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * <a href="ServerStatistics.java.html"><b><i>View Source</i></b></a>
 *
 * @author Michael C. Han
 */
public class ServerStatistics
	implements DataSampleProcessor<ServiceRequestDataSample> {

	public long getAverageTime(
			String className, String methodName, String[] parameterTypes)
		throws SystemException {

		ServiceStatistics serviceStatistics = _serviceStatistics.get(className);

		if (serviceStatistics != null) {
			return serviceStatistics.getAverageTime(methodName, parameterTypes);
		}

		return -1;
	}

	public long getErrorCount(
			String className, String methodName, String[] parameterTypes)
		throws SystemException {

		ServiceStatistics serviceStatistics = _serviceStatistics.get(className);

		if (serviceStatistics != null) {
			return serviceStatistics.getErrorCount(methodName, parameterTypes);
		}

		return -1;
	}

	public long getMaxTime(
			String className, String methodName, String[] parameterTypes)
		throws SystemException {

		ServiceStatistics serviceStatistics = _serviceStatistics.get(className);

		if (serviceStatistics != null) {
			return serviceStatistics.getMaxTime(methodName, parameterTypes);
		}

		return -1;
	}

	public long getMinTime(
			String className, String methodName, String[] parameterTypes)
		throws SystemException {

		ServiceStatistics serviceStatistics = _serviceStatistics.get(className);

		if (serviceStatistics != null) {
			return serviceStatistics.getMinTime(methodName, parameterTypes);
		}

		return -1;
	}

	public long getRequestCount(
			String className, String methodName, String[] parameterTypes)
		throws SystemException {

		ServiceStatistics serviceStatistics = _serviceStatistics.get(className);

		if (serviceStatistics != null) {
			return serviceStatistics.getRequestCount(
				methodName, parameterTypes);
		}

		return -1;
	}

	public void processDataSample(ServiceRequestDataSample dataSample)
		throws MonitoringException {

		String className = dataSample.getMethodKey().getClassName();

		ServiceStatistics serviceStatistics = _serviceStatistics.get(className);
		if (serviceStatistics == null) {
			serviceStatistics = new ServiceStatistics(className);

			_serviceStatistics.put(className, serviceStatistics);
		}

		serviceStatistics.processDataSample(dataSample);
	}

	private Map<String, ServiceStatistics> _serviceStatistics =
		new ConcurrentHashMap<String, ServiceStatistics>();
}