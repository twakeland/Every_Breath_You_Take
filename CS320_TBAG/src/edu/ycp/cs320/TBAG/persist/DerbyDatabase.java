package edu.ycp.cs320.TBAG.persist;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import edu.ycp.cs320.TBAG.model.*;

public class DerbyDatabase implements IDatabase {
	static {
		try {
			Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
		} catch (Exception e) {
			throw new IllegalStateException("Could not load Derby driver");
		}
	}
	
	private interface Transaction<ResultType> {
		public ResultType execute(Connection conn) throws SQLException;
	}

	private static final int MAX_ATTEMPTS = 10;
	
	@Override
	public Room findRoomByRoomId(final int roomId) {
		return executeTransaction(new Transaction<Room>() {
			@Override
			public Room execute(Connection conn) throws SQLException {
				PreparedStatement stmt = null;
				ResultSet resultSet = null;
				
				try {
					// retrieve all attributes from Rooms table
					stmt = conn.prepareStatement(
							"select rooms.* " +
							"  from rooms " +
							" where room_id = ?"
					);
					stmt.setInt(1, roomId);
					
					resultSet = stmt.executeQuery();
					
					Room room = new Room();
					
					// for testing that a result was returned
					Boolean found = false;
					
					while (resultSet.next()) {
						found = true;
						
						// create new Room object
						// retrieve attributes from resultSet starting with index 1
						loadRoom(room, resultSet, 1);
						room.setConnections(findConnectionsByRoomId(roomId));
					}
					
					// check if the title was found
					if (!found) {
						System.out.println("<" + roomId + "> was not found in the books table");
					}
					
					return room;
				} finally {
					DBUtil.closeQuietly(resultSet);
					DBUtil.closeQuietly(stmt);
				}
			}
		});
	}
	
	@Override
	public Map<String, Integer> findConnectionsByRoomId(final int roomId) {
		return executeTransaction(new Transaction<Map<String, Integer>>() {
			@Override
			public Map<String, Integer> execute(Connection conn) throws SQLException {
				PreparedStatement stmt = null;
				ResultSet resultSet = null;
				
				try {
					// retrieve all attributes from Rooms table
					stmt = conn.prepareStatement(
							"select connections.direction, connections.connection_id " +
							"  from connections, rooms " +
							" where rooms.room_id = connections.room_id" +
							"   and rooms.room_id = ? " 
					);
					stmt.setInt(1, roomId);
					
					resultSet = stmt.executeQuery();
					
					Map<String, Integer> connections = new TreeMap<>();
					
					// for testing that a result was returned
					Boolean found = false;
					
					while (resultSet.next()) {
						found = true;
						
						// create new Map object
						// retrieve attributes from resultSet starting with index 1
						loadConnections(connections, resultSet, 1);
					}
					
					// check if the title was found
					if (!found) {
						System.out.println("<" + roomId + "> was not found in the connections table");
					}
					
					return connections;
				} finally {
					DBUtil.closeQuietly(resultSet);
					DBUtil.closeQuietly(stmt);
				}
			}
		});
	}
	
	@Override
	public Inventory findInventoryByInventoryId(final int inventoryId) {
		return executeTransaction(new Transaction<Inventory>() {
			@Override
			public Inventory execute(Connection conn) throws SQLException {
				PreparedStatement stmt = null;
				ResultSet resultSet = null;
				
				try {
					// retrieve all attributes from Rooms table
					stmt = conn.prepareStatement(
							"select inventories.item_id " +
							"  from inventories " +
							" where inventories.inventory_id = ? "
					);
					stmt.setInt(1, inventoryId);
					
					resultSet = stmt.executeQuery();
					
					Inventory inventory = new Inventory();
					
					inventory.setInventoryId(inventoryId);
					
					// for testing that a result was returned
					Boolean found = false;
					
					while (resultSet.next()) {
						found = true;
						
						// create new Map object
						// retrieve attributes from resultSet starting with index 1
						loadInventory(inventory, resultSet, 1);
					}
					
					// check if the title was found
					if (!found) {
						System.out.println("<" + inventoryId + "> was not found in the inventories table");
					}
					
					return inventory;
				} finally {
					DBUtil.closeQuietly(resultSet);
					DBUtil.closeQuietly(stmt);
				}
			}
		});
	}
	
	public<ResultType> ResultType executeTransaction(Transaction<ResultType> txn) {
		try {
			return doExecuteTransaction(txn);
		} catch (SQLException e) {
			throw new PersistenceException("Transaction failed", e);
		}
	}
	
	public<ResultType> ResultType doExecuteTransaction(Transaction<ResultType> txn) throws SQLException {
		Connection conn = connect();
		
		try {
			int numAttempts = 0;
			boolean success = false;
			ResultType result = null;
			
			while (!success && numAttempts < MAX_ATTEMPTS) {
				try {
					result = txn.execute(conn);
					conn.commit();
					success = true;
				} catch (SQLException e) {
					if (e.getSQLState() != null && e.getSQLState().equals("41000")) {
						// Deadlock: retry (unless max retry count has been reached)
						numAttempts++;
					} else {
						// Some other kind of SQLException
						throw e;
					}
				}
			}
			
			if (!success) {
				throw new SQLException("Transaction failed (too many retries)");
			}
			
			// Success!
			return result;
		} finally {
			DBUtil.closeQuietly(conn);
		}
	}

