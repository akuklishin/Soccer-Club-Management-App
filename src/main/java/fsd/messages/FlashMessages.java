package fsd.messages;

import fsd.entities.User;

public class FlashMessages {

    private static String message;

    public static String userAdded(User user){

        if(user.getRole().equals(User.Role.ADMIN)) {
            message = "Admin " + user.getFirstName() + " " + user.getLastName() + " has been configured successfully";
            return message;
        } else if (user.getRole().equals(User.Role.COACH)) {
            message = "Coach " + user.getFirstName() + " " + user.getLastName() + " has been configured successfully";
            return message;
        }else {
            message = "Player " + user.getFirstName() + " " + user.getLastName() + " has been configured successfully";
            return message;
        }
    }

    public  static String userDeleted(User user){

        if(user.getRole().equals(User.Role.ADMIN)) {
            message = "Admin " + user.getFirstName() + " " + user.getLastName() + " has been deleted successfully";
            return message;
        } else if (user.getRole().equals(User.Role.COACH)) {
            message = "Coach " + user.getFirstName() + " " + user.getLastName() + " has been deleted successfully";
            return message;
        }else {
            message = "Player " + user.getFirstName() + " " + user.getLastName() + " has been deleted successfully";
            return message;
        }
    }

    public  static String userModified(User user){

        if(user.getRole().equals(User.Role.ADMIN)) {
            message = "Admin " + user.getFirstName() + " " + user.getLastName() + " has been modified successfully";
            return message;
        } else if (user.getRole().equals(User.Role.COACH)) {
            message = "Coach " + user.getFirstName() + " " + user.getLastName() + " has been modified successfully";
            return message;
        }else {
            message = "Player " + user.getFirstName() + " " + user.getLastName() + " has been modified successfully";
            return message;
        }
    }

    public  static String matchDeleted(){
        message = "Match has been deleted successfully";
        return message;
    }

    public  static String matchAdded(){
        message = "Match has been added/modified successfully";
        return message;
    }

    public  static String matchDetailAdded(){
        message = "Match Detail has been added/modified successfully";
        return message;
    }

    public  static String matchDetailDeleted(){
        message = "Match Detail has been deleted successfully";
        return message;
    }

    public  static String trainingDeleted(){
        message = "Training has been deleted successfully";
        return message;
    }

    public  static String trainingAdded(){
        message = "Training has been added/modified successfully";
        return message;
    }

}
