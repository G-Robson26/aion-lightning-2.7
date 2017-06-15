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
package mysql5;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import com.aionlightning.commons.database.DB;
import com.aionlightning.commons.database.ReadStH;
import com.aionlightning.loginserver.GameServerInfo;
import com.aionlightning.loginserver.dao.GameServersDAO;

/**
 * GameServers DAO implementation for MySQL5
 * 
 * @author -Nemesiss-
 */
public class MySQL5GameServersDAO extends GameServersDAO {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Map<Byte, GameServerInfo> getAllGameServers() {

		final Map<Byte, GameServerInfo> result = new HashMap<Byte, GameServerInfo>();
		DB.select("SELECT * FROM gameservers", new ReadStH() {

			@Override
			public void handleRead(ResultSet resultSet) throws SQLException {
				while (resultSet.next()) {
					byte id = resultSet.getByte("id");
					String ipMask = resultSet.getString("mask");
					String password = resultSet.getString("password");
					GameServerInfo gsi = new GameServerInfo(id, ipMask, password);
					result.put(id, gsi);
				}
			}
		});
		return result;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean supports(String s, int i, int i1) {
		return MySQL5DAOUtils.supports(s, i, i1);
	}
}