	private Connection connect() throws SQLException {
		Connection conn = DriverManager.getConnection("jdbc:derby:test.db;create=true");
		
		// Set autocommit to false to allow execution of
		// multiple queries/statements as part of the same transaction.
		conn.setAutoCommit(false);
		
		return conn;
	}
	
	private void loadInventory(Inventory inventory, ResultSet resultSet, int index) throws SQLException {
		inventory.addItem(index++);
	}
	
	private void loadRoom(Room room, ResultSet resultSet, int index) throws SQLException {
		room.setRoomId(resultSet.getInt(index++));
		room.setInventoryId(resultSet.getInt(index++));
		room.setRoomName(resultSet.getString(index++));
		room.setShortDesc(resultSet.getString(index++));
		room.setLongDesc(resultSet.getString(index++));
	}
	
	private void loadConnections(Map<String, Integer> connections, ResultSet resultSet, int index) throws SQLException {
		connections.put(resultSet.getString(index++), resultSet.getInt(index++));
	}
	
	public void createTables() {
		executeTransaction(new Transaction<Boolean>() {
			@Override
			public Boolean execute(Connection conn) throws SQLException {
				PreparedStatement stmt1 = null;
				PreparedStatement stmt2 = null;
				PreparedStatement stmt3 = null;
				
				try {
					
					stmt3 = conn.prepareStatement(
							"create table inventories (" +
							"	inventory_id integer, " +									
							"	item_id integer " +
							")"
					);
					stmt3.executeUpdate();
					
					stmt1 = conn.prepareStatement(
							"create table rooms (" +
							"	room_id integer primary key " +
							"		generated always as identity (start with 1, increment by 1), " +									
							"	inventory_id integer, " +
							"	roomName varchar(200), " +
							"	shortDesc varchar(200), " +
							"	longDesc varchar(200)" +
							")"
					);	
					stmt1.executeUpdate();
					
					stmt2 = conn.prepareStatement(
							"create table connections (" +
							"	room_id integer, " +									
							"	direction varchar(40), " +
							"	connection_id integer constraint connection_id references rooms " +
							")"
					);
					stmt2.executeUpdate();
						
					return true;
				} finally {
					DBUtil.closeQuietly(stmt1);
					DBUtil.closeQuietly(stmt2);
					DBUtil.closeQuietly(stmt3);
				}
			}
		});
	}
	
	public void loadInitialData() {
		executeTransaction(new Transaction<Boolean>() {
			@Override
			public Boolean execute(Connection conn) throws SQLException {
				List<Room> roomList;
				List<Inventory> inventoryList;
				
				try {
					inventoryList = InitialData.getInventories();
					roomList = InitialData.getRooms();
				} catch (IOException e) {
					throw new SQLException("Couldn't read initial data", e);
				}

				PreparedStatement insertRoom = null;
				PreparedStatement insertConnection = null;
				PreparedStatement insertInventory = null;
				
				try {
					insertInventory = conn.prepareStatement("insert into inventories (inventory_id, item_id) values (?, ?)");
					for (Inventory inventory : inventoryList) {
						ArrayList<Integer> items = new ArrayList<>();
						for (int itemId : items) {
							insertInventory.setInt(1, inventory.getInventoryId());
							insertInventory.setInt(2, itemId);
							insertInventory.addBatch();
						}
					}
					insertInventory.executeBatch();
					
					insertRoom = conn.prepareStatement("insert into rooms (inventory_id, roomName, shortDesc, longDesc) values (?, ?, ?, ?)");
					for (Room room : roomList) {
						insertRoom.setInt(1, room.getInventoryId());
						insertRoom.setString(2, room.getRoomName());
						insertRoom.setString(3, room.getShortDesc());
						insertRoom.setString(4, room.getLongDesc());
						insertRoom.addBatch();
					}
					insertRoom.executeBatch();
					
					insertConnection = conn.prepareStatement("insert into connections (room_id, direction, connection_id) values (?, ?, ?)");
					for (Room room : roomList) {
						Set<String> directions = room.getConnections().keySet();
						for (String direction : directions) {
							insertConnection.setInt(1, room.getRoomId());
							insertConnection.setString(2, direction);
							insertConnection.setInt(3, room.getConnection(direction));
							insertConnection.addBatch();
						}
					}
					insertConnection.executeBatch();
					
					return true;
				} finally {
					DBUtil.closeQuietly(insertRoom);
					DBUtil.closeQuietly(insertConnection);
					DBUtil.closeQuietly(insertInventory);
				}
			}
		});
	}
	
	// The main method creates the database tables and loads the initial data.
	public static void main(String[] args) throws IOException {
		System.out.println("Creating tables...");
		DerbyDatabase db = new DerbyDatabase();
		db.createTables();
		
		System.out.println("Loading initial data...");
		db.loadInitialData();
		
		System.out.println("Success!");
	}
}
