package edu.ycp.cs320.TBAG.persist;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
						System.out.println("<" + itemId + "> was not found in the books table");
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
    public List<Item> findAllItems() {
        return executeTransaction(new Transaction<List<Item>>() {
            @Override
            public List<Item> execute(Connection conn) throws SQLException {
                PreparedStatement stmt = null;
                ResultSet resultSet = null;

                try {
                    stmt = conn.prepareStatement("SELECT items.* FROM items");
                    resultSet = stmt.executeQuery();

                    List<Item> items = new ArrayList<>();
                    while (resultSet.next()) {
                        Item item = new Item();
                        loadItem(item, resultSet, 1);
                        items.add(item);
                    }
                    return items;
                } finally {
                    DBUtil.closeQuietly(resultSet);
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
	            PreparedStatement stmt     = null;
	            ResultSet        resultSet = null;
	            
	            try {
	                // select every column from items where the FK matches
	                stmt = conn.prepareStatement(
	                    "SELECT items.* " +
	                    "  FROM items " +
	                    " WHERE inventory_id = ?"
	                );
	                stmt.setInt(1, inventory.getInventoryId());
	                resultSet = stmt.executeQuery();
	                
	                List<Pair<Inventory,Item>> results = new ArrayList<>();
	                while (resultSet.next()) {
	                    // build the Item
	                    Item item = new Item();
	                    loadItem(item, resultSet, 1);
	                    
	                    // pair it with the passed–in Inventory
	                    results.add(new Pair<>(inventory, item));
	                }
	                
	                return results;
	            } finally {
	                DBUtil.closeQuietly(resultSet);
	                DBUtil.closeQuietly(stmt);
	            }
	        }
	    });
	}
	
	
	@Override
	public List<Item> findItemsByItemName(final String itemName) {
	    return executeTransaction(new Transaction<List<Item>>() {
	        @Override
	        public List<Item> execute(Connection conn) throws SQLException {
	            PreparedStatement stmt     = null;
	            ResultSet        resultSet = null;

	            try {
	                // select every column from items where the name matches
	                stmt = conn.prepareStatement(
	                    "SELECT items.* " +
	                    "  FROM items " +
	                    " WHERE item_name = ?"
	                );
	                stmt.setString(1, itemName);
	                resultSet = stmt.executeQuery();

	                List<Item> items = new ArrayList<>();
	                while (resultSet.next()) {
	                    Item item = new Item();
	                    loadItem(item, resultSet, 1);
	                    items.add(item);
	                }
	                return items;
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
	
	public void createTables() {
		executeTransaction(new Transaction<Boolean>() {
			@Override
			public Boolean execute(Connection conn) throws SQLException {
				PreparedStatement stmt1 = null;
				PreparedStatement stmt2 = null;
				
				try {
					
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
							"create table items (" +
							"	itemID integer primary key " +
							"		generated always as identity (start with 1, increment by 1), " +	
							"	itemName varchar(200), " +
							"	uses integer, " +
							"	value integer, " +
							"	itemType varchar(200), " +
							"	itemDescription varchar(200) " +
							")"
					);	
					stmt2.executeUpdate();
						
					return true;
				} finally {
					DBUtil.closeQuietly(stmt1);
				}
			}
		});
		
		
	}
	
	public void loadInitialData() {
		executeTransaction(new Transaction<Boolean>() {
			@Override
			public Boolean execute(Connection conn) throws SQLException {
				List<Room> roomList;
				List<Item> itemList;
				
				try {
					roomList = InitialData.getRooms();
					itemList = InitialData.getItems();
				} catch (IOException e) {
					throw new SQLException("Couldn't read initial data", e);
				}

				PreparedStatement insertRoom   = null;
				PreparedStatement insertItem   = null;

				try {
					
					insertRoom = conn.prepareStatement("insert into rooms (inventory_id, roomName, shortDesc, longDesc) values (?, ?, ?, ?)");
					for (Room room : roomList) {
						insertRoom.setInt(1, room.getInventoryId());
						insertRoom.setString(2, room.getRoomName());
						insertRoom.setString(3, room.getShortDesc());
						insertRoom.setString(4, room.getLongDesc());
						insertRoom.addBatch();
					}
					insertRoom.executeBatch();
					
					insertItem = conn.prepareStatement("insert into items (itemName, uses, value, itemType, itemDescription) values (?, ?, ?, ?, ?)");
					for (Item item : itemList) {
						insertItem.setString(1, item.getItemName());
						insertItem.setInt(2, item.getUses());
						insertItem.setInt(3, item.getValue());
						insertItem.setString(4, item.getItemType());
						insertItem.setString(5, item.getDescription());
						insertItem.addBatch();
					}
					insertItem.executeBatch();
					
					return true;
				} finally {
					DBUtil.closeQuietly(insertRoom);
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
