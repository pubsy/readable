package resources;

import controllers.SecurityController;
import hypermedia.annotations.Link;
import hypermedia.annotations.Operation;
import hypermedia.annotations.Parameter;
import hypermedia.core.Resource;

public abstract class ReadableBasicResource
    implements Resource {

    @Link(rel = "self", title = "self")
    public String self;

    @Link(rel = "books navigation", title = "Books")
    public String books = "/";

    @Link(rel = "users navigation", title = "Users")
    public String users = "/users";

    @Link(rel = "my-profile navigation", title = "My Profile")
    public String myProfile;

    @Operation(rel = "register navigation", method = "POST", title = "Register", params =
        { @Parameter(name = "name"), @Parameter(name = "password") })
    public String register;

    public ReadableBasicResource() {
        if (SecurityController.isAuthenticated()) {
            myProfile = "/my-profile";
        } else {
            register = "/register";
        }
    }

    public void setSelf(String self) {
        this.self = self;
    }

}
