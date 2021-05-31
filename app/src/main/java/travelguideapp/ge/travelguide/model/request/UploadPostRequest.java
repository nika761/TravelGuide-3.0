package travelguideapp.ge.travelguide.model.request;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

import travelguideapp.ge.travelguide.model.response.PostResponse;

public class UploadPostRequest implements Parcelable {

    private List<Post_stories> post_stories;
    private List<Integer> tages_user_id;
    private List<String> hashtags;
    private int music_id;
    private String latLng;
    private String address;
    private String address_name;
    private String description;
    private String title;

    public UploadPostRequest(List<Post_stories> post_stories, List<Integer> tages_user_id, List<String> hashtags, int music_id, String latLng, String address, String address_name, String description, String title) {
        this.post_stories = post_stories;
        this.tages_user_id = tages_user_id;
        this.hashtags = hashtags;
        this.music_id = music_id;
        this.latLng = latLng;
        this.address = address;
        this.address_name = address_name;
        this.description = description;
        this.title = title;
    }


    protected UploadPostRequest(Parcel in) {
        hashtags = in.createStringArrayList();
        music_id = in.readInt();
        latLng = in.readString();
        address = in.readString();
        address_name = in.readString();
        description = in.readString();
        title = in.readString();
        if (in.readByte() == 0x01) {
            post_stories = new ArrayList<>();
            in.readList(post_stories, PostResponse.Posts.class.getClassLoader());
        } else {
            post_stories = null;
        }

        if (in.readByte() == 0x01) {
            tages_user_id = new ArrayList<>();
            in.readList(tages_user_id, Integer.class.getClassLoader());
        } else {
            tages_user_id = null;
        }
    }

    public List<Post_stories> getPost_stories() {
        return post_stories;
    }

    public void setPost_stories(List<Post_stories> post_stories) {
        this.post_stories = post_stories;
    }

    public List<Integer> getTages_user_id() {
        return tages_user_id;
    }

    public void setTages_user_id(List<Integer> tages_user_id) {
        this.tages_user_id = tages_user_id;
    }

    public List<String> getHashtags() {
        return hashtags;
    }

    public void setHashtags(List<String> hashtags) {
        this.hashtags = hashtags;
    }

    public int getMusic_id() {
        return music_id;
    }

    public void setMusic_id(int music_id) {
        this.music_id = music_id;
    }

    public String getLatLng() {
        return latLng;
    }

    public void setLatLng(String latLng) {
        this.latLng = latLng;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddress_name() {
        return address_name;
    }

    public void setAddress_name(String address_name) {
        this.address_name = address_name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public static final Creator<UploadPostRequest> CREATOR = new Creator<UploadPostRequest>() {
        @Override
        public UploadPostRequest createFromParcel(Parcel in) {
            return new UploadPostRequest(in);
        }

        @Override
        public UploadPostRequest[] newArray(int size) {
            return new UploadPostRequest[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringList(hashtags);
        dest.writeInt(music_id);
        dest.writeString(latLng);
        dest.writeString(address);
        dest.writeString(address_name);
        dest.writeString(description);
        dest.writeString(title);
        if (post_stories == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(post_stories);
        }
        if (tages_user_id == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(tages_user_id);
        }
    }

    public static class Post_stories implements Parcelable {
        private String story_link;
        private String story_lenght;
        private int voice;
        private int type;

        public Post_stories(String story_link, String story_lenght, int voice, int type) {
            this.story_link = story_link;
            this.story_lenght = story_lenght;
            this.voice = voice;
            this.type = type;
        }

        protected Post_stories(Parcel in) {
            story_link = in.readString();
            story_lenght = in.readString();
            voice = in.readInt();
            type = in.readInt();
        }

        public static final Creator<Post_stories> CREATOR = new Creator<Post_stories>() {
            @Override
            public Post_stories createFromParcel(Parcel in) {
                return new Post_stories(in);
            }

            @Override
            public Post_stories[] newArray(int size) {
                return new Post_stories[size];
            }
        };

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(story_link);
            dest.writeString(story_lenght);
            dest.writeInt(voice);
            dest.writeInt(type);
        }
    }
}
