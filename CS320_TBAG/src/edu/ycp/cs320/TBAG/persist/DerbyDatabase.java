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
	public List<Pair<Author, Book>> findAuthorAndBookByTitle(final String title) {
		return executeTransaction(new Transaction<List<Pair<Author,Book>>>() {
			@Override
			public List<Pair<Author, Book>> execute(Connection conn) throws SQLException {
				PreparedStatement stmt = null;
				ResultSet resultSet = null;
				
				try {
					// retreive all attributes from both Books and Authors tables
					stmt = conn.prepareStatement(
							"select authors.*, books.* " +
							"  from authors, books " +
							" where authors.author_id = books.author_id " +
							"   and books.title = ?"
					);
					stmt.setString(1, title);
					
					List<Pair<Author, Book>> result = new ArrayList<Pair<Author,Book>>();
					
					resultSet = stmt.executeQuery();
					
					// for testing that a result was returned
					Boolean found = false;
					
					while (resultSet.next()) {
						found = true;
						
						// create new Author object
						// retrieve attributes from resultSet starting with index 1
						Author author = new Author();
						loadAuthor(author, resultSet, 1);
						
						// create new Book object
						// retrieve attributes from resultSet starting at index 4
						Book book = new Book();
						loadBook(book, resultSet, 4);
						
						result.add(new Pair<Author, Book>(author, book));
					}
					
					// check if the title was found
					if (!found) {
						System.out.println("<" + title + "> was not found in the books table");
					}
					
					return result;
				} finally {
					DBUtil.closeQuietly(resultSet);
					DBUtil.closeQuietly(stmt);
				}
			}
		});
	}
	
	@Override
	public List<Pair<Author, Book>> findAuthorAndBookByAuthorLastName(String lastname){
		return executeTransaction(new Transaction<List<Pair<Author,Book>>>() {
			@Override
			public List<Pair<Author, Book>> execute(Connection conn) throws SQLException {
				PreparedStatement stmt = null;
				ResultSet resultSet = null;
				
				try {
					// retreive all attributes from both Books and Authors tables
					stmt = conn.prepareStatement(
							"select authors.*, books.* " +
							"  from authors, books " +
							" where authors.author_id = books.author_id " +
							"   and authors.lastName = ?" +
							" order by books.title asc"
					);
					stmt.setString(1, lastname);
					
					List<Pair<Author, Book>> result = new ArrayList<Pair<Author,Book>>();
					
					resultSet = stmt.executeQuery();
					
					// for testing that a result was returned
					Boolean found = false;
					
					while (resultSet.next()) {
						found = true;
						
						// create new Author object
						// retrieve attributes from resultSet starting with index 1
						Author author = new Author();
						loadAuthor(author, resultSet, 1);
						
						// create new Book object
						// retrieve attributes from resultSet starting at index 4
						Book book = new Book();
						loadBook(book, resultSet, 4);
						
						result.add(new Pair<Author, Book>(author, book));
					}
					
					// check if the title was found
					if (!found) {
						System.out.println("<" + lastname + "> was not found in the authors table");
					}
					
					return result;
				} finally {
					DBUtil.closeQuietly(resultSet);
					DBUtil.closeQuietly(stmt);
				}
			}
		});
	}
	
	@Override
	public List<Pair<Author, Book>> insertAuthorAndBook(String firstname, String lastname, String title, String isbn, String published) {
		return executeTransaction(new Transaction<List<Pair<Author,Book>>>() {
			@Override
			public List<Pair<Author, Book>> execute(Connection conn) throws SQLException {
				PreparedStatement stmt = null;
				ResultSet resultSet = null;
				
				try {
					// retreive all attributes from both Books and Authors tables
					stmt = conn.prepareStatement(
							"select authors.* " +
							"  from authors " +
							" where firstname = ? and lastname = ? "
					);
					stmt.setString(1, firstname);
					stmt.setString(2, lastname);
					
					List<Pair<Author, Book>> result = new ArrayList<Pair<Author,Book>>();
					
					resultSet = stmt.executeQuery();
					
					// for testing that a result was returned
					Boolean found = false;
					
					if (resultSet.next()) {
						found = true;
						
						// retrieve attributes from resultSet starting with index 1
						Author author = new Author();
						loadAuthor(author, resultSet, 1);
						
						stmt = conn.prepareStatement(
								"insert into books (author_id, title, isbn, published) "
								+ "  values ( ?, ?, ?, ?) "
						);
						
						stmt.setString(1, Integer.toString(author.getAuthorId()));
						stmt.setString(2, title);
						stmt.setString(3, isbn);
						stmt.setString(4, published);
						
						// execute the query
						stmt.execute();
						
						stmt = conn.prepareStatement(
								"select authors.*, books.* " +
								"  from authors, books " +
								" where authors.author_id = books.author_id " +
								"   and authors.lastName = ?" +
								" order by books.title asc"
						);
						
						stmt.setString(1, lastname);
						
						resultSet = stmt.executeQuery();
						
						while (resultSet.next()) {
							
							// create new Book object
							// retrieve attributes from resultSet starting at index 4
							Book book = new Book();
							loadBook(book, resultSet, 4);
							
							result.add(new Pair<Author, Book>(author, book));
						}
						
					}
					
					// check if the title was found
					if (!found) {
						System.out.println("<" + firstname + " " + lastname + "> was not found in the authors table, creating new author now");
						
						stmt = conn.prepareStatement(
								"insert into authors (lastname, firstname) "
								+ "  values( ?, ?)"
						);
						
						stmt.setString(1, lastname);
						stmt.setString(2, firstname);
						
						stmt.execute();
						
						// a canned query to find the existing authorID
						stmt = conn.prepareStatement(
								"select authors.* "
								+ "  from authors "
								+ "  where firstname = ? and authors.lastname = ? "
						);

						// substitute the title entered by the user for the placeholder in the query
						stmt.setString(1, firstname);
						stmt.setString(2, lastname);

						// execute the query
						resultSet = stmt.executeQuery();
						resultSet.next();
						
						// retrieve attributes from resultSet starting with index 1
						Author author = new Author();
						loadAuthor(author, resultSet, 1);
						
						stmt = conn.prepareStatement(
								"insert into books (author_id, title, isbn, published) "
								+ "  values ( ?, ?, ?, ?) "
						);
						
						stmt.setString(1, Integer.toString(author.getAuthorId()));
						stmt.setString(2, title);
						stmt.setString(3, isbn);
						stmt.setString(4, published);
						
						// execute the query
						stmt.execute();
						
						stmt = conn.prepareStatement(
								"select authors.*, books.* " +
								"  from authors, books " +
								" where authors.author_id = books.author_id " +
								"   and authors.lastName = ?" +
								" order by books.title asc"
						);
						
						stmt.setString(1, lastname);
						
						resultSet = stmt.executeQuery();
						
						while (resultSet.next()) {
							
							// create new Book object
							// retrieve attributes from resultSet starting at index 4
							Book book = new Book();
							loadBook(book, resultSet, 4);
							
							result.add(new Pair<Author, Book>(author, book));
						}
					}
					
					return result;
					
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
	
	private void loadAuthor(Author author, ResultSet resultSet, int index) throws SQLException {
		author.setAuthorId(resultSet.getInt(index++));
		author.setLastname(resultSet.getString(index++));
		author.setFirstname(resultSet.getString(index++));
	}
	
	private void loadBook(Book book, ResultSet resultSet, int index) throws SQLException {
		book.setBookId(resultSet.getInt(index++));
		book.setAuthorId(resultSet.getInt(index++));
		book.setTitle(resultSet.getString(index++));
		book.setIsbn(resultSet.getString(index++));
		book.setPublished(resultSet.getInt(index++));		
	}
	
	public void createTables() {
		executeTransaction(new Transaction<Boolean>() {
			@Override
			public Boolean execute(Connection conn) throws SQLException {
				PreparedStatement stmt1 = null;
				PreparedStatement stmt2 = null;
				PreparedStatement stmt3 = null;
				PreparedStatement stmt4 = null;
				
				try {
					stmt1 = conn.prepareStatement(
						"create table items (" +
						"	item_id integer primary key " +
						"		generated always as identity (start with 1, increment by 1), " +									
						"	type varchar(40)," +
						"	itemName varchar(40)" +
						"	uses integer" +
						"	value integer" +
						"	description varchar(40)" +
						"	extra integer" +
						")"
					);	
					stmt1.executeUpdate();
					
					stmt2 = conn.prepareStatement(
							"create table inventories (" +
							"	inventory_id integer primary key " +
							"		generated always as identity (start with 1, increment by 1), " +
							"	item_id1 integer constraint item_id references items, " +
							"	item_id2 integer constraint item_id references items, " +
							"	item_id3 integer constraint item_id references items, " +
							"	item_id4 integer constraint item_id references items, " +
							"	item_id5 integer constraint item_id references items, " +
							"	item_id6 integer constraint item_id references items, " +
							"	item_id7 integer constraint item_id references items, " +
							"	item_id8 integer constraint item_id references items, " +
							"	item_id9 integer constraint item_id references items, " +
							"	item_id10 integer constraint item_id references items, " +
							")"
					);
					stmt2.executeUpdate();
					
					stmt3 = conn.prepareStatement(
							"create table rooms (" +
							"	room_id integer primary key " +
							"		generated always as identity (start with 1, increment by 1), " +									
							"	inventory_id integer constraint inventory_id references inventories, " +
							"	roomName varchar(40)" +
							"	shortDesc varchar(40)" +
							"	longDesc varchar(40)" +
							")"
					);	
					stmt3.executeUpdate();
					
					stmt4 = conn.prepareStatement(
							"create table actors (" +
							"	actor_id integer primary key " +
							"		generated always as identity (start with 1, increment by 1), " +
							"	type varchar(40)" +
							"	room_id integer constraint room_id references rooms, " +
							"	actorName varchar(40)" +
							"	health integer" +
							"	inventory_id integer constraint inventory_id references inventories, " +
							")"
					);	
					stmt4.executeUpdate();
						
					return true;
				} finally {
					DBUtil.closeQuietly(stmt1);
					DBUtil.closeQuietly(stmt2);
					DBUtil.closeQuietly(stmt3);
					DBUtil.closeQuietly(stmt4);
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
				List<Actor> actorList;
				List<Inventory> inventoryList;
				
				try {
					roomList = InitialData.getRooms();
					itemList = InitialData.getItems();
					actorList = InitialData.getActors();
					inventoryList = InitialData.getInventories();
				} catch (IOException e) {
					throw new SQLException("Couldn't read initial data", e);
				}

				PreparedStatement insertActor = null;
				PreparedStatement insertRoom   = null;
				PreparedStatement insertItem = null;
				PreparedStatement insertInventory   = null;

				try {
					// populate items table (do items first, since item_id is foreign key in inventories table)
					insertItem = conn.prepareStatement("insert into items (itemName, uses, value, description, extra) values (?, ?, ?, ?, ?)");
					for (Item item : itemList) {
						insertItem.setString(1, item.getItemName());
						insertItem.setInt(2, item.getUses());
						insertItem.setInt(3, item.getValue());
						insertItem.setString(4, item.getDescription());
						insertItem.setInt(5, item.getValue());
						insertItem.addBatch();
					}
					insertItem.executeBatch();
					
					// populate inventories table (do this after items table,
					// since itemr_id must exist in items table before inserting inventory)
					insertInventory = conn.prepareStatement("insert into inventories (item_id1, item_id2, item_id3, item_id4, item_id5, item_id6, item_id7, item_id8, item_id9, item_id10) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
					for (Inventory inventory : inventoryList) {
						int size = inventory.getItems().size();
						for(int i = 1; i <= 10; i++) {
							if(i < size) {
								insertInventory.setInt(i, inventory.getItem(i));
							}
							else {
								insertInventory.setInt(i, 0);
							}
						}
						insertInventory.addBatch();
					}
					insertInventory.executeBatch();
					
					insertRoom = conn.prepareStatement("insert into rooms (inventory_id, roomName, shortDesc, longDesc) values (?, ?, ?, ?)");
					for (Room room : roomList) {
						insertItem.setInt(1, room.getInventoryId());
						insertItem.setString(2, room.getRoomName());
						insertItem.setString(3, room.getShortDesc());
						insertItem.setString(4, room.getLongDesc());
						insertItem.addBatch();
					}
					insertItem.executeBatch();
					
					insertActor = conn.prepareStatement("insert into actors (room_id, actorName, health, inventory_id) values (?, ?, ?, ?, ?)");
					for (Actor actor : actorList) {
						insertItem.setInt(1, actor.getRoomId());
						insertItem.setString(2, actor.getActorName());
						insertItem.setInt(3, actor.getHealth());
						insertItem.setInt(4, actor.getInventoryId());
						insertItem.addBatch();
					}
					insertItem.executeBatch();
					
					return true;
				} finally {
					DBUtil.closeQuietly(insertItem);
					DBUtil.closeQuietly(insertInventory);
					DBUtil.closeQuietly(insertRoom);
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
