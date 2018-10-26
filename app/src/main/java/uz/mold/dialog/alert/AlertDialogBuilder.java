package uz.mold.dialog.alert;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import uz.mold.dialog.DialogError;
import uz.mold.dialog.R;
import uz.mold.dialog.Command;
import uz.mold.dialog.CommandDialogFacade;

public class AlertDialogBuilder {

    private Object mTitle;
    private Object mMessage;

    private Object positiveName;
    private Command positiveCommand;

    private Object negativeName;
    private Command negativeCommand;

    private Object neutralName;
    private Command neutralCommand;

    private List<Object> optionNames;
    private List<Command> optionCommands;

    private ListAdapter adapter;
    private DialogInterface.OnClickListener onClickListener;

    private boolean cancelable = true;

    public AlertDialogBuilder setAdapter(ListAdapter adapter, DialogInterface.OnClickListener onClickListener) {
        this.adapter = adapter;
        this.onClickListener = onClickListener;
        return this;
    }

    public AlertDialogBuilder cancelable(boolean cancelable) {
        this.cancelable = cancelable;
        return this;
    }

    public AlertDialogBuilder title(CharSequence st) {
        this.mTitle = st;
        return this;
    }

    public AlertDialogBuilder title(int stringId) {
        this.mTitle = stringId;
        return this;
    }

    public AlertDialogBuilder message(CharSequence st) {
        if (optionNames != null) {
            throw DialogError.Unsupported();
        }
        this.mMessage = st;
        return this;
    }

    public AlertDialogBuilder message(int stringId) {
        if (optionNames != null) {
            throw DialogError.Unsupported();
        }
        this.mMessage = stringId;
        return this;
    }

    public AlertDialogBuilder positive(CharSequence st, Command command) {
        positiveName = st;
        positiveCommand = command;
        return this;
    }

    public AlertDialogBuilder positive(int stringId, Command command) {
        positiveName = stringId;
        positiveCommand = command;
        return this;
    }

    public AlertDialogBuilder positive(Command command) {
        positiveName = null;
        positiveCommand = command;
        return this;
    }

    public AlertDialogBuilder negative(CharSequence st, Command command) {
        negativeName = st;
        negativeCommand = command;
        return this;
    }

    public AlertDialogBuilder negative(int stringId, Command command) {
        negativeName = stringId;
        negativeCommand = command;
        return this;
    }

    public AlertDialogBuilder negative(Command command) {
        negativeName = null;
        negativeCommand = command;
        return this;
    }

    public AlertDialogBuilder neutral(CharSequence st, Command command) {
        neutralName = st;
        neutralCommand = command;
        return this;
    }

    public AlertDialogBuilder neutral(int stringId, Command command) {
        neutralName = stringId;
        neutralCommand = command;
        return this;
    }

    public AlertDialogBuilder neutral(Command command) {
        neutralName = null;
        neutralCommand = command;
        return this;
    }

    private AlertDialogBuilder optionPrivate(Object name, Command command) {
        if (mMessage != null) {
            throw DialogError.Unsupported();
        }
        if (optionNames == null) {
            optionNames = new ArrayList<>();
            optionCommands = new ArrayList<>();
        }
        optionNames.add(name);
        optionCommands.add(command);
        return this;
    }

    public AlertDialogBuilder option(CharSequence name, Command command) {
        return optionPrivate(name, command);
    }

    public AlertDialogBuilder option(int stringId, Command command) {
        return optionPrivate(stringId, command);
    }

    public <T> AlertDialogBuilder option(Collection<T> values, final CommandDialogFacade<T> command) {
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

    public <T> AlertDialogBuilder option(T[] values, final CommandDialogFacade<T> command) {
        return option(Arrays.asList(values), command);
    }

    public void show(Activity activity) {
        fixButtonNames(activity);
        AlertDialog.Builder b = new AlertDialog.Builder(activity);
        b.setCancelable(cancelable);

        if (mTitle != null) {
            b.setTitle(getString(activity, mTitle));
        }

        if (mMessage != null) {
            b.setMessage(getString(activity, mMessage));
        }

        if (optionNames != null) {
            List<CharSequence> ns = new ArrayList<>();
            for (Object o : optionNames) {
                ns.add(getString(activity, o));
            }
            ArrayAdapter<CharSequence> adapter =
                    new ArrayAdapter<>(activity, android.R.layout.select_dialog_item, ns);
            b.setAdapter(adapter, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    optionCommands.get(which).apply();
                }
            });
        }

        if (adapter != null) {
            b.setAdapter(adapter, onClickListener);
        }

        if (positiveCommand != null) {
            b.setPositiveButton(getString(activity, positiveName), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    positiveCommand.apply();
                }
            });
        }
        if (negativeCommand != null) {
            b.setNegativeButton(getString(activity, negativeName), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    negativeCommand.apply();
                }
            });
        }
        if (neutralCommand != null) {
            b.setNeutralButton(getString(activity, neutralName), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    neutralCommand.apply();
                }
            });
        }

        try {
            b.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private CharSequence getString(Activity activity, Object val) {
        if (val == null) {
            return null;
        }
        if (val instanceof Integer) {
            return activity.getString((Integer) val);
        }
        return (CharSequence) val;
    }

    private void setButtonNames(int positiveId, int negativeId, int neutralId) {
        if (positiveName == null) {
            this.positiveName = positiveId;
        }
        if (negativeName == null) {
            this.negativeName = negativeId;
        }
        if (neutralName == null) {
            this.neutralName = neutralId;
        }
    }

    private void fixButtonNames(Activity activity) {
        if (positiveCommand != null && negativeCommand == null && neutralCommand == null) {
            setButtonNames(R.string.ok, 0, 0);
        } else if (positiveCommand == null && negativeCommand != null && neutralCommand == null) {
            setButtonNames(0, R.string.cancel, 0);
        } else if (positiveCommand == null && negativeCommand == null && neutralCommand != null) {
            setButtonNames(0, 0, R.string.cancel);
        } else {
            setButtonNames(R.string.yes, R.string.no, R.string.cancel);
        }
    }
}
