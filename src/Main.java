import java.util.GregorianCalendar;

public class Main {    
    
    public static void main(String[] args) {
    	
    	
    	/* EXAMPLES FOR CLASSES AND METHODS */
    	
    	// Customer class
    	// PK(email) name birthday gender serviceStart serviceEnd payment(yearly/monthly), phone, nickname
    	System.out.println("Customer Class showcase:");
    	String[] strArrCustomer = {"abc@walla.co.il", "name", "12/03/1985", "F", 
    			"20/8/2017", "28/08/2017", "M", "0501234567", "nickname"};
    	Customer c01 = new Customer(strArrCustomer);
    	System.out.println(c01.entityToString());
    	System.out.println(c01.validateData());
    	
    	System.out.print("\n");
    	// Playlist class
    	// PK(1xxxxxx) name creationDate numOfSongs duration
    	System.out.println("Playlist Class showcase:");
    	String[] strArrPlaylist = {"1000042", "playlist01", "12/10/2019", "5", "20"};
    	Playlist pl01 = new Playlist(strArrPlaylist);
    	System.out.println(pl01.entityToString());
    	System.out.println(pl01.validateData());
    	
    	System.out.print("\n");
    	// Artist class
    	// PK(2xxxxxx) name wikipediaLink description
    	System.out.println("Artist Class showcase:");
    	String[] strArrArtist = {"2000078", "Shlomo_Artzi", "https://en.wikipedia.org/wiki/Shlomo_Artzi", "israeli_folk_rock_singer"};
    	Artist ar01 = new Artist(strArrArtist);
    	System.out.println(ar01.entityToString());
    	System.out.println(ar01.validateData());
    	
    	System.out.print("\n");
    	// Song class
    	// PK(3xxxxxx) name releaseDate duration youtubeLink file
    	System.out.println("Song Class showcase:");
    	String[] strArrSong = {"3000987", "Rock_Star", "29/9/2003", "198", "http://y2u.be/dQw4w9WgXcQ", "play.mp4"};
    	Song so01 = new Song(strArrSong);
    	System.out.println(so01.entityToString());
    	System.out.println(so01.validateData());
    	
    	System.out.print("\n");
    	// CreatedBy class
    	// PK1(customer) PK2(playlist)
    	System.out.println("CreatedBy Class showcase:");
    	String[] strArrCB = {"abc@gmail.com", "1989765"};
    	CreatedBy cb01 = new CreatedBy(strArrCB);
    	System.out.println(cb01.relationToString());
    	System.out.println(cb01.validateData());
    	
    	System.out.print("\n");
    	// SongInPlaylist class
    	// PK1(song) PK2(playlist) creationDate
    	System.out.println("SongInPlaylist Class showcase:");
    	String[] strArrSIP = {"3098092", "1029009", "1/1/1999"};
    	SongInPlaylist sip01 = new SongInPlaylist(strArrSIP);
    	System.out.println(sip01.relationToString());
    	System.out.println(sip01.validateData());
    	
    	System.out.print("\n");
    	// SongPerformedByArtist class
    	// PK1(song) PK2(artist) role
    	System.out.println("SongPerformedByArtist Class showcase:");
    	String[] strArrSPBA = {"3000987", "2193857", "S"};
    	SongPerformedByArtist spba01 = new SongPerformedByArtist(strArrSPBA);
    	System.out.println(spba01.relationToString());
    	System.out.println(spba01.validateData());
    	
    	System.out.print("\n");
    	// MyUtility
    	System.out.println("MyUtility Class showcase:");
    	String date01 = "1/03/1977";
    	System.out.println(date01);
    	GregorianCalendar gc01 = MyUtility.textToDate(date01);
    	System.out.println(MyUtility.dateToText(gc01));
    	String date02 = "29/02/2017";
    	System.out.println(MyUtility.validateDate(date02));
    	
    	System.out.print("\n");
    	// Initialize the app
    	SpotibyApp st2 = new SpotibyApp();
    	System.out.println(st2.isExists("roei@gmail.com"));
    	System.out.println(st2.numOfEntityInDB(Song.class));
    }

}
