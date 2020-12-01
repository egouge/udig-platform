/*
 *    uDig - User Friendly Desktop Internet GIS client
 *    http://udig.refractions.net
 *    (C) 2012, Refractions Research Inc.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * (http://www.eclipse.org/legal/epl-v10.html), and the Refractions BSD
 * License v1.0 (http://udig.refractions.net/files/bsd3-v10.html).
 */
package org.locationtech.udig.tools.internal;

import java.awt.Rectangle;

import org.eclipse.core.runtime.IProgressMonitor;
import org.locationtech.udig.project.command.MapCommand;
import org.locationtech.udig.project.ui.commands.AbstractDrawCommand;
import org.locationtech.udig.project.ui.commands.IDrawCommand;
import org.locationtech.udig.project.ui.commands.TransformDrawCommand;

/**
 * @author jesse
 */
public class InvalidateCommand extends AbstractDrawCommand implements MapCommand, IDrawCommand {

	private TransformDrawCommand m_toInvalidate;

	public InvalidateCommand(TransformDrawCommand command) {
		m_toInvalidate = command;
	}

	public void run(IProgressMonitor monitor) throws Exception {
		m_toInvalidate.setValid(false);
	}

	public Rectangle getValidArea() {
		return null;
	}

}
