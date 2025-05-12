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
							"  from inventories, items " +
							" where inventories.item_id = items.item_id " +
							" and inventories.inventory_id = ? "
					);
					stmt.setInt(1, inventoryId);
					
					resultSet = stmt.executeQuery();
					
					Inventory inventory = new Inventory();
					
					inventory.setInventoryId(inventoryId);
					
					// for testing that a result was returned
					Boolean found = false;
					
					while (resultSet.next()) {
						found = true;
						
						// create new Inventory object
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
	
	@Override
	public Item findItemByItemId(final int itemId) {
		return executeTransaction(new Transaction<Item>() {
			@Override
			public Item execute(Connection conn) throws SQLException {
				PreparedStatement stmt = null;
				ResultSet resultSet = null;
				
				try {
					// retrieve all attributes from Items table
					stmt = conn.prepareStatement(
							"select items.* " +
							"  from items " +
							" where item_id = ?"
					);
					stmt.setInt(1, itemId);
					
					resultSet = stmt.executeQuery();
					
					Item item = new Item();
					
					// for testing that a result was returned
					Boolean found = false;
					
					while (resultSet.next()) {
						found = true;
						
						// create new Room object
						// retrieve attributes from resultSet starting with index 1
						loadItem(item, resultSet, 1);
					}
					
					// check if the title was found
					if (!found) {
						System.out.println("<" + itemId + "> was not found in the items table");
					}
					
					return item;
				} finally {
					DBUtil.closeQuietly(resultSet);
					DBUtil.closeQuietly(stmt);
				}
			}
		});
	}
	
	@Override
	public List<Item> findItemByItemName(final String itemName) {
	    return executeTransaction(new Transaction<List<Item>>() {
	        @Override
	        public List<Item> execute(Connection conn) throws SQLException {
	            PreparedStatement stmt = null;
	            ResultSet        rs   = null;
	            try {
	                stmt = conn.prepareStatement(
	                    "SELECT items.* " +
	                    "  FROM items " +
	                    " WHERE item_name = ?"
	                );
	                stmt.setString(1, itemName);
	                rs = stmt.executeQuery();

	                List<Item> items = new ArrayList<>();
	                while (rs.next()) {
	                    Item item = new Item();
	                    loadItem(item, rs, 1);
	                    items.add(item);
	                }
	                return items;
	            } finally {
	                DBUtil.closeQuietly(rs);
	                DBUtil.closeQuietly(stmt);
	            }
	        }
	    });
	}
	
	@Override
	public List<Pair<Inventory,Item>> findItemInInventory(final Inventory inventory) {
	    return executeTransaction(new Transaction<List<Pair<Inventory,Item>>>() {
	        @Override
	        public List<Pair<Inventory,Item>> execute(Connection conn) throws SQLException {
	            PreparedStatement stmt = null;
	            ResultSet        rs   = null;
	            try {
	                stmt = conn.prepareStatement(
	                    "SELECT items.* " +
	                    "  FROM items " +
	                    " WHERE inventory_id = ?"
	                );
	                stmt.setInt(1, inventory.getInventoryId());
	                rs = stmt.executeQuery();

	                List<Pair<Inventory,Item>> results = new ArrayList<>();
	                while (rs.next()) {
	                    Item item = new Item();
	                    loadItem(item, rs, 1);
	                    results.add(new Pair<>(inventory, item));
	                }
	                return results;
	            } finally {
	                DBUtil.closeQuietly(rs);
	                DBUtil.closeQuietly(stmt);
	            }
	        }
	    });
	}
	
	@Override
	public Actor findActorByActorId(final int actorId) {
		return executeTransaction(new Transaction<Actor>() {
			@Override
			public Actor execute(Connection conn) throws SQLException {
				PreparedStatement stmt = null;
				ResultSet resultSet = null;
				
				try {
					// retrieve all attributes from Items table
					stmt = conn.prepareStatement(
							"select actors.* " +
							"  from actors " +
							" where actor_id = ?"
					);
					stmt.setInt(1, actorId);
					
					resultSet = stmt.executeQuery();
					
					Actor actor = new Actor();
					
					// for testing that a result was returned
					Boolean found = false;
					
					while (resultSet.next()) {
						found = true;
						
						// create new Room object
						// retrieve attributes from resultSet starting with index 1
						loadActor(actor, resultSet, 1);
					}
					
					// check if the title was found
					if (!found) {
						System.out.println("<" + actorId + "> was not found in the items table");
					}
					
					return actor;
				} finally {
					DBUtil.closeQuietly(resultSet);
					DBUtil.closeQuietly(stmt);
				}
			}
		});
	}
	
	@Override
	public Actor updateActorRoomId(final int actorId, final int roomId) {
		return executeTransaction(new Transaction<Actor>() {
			@Override
			public Actor execute(Connection conn) throws SQLException {
				PreparedStatement stmt = null;
				ResultSet resultSet = null;
				
				try {
					// retrieve all attributes from Items table
					stmt = conn.prepareStatement(
							"update actors " +
							"  set room_id = ? " +
							" where actor_id = ?"
					);
					stmt.setInt(1, roomId);
					stmt.setInt(2, actorId);
					
					stmt.execute();
					
					stmt = conn.prepareStatement(
							"select actors.* " +
							"  from actors " +
							" where actor_id = ?"
					);
					stmt.setInt(1, actorId);
					
					resultSet = stmt.executeQuery();
					
					Actor actor = new Actor();
					
					// for testing that a result was returned
					Boolean found = false;
					
					while (resultSet.next()) {
						found = true;
						
						// create new Room object
						// retrieve attributes from resultSet starting with index 1
						loadActor(actor, resultSet, 1);
					}
					
					// check if the title was found
					if (!found) {
						System.out.println("<" + actorId + "> was not found in the items table");
					}
					
					return actor;
				} finally {
					DBUtil.closeQuietly(resultSet);
					DBUtil.closeQuietly(stmt);
				}
			}
		});
	}
	
	@Override
	public Inventory updateInventoryItems(final int itemId, final int senderId, final int destinationId) {
		return executeTransaction(new Transaction<Inventory>() {
			@Override
			public Inventory execute(Connection conn) throws SQLException {
				PreparedStatement stmt = null;
				ResultSet resultSet = null;
				
				try {
					// retrieve all attributes from Items table
					stmt = conn.prepareStatement(
							"insert into inventories (inventory_id, item_id) values (?, ?) "
					);
					stmt.setInt(1, destinationId);
					stmt.setInt(2, itemId);
					
					stmt.execute();
					
					stmt = conn.prepareStatement(
							"delete from inventories " +
							"  where inventory_id = ? and item_id = ? "
					);
					stmt.setInt(1, senderId);
					stmt.setInt(2, itemId);
					
					stmt.execute();
					
					// retrieve all attributes from Rooms table
					stmt = conn.prepareStatement(
							"select inventories.item_id " +
							"  from inventories, items " +
							" where inventories.item_id = items.item_id " +
							" and inventories.inventory_id = ? "
					);
					stmt.setInt(1, destinationId);
					
					resultSet = stmt.executeQuery();
					
					Inventory inventory = new Inventory();
					
					inventory.setInventoryId(destinationId);
					
					// for testing that a result was returned
					Boolean found = false;
					
					while (resultSet.next()) {
						found = true;
						
						// create new Inventory object
						// retrieve attributes from resultSet starting with index 1
						loadInventory(inventory, resultSet, 1);
					}
					
					// check if the title was found
					if (!found) {
						System.out.println("<" + destinationId + "> was not found in the inventories table");
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
	
	private void loadActor(Actor actor, ResultSet resultSet, int index) throws SQLException{
		actor.setActorId(resultSet.getInt(index++));
		actor.setRoomId(resultSet.getInt(index++));
		actor.setInventoryId(resultSet.getInt(index++));
		actor.setHealth(resultSet.getInt(index++));
		actor.setActorName(resultSet.getString(index++));
	}
	
	private void loadInventory(Inventory inventory, ResultSet resultSet, int index) throws SQLException {
		inventory.addItem(resultSet.getInt(index++));
	}
	
	private void loadRoom(Room room, ResultSet resultSet, int index) throws SQLException {
		room.setRoomId(resultSet.getInt(index++));
		room.setInventoryId(resultSet.getInt(index++));
		room.setRoomName(resultSet.getString(index++));
		room.setShortDesc(resultSet.getString(index++));
		room.setLongDesc(resultSet.getString(index++));
	}
	
	private void loadItem(Item item, ResultSet resultSet, int index) throws SQLException {
		item.setItemId(resultSet.getInt(index++));
		item.setItemName(resultSet.getString(index++));
		item.setValue(resultSet.getInt(index++));
		item.setUses(resultSet.getInt(index++));
		item.setItemType(resultSet.getString(index++));
		item.setDescription(resultSet.getString(index++));
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
				PreparedStatement stmt4 = null;
				PreparedStatement stmt5 = null;
				
				try {
					stmt1 = conn.prepareStatement(
							"create table items (" +
							"	item_id integer primary key " +
							"		generated always as identity (start with 1, increment by 1), " +	
							"	itemName varchar(200), " +
							"	uses integer, " +
							"	value integer, " +
							"	itemType varchar(200), " +
							"	itemDescription varchar(200) " +
			
							")"
					);	
					stmt1.executeUpdate();
					
					stmt2 = conn.prepareStatement(
							"create table inventories (" +
							"	inventory_id integer, " +									
							"	item_id integer constraint item_id references items" +
							")"
					);
					stmt2.executeUpdate();
					
					stmt3 = conn.prepareStatement(
							"create table rooms (" +
							"	room_id integer primary key " +
							"		generated always as identity (start with 1, increment by 1), " +									
							"	inventory_id integer, " +
							"	roomName varchar(200), " +
							"	shortDesc varchar(200), " +
							"	longDesc varchar(400)" +
							")"
					);	
					stmt3.executeUpdate();
					
					stmt4 = conn.prepareStatement(
							"create table connections (" +
							"	room_id integer, " +									
							"	direction varchar(40), " +
							"	connection_id integer constraint connection_id references rooms " +
							")"
					);
					stmt4.executeUpdate();
					
					stmt5 = conn.prepareStatement(
							"create table actors (" +
							"	actor_id integer primary key " +	
							"		generated always as identity (start with 1, increment by 1), " +	
							"	room_id integer constraint room_id references rooms, " +
							"	inventory_id integer, " +
							"	health integer, " +
							"	actorName varchar(40) " +
							")"
					);
					stmt5.executeUpdate();
					
						
					return true;
				} finally {
					DBUtil.closeQuietly(stmt1);
					DBUtil.closeQuietly(stmt2);
					DBUtil.closeQuietly(stmt3);
					DBUtil.closeQuietly(stmt4);
					DBUtil.closeQuietly(stmt5);
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
				List<Item> itemList;
				List<Actor> actorList;
				
				try {
					inventoryList = InitialData.getInventories();
					roomList = InitialData.getRooms();
					itemList = InitialData.getItems();
					actorList = InitialData.getActors();
				} catch (IOException e) {
					throw new SQLException("Couldn't read initial data", e);
				}

				PreparedStatement insertRoom = null;
				PreparedStatement insertConnection = null;
				PreparedStatement insertInventory = null;
				PreparedStatement insertItem   = null;
				PreparedStatement insertActor = null;
				
				try {
					insertItem = conn.prepareStatement("insert into items (itemName, uses, value, itemType, itemDescription) values (?, ?, ?, ?,?)");
					for (Item item : itemList) {
						insertItem.setString(1, item.getItemName());
						insertItem.setInt(2, item.getUses());
						insertItem.setInt(3, item.getValue());
						insertItem.setString(4, item.getItemType());
						insertItem.setString(5, item.getDescription());
						insertItem.addBatch();
					}
					insertItem.executeBatch();
					
					insertInventory = conn.prepareStatement("insert into inventories (inventory_id, item_id) values (?, ?)");
					for (Inventory inventory : inventoryList) {
						ArrayList<Integer> items = inventory.getItems();
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
					
					insertActor = conn.prepareStatement("insert into actors (room_id, inventory_id, health, actorName) values (?, ?, ?, ?)");
					for (Actor actor : actorList) {
						insertActor.setInt(1, actor.getRoomId());
						insertActor.setInt(2, actor.getInventoryId());
						insertActor.setInt(3, actor.getHealth());
						insertActor.setString(4, actor.getActorName());
						insertActor.addBatch();
					}
					insertActor.executeBatch();
					
					return true;
				} finally {
					DBUtil.closeQuietly(insertRoom);
					DBUtil.closeQuietly(insertConnection);
					DBUtil.closeQuietly(insertInventory);
					DBUtil.closeQuietly(insertItem);
					DBUtil.closeQuietly(insertActor);
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
