package com.dijon.graffiti.network;

import com.dijon.graffiti.BuildConfig;
import com.dijon.graffiti.network.models.Post;
import com.dijon.graffiti.network.models.User;
import com.firebase.client.Firebase;

/**
 * Client service use to wrap api requests.
 * This will serve as a help in case backend structures ever switch,
 * then we only need to update one location.
 */
public class GraffitiClient {

    private static Firebase mFirebaseRef = new Firebase(BuildConfig.API_URL);

    /**
     * Creates or Updates the value for a User
     * @param uid id of User to create or update
     * @param user User object to write to db
     */
    public static void createOrUpdateUser(String uid, User user, Firebase.CompletionListener listener) {
        mFirebaseRef.child(GraffitiService.USERS).child(uid).setValue(user, listener);
    }

    /**
     * Uploads an Post to the backend.
     * TODO: Consider DB structure
     *
     * @param post post to upload
     * @param listener completion listener
     */
    public static void postImage(Post post, Firebase.CompletionListener listener) {
        mFirebaseRef.child(GraffitiService.POSTS).push().setValue(post, listener);
    }

}
