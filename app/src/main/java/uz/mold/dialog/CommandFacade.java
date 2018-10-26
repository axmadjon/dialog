package uz.mold.dialog;


public interface CommandFacade<T> extends CommandDialogFacade<T> {
    Object getIcon(T val);
}

