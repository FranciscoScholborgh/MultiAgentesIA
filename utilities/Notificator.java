/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utilities;

import javafx.geometry.Pos;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;

/**
 *
 * @author frank
 */
public class Notificator {
    
    private static Notificator instance;
    
    private Notifications notification;
    
    private Notificator() {
        this.notification = Notifications.create();
        this.notification.graphic(null);
        this.notification.position(Pos.BOTTOM_RIGHT);
    }
    
    public static Notificator getInstance() {
        if (instance == null) {
            synchronized (Notificator.class) {
                instance = new Notificator();
            }
        }
        return instance;
    }
    
    private void notify (String title, String text, int duration) {
        notification.title(title);
        notification.text(text);
        notification.hideAfter(Duration.seconds(duration));
    }
    
    public void notifyInformation(String title, String text, int duration) {
        notify(title, text, duration);
        notification.showInformation();
    }
    
    public void notifyWarning (String title, String text, int duration) {
        notify(title, text, duration);
        notification.showWarning();
    }
}
