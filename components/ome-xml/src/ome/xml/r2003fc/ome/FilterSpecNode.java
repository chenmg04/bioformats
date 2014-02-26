/*
 * #%L
 * OME-XML Java library for working with OME-XML metadata structures.
 * %%
 * Copyright (C) 2006 - 2013 Open Microscopy Environment:
 *   - Massachusetts Institute of Technology
 *   - National Institutes of Health
 *   - University of Dundee
 *   - Board of Regents of the University of Wisconsin-Madison
 *   - Glencoe Software, Inc.
 * %%
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * 
 * 1. Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDERS OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 * 
 * The views and conclusions contained in the software and documentation are
 * those of the authors and should not be interpreted as representing official
 * policies, either expressed or implied, of any organization.
 * #L%
 */

/*-----------------------------------------------------------------------------
 *
 * THIS IS AUTOMATICALLY GENERATED CODE.  DO NOT MODIFY.
 * Created by curtis via xsd-fu on 2008-10-16 06:18:35-0500
 *
 *-----------------------------------------------------------------------------
 */

package ome.xml.r2003fc.ome;

import ome.xml.DOMUtil;
import ome.xml.OMEXMLNode;
import ome.xml.r2003fc.ome.*;

import org.w3c.dom.Element;

/**
 * @deprecated The r2003fc classes are replaced by the ome.xml.model classes
 */
@Deprecated
public class FilterSpecNode extends OMEXMLNode
{

	// -- Constructors --

	/** Constructs a FilterSpec node with an associated DOM element. */
	public FilterSpecNode(Element element)
	{
		super(element);
	}

	/**
	 * Constructs a FilterSpec node with an associated DOM element beneath
	 * a given parent.
	 */
	public FilterSpecNode(OMEXMLNode parent)
	{
		this(parent, true);
	}

	/**
	 * Constructs a FilterSpec node with an associated DOM element beneath
	 * a given parent.
	 */
	public FilterSpecNode(OMEXMLNode parent, boolean attach)
	{
		super(DOMUtil.createChild(parent.getDOMElement(),
		                          "FilterSpec", attach));
	}

	// -- FilterSpec API methods --

	// Attribute
	public String getModel()
	{
		return getStringAttribute("Model");
	}

	public void setModel(String model)
	{
		setAttribute("Model", model);
	}

	// Attribute
	public String getLotNumber()
	{
		return getStringAttribute("LotNumber");
	}

	public void setLotNumber(String lotNumber)
	{
		setAttribute("LotNumber", lotNumber);
	}

	// Attribute
	public String getManufacturer()
	{
		return getStringAttribute("Manufacturer");
	}

	public void setManufacturer(String manufacturer)
	{
		setAttribute("Manufacturer", manufacturer);
	}

	// -- OMEXMLNode API methods --

	public boolean hasID()
	{
		return false;
	}

}