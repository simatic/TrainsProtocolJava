/**
 Trains Protocol: Middleware for Uniform and Totally Ordered Broadcasts
 Copyright: Copyright (C) 2010-2012
 Contact: michel.simatic@telecom-sudparis.eu

 This library is free software; you can redistribute it and/or
 modify it under the terms of the GNU Lesser General Public
 License as published by the Free Software Foundation; either
 version 3 of the License, or any later version.

 This library is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 Lesser General Public License for more details.

 You should have received a copy of the GNU Lesser General Public
 License along with this library; if not, write to the Free Software
 Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307
 USA

 Developer(s): Stephanie Ouillon
 */

package trains;

/**
 * Interface for the callback that is called when a message is delivered (received).
 * 
 * IMPORTANT: when implementing this interface, define a static factory getInstance().
 * This factory is called in the native code to make sure only one callback exists.
 * ALSO: the name of the class should be of less than 128 characters.
 * @author Stephanie Ouillon
 *
 */

public interface CallbackUtoDeliver {
	//Important: need to implement a static factory getInstance()

	/**
	 * Method implemented by the Java user and called from the native code.
	 * 
	 * @param sender sender of the message
	 * @param msg application message (see {@link trains.Message Message}
	 */
	public void run(int sender, Message msg);
}
