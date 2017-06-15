/**
 * This file is part of aion-lightning <aion-lightning.org>.
 * 
 * aion-lightning is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * aion-lightning is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with aion-lightning.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.aionlightning.loginserver.network.aion.clientpackets;

import java.nio.ByteBuffer;

import com.aionlightning.loginserver.GameServerTable;
import com.aionlightning.loginserver.controller.AccountController;
import com.aionlightning.loginserver.network.aion.AionAuthResponse;
import com.aionlightning.loginserver.network.aion.AionClientPacket;
import com.aionlightning.loginserver.network.aion.LoginConnection;
import com.aionlightning.loginserver.network.aion.serverpackets.SM_LOGIN_FAIL;

/**
 * @author -Nemesiss-
 */
public class CM_SERVER_LIST extends AionClientPacket {

	/**
	 * accountId is part of session key - its used for security purposes
	 */
	private int accountId;
	/**
	 * loginOk is part of session key - its used for security purposes
	 */
	private int loginOk;

	/**
	 * Constructs new instance of <tt>CM_SERVER_LIST </tt> packet.
	 * 
	 * @param buf
	 * @param client
	 */
	public CM_SERVER_LIST(ByteBuffer buf, LoginConnection client) {
		super(buf, client, 0x05);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void readImpl() {
		accountId = readD();
		loginOk = readD();
		readD();// unk
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void runImpl() {
		LoginConnection con = getConnection();
		if (con.getSessionKey().checkLogin(accountId, loginOk)) {
			if (GameServerTable.getGameServers().size() == 0)
				con.close(new SM_LOGIN_FAIL(AionAuthResponse.NO_GS_REGISTERED), false);
			else
				AccountController.loadGSCharactersCount(accountId);
		}
		else {
			/**
			 * Session key is not ok - inform client that smth went wrong - dc client
			 */
			con.close(new SM_LOGIN_FAIL(AionAuthResponse.SYSTEM_ERROR), false);
		}
	}
}
