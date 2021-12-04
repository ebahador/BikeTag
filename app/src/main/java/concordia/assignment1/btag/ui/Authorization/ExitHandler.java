package concordia.assignment1.btag.ui.Authorization;

import android.app.Activity;
import android.content.Context;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import concordia.assignment1.btag.R;


public final class ExitHandler extends AppCompatActivity {

    public static void exitDialog(Context context) {
        Activity activity = (Activity) context;
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setCancelable(false);
        builder.setTitle("Do your want to exit?");
        builder.setPositiveButton("Yes", (dialog, item) -> activity.finishAffinity());
        builder.setNegativeButton("No", (dialog, item) -> dialog.dismiss());
        builder.setIcon(R.drawable.warning);
        builder.show();
    }
}
