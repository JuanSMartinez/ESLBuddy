package backend;

public class UserProfile {

    //User name
    private String name;

    //Singleton
    public static UserProfile instance = null;

    //Get instance
    public static UserProfile getInstance(){
        if(instance == null)
            instance = new UserProfile();
        return instance;
    }

    private UserProfile(){
        name = "User";
    }

    public void setName(String name){
        this.name = name;
    }

    public String getName(){
        return this.name;
    }

}
