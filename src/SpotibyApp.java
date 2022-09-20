import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.*;
import javax.swing.*;

import baseForms.Entity;
import baseForms.Relation;

public class SpotibyApp {
	
    // DATA
    private ArrayList<Entity> entities;
    private ArrayList<Relation> relations;
    private String[] entitiesNames = {"Customer.txt", "Playlist.txt", "Artist.txt", "Song.txt"};
    private String[] relationsNames = {"CreatedBy.txt", "SongInPlaylist.txt", "SongPerformedByArtist.txt"};
    
    // UI components
    private JFrame window;
    private JPanel contentPanel;
    private JButton customersButton, artistsButton, addSong, addPlaylist, addSongToPlaylistButton, doneButton;
    private JLabel email, artistName, playlistPk;
    private JTextField idTextField, playlistNameTextField, SongDataTextField[], songIDTextField;
    private ButtonHandler bHandler;
    
    /**
     * The main app's constructor.
     * The app's GUI, data and functionality are here.
     */
    public SpotibyApp() {
    	    	
        // DATA
    	// initialize empty array lists
        entities = new ArrayList<>();
        relations = new ArrayList<>();
              
        //read from files
        //entities
        for (String fileName : entitiesNames)
        	entityStringToAL(readFromDB(fileName));
        //relations
        for (String str : relationsNames)
        	relationStringToAL(readFromDB(str));
        

        // UI
        // inits
        email = new JLabel();
        artistName = new JLabel();
    	playlistPk = new JLabel();
        playlistNameTextField = new JTextField(18);
    	songIDTextField = new JTextField(18);

        // main frame
        window = new JFrame("Spotiby");

        // cards
        contentPanel = new JPanel(new CardLayout());
        JPanel mainCard = new JPanel();
        JPanel customerCard = new JPanel();
        JPanel artistCard = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JPanel songsToPlaylistCard = new JPanel();

        contentPanel.add(mainCard, "Main");
        contentPanel.add(customerCard, "Customer");
        contentPanel.add(artistCard, "Artist");
        contentPanel.add(songsToPlaylistCard, "Playlist");

        // listener
        bHandler = new ButtonHandler();

        // buttons
        customersButton = new JButton("Customer");
        customersButton.setActionCommand("Customer");
        customersButton.addActionListener(bHandler);

        artistsButton = new JButton("Artist");
        artistsButton.setActionCommand("Artist");
        artistsButton.addActionListener(bHandler);

        addSong = new JButton("Add song");
        addSong.setActionCommand("Main");
        addSong.addActionListener(bHandler);

        addPlaylist = new JButton("Add playlist");
        addPlaylist.setActionCommand("Playlist");
        addPlaylist.addActionListener(bHandler);
        
        addSongToPlaylistButton = new JButton("Add");
        addSongToPlaylistButton.setActionCommand("Playlist");
        addSongToPlaylistButton.addActionListener(bHandler);
        
        doneButton = new JButton("Done");
        doneButton.setActionCommand("Main");
        doneButton.addActionListener(bHandler);
        
        customersButton.setToolTipText("ID should look as follows: name@domain");
        artistsButton.setToolTipText("ID should look as follows: 2xxxxxx");
        addSong.setToolTipText("Add a new song to the database");
        addPlaylist.setToolTipText("Add a new playlist for the customer");
        addSongToPlaylistButton.setToolTipText("Add an existing song to the playlist. "
        		+ "Song ID should look like: 3xxxxxx");
        doneButton.setToolTipText("Back to main menu");

        // other components
        idTextField = new JTextField(10);

        // components to cards
        MyUtility.addToCenter(mainCard, MyUtility.createRow("Primary Identifier", idTextField));
        mainCard.add(customersButton);
        mainCard.add(artistsButton);
        
        customerCard.add(email);
        MyUtility.addToCenter(customerCard, MyUtility.createRow("New Playlist Name", playlistNameTextField));
        customerCard.add(addPlaylist); // add button

        artistCard.add(artistName);
        String array[] = { "Song's Name", "Release Date", "Duration", "Artist's Role" };
        SongDataTextField = new JTextField[array.length];
        for (int i = 0; i < array.length; i++) {
            SongDataTextField[i] = new JTextField(18);
            MyUtility.addToCenter(artistCard, MyUtility.createRow(array[i], SongDataTextField[i]));
        }
        artistCard.add(addSong);
        
        songsToPlaylistCard.add(playlistPk);
        MyUtility.addToCenter(songsToPlaylistCard, MyUtility.createRow("Song ID", songIDTextField));
        songsToPlaylistCard.add(addSongToPlaylistButton);
        songsToPlaylistCard.add(doneButton);
        
        window.add(contentPanel);
        window.setVisible(true);
        window.setSize(300, 130);
        window.setResizable(false);
        window.setLocationRelativeTo(null);
        window.addWindowListener(new WindowAdapter() {
        	public void windowClosing(WindowEvent e) {
        		boolean alive = false;
        		Set<Thread> threads = Thread.getAllStackTraces().keySet();
        		 //check that the running threads we defined have finished working
        		for (Thread t : threads) {
        		    if (t.getName().equals("ThreadNumber2")) {
        		    	alive = true;
        		    	window.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
                		JOptionPane.showMessageDialog(window, "Data being processed, please wait...");
        		    	break;
        		    }
        		}
        		if(!alive) {
        			window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE );
        			if (validateDB() == true)
            			writeToDB();
                    System.exit(0);
        		}
        	}
        });
        
    }
    
    /**
     * Update's the playlist numOfSongs and totalDuration according to
     * the song's properties. In addition, creates a new instance
     * of SongInPlaylist.
     */
    private void addSongToPlaylist(String songPK, String playlistPK) {
    	Playlist playlist = (Playlist)getEntity(playlistPK);
    	Song song = (Song)getEntity(songPK);
    	//update playlist info thread
		Thread t1 = new Thread() {
			public void run() {
				try {
			    	playlist.addSongInfo(song);
			    	System.out.println("New playlist duration: " + playlist.getTotalDuration() + 
			    			". New playlist num of songs: " + playlist.getNumOfSongs() + ".");
				}
				finally {}
			}
		};
    	//creation of relation-class instance thread
		Thread t2 = new Thread() {
			public void run() {
				try {
					setName("ThreadNumber2");
					//added sleep time to simulate delay
					Thread.sleep(5000);
			    	String[] strArr = {songPK, playlistPK, MyUtility.dateToText(new GregorianCalendar())};
			    	relations.add(new SongInPlaylist(strArr));
					System.out.println("The song " + song.getPK() + ", " + song.getName() +
							", was successfully added to the playlist " + playlist.getPK() + 
							", " + playlist.getName() + ".");
				}
				catch(InterruptedException ie) {}
			}
		};
		t1.start();
		t2.start();
    }
    
    /**
     * Returns the requested entity according to the primary key.
     */
    private Entity getEntity(String pk) {
    	for (Entity entity : entities)
    		if (entity.getPK().equals(pk))
    			return entity;
    	return null;
    }
    
    //the method was made public to demonstrate its use in the Main class
    /**
     * Returns the number of instances of the requested Entity type.
     */
    public int numOfEntityInDB(Class<?> c) {
    	//0 - Customer, 1 - Playlist, 2 - Artist, 3 - Song
    	int size = 0;
    	for (Entity entity : entities)
    		if (entity.getClass() == c)
    			size++;
    	return size;
    }
    
    /**
     * This method receives a new Song's properties and the Artist's
     * info, creates a new instance of Song and a new instance
     * of SongPerformedByArtist and adds them to the database.
     */
    private void addNewSongToDB(String name, String releaseDate, String duration, String artistPK, String role) {
    	//create the new song and add to the array list
    	String serialNum = String.valueOf(3000000 + numOfEntityInDB(Song.class) + 1);
    	String[] strArrSong = {
    			serialNum, name, releaseDate, duration,
    			"http://y2u.be/dQw4w9WgXcQ", "play.mp4"
    	};
    	entities.add(new Song(strArrSong));
    	//create the relation class instance and add to the arraylist
    	String[] strArrSPBA = {serialNum, artistPK, role};
    	relations.add(new SongPerformedByArtist(strArrSPBA));
    }
    
    /**
     * This method receives a Customer's primary key and a Playlist's
     * name, creates a new instance of Playlist and a new instance of
     * CreatedBy and adds them to the database. 
     */
    private String addNewPlaylistToCustomer(String customerPK, String playlistName) {
    	//create the playlist and add to the arraylist
    	Playlist newPlaylist = new Playlist(playlistName, 1000000 + numOfEntityInDB(Playlist.class) + 1);
    	entities.add(newPlaylist);
    	//create the relation class instance and add to the arraylist
    	String[] strArr = {customerPK, newPlaylist.getPK()};
    	relations.add(new CreatedBy(strArr));
    	return newPlaylist.getPK();
    }
    
    /**
     * This method receives a file's name and returns its entire
     * content, parsed by lines, as an ArrayList of Strings
     * where each element is a line from the file.
     */
    private ArrayList<String> readFromDB(String fileName) {
        ArrayList<String> linesList = new ArrayList<String>();

        try {
            File file = new File(fileName);
            Scanner sc = new Scanner(file);
            while (sc.hasNextLine()) {
                linesList.add(sc.nextLine());
            }
            sc.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        return linesList;
    }
    
    //the method was made public to demonstrate its use in the Main class
    /**
     * This method invokes each instance's validateData method to validate
     * the entire database according to the Entities' and Relations' regex.
     */
    private boolean validateDB() {
    	for (Entity ent : entities)
    		if (ent.validateData() == false)
    			return false;
    	for (Relation rel : relations)
    		if (rel.validateData() == false)
    			return false;
    	return true;
    }
    
    /**
     * This method saves the entire database to different files according
     * to each Entity type: Customer, Playlist, Artist, Song; and according
     * to each Relation type: CreatedBy, SongInPlaylist,
     * SongPerformedByArtist.
     */
    private void writeToDB() {
    	ArrayList<PrintWriter> entsOuts = new ArrayList<>();
    	ArrayList<PrintWriter> relsOuts = new ArrayList<>();
		String entityStr, relationStr;
    	
    	try {
    		//ENTITIES
        	//0 - Customer, 1 - Playlist, 2 - Artist, 3 - Song
			for (int i = 0 ; i < entitiesNames.length; i++)
				entsOuts.add(new PrintWriter(entitiesNames[i]));
			
			for (Entity ent : entities) {
				entityStr = ent.entityToString();
				switch(ent.getPK().charAt(0)) {
				//playlist
				case '1':
					entsOuts.get(1).println(entityStr);
					break;
				//artist
				case '2':
					entsOuts.get(2).println(entityStr);
					break;
				//song
				case '3':
					entsOuts.get(3).println(entityStr);
					break;
				//customer
				default:
					entsOuts.get(0).println(entityStr);
					break;
				}
			}
			for (PrintWriter out : entsOuts)
				out.close();
			
			//RELATIONS
			//0 - CreatedBy, 1 - SongInPlaylist, 2 - SongPerformedByArtist
			for (int i = 0; i <relationsNames.length; i++)
				relsOuts.add(new PrintWriter(relationsNames[i]));
						
			for (Relation ren : relations) {
				relationStr = ren.relationToString();
				//CreatedBy
				if (CreatedBy.validateData(relationStr)) {
					relsOuts.get(0).println(ren.relationToString());
					continue;
				}
				//SongInPlaylist
				if (SongInPlaylist.validateData(relationStr)) {
					relsOuts.get(1).println(ren.relationToString());
					continue;
				}
				//SongPerformedByArtist
				if (SongPerformedByArtist.validateData(relationStr)) {
					relsOuts.get(2).println(ren.relationToString());
					continue;
				}
			}
			
			for (PrintWriter out : relsOuts)
				out.close();
    	}
    	
    	catch (FileNotFoundException fnfe) {}
    }
    
    /**
     * The method receives an ArrayList of Relations,
     * creates a new instance of each Relation
     * and adds it to the Relations ArrayList.
     */
    private void relationStringToAL(ArrayList<String> ALoStr) {
    	
    	for (String line : ALoStr) {
    		//CreatedBy
    		if (CreatedBy.validateData(line)) {
    			relations.add(new CreatedBy(line.split(" " )));
    			continue;
    		}
    		//SongInPlaylist
    		if (SongInPlaylist.validateData(line)) {
    			relations.add(new SongInPlaylist(line.split(" " )));
    			continue;
    		}
    		//SongPerformedByArtist
    		if (SongPerformedByArtist.validateData(line)) {
    			relations.add(new SongPerformedByArtist(line.split(" " )));
    			continue;
    		}
    	}
    }
	
    /**
     * The method receives an ArrayList of Entities,
     * creates a new instance of each Entity
     * and adds it to the Entities ArrayList.
     */
    private void entityStringToAL(ArrayList<String> ALoStr) {
        for (String line : ALoStr) {
        	switch(line.charAt(0)) {
        	//playlist
        	case '1':
            	if (Playlist.validateData(line))
            		entities.add(new Playlist(line.split(" ")));
        		break;
        	//artist
        	case '2':
        		if (Artist.validateData(line))
        			entities.add(new Artist(line.split(" ")));
        		break;
        	//song
        	case '3':
        		if (Song.validateData(line))
        			entities.add(new Song(line.split(" ")));
        		break;
        	//customer
        	default:
            	if (Customer.validateData(line))
            		entities.add(new Customer(line.split(" ")));
        		break;
        	}
        }
    }
    
    //the method was made public to demonstrate its use in the Main class
    /**
     * Check if an entity already exists in the database.
     */
    public boolean isExists(String pk) {
    	if (getEntity(pk) == null)
    		return false;
    	return true;
    }
    
    /**
     * Handling hidden class for the buttons.
     */
    class ButtonHandler implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            CardLayout cl = (CardLayout) (contentPanel.getLayout());

            if (e.getSource() == customersButton) {
            	if (Customer.validatePK(idTextField.getText()) && isExists(idTextField.getText())) {
                    window.setSize(350, 200);
                    email.setText("Customer's ID: " + idTextField.getText());
                    cl.show(contentPanel, e.getActionCommand());
            	}
            	else
            		JOptionPane.showMessageDialog(window, "Customer is not in the database.");
            }

            if (e.getSource() == artistsButton) {
            	if (Artist.validatePK(idTextField.getText()) && isExists(idTextField.getText())) {
                    window.setSize(300, 250);
                    artistName.setText("Artist's ID: " + idTextField.getText());
                    cl.show(contentPanel, e.getActionCommand());
            	}
            	else
            		JOptionPane.showMessageDialog(window, "Artist is not in the database.");
            }

            if (e.getSource() == addSong) {
            	if (Song.validatePartialData(SongDataTextField[0].getText() + " " + SongDataTextField[1].getText() + 
            			" " + SongDataTextField[2].getText()) && SongDataTextField[3].getText().matches("[LSPD]")) {
	                // addSong function
	                addNewSongToDB(SongDataTextField[0].getText(), SongDataTextField[1].getText(),
	                        SongDataTextField[2].getText(), idTextField.getText(), SongDataTextField[3].getText());
	                toMainCard();
	                for (JTextField tf : SongDataTextField)
	                	tf.setText("");
	                artistName.setText("");
	                cl.show(contentPanel, e.getActionCommand());
            	}
            	else
            		JOptionPane.showMessageDialog(window, "Wrong format.");
            }

            if (e.getSource() == addPlaylist) {
            	if (!playlistNameTextField.getText().equals("")) {
                    // addPlaylist function
            		playlistNameTextField.setText(addNewPlaylistToCustomer(idTextField.getText(), playlistNameTextField.getText()));
                    window.setSize(300, 200);
                    playlistPk.setText("Playlist's ID: " + playlistNameTextField.getText());
                    cl.show(contentPanel, e.getActionCommand());
            	}
            	else
            		JOptionPane.showMessageDialog(window, "Cannot add empty name.");
            	
            }
            
        	if (e.getSource() == addSongToPlaylistButton) {
        		//check that it's a song's PK and that the song is in the database
        		String songPK = songIDTextField.getText();
        		if (Song.validatePK(songPK) && isExists(songPK)) {
        			addSongToPlaylist(songPK, playlistNameTextField.getText());
        		}
        		else
            		JOptionPane.showMessageDialog(window, "Song is not in the database.");
        		songIDTextField.setText("");
        	}
        	
        	if (e.getSource() == doneButton) {
        		toMainCard();
                cl.show(contentPanel, e.getActionCommand());
        	}
        }

        /**
         * Sets Frame size to default of main card.
         */
        private void toMainCard() {
            window.setSize(300, 130);
            email.setText("");
            idTextField.setText("");
    		playlistNameTextField.setText("");
        }
    }
}