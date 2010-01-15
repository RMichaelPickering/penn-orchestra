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
package edu.upenn.cis.orchestra;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Common ancestor for {@code IOpertationFactory} implementations which are based
 * on a directory containing a DbUnit XML dataset file for each operation.
 * <p>
 * If {@code dumpDatasets == true} (see below), then the content of each file
 * should be DbUnit XML datasets. This dataset should be the expected state of
 * the database after the operation is performed. This allows a check to be done
 * against the actual state.
 * <p>
 * If {@code dumpDatasets == false}, then the contents of the Orchestra
 * operation files are irrelevant because we will be writing out the resulting
 * database step to the file, although not in the given directory, but the
 * working directory of the JVM.
 * 
 * @see edu.upenn.cis.orchestra.OrchestraOperationExecutor
 * @author John Frommeyer
 * 
 */
public abstract class AbstractMultiSystemDsFileOperationFactory implements
		IOrchestraOperationFactory {

	/** The logger. */
	protected final Logger logger = LoggerFactory.getLogger(getClass());
	/** The schema for the system we are testing. */
	protected final OrchestraSchema orchestraSchema;
	/** Location of test data. */
	protected final File testDataDirectory;
	/** Indicates if we are dumping, or testing, datasets. */
	protected final boolean dumpDatasets;
	
	/** Translates Berkeley update store into DbUnit dataset. */
	protected final BdbDataSetFactory bdbDataSetFactory;

	/**
	 * Creates an {@code IOrchestraOperationFactory} which will use DbUnit XML dataset
	 * files to configure operations.
	 * 
	 * @param orchestraSchema
	 * @param testDataDirectory
	 * @param dumpDatasets
	 * @param bdbDataSetFactory
	 */
	protected AbstractMultiSystemDsFileOperationFactory(
			@SuppressWarnings("hiding") final OrchestraSchema orchestraSchema,
			@SuppressWarnings("hiding") final File testDataDirectory,
			@SuppressWarnings("hiding") final boolean dumpDatasets,
			@SuppressWarnings("hiding") final BdbDataSetFactory bdbDataSetFactory) {
		this.orchestraSchema = orchestraSchema;
		this.testDataDirectory = testDataDirectory;
		this.dumpDatasets = dumpDatasets;
		this.bdbDataSetFactory = bdbDataSetFactory;
	}

	/**
	 * Returns the appropriate operation for the given parameters.
	 * 
	 * @param operationName
	 * @param peerName
	 * @param datasetFile
	 * @return an {@code IOrchestraOperation}
	 */
	protected abstract IOrchestraOperation operation(String operationName,
			String peerName, File datasetFile);

	/**
	 * The value of {@code operation} should be of the form {@code
	 * operation-name} possibly followed by a dash and a {@code peer-name}.
	 * 
	 */
	@Override
	public IOrchestraOperation newOperation(String operation, File datasetFile) {
		String[] parsedName = operation.split("-");
		String operationName = parsedName[0];
		String peerName = null;
		if (parsedName.length > 1) {
			peerName = parsedName[1];
			logger.debug("Operation {} on {}.", operationName, peerName);
		} else {
			logger.debug("Operation {}.", operationName);
		}

		return operation(operationName, peerName, datasetFile);
	}

	@Override
	public boolean getDumpDatasets() {
		return dumpDatasets;
	}

	@Override
	public OrchestraSchema getOrchestraSchema() {
		return orchestraSchema;
	}

	@Override
	public File getTestDataDirectory() {
		return testDataDirectory;
	}

}
