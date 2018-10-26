package uz.mold.dialog.popup.repository;

import uz.mold.dialog.Command;

public class PopupItem {

    public int id;
    public final Object title;
    public final Command command;

    public PopupItem(Object title, Command command) {
        this.title = title;
        this.command = command;
    }
}
