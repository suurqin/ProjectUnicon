package com.jm.projectunion.common.share;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.jm.projectunion.R;


/**
 * Created by Young on 2017/3/31.
 */

public class ShareView {

    private static ShareView instance;
    private ShareManager.ShareContent shareContent;
    private Context context;
    private Dialog dialog;

    public ShareView(Context context, ShareManager.ShareContent shareContent) {
        this.context = context;
        this.shareContent = shareContent;
    }

    public void setDialog(Dialog dialog) {
        this.dialog = dialog;
    }

//    public static ShareView newInstance(Context context, ShareManager.ShareContent shareContent) {
//        if (instance == null) {
//            synchronized (ShareView.class) {
//                if (instance == null)
//                    instance = new ShareView(context, shareContent);
//            }
//        }
//        return instance;
//    }

    public View createView() {
        View view = View.inflate(context, R.layout.dl_share, null);
        TextView btn_share_wx = (TextView) view.findViewById(R.id.share_wx);
        TextView btn_share_circle = (TextView) view.findViewById(R.id.share_circle);

        btn_share_wx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShareManager.getInstance(context).shareByWebchat(shareContent, ShareManager.WECHAT_SHARE_TYPE_TALK);
                dialog.dismiss();
            }
        });

        btn_share_circle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShareManager.getInstance(context).shareByWebchat(shareContent, ShareManager.WECHAT_SHARE_TYPE_FRENDS);
                dialog.dismiss();
            }
        });
        return view;
    }
}
