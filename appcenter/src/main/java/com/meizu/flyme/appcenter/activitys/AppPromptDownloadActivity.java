package com.meizu.flyme.appcenter.activitys;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.DialogInterface.OnDismissListener;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import com.meizu.cloud.pushsdk.constants.PushConstants;
import com.meizu.mstore.R;

public class AppPromptDownloadActivity extends Activity {
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        Bundle bundle = getIntent().getExtras();
        String title = bundle.getString(PushConstants.TITLE);
        String content = bundle.getString(PushConstants.CONTENT);
        String uriStr = null;
        if (bundle.containsKey("app_identify")) {
            uriStr = "mstore:http://app.meizu.com/phone/apps/" + bundle.getString("app_identify");
        } else if (bundle.containsKey("app_package")) {
            uriStr = "http://app.meizu.com/apps/public/detail?package_name=" + bundle.getString("app_package");
        }
        Builder builder = new Builder(this, 5);
        builder.setMessage(content);
        builder.setNegativeButton(R.string.cancel, null);
        final Uri finalUri = Uri.parse(uriStr);
        builder.setPositiveButton(R.string.confirm, new OnClickListener(this) {
            final /* synthetic */ AppPromptDownloadActivity b;

            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(this.b.getApplicationContext(), AppMainActivity.class);
                intent.setAction("android.intent.action.VIEW");
                intent.setData(finalUri);
                this.b.startActivity(intent);
            }
        });
        Dialog dialog = builder.create();
        dialog.setOnDismissListener(new OnDismissListener(this) {
            final /* synthetic */ AppPromptDownloadActivity a;

            {
                this.a = r1;
            }

            public void onDismiss(DialogInterface dialog) {
                this.a.finish();
            }
        });
        dialog.getWindow().setType(2003);
        dialog.show();
    }
}
