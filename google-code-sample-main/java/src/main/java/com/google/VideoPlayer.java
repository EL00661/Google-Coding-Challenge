package com.google;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class VideoPlayer {

	private final VideoLibrary videoLibrary;
	
	private Video playing;
	private boolean paused;
	
	Map<String, ArrayList<Video>> playlists = new HashMap<String, ArrayList<Video>>();

	public VideoPlayer() {
		this.videoLibrary = new VideoLibrary();
		this.playing = null;
		this.paused = false;
	}

	public void numberOfVideos() {
		System.out.printf("%s videos in the library%n", videoLibrary.getVideos().size());
	}

	public void showAllVideos() {
		ArrayList<Video> allVideos = new ArrayList<>(videoLibrary.getVideos());
		ArrayList<String> allVideoTitles = new ArrayList<>();
		for (Video video : allVideos) {
			allVideoTitles.add(video.getTitle());
		}
		Collections.sort(allVideoTitles);
		
		System.out.println("Here's a list of all available videos:");
		for (int i = 0 ; i < allVideos.size() ; i++) {
			for (Video video : allVideos) {
				if (video.getTitle().equals(allVideoTitles.get(i))) {
					System.out.println(video.getTitle() + " (" + video.getVideoId() + ") " + video.getTags());
				}
			}
		}
	}

	public void playVideo(String videoId) {
		if (videoLibrary.getVideo(videoId) != null) {
			paused = false;
			if (playing == null) {
				playing = videoLibrary.getVideo(videoId);
				System.out.println("Playing video: " + playing.getTitle());
			} else {
				System.out.println("Stopping video: " + playing.getTitle());
				playing = videoLibrary.getVideo(videoId);
				System.out.println("Playing video: " + playing.getTitle());
			}
		} else {
			System.out.println("Cannot play video: Video does not exist");
		}
	}

	public void stopVideo() {
		if (playing != null) {
			System.out.println("Stopping video: " + playing.getTitle());
			playing = null;
		} else {
			System.out.println("Cannot stop video: No video is currently playing");
		}
	}

	public void playRandomVideo() {
		
		ArrayList<Video> allVideos = new ArrayList<>(videoLibrary.getVideos());
		
		if (!allVideos.isEmpty()) {
			paused = false;
			
			if (playing != null) {
				System.out.println("Stopping video: " + playing.getTitle());
				playing = allVideos.get((int) Math.random() * allVideos.size());
				System.out.println("Playing video: " + playing.getTitle());
			} else {
				playing = allVideos.get((int) Math.random() * allVideos.size());
				System.out.println("Playing video: " + playing.getTitle());
			}
		} else {
			System.out.println("No videos available");
		}
	}

	public void pauseVideo() {
		if (playing != null) {
			if (!paused) {
				paused = true;
				System.out.println("Pausing video: " + playing.getTitle());
			} else {
				System.out.println("Video already paused: " + playing.getTitle());
			}
		} else {
			System.out.println("Cannot pause video: No video is currently playing");
		}
	}

	public void continueVideo() {
		if (playing != null) {
			if (paused) {
				paused = false;
				System.out.println("Continuing video: " + playing.getTitle());
			} else {
				System.out.println("Cannot continue video: Video is not paused");
			}
		} else {
			System.out.println("Cannot continue video: No video is currently playing");
		}
	}

	public void showPlaying() {
		if (playing != null) {
			if (paused == false) {
				System.out.println("Currently playing: " + playing.getTitle() + " (" + playing.getVideoId() + ") " + playing.getTags());
			} else {
				System.out.println("Currently playing: " + playing.getTitle() + " (" + playing.getVideoId() + ") " + playing.getTags() + " - PAUSED");
			}
		} else {
			System.out.println("No video is currently playing");
		}
	}

	public void createPlaylist(String playlistName) {
		ArrayList<String> playlistsLowercase = new ArrayList<String>();
		for (String name : playlists.keySet()) {
			playlistsLowercase.add(name.toLowerCase());
		}
		
		if (!playlistsLowercase.contains(playlistName.toLowerCase())) {
			playlists.put(playlistName, new ArrayList<Video>());
			System.out.println("Successfully created new playlist: " + playlistName);
		} else {
			System.out.println("Cannot create playlist: A playlist with the same name already exists");
		}
	}

	public void addVideoToPlaylist(String playlistName, String videoId) {
		ArrayList<String> playlistsLowercase = new ArrayList<String>();
		for (String name : playlists.keySet()) {
			playlistsLowercase.add(name.toLowerCase());
		}
		
		if (playlistsLowercase.contains(playlistName.toLowerCase())) {
			if (videoLibrary.getVideo(videoId) != null) {
				for (int i = 0 ; i < playlists.size() ; i++) {
					if (playlists.keySet().toArray()[i].toString().toLowerCase().equals(playlistName.toLowerCase())) {
						if (playlists.get(i) != null) {
							// ERRORS lines 154 - 166
							if (!playlists.get(i).contains(videoLibrary.getVideo(videoId))) {
								playlists.get(i).add(videoLibrary.getVideo(videoId));
								System.out.println("Added video to " + playlistName + ": " + videoLibrary.getVideo(videoId).getTitle());
							} else {
								System.out.println("Cannot add video to " + playlistName + ": Video already added");
							}
						} else {
							ArrayList<Video> list = new ArrayList<Video>();
							list.add(videoLibrary.getVideo(videoId));
							playlists.replace(playlists.keySet().toArray()[i].toString(), list);
							System.out.println("Added video to " + playlistName + ": " + videoLibrary.getVideo(videoId).getTitle());
						}
					}
				}
			} else {
				System.out.println("Cannot add video to " + playlistName + ": Video does not exist");
			}
		} else {
			System.out.println("Cannot add video to " + playlistName + ": Playlist does not exist");
		}
	}

	public void showAllPlaylists() {
		if (!playlists.isEmpty()) {
			ArrayList<String> playlistNames = new ArrayList<String>();
			for (String name : playlists.keySet()) {
				playlistNames.add(name);
			}
			Collections.sort(playlistNames);
			
			System.out.println("Showing all playlists:");
			for (String name : playlistNames) {
				System.out.println(name);
			}
		} else {
			System.out.println("No playlists exist yet");
		}
	}

	public void showPlaylist(String playlistName) {
		ArrayList<String> playlistsLowercase = new ArrayList<String>();
		for (String name : playlists.keySet()) {
			playlistsLowercase.add(name.toLowerCase());
		}
		
		if (playlistsLowercase.contains(playlistName.toLowerCase())) {
			System.out.println("Showing playlist: " + playlistName);
			for (int i = 0 ; i < playlists.size() ; i++) {
				if (playlists.keySet().toArray()[i].toString().toLowerCase().equals(playlistName.toLowerCase())) {
					if (playlists.get(i) != null) {
						for (Video video : playlists.get(i)) {
							System.out.println(video.getTitle() + " (" + video.getVideoId() + ") " + video.getTags());
						}
					} else {
						System.out.println("No videos here yet");
					}
				}
			}
		} else {
			System.out.println("Cannot show playlist " + playlistName + ": Playlist does not exist");
		}
	}

	public void removeFromPlaylist(String playlistName, String videoId) {
		System.out.println("removeFromPlaylist needs implementation");
	}

	public void clearPlaylist(String playlistName) {
		ArrayList<String> playlistsLowercase = new ArrayList<String>();
		for (String name : playlists.keySet()) {
			playlistsLowercase.add(name.toLowerCase());
		}
		
		if (playlistsLowercase.contains(playlistName.toLowerCase())) {
			for (int i = 0 ; i < playlists.size() ; i++) {
				if (playlists.keySet().toArray()[i].toString().toLowerCase().equals(playlistName.toLowerCase())) {
					// ERRORS playlistName doesn't check for letter cases
					playlists.replace(playlistName, new ArrayList<Video>());
					System.out.println("Successfully removed all videos from " + playlistName);
				}
			}
		} else {
			System.out.println("Cannot clear playlist " + playlistName + ": Playlist does not exist");
		}
	}

	public void deletePlaylist(String playlistName) {
		ArrayList<String> playlistsLowercase = new ArrayList<String>();
		for (String name : playlists.keySet()) {
			playlistsLowercase.add(name.toLowerCase());
		}
		
		if (playlistsLowercase.contains(playlistName.toLowerCase())) {
			//TODO delete playlist
		} else {
			System.out.println("Cannot delete playlist " + playlistName + ": Playlist does not exist");
		}
	}

	public void searchVideos(String searchTerm) {
		ArrayList<Video> allVideos = new ArrayList<>(videoLibrary.getVideos());
		ArrayList<Video> relevantVideos = new ArrayList<>();
		
		for (Video video : allVideos) {
			if (video.getTitle().toLowerCase().contains(searchTerm.toLowerCase())) {
				relevantVideos.add(video);
			}
		}
		
		ArrayList<String> videoTitles = new ArrayList<>();
		for (Video video : relevantVideos) {
			videoTitles.add(video.getTitle());
		}
		Collections.sort(videoTitles);
		
		if (!relevantVideos.isEmpty()) {
			System.out.println("Here are the results for " + searchTerm + ":");
			
			for (int i = 0 ; i < relevantVideos.size() ; i++) {
				for (Video video : relevantVideos) {
					if (video.getTitle().equals(videoTitles.get(i))) {
						System.out.println(i+1 + ") " + video.getTitle() + " (" + video.getVideoId() + ") " + video.getTags());
					}
				}
			}
			
			System.out.println("Would you like to play any of the above? If yes, specify the number of the video.");
			System.out.println("If your answer is not a valid number, we will assume it's a no.");
			//TODO get input from user and play video
			Scanner scanner = new Scanner(System.in);
			String vidNumber = scanner.nextLine();
			if (vidNumber.matches("[0-9]+")) {
				if (relevantVideos.size() >= Integer.parseInt(vidNumber)) {
					// ERROR plays wrong video
					playing = relevantVideos.get(Integer.parseInt(vidNumber) - 1);
					System.out.println("Playing video: " + playing.getTitle());
				}
			}
		} else {
			System.out.println("No search results for " + searchTerm);
		}
	}

	public void searchVideosWithTag(String videoTag) {
		ArrayList<Video> allVideos = new ArrayList<>(videoLibrary.getVideos());
		ArrayList<Video> relevantVideos = new ArrayList<>();
		
		for (Video video : allVideos) {
			if (video.getTags().toLowerCase().contains(videoTag.toLowerCase())) {
				relevantVideos.add(video);
			}
		}
		
		ArrayList<String> allVideoTitles = new ArrayList<>();
		for (Video video : allVideos) {
			allVideoTitles.add(video.getTitle());
		}
		Collections.sort(allVideoTitles);
		
		if (!relevantVideos.isEmpty()) {
			System.out.println("Here are the results for " + videoTag + ":");
			
			for (int i = 0 ; i < relevantVideos.size() ; i++) {
				for (Video video : relevantVideos) {
					if (video.getTitle().equals(allVideoTitles.get(i))) {
						System.out.println(i+1 + ") " + video.getTitle() + " (" + video.getVideoId() + ") " + video.getTags());
					}
				}
			}
			
			System.out.println("Would you like to play any of the above? If yes, specify the number of the video.");
			System.out.println("If your answer is not a valid number, we will assume it's a no.");
			//TODO get input from user and play video
		} else {
			System.out.println("No search results for " + videoTag);
		}
	}

	public void flagVideo(String videoId) {
		System.out.println("flagVideo needs implementation");
	}

	public void flagVideo(String videoId, String reason) {
		System.out.println("flagVideo needs implementation");
	}

	public void allowVideo(String videoId) {
		System.out.println("allowVideo needs implementation");
	}
}