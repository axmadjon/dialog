package uz.mold.dialog.popup;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.v7.widget.PopupMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import uz.mold.dialog.Command;
import uz.mold.dialog.CommandFacade;
import uz.mold.dialog.CommandPopup;
import uz.mold.dialog.popup.repository.PopupItem;

public class PopupBuilder {

    private List<PopupItem> options = new ArrayList<>();

    private PopupBuilder optionPrivate(@NonNull Object name, @NonNull Command command) {
        options.add(new PopupItem(name, command));
        return this;
    }

    public PopupBuilder option(@NonNull CharSequence name, @NonNull Command command) {
        return optionPrivate(name, command);
    }

    public PopupBuilder option(@StringRes int stringId, @NonNull Command command) {
        return optionPrivate(stringId, command);
    }

    public <T> PopupBuilder option(Collection<T> values, final CommandFacade<T> command) {
        for (final T val : values) {
            optionPrivate(command.getName(val), new Command() {
                @Override
                public void apply() {
                    command.apply(val);
                }
            });
        }
        return this;
    }

    public <T> PopupBuilder option(T[] values, final CommandFacade<T> command) {
        return option(Arrays.asList(values), command);
    }

    public PopupMenu show(View view, CommandPopup command) {
        Context context = view.getContext();
        PopupMenu popup = new PopupMenu(context, view);
        Menu menu = popup.getMenu();
        menu.clear();
        boolean running;
        do {
            running = command.populateMenu(menu);
        } while (running);
        popup.show();
        return popup;
    }

    public PopupMenu show(View view) {
        final Context context = view.getContext();
        PopupMenu popupMenu = show(view, new CommandPopup() {
            int position = 0;

            @Override
            public boolean populateMenu(Menu menu) {
                if (options.size() > 0 && position < options.size()) {
                    PopupItem item = options.get(position);
                    item.id = position;
                    menu.add(Menu.NONE, item.id, Menu.NONE, getString(context, item.title));
                    position++;
                    return position < options.size();
                }
                return false;
            }
        });

        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                PopupItem found = options.get(item.getItemId());
                if (found != null) {
                    if (found.command != null) {
                        found.command.apply();
                    }
                    return true;
                }
                return false;
            }
        });

        return popupMenu;
    }

    private CharSequence getString(Context context, Object val) {
        if (val == null) {
            return null;
        }
        if (val instanceof Integer) {
            return context.getString((Integer) val);
        }
        return (CharSequence) val;
    }
}
