/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.atlas.ocf.properties;

import org.apache.atlas.ocf.ffdc.OCFErrorCode;
import org.apache.atlas.ocf.ffdc.OCFRuntimeException;

/**
 * The Classification class stores information about a classification assigned to an asset.  The Classification
 * has a name and some properties.  It also stores the typename of the asset it is connected to for debug purposes.
 *
 * Note: it is not valid to have a classification with a null or blank name.
 */
public class Classification extends AssetPropertyBase
{
    private String                       classificationName = null;
    private AdditionalProperties         classificationProperties = null;

    /**
     * A private validation method used by the constructors.
     *
     * @param name - name to check
     * @return validated name
     */
    private String validateName(String   name)
    {
        /*
         * Throw an exception if the classification's name is null because that does not make sense.
         * The constructors do not catch this exception so it is received by the creator of the classification
         * object.
         */
        if (name == null || name.equals(""))
        {
            /*
             * Build and throw exception.  This should not happen - likely to be a problem in the
             * repository connector.
             */
            OCFErrorCode errorCode = OCFErrorCode.NULL_CLASSIFICATION_NAME;
            String       errorMessage = errorCode.getErrorMessageId()
                                      + errorCode.getFormattedErrorMessage(super.getParentAssetName(),
                                                                           super.getParentAssetTypeName());

            throw new OCFRuntimeException(errorCode.getHTTPErrorCode(),
                                          this.getClass().getName(),
                                          "validateName",
                                          errorMessage,
                                          errorCode.getSystemAction(),
                                          errorCode.getUserAction());
        }
        else
        {
            return name;
        }
    }


    /**
     * Typical constructor - verifies and saves parameters.
     *
     * @param parentAsset - name and type of related asset
     * @param name - name of the classification
     * @param properties - additional properties for the classification
     */
    public Classification(AssetDescriptor      parentAsset,
                          String               name,
                          AdditionalProperties properties)
    {
        super(parentAsset);

        this.classificationName = validateName(name);
        this.classificationProperties = properties;
    }


    /**
     * Copy/clone Constructor - sets up new classification using values from the template
     *
     * @param parentAsset - details of the asset that this classification is linked to.
     * @param templateClassification - object to copy
     */
    public Classification(AssetDescriptor parentAsset, Classification templateClassification)
    {
        super(parentAsset, templateClassification);

        /*
         * An empty classification object is passed in the variable declaration so throw exception
         * because we need the classification name.
         */
        if (templateClassification == null)
        {
            /*
             * Build and throw exception.  This should not happen - likely to be a problem in the
             * repository connector.
             */
            OCFErrorCode errorCode = OCFErrorCode.NULL_CLASSIFICATION_NAME;
            String       errorMessage = errorCode.getErrorMessageId()
                                      + errorCode.getFormattedErrorMessage("<Unknown>");

            throw new OCFRuntimeException(errorCode.getHTTPErrorCode(),
                                          this.getClass().getName(),
                                          "Copy Constructor",
                                          errorMessage,
                                          errorCode.getSystemAction(),
                                          errorCode.getUserAction());
        }
        else
        {
            /*
             * Save the name and properties.
             */
            this.classificationName = validateName(templateClassification.getName());
            this.classificationProperties = templateClassification.getProperties();
        }
    }


    /**
     * Return the name of the classification
     *
     * @return name of classification
     */
    public String getName()
    {
        return classificationName;
    }


    /**
     * Returns a collection of the additional stored properties for the classification.
     * If no stored properties are present then null is returned.
     *
     * @return properties for the classification
     */
    public AdditionalProperties getProperties()
    {
        if (classificationProperties == null)
        {
            return classificationProperties;
        }
        else
        {
            return new AdditionalProperties(super.getParentAsset(), classificationProperties);
        }
    }


    /**
     * Standard toString method.
     *
     * @return print out of variables in a JSON-style
     */
    @Override
    public String toString()
    {
        return "Classification{" +
                "classificationName='" + classificationName + '\'' +
                ", classificationProperties=" + classificationProperties +
                '}';
    }
}