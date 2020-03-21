package sample.Java;

import java.util.ArrayList;

public class AlbumRegister {
    //An ArrayList of albums which contains pictures.
    private ArrayList<Album> albums;
    private int albumsSize;

    public AlbumRegister(ArrayList<Album> albums, int albumsSize) {
        this.albums = new ArrayList<>();
        this.albumsSize = albums.size();
    }

    public AlbumRegister(){
        this.albums = new ArrayList<>();
        this.albumsSize = albums.size();
    }

    public ArrayList<Album> getAlbums() {
        return albums;
    }
    public int getAlbumsSize() {
        return albumsSize;
    }

    public Album findAlbum(String albumName){
        Album album = (Album) albums.stream().filter(a -> a.getAlbumName().equalsIgnoreCase(albumName));
        return album;
    }
}
