package edu.ycp.cs320.TBAG.persist;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import edu.ycp.cs320.TBAG.model.Room;
import edu.ycp.cs320.TBAG.model.Actor;
import edu.ycp.cs320.TBAG.model.Inventory;
import edu.ycp.cs320.TBAG.model.Item;
import edu.ycp.cs320.TBAG.model.Pair;

public class FakeDatabase implements IDatabase {
	
	private List<Room> roomList;
	private List<Actor> actorList;
	private List<Inventory> inventoryList;
	private List<Item> itemList;
	
	public FakeDatabase() {
		roomList = new ArrayList<Room>();
		actorList = new ArrayList<Actor>();
		inventoryList = new ArrayList<Inventory>();
		itemList = new ArrayList<Item>();
		
		// Add initial data
		readInitialData();
		
		System.out.println(roomList.size() + " authors");
		System.out.println(actorList.size() + " books");
		System.out.println(inventoryList.size() + " inventories");
		System.out.println(itemList.size() + " items");
	}

	public void readInitialData() {
		try {
			roomList.addAll(InitialData.getRooms());
			actorList.addAll(InitialData.getActors());
			inventoryList.addAll(InitialData.getInventories());
			itemList.addAll(InitialData.getItems());
		} catch (IOException e) {
			throw new IllegalStateException("Couldn't read initial data", e);
		}
	}
	
	@Override
	public List<Pair<Author, Book>> findAuthorAndBookByTitle(String title) {
		List<Pair<Author, Book>> result = new ArrayList<Pair<Author,Book>>();
		for (Book book : bookList) {
			if (book.getTitle().equals(title)) {
				Author author = findAuthorByAuthorId(book.getAuthorId());
				result.add(new Pair<Author, Book>(author, book));
			}
		}
		return result;
	}
	
	@Override
	public List<Pair<Author, Book>> findAuthorAndBookByAuthorLastName(String lastname){
		List<Pair<Author, Book>> result = new ArrayList<Pair<Author,Book>>();
		for (Book book : bookList) {
			Author author = findAuthorByAuthorId(book.getAuthorId());
			if (author.getLastname().equals(lastname)) {
				result.add(new Pair<Author, Book>(author, book));
			}
		}
		result.sort( (a, b) -> { return 1 * a.getRight().getTitle().compareTo(b.getRight().getTitle()); } );
		
		return result;
	}
	
	@Override
	public List<Pair<Author, Book>> insertAuthorAndBook(String firstname, String lastname, String title, String isbn, String published) {
		List<Pair<Author, Book>> result = new ArrayList<Pair<Author,Book>>();
		for (Author author : authorList) {
			if (author.getFirstname().equals(firstname) && author.getLastname().equals(lastname)) {
				
				Book book = new Book();
				book.setBookId(bookList.size() + 1);
				book.setAuthorId(author.getAuthorId());
				book.setTitle(title);
				book.setIsbn(isbn);
				book.setPublished(Integer.parseInt(published));
				
				bookList.add(book);
				
				result.add(new Pair<Author, Book>(author, book));
				
				return result;
			}
		}
		
		Author author = new Author();
		author.setAuthorId(authorList.size() + 1);
		author.setFirstname(firstname);
		author.setLastname(lastname);
		
		authorList.add(author);
		
		Book book = new Book();
		book.setBookId(bookList.size() + 1);
		book.setAuthorId(author.getAuthorId());
		book.setTitle(title);
		book.setIsbn(isbn);
		book.setPublished(Integer.parseInt(published));
		
		bookList.add(book);
		
		result.add(new Pair<Author, Book>(author, book));
		
		return result;
	}

	private Author findAuthorByAuthorId(int authorId) {
		for (Author author : authorList) {
			if (author.getAuthorId() == authorId) {
				return author;
			}
		}
		return null;
	}
}
